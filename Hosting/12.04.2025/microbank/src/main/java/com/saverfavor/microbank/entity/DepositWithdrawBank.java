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
public class DepositWithdrawBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String withdrawbalance;
    private int accountnumber;
    private String bankname;
    private int routingnumber;
    private int swiftcode;
    private int dipositwithdrawamount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestdate;
    // Automatically set the current date before persisting


    private String generatedOtp; // Add this

    @PrePersist
    protected void onCreate() {
        this.requestdate = new Date();
    }




    private boolean otpVerified = false;

    @Temporal(TemporalType.TIMESTAMP)
    private Date otpGeneratedTime;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User userRegistration;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "balanceId")
    private Balance balance;
}
