package com.alami.sofianhcodingtest.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "Transaction")
@Table(name = "transactions")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date transactionDate;

    private BigDecimal transactionDebit;

    private BigDecimal transactionCredit;

    private BigDecimal transactionBalance;

    @Enumerated(EnumType.ORDINAL)
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;
}
