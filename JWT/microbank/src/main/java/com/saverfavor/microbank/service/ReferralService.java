package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.Referral;
import com.saverfavor.microbank.entity.UserRegistration;
import com.saverfavor.microbank.repository.ReferralRepository;
import com.saverfavor.microbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ReferralService {

    @Autowired
    private ReferralRepository referralRepository;

    @Autowired
    private UserRepository userRepository;

    // Fetch all referrals
    public List<Referral> getAllReferrals() {
        return referralRepository.findAll();
    }

    // Create referral entry when someone is invited
    public void createReferral(int inviterId, int inviteeId) {
        Optional<UserRegistration> inviterOpt = userRepository.findById(inviterId);
        Optional<UserRegistration> inviteeOpt = userRepository.findById(inviteeId);

        if (inviterOpt.isEmpty()) {
            throw new RuntimeException("Inviter not found!");
        }
        if (inviteeOpt.isEmpty()) {
            throw new RuntimeException("Invitee not found!");
        }

        UserRegistration inviter = inviterOpt.get();
        UserRegistration invitee = inviteeOpt.get();

        // Ensure inviter has a referral code, generate one if missing
        if (inviter.getReferralCode() == null || inviter.getReferralCode().isEmpty()) {
            inviter.setReferralCode(generateReferralNumber()); // Generate a referral code if none exists
            userRepository.save(inviter);
        }

        // Set the referral entry
        Referral referral = new Referral();
        referral.setUser(invitee);  // Invitee is the referred user
        referral.setReferredbycode(inviter.getReferralCode()); // Assign inviter's referral code
        referral.setReferral(generateReferralNumber()); // Generate a unique referral number

        referralRepository.save(referral);
    }

    // Generate a unique referral code
    private String generateReferralNumber() {
        int length = 6;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            codeBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return codeBuilder.toString();
    }
}
