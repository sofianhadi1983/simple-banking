package com.alami.sofianhcodingtest.services;

import com.alami.sofianhcodingtest.dtos.CreateMemberResponseDTO;
import com.alami.sofianhcodingtest.dtos.CreateMemberResquestDTO;

public interface IMemberService {
    CreateMemberResponseDTO createNewMember(CreateMemberResquestDTO resquestDTO);
}
