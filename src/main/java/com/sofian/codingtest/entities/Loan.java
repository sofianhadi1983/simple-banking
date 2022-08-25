package com.sofian.codingtest.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "Loan")
@Table(name = "loans")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    private BigDecimal loanPayable;

    private BigDecimal loanPayment;

    private BigDecimal loanRemaining;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_owner")
    private Account loanOwner;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;
}
