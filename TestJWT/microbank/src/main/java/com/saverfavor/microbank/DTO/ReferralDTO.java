package com.saverfavor.microbank.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReferralDTO {
    private int id;
    private String referralCode;
    private String referredByCode;
     // The user registration details within the referral
}

