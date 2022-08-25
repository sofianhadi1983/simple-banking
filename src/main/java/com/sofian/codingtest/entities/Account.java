package com.sofian.codingtest.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "Account")
@Table(name = "accounts")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private BigDecimal totalDebit;

    private BigDecimal totalCredit;

    private BigDecimal endBalance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_owner")
    private Member accountOwner;

    @OneToOne(mappedBy = "loanOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Loan loan;

    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Transaction> transactions = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;
}
