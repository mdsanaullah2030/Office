package com.saverfavor.microbank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Setter
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

    @Column(columnDefinition = "boolean default false")
    private boolean emailSent;

    @Temporal(TemporalType.TIMESTAMP)
    private Date requestdate;
    // Automatically set the current date before persisting
    @PrePersist
    protected void onCreate() {
        this.requestdate = new Date();
    }



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User userRegistration;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "balanceId")
    private Balance balance;



}