package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.Balance;
import com.saverfavor.microbank.entity.CryptoProfitWithdrawal;
import com.saverfavor.microbank.entity.User;
import com.saverfavor.microbank.repository.BalanceRepository;
import com.saverfavor.microbank.repository.CryptoProfitRepository;
import com.saverfavor.microbank.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CryptoProfitService {

    @Autowired
    private CryptoProfitRepository cryptoProfitRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;




        // Get all transactions
        public List<CryptoProfitWithdrawal> getAllTransactions() {
            return cryptoProfitRepository.findAll();
        }

        // Get transaction by ID
        public Optional<CryptoProfitWithdrawal> getProfitById(int id) {
            return cryptoProfitRepository.findById(id);
        }


        //User Id loan data get//
        public List<CryptoProfitWithdrawal> getProfitReferraWithdrawBank(long userId) {
            return cryptoProfitRepository.findByUserRegistrationId(userId);
        }








    public String saveWithdrawalRequest(CryptoProfitWithdrawal request) {
        long userId = request.getUserRegistration().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        request.setUserRegistration(user);

        Optional<CryptoProfitWithdrawal> lastRequestOpt =
                cryptoProfitRepository.findTopByUserRegistrationIdOrderByRequestdateDesc(userId);

        if (lastRequestOpt.isPresent()) {
            long diff = new Date().getTime() - lastRequestOpt.get().getRequestdate().getTime();
            if (diff < 3 * 60 * 1000) {
                return "You must wait at least 3 minutes between withdrawals.";
            }
        }

        Balance balance = balanceRepository
                .findTopByUserRegistrationIdOrderByIdDesc(userId)
                .orElseThrow(() -> new RuntimeException("Balance not found"));

        double currentProfitB = balance.getProfitB();
        double withdrawAmount = request.getWithdrawamount();

        if (withdrawAmount > currentProfitB) {
            return "Insufficient profit balance.";
        }

        request.setWithdrawbalance(String.valueOf(currentProfitB));
        balance.setProfitB(currentProfitB - withdrawAmount);
        balanceRepository.save(balance);

        String otp = generateOtp();
        request.setGeneratedOtp(otp);
        request.setOtpVerified(false);
        request.setOtpGeneratedTime(new Date());
        request.setBalance(balance);

        cryptoProfitRepository.save(request);

        try {
            emailService.sendSimpleEmail(
                    user.getEmail(),
                    "Withdrawal OTP Verification",
                    "Dear " + user.getName() + ",\n\nYour OTP for withdrawal: " + otp +
                            "\n\nThanks,\nSaverFavor Microbank"
            );
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email.");
        }

        return "Withdrawal request submitted successfully!";
    }

    public boolean verifyOtp(Long userId, String otp) {
        Optional<CryptoProfitWithdrawal> latestOpt =
                cryptoProfitRepository.findTopByUserRegistrationIdOrderByRequestdateDesc(userId);

        if (latestOpt.isEmpty()) {
            throw new RuntimeException("No withdrawal request found for user.");
        }

        CryptoProfitWithdrawal latest = latestOpt.get();

        if (latest.isOtpVerified()) {
            throw new RuntimeException("OTP already verified.");
        }

        if (!latest.getGeneratedOtp().equals(otp)) {
            return false;
        }

        long timeDiff = new Date().getTime() - latest.getOtpGeneratedTime().getTime();
        if (timeDiff > 5 * 60 * 1000) {
            throw new RuntimeException("OTP has expired. Please request a new withdrawal.");
        }

        latest.setOtpVerified(true);
        cryptoProfitRepository.save(latest);

        return true;
    }

    private String generateOtp() {
        Random r = new Random();
        return String.valueOf(100000 + r.nextInt(900000));
    }
}
