package com.sofian.codingtest.repositories;

import com.sofian.codingtest.entities.Account;
import com.sofian.codingtest.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT CAST(SUM(t.transactionDebit) AS big_decimal) FROM Transaction t WHERE t.account.accountId = :#{#account.accountId}")
    BigDecimal calculateTotalDebitByAccount(@Param("account") Account account);

    @Query("SELECT CAST(SUM(t.transactionCredit) AS big_decimal) FROM Transaction t WHERE t.account.accountId = :#{#account.accountId}")
    BigDecimal calculateTotalCreditByAccount(@Param("account") Account account);

    @Query("SELECT CAST(SUM(t.transactionDebit) AS big_decimal) FROM Transaction t WHERE t.account.accountId = :#{#account.accountId} AND t.transactionType = 'LOAN_PAYMENT'")
    BigDecimal calculateLoanPaymentByAccount(@Param("account") Account account);

    @Query("SELECT CAST(SUM(t.transactionCredit) AS big_decimal) FROM Transaction t WHERE t.account.accountId = :#{#account.accountId} AND t.transactionType = 'LOAN'")
    BigDecimal calculateLoanPayableByAccount(@Param("account") Account account);

    List<Transaction> findAllByAccountAccountIdOrderByTransactionDateAsc(Long memberId);
}
