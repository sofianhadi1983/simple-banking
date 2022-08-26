package com.sofian.codingtest.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sofian.codingtest.entities.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CreateTransactionRequestDTO implements Serializable {
    @JsonProperty("transaction_amount")
    private BigDecimal transactionAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("transaction_date")
    private Date transactionDate;

    @JsonProperty("transaction_type")
    private TransactionType transactionType;
}
