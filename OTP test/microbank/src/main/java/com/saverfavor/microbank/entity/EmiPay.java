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
public class EmiPay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int accountnumber;
    private String bankname;
    private int routingnumber;
    private int swiftcode;
    private  int eminumber;
    private int emiamount;



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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loanID")
    private Loan loan;

}
