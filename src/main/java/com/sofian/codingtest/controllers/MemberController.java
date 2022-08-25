package com.sofian.codingtest.controllers;

import com.sofian.codingtest.dtos.CreateMemberResponseDTO;
import com.sofian.codingtest.dtos.CreateMemberResquestDTO;
import com.sofian.codingtest.services.IMemberService;
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
        CreateMemberResponseDTO response = memberService.createNewMember(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // TODO - create get all member
    

}
