package com.sofian.codingtest.services;

import com.sofian.codingtest.dtos.CreateMemberResponseDTO;
import com.sofian.codingtest.dtos.CreateMemberResquestDTO;

import java.util.List;

public interface IMemberService {
    CreateMemberResponseDTO createNewMember(CreateMemberResquestDTO resquestDTO);
    List<CreateMemberResponseDTO> getAllMembers();
}
