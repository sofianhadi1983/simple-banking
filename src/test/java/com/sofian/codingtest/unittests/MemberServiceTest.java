package com.sofian.codingtest.unittests;

import com.sofian.codingtest.dtos.CreateMemberResponseDTO;
import com.sofian.codingtest.dtos.CreateMemberResquestDTO;
import com.sofian.codingtest.entities.Account;
import com.sofian.codingtest.entities.Loan;
import com.sofian.codingtest.entities.Member;
import com.sofian.codingtest.exceptions.DataAlreadyExistException;
import com.sofian.codingtest.exceptions.ValidationErrorException;
import com.sofian.codingtest.repositories.AccountRepository;
import com.sofian.codingtest.repositories.LoanRepository;
import com.sofian.codingtest.repositories.MemberRepository;
import com.sofian.codingtest.services.MemberService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MemberServiceTest {
    @Mock
    MemberRepository memberRepository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    LoanRepository loanRepository;

    ModelMapper modelMapper = spy(new ModelMapper());

    @InjectMocks
    MemberService serviceUnderTest = spy(new MemberService());

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenValidRequest_whenCreateNewMember_thenShouldBeCreated() {
        CreateMemberResquestDTO requestDTO = new CreateMemberResquestDTO();
        requestDTO.setNik("3304062207870003");
        requestDTO.setName("Abdullah");
        requestDTO.setAddress("Pondokgede");

        Date dob = Date.from(LocalDate.of(1989, 7, 23)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        requestDTO.setDob(dob);

        when(memberRepository.findByNik(anyString()))
                .thenReturn(Optional.empty());

        Member member = modelMapper.map(requestDTO, Member.class);
        member.setMemberId(1L);
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

        when(memberRepository.save(any(Member.class)))
                .thenReturn(member);

        CreateMemberResponseDTO responseDTO = serviceUnderTest.createNewMember(requestDTO);
        assertThat(responseDTO.getMemberId()).isNotNull();
        assertThat(responseDTO.getNik()).isEqualTo(requestDTO.getNik());
        assertThat(responseDTO.getName()).isEqualTo(requestDTO.getName());
        assertThat(responseDTO.getAddress()).isEqualTo(requestDTO.getAddress());
    }

    @Test(expected = DataAlreadyExistException.class)
    public void givenValidRequestAndMemberAlreadyExists_whenCreateNewMember_thenShouldThrowException() {
        CreateMemberResquestDTO requestDTO = new CreateMemberResquestDTO();
        requestDTO.setNik("3304062207870003");
        requestDTO.setName("Abdullah");
        requestDTO.setAddress("Pondokgede");

        Date dob = Date.from(LocalDate.of(1989, 7, 23)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        requestDTO.setDob(dob);

        Member member = modelMapper.map(requestDTO, Member.class);
        member.setMemberId(1L);

        when(memberRepository.findByNik(anyString()))
                .thenReturn(Optional.of(member));

        serviceUnderTest.createNewMember(requestDTO);
    }

    @Test(expected = ValidationErrorException.class)
    public void givenNullRequest_whenCreateNewMember_thenShouldThrowException() {
        serviceUnderTest.createNewMember(null);
    }

    @Test(expected = ValidationErrorException.class)
    public void givenRequestWithEmptyNik__whenCreateNewMember_thenShouldThrowException() {
        CreateMemberResquestDTO requestDTO = new CreateMemberResquestDTO();
        requestDTO.setNik("");
        requestDTO.setName("Abdullah");
        requestDTO.setAddress("Pondokgede");

        Date dob = Date.from(LocalDate.of(1989, 7, 23)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        requestDTO.setDob(dob);

        serviceUnderTest.createNewMember(requestDTO);
    }

    @Test(expected = ValidationErrorException.class)
    public void givenRequestWithEmptyName__whenCreateNewMember_thenShouldThrowException() {
        CreateMemberResquestDTO requestDTO = new CreateMemberResquestDTO();
        requestDTO.setNik("3304062207870003");
        requestDTO.setName("");
        requestDTO.setAddress("Pondokgede");

        Date dob = Date.from(LocalDate.of(1989, 7, 23)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        requestDTO.setDob(dob);

        serviceUnderTest.createNewMember(requestDTO);
    }

    @Test(expected = ValidationErrorException.class)
    public void givenRequestWithEmptyAddress__whenCreateNewMember_thenShouldThrowException() {
        CreateMemberResquestDTO requestDTO = new CreateMemberResquestDTO();
        requestDTO.setNik("3304062207870003");
        requestDTO.setName("nandang");
        requestDTO.setAddress("");

        Date dob = Date.from(LocalDate.of(1989, 7, 23)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        requestDTO.setDob(dob);

        serviceUnderTest.createNewMember(requestDTO);
    }

    @Test(expected = ValidationErrorException.class)
    public void givenRequestWithInvalidDob__whenCreateNewMember_thenShouldThrowException() {
        CreateMemberResquestDTO requestDTO = new CreateMemberResquestDTO();
        requestDTO.setNik("3304062207870003");
        requestDTO.setName("nandang");
        requestDTO.setAddress("Pondokgede");

        Date dob = Date.from(LocalDate.of(2200, 7, 23)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        requestDTO.setDob(dob);

        serviceUnderTest.createNewMember(requestDTO);
    }
}
