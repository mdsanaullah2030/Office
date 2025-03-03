package com.saverfavor.microbank.DTO;

import com.saverfavor.microbank.entity.UserRegistration;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReferralDTO {
    private int id;
    private String referralCode;
    private String referredByCode;
    private UserRegistration user;  // The user registration details within the referral
}

