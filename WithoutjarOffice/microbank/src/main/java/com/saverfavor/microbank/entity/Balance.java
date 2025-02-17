package com.saverfavor.microbank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private UserRegistration userRegistration;

    @ManyToOne(fetch =FetchType.EAGER )
    @JoinColumn(name = "referralId")
    private Referral referral;



}
