package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.Balance;
import com.saverfavor.microbank.entity.ProfitReferralWithdrawalBank;
import com.saverfavor.microbank.entity.User;
import com.saverfavor.microbank.repository.BalanceRepository;
import com.saverfavor.microbank.repository.ProfitReferralWithdrawalBankRepository;
import com.saverfavor.microbank.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProfitReferralWithdrawalBankService {

    @Autowired
    private ProfitReferralWithdrawalBankRepository withdrawalRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    /**
     * Create a withdrawal request with OTP generation.
     */
    public ProfitReferralWithdrawalBank createWithdrawalRequest(long userId, int balanceId, ProfitReferralWithdrawalBank withdrawalRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Balance balance = balanceRepository.findById(balanceId)
                .orElseThrow(() -> new RuntimeException("Balance not found with ID: " + balanceId));

        // Set user and balance reference
        withdrawalRequest.setUserRegistration(user);
        withdrawalRequest.setBalance(balance);

        // Generate and set OTP
        String otp = generateOtp();
        withdrawalRequest.setGeneratedOtp(otp);
        withdrawalRequest.setOtpVerified(false);
        withdrawalRequest.setOtpGeneratedTime(new Date());

        // Save to DB
        return withdrawalRepo.save(withdrawalRequest);
    }

    /**
     * Verify OTP and mark the withdrawal as verified.
     */
    public boolean verifyOtp(int withdrawalId, String enteredOtp) {
        Optional<ProfitReferralWithdrawalBank> optional = withdrawalRepo.findById(withdrawalId);

        if (optional.isEmpty()) {
            throw new RuntimeException("Withdrawal not found");
        }

        ProfitReferralWithdrawalBank request = optional.get();

        if (request.getGeneratedOtp().equals(enteredOtp)) {
            request.setOtpVerified(true);
            withdrawalRepo.save(request);
            return true;
        }

        return false;
    }

    /**
     * Get all withdrawals for a user.
     */
    public List<ProfitReferralWithdrawalBank> getWithdrawalsByUser(int userId) {
        return withdrawalRepo.findAll().stream()
                .filter(w -> w.getUserRegistration().getId() == userId)
                .toList();
    }

    /**
     * Helper method to generate 6-digit OTP
     */
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

}
