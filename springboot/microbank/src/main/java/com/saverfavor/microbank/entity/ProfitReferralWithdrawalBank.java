package com.saverfavor.microbank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfitReferralWithdrawalBank {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Double withdrawbalance;
    private int accountnumber;
    private String bankname;
    private int routingnumber;
    private int swiftcode;
    private Double withdrawamount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestdate;
    // Automatically set the current date before persisting
    @PrePersist
    protected void onCreate() {
        this.requestdate = new Date();
    }



    private String generatedOtp; // Add this

    private Boolean otpVerified;


    @Temporal(TemporalType.TIMESTAMP)
    private Date otpGeneratedTime;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User userRegistration;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "balanceId")
    private Balance balance;



}







