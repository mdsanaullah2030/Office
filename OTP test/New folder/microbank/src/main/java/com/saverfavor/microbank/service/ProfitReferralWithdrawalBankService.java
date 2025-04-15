package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.Balance;
import com.saverfavor.microbank.entity.ProfitReferralWithdrawalBank;
import com.saverfavor.microbank.entity.User;
import com.saverfavor.microbank.repository.BalanceRepository;
import com.saverfavor.microbank.repository.ProfitReferralWithdrawalBankRepository;
import com.saverfavor.microbank.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class ProfitReferralWithdrawalBankService {

    @Autowired
    private ProfitReferralWithdrawalBankRepository withdrawalRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    /**
     * Creates a withdrawal request, subtracts from profitB, sends OTP.
     */
    public String saveWithdrawalRequest(ProfitReferralWithdrawalBank request) {
        long userId = request.getUserRegistration().getId();

        // 1. Get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        request.setUserRegistration(user);

        // 2. Check cooldown (3 mins)
        Optional<ProfitReferralWithdrawalBank> lastRequestOpt =
                withdrawalRepository.findTopByUserRegistrationIdOrderByRequestdateDesc(userId);

        if (lastRequestOpt.isPresent()) {
            long diff = new Date().getTime() - lastRequestOpt.get().getRequestdate().getTime();
            if (diff < 3 * 60 * 1000) {
                return "You must wait at least 3 minutes between withdrawals.";
            }
        }

        // 3. Get balance record
        Balance balance = balanceRepository
                .findTopByUserRegistrationIdOrderByIdDesc(userId)
                .orElseThrow(() -> new RuntimeException("Balance not found"));

        double currentProfitB = balance.getProfitB();
        double withdrawAmount = request.getWithdrawamount();

        if (withdrawAmount > currentProfitB) {
            return "Insufficient profit balance.";
        }

        // 4. Set withdrawbalance to current profitB
        request.setWithdrawbalance(currentProfitB);

        // 5. Subtract withdraw amount from profitB and save
        balance.setProfitB(currentProfitB - withdrawAmount);
        balanceRepository.save(balance);

        // 6. Generate OTP
        String otp = generateOtp();
        request.setGeneratedOtp(otp);
        request.setOtpVerified(false);
        request.setOtpGeneratedTime(new Date());
        request.setBalance(balance);

        withdrawalRepository.save(request);

        // 7. Send email
        try {
            emailService.sendSimpleEmail(
                    user.getEmail(),
                    "Withdrawal OTP Verification",
                    "Dear " + user.getName() + ",\n\n" +
                            "Your OTP for withdrawal: " + otp + "\n\n" +
                            "Thanks,\nSaverFavor Microbank"
            );
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email.");
        }

        return "Withdrawal request submitted successfully!";
    }


    public void syncWithdrawBalanceWithProfitB() {
        // For all users' latest balance
        var balances = balanceRepository.findLatestBalanceForAllUsers();
        for (Balance balance : balances) {
            User user = balance.getUserRegistration();
            Optional<ProfitReferralWithdrawalBank> latestWithdrawalOpt =
                    withdrawalRepository.findTopByUserRegistrationIdOrderByRequestdateDesc(user.getId());

            if (latestWithdrawalOpt.isPresent()) {
                ProfitReferralWithdrawalBank withdrawal = latestWithdrawalOpt.get();
                withdrawal.setWithdrawbalance(balance.getProfitB());
                withdrawalRepository.save(withdrawal);
            }
        }
    }

    private String generateOtp() {
        Random r = new Random();
        return String.valueOf(100000 + r.nextInt(900000));
    }
}
