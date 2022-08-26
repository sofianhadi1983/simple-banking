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
public class TransactionLogDTO implements Serializable {
    @JsonProperty("transaction_id")
    private Long transactionId;

    @JsonProperty("transaction_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date transactionDate;

    @JsonProperty("transaction_debit")
    private BigDecimal transactionDebit;

    @JsonProperty("transaction_credit")
    private BigDecimal transactionCredit;

    @JsonProperty("transaction_balance")
    private BigDecimal transactionBalance;

    @JsonProperty("transaction_type")
    private TransactionType transactionType;
}
