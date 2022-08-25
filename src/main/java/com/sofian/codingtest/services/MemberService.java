package com.sofian.codingtest.services;

import com.sofian.codingtest.dtos.CreateMemberResponseDTO;
import com.sofian.codingtest.dtos.CreateMemberResquestDTO;
import com.sofian.codingtest.entities.Account;
import com.sofian.codingtest.entities.Loan;
import com.sofian.codingtest.entities.Member;
import com.sofian.codingtest.exceptions.ValidationErrorException;
import com.sofian.codingtest.repositories.MemberRepository;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.*;
import java.util.Date;

@Service
public class MemberService implements IMemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ModelMapper modelMapper;

    @SneakyThrows
    @Override
    @Transactional
    public CreateMemberResponseDTO createNewMember(CreateMemberResquestDTO resquestDTO) {
        isValidDob(resquestDTO.getDob());
        // TODO cek nik

        Loan loanRecord = new Loan();
        loanRecord.setLoanPayable(BigDecimal.ZERO);
        loanRecord.setLoanPayment(BigDecimal.ZERO);
        loanRecord.setLoanRemaining(BigDecimal.ZERO);

        Account account = new Account();
        account.setTotalDebit(BigDecimal.ZERO);
        account.setTotalCredit(BigDecimal.ZERO);
        account.setEndBalance(BigDecimal.ZERO);
        account.setLoan(loanRecord);

        Member member = convertToEntity(resquestDTO);
        member.setAccount(account);

        Member createdMember = memberRepository.save(member);

        return convertToDto(createdMember);
    }

    private void isValidDob(Date date) {
        LocalDate dob = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();

        if (dob.getYear() > now.getYear()) {
            throw new ValidationErrorException("Invalid year in dob.");
        }
    }

    private Member convertToEntity(CreateMemberResquestDTO resquestDTO) throws ParseException {
         return modelMapper.map(resquestDTO, Member.class);
    }

    private CreateMemberResponseDTO convertToDto(Member member) throws ParseException {
        return modelMapper.map(member, CreateMemberResponseDTO.class);
    }
}
