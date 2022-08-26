package com.sofian.codingtest.controllers;

import com.sofian.codingtest.dtos.TransactionLogDTO;
import com.sofian.codingtest.services.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    @Autowired
    ITransactionService transactionService;

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionLogDTO>> getTransactionByMember(
            @RequestParam("start") @DateTimeFormat(pattern = "dd-MM-yyyy") Date start,
            @RequestParam("end") @DateTimeFormat(pattern = "dd-MM-yyyy") Date end) {
        List<TransactionLogDTO> responses = transactionService.getTransactionsByPeriod(start, end);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
