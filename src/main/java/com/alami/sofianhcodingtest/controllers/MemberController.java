package com.alami.sofianhcodingtest.controllers;

import com.alami.sofianhcodingtest.dtos.CreateMemberResponseDTO;
import com.alami.sofianhcodingtest.dtos.CreateMemberResquestDTO;
import com.alami.sofianhcodingtest.services.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    IMemberService memberService;

    @PostMapping("/api/v1/members")
    public ResponseEntity<CreateMemberResponseDTO> createNewMember(@RequestBody CreateMemberResquestDTO request) {
        // TODO validate dob - isInFuture
        CreateMemberResponseDTO response = memberService.createNewMember(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    // TODO implement endpoint with response entity construct
    // TODO implement controller advice in case validation failed
}
