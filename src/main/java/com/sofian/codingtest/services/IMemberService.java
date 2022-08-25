package com.sofian.codingtest.services;

import com.sofian.codingtest.dtos.CreateMemberResponseDTO;
import com.sofian.codingtest.dtos.CreateMemberResquestDTO;

public interface IMemberService {
    CreateMemberResponseDTO createNewMember(CreateMemberResquestDTO resquestDTO);
}
