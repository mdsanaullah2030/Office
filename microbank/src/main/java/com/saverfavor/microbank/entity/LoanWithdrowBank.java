package com.saverfavor.microbank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanWithdrowBank {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 private String accountName;
 private int accountNumber;
 private String bankName;
 private int routingNumber;
 private int swiftCode;
 private int amountLoan;

 @ManyToOne(fetch = FetchType.EAGER)
 @JoinColumn(name = "userId", nullable = false)
 private UserRegistration userRegistration;

 @ManyToOne(fetch = FetchType.EAGER)
 @JoinColumn(name = "loanId", nullable = false)
 private Loan loan;
}
