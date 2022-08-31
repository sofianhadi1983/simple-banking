package com.sofian.codingtest.services;

import com.sofian.codingtest.dtos.CreateTransactionRequestDTO;
import com.sofian.codingtest.dtos.TransactionLogDTO;
import com.sofian.codingtest.entities.*;
import com.sofian.codingtest.exceptions.ResourceNotFoundException;
import com.sofian.codingtest.exceptions.ValidationErrorException;
import com.sofian.codingtest.repositories.AccountRepository;
import com.sofian.codingtest.repositories.LoanRepository;
import com.sofian.codingtest.repositories.MemberRepository;
import com.sofian.codingtest.repositories.TransactionRepository;
import com.sofian.codingtest.utils.BankUtils;
import org.apache.commons.lang3.EnumUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TransactionService implements ITransactionService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public TransactionLogDTO createNewTransaction(Long memberId, CreateTransactionRequestDTO requestDTO) {
        validate(requestDTO);
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (!memberOptional.isPresent())
            throw new ResourceNotFoundException("Member not found. Please check your corresponding member Id!");

        Member member = memberOptional.get();
        Account account = member.getAccount();

        isBalanceSufficient(requestDTO, account);

        Transaction createdTransaction = createTransactionJournal(requestDTO, account);
        createBalanceSummary(account);

        if (TransactionType.LOAN.equals(requestDTO.getTransactionType()) ||
                TransactionType.LOAN_PAYMENT.equals(requestDTO.getTransactionType())) {
            createLoanSummary(account);
        }
        TransactionLogDTO dto = modelMapper.map(createdTransaction, TransactionLogDTO.class);
        return modelMapper.map(createdTransaction, TransactionLogDTO.class);
    }

    @Override
    public List<TransactionLogDTO> getTransactionByMember(Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (!memberOptional.isPresent())
            throw new ResourceNotFoundException("Member not found. Please check your corresponding member Id!");
        Member member = memberOptional.get();
        Account account = member.getAccount();

        List<Transaction> transactions = transactionRepository.findAllByAccountAccountIdOrderByTransactionDateAsc(
                account.getAccountId());
        if (!transactions.isEmpty()) {
            return transactions.stream()
                    .map(transaction -> modelMapper.map(transaction, TransactionLogDTO.class))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    @Override
    public List<TransactionLogDTO> getTransactionsByPeriod(Date start, Date end) {
        List<Transaction> transactions = transactionRepository.findAllByTransactionDateBetweenOrderByTransactionDateAsc(
                start, end);
        if (!transactions.isEmpty()) {
            return transactions.stream()
                    .map(transaction -> modelMapper.map(transaction, TransactionLogDTO.class))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    private Transaction createTransactionJournal(CreateTransactionRequestDTO requestDTO, Account account) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(requestDTO.getTransactionType());
        transaction.setTransactionDate(requestDTO.getTransactionDate());
        BigDecimal startBalance = account.getEndBalance();
        BigDecimal amount = requestDTO.getTransactionAmount();
        BigDecimal endBalance;
        if (TransactionType.SAVING.equals(requestDTO.getTransactionType()) ||
                TransactionType.LOAN.equals(requestDTO.getTransactionType())) {
            transaction.setTransactionDebit(BigDecimal.ZERO);
            transaction.setTransactionCredit(requestDTO.getTransactionAmount());
            endBalance = startBalance.add(amount);
        } else if (TransactionType.WITHDRAWAL.equals(requestDTO.getTransactionType()) ||
                TransactionType.LOAN_PAYMENT.equals(requestDTO.getTransactionType())) {
            transaction.setTransactionDebit(requestDTO.getTransactionAmount());
            transaction.setTransactionCredit(BigDecimal.ZERO);
            endBalance = startBalance.subtract(amount);
        } else {
            throw new ValidationErrorException("Transaction type is not valid!");
        }
        transaction.setTransactionBalance(endBalance);
        transaction.setAccount(account);

        return transactionRepository.save(transaction);
    }

    private Account createBalanceSummary(Account account) {
        BigDecimal totalTransactionDebitSoFar = transactionRepository.calculateTotalDebitByAccount(account);
        BigDecimal totalTransactionCreditSoFar = transactionRepository.calculateTotalCreditByAccount(account);

        if (totalTransactionDebitSoFar == null)
            totalTransactionDebitSoFar = BigDecimal.ZERO;
        if (totalTransactionCreditSoFar == null)
            totalTransactionCreditSoFar = BigDecimal.ZERO;

        BigDecimal totalEndBalanceSoFar = totalTransactionCreditSoFar.subtract(totalTransactionDebitSoFar);
        account.setTotalDebit(totalTransactionDebitSoFar);
        account.setTotalCredit(totalTransactionCreditSoFar);
        account.setEndBalance(totalEndBalanceSoFar);

        return accountRepository.save(account);
    }

    private Loan createLoanSummary(Account account) {
        Loan loan = account.getLoan();
        BigDecimal totalLoanPayableSoFar = transactionRepository.calculateLoanPayableByAccount(account);
        BigDecimal totalLoanPaymentSoFar = transactionRepository.calculateLoanPaymentByAccount(account);

        if (totalLoanPayableSoFar == null)
            totalLoanPayableSoFar = BigDecimal.ZERO;

        if (totalLoanPaymentSoFar == null)
            totalLoanPaymentSoFar = BigDecimal.ZERO;

        BigDecimal totalLoanRemaining = totalLoanPayableSoFar.subtract(totalLoanPaymentSoFar);
        loan.setLoanPayable(totalLoanPayableSoFar);
        loan.setLoanPayment(totalLoanPaymentSoFar);
        loan.setLoanRemaining(totalLoanRemaining);

        return loanRepository.save(loan);
    }

    private void validate(CreateTransactionRequestDTO requestDTO) {
        if (!EnumUtils.isValidEnum(TransactionType.class, requestDTO.getTransactionType().toString())) {
            throw new ValidationErrorException("Transaction type invalid! Correct value: SAVING, WITHDRAWAL, LOAN, LOAN_PAYMENT");
        }

        if (BankUtils.isFutureDate(requestDTO.getTransactionDate())) {
            throw new ValidationErrorException("Transaction date invalid!");
        }

        if (requestDTO.getTransactionAmount().signum() == -1) {
            throw new ValidationErrorException("Transaction amount must be positive!");
        }
    }

    private void isBalanceSufficient(CreateTransactionRequestDTO requestDTO, Account account) {
        if (TransactionType.WITHDRAWAL.equals(requestDTO.getTransactionType()) ||
                TransactionType.LOAN_PAYMENT.equals(requestDTO.getTransactionType())) {
            BigDecimal currentBalance = account.getEndBalance();
            if (currentBalance.compareTo(requestDTO.getTransactionAmount()) == -1) {
                throw new ValidationErrorException("Insufficient balance for this operation.");
            }
        }
    }
}
