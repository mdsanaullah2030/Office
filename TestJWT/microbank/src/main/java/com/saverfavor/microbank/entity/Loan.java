package com.saverfavor.microbank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int eligeblebalancey;
    private int loanamuont;
    private double weeklypay;
    private  double totalpay;
    private int tenure;
    private  String status;
    private Date requestdate;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User userRegistration;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "balanceId")
    private Balance balance;



}