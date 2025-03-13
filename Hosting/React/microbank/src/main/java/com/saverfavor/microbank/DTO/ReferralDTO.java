package com.saverfavor.microbank.DTO;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ReferralDTO {
    private int id;
    private String referralCode;
    private String referredByCode;
     // The user registration details within the referral
}

