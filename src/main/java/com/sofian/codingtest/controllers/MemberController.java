package com.sofian.codingtest.controllers;

import com.sofian.codingtest.dtos.CreateMemberResponseDTO;
import com.sofian.codingtest.dtos.CreateMemberResquestDTO;
import com.sofian.codingtest.dtos.CreateTransactionRequestDTO;
import com.sofian.codingtest.dtos.TransactionLogDTO;
import com.sofian.codingtest.services.IMemberService;
import com.sofian.codingtest.services.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MemberController {

    @Autowired
    IMemberService memberService;

    @Autowired
    ITransactionService transactionService;

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

    @PostMapping("/members/{memberId}/transactions")
    public ResponseEntity<Object> createNewTransaction(@PathVariable("memberId") Long memberId,
                                                       @RequestBody CreateTransactionRequestDTO request) {
        TransactionLogDTO response = transactionService.createNewTransaction(memberId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
