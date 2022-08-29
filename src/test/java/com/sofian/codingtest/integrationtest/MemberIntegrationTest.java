package com.sofian.codingtest.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofian.codingtest.dtos.CreateMemberResquestDTO;
import com.sofian.codingtest.repositories.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberIntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    @Order(1)
    @WithMockUser(username = "nandangs", password = "password", roles={"ADMIN"})
    void givenValidMemberRequest_WhenCreateMember_ThenReturnCreatedMemberWithActualID() throws Exception {
        // given
        CreateMemberResquestDTO requestDTO = new CreateMemberResquestDTO();
        requestDTO.setNik("3303061708930001");
        requestDTO.setName("Nandang");
        requestDTO.setAddress("Bogor");

        Date dob = Date.from(LocalDate.now().atStartOfDay(ZoneId.of("Asia/Jakarta")).toInstant());
        requestDTO.setDob(dob);

        mockMvc.perform(post("/api/v1/members")
                .content(new ObjectMapper().writeValueAsString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.member_id").value(1))
                .andExpect(jsonPath("$.nik").value("3303061708930001"))
                .andExpect(jsonPath("$.name").value("Nandang"))
                .andExpect(jsonPath("$.address").value("Bogor"));
    }

    // this is a grouping
    @Nested
    class MemberTestSuites {
        // define your test suite
    }
}
