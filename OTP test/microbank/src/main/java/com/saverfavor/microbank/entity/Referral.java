package com.saverfavor.microbank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Referral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String referral;
    private String referredbycode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="userid")
    private User user;

}