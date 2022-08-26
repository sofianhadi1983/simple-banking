package com.sofian.codingtest.services;

import com.sofian.codingtest.dtos.CreateMemberResponseDTO;
import com.sofian.codingtest.dtos.CreateMemberResquestDTO;
import com.sofian.codingtest.entities.Account;
import com.sofian.codingtest.entities.Loan;
import com.sofian.codingtest.entities.Member;
import com.sofian.codingtest.exceptions.ValidationErrorException;
import com.sofian.codingtest.repositories.AccountRepository;
import com.sofian.codingtest.repositories.LoanRepository;
import com.sofian.codingtest.repositories.MemberRepository;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService implements IMemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ModelMapper modelMapper;

    @SneakyThrows
    @Override
    @Transactional
    public CreateMemberResponseDTO createNewMember(CreateMemberResquestDTO resquestDTO) {
        validate(resquestDTO);

        Member member = convertToEntity(resquestDTO);

        Loan loanRecord = new Loan();
        loanRecord.setLoanPayable(BigDecimal.ZERO);
        loanRecord.setLoanPayment(BigDecimal.ZERO);
        loanRecord.setLoanRemaining(BigDecimal.ZERO);

        Account account = new Account();
        account.setTotalDebit(BigDecimal.ZERO);
        account.setTotalCredit(BigDecimal.ZERO);
        account.setEndBalance(BigDecimal.ZERO);

        loanRecord.setLoanOwner(account);
        account.setLoan(loanRecord);
        member.setAccount(account);
        account.setAccountOwner(member);

        Member createdMember = memberRepository.save(member);
        return convertToDto(createdMember);
    }

    @Override
    public List<CreateMemberResponseDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        if (!members.isEmpty()) {
            return members.stream()
                    .map(member -> modelMapper.map(member, CreateMemberResponseDTO.class))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private void isValidDob(Date date) {
        LocalDate dob = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();

        if (dob.getYear() > now.getYear()) {
            throw new ValidationErrorException("Invalid year in dob.");
        }
    }

    private void validate(CreateMemberResquestDTO resquestDTO) {
        if (resquestDTO == null) {
            throw new ValidationErrorException("Body request cannot be empty");
        }

        if (StringUtils.isEmpty(resquestDTO.getNik())) {
            throw new ValidationErrorException("nik cannot be empty");
        }

        if (StringUtils.isEmpty(resquestDTO.getName())) {
            throw new ValidationErrorException("name cannot be empty");
        }

        if (StringUtils.isEmpty(resquestDTO.getAddress())) {
            throw new ValidationErrorException("address cannot be empty");
        }

        isValidDob(resquestDTO.getDob());
    }

    private Member convertToEntity(CreateMemberResquestDTO resquestDTO) {
         return modelMapper.map(resquestDTO, Member.class);
    }

    private CreateMemberResponseDTO convertToDto(Member member) {
        return modelMapper.map(member, CreateMemberResponseDTO.class);
    }
}
