package com.sofian.codingtest.services;

import com.sofian.codingtest.dtos.CreateTransactionRequestDTO;
import com.sofian.codingtest.dtos.TransactionLogDTO;

import java.util.List;

public interface ITransactionService {
    TransactionLogDTO createNewTransaction(Long memberId, CreateTransactionRequestDTO requestDTO);
    List<TransactionLogDTO> getTransactionByMember(Long memberId);
}
