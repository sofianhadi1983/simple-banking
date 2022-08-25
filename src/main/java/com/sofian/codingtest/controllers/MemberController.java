package com.sofian.codingtest.controllers;

import com.sofian.codingtest.dtos.CreateMemberResponseDTO;
import com.sofian.codingtest.dtos.CreateMemberResquestDTO;
import com.sofian.codingtest.services.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Validated
public class MemberController {

    @Autowired
    IMemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<CreateMemberResponseDTO> createNewMember(@RequestBody CreateMemberResquestDTO request) {
        CreateMemberResponseDTO response = memberService.createNewMember(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/members")
    public ResponseEntity<List<CreateMemberResponseDTO>> getAllMember() {
        List<CreateMemberResponseDTO> response = memberService.getAllMembers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
