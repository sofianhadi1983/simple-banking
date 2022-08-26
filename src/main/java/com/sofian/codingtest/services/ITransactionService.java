package com.sofian.codingtest.services;

import com.sofian.codingtest.dtos.CreateTransactionRequestDTO;
import com.sofian.codingtest.dtos.TransactionLogDTO;

public interface ITransactionService {
    TransactionLogDTO createNewTransaction(Long memberId, CreateTransactionRequestDTO requestDTO);
}
