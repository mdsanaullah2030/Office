package com.saverfavor.microbank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double addBalance;
    private double dipositB;
    private String packages;
    private double profitB;
    private double referralB;
    private double profitreferralwithdraw;
    private double dipositwithdra;
    private double profitwithdra;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    // Automatically set the current date before persisting
    @PrePersist
    protected void onCreate() {
        this.date = new Date();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private User userRegistration;

    @ManyToOne(fetch =FetchType.EAGER )
    @JoinColumn(name = "referralId")
    private Referral referral;



}