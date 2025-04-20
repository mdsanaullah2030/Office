package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.*;
import com.saverfavor.microbank.repository.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CryptoDepositService {

    @Autowired
    private CryptoDepositRepository cryptoDepositRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private EmailService emailService;


  /// Get all transactions
    public List<CryptoDepositWithdrawal> getAllTransactions() {
        return cryptoDepositRepository.findAll();
    }


    /// Get all transactions find by ID
    public Optional<CryptoDepositWithdrawal> getTransactionById(int id) {
        return cryptoDepositRepository.findById(id);
    }


    //User Id loan data get//
    public List<CryptoDepositWithdrawal> getCryptoDepositByUserId(long userId) {
        return cryptoDepositRepository.findByUserRegistrationId(userId);
    }



    @Transactional
    public String saveTransaction(CryptoDepositWithdrawal transaction, String userEnteredOtp) {
        long userId = transaction.getUserRegistration().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Balance latestBalance = balanceRepository
                .findTopByUserRegistrationIdOrderByDateDesc(userId)
                .orElseThrow(() -> new RuntimeException("No balance record found for this user."));

        double availableWithdrawBalance = latestBalance.getDipositwithdra();
        double withdrawAmount = transaction.getAmount();

        if (withdrawAmount > availableWithdrawBalance) {
            throw new RuntimeException("Withdraw amount exceeds available balance.");
        }

        // Set references
        transaction.setUserRegistration(user);
        transaction.setBalance(latestBalance);

        // Set current available withdraw balance snapshot
        transaction.setWithdrawbalance(String.valueOf(availableWithdrawBalance));

        // Update dipositB and dipositwithdra
        latestBalance.setDipositB(latestBalance.getDipositB() - withdrawAmount);
        latestBalance.setDipositwithdra(availableWithdrawBalance - withdrawAmount);
        balanceRepository.save(latestBalance);

        // Generate OTP and save it
        String otp = generateOtp();
        transaction.setGeneratedOtp(otp);
        transaction.setOtpVerified(false);
        transaction.setOtpGeneratedTime(new Date());

        cryptoDepositRepository.save(transaction);

        // Send OTP email
        try {
            emailService.sendSimpleEmail(
                    user.getEmail(),
                    "Withdrawal OTP Verification",
                    "Dear " + user.getName() + ",\n\nYour OTP for crypto withdrawal is: " + otp +
                            "\n\nUse this OTP to confirm your transaction.\n\nThank you."
            );
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email.");
        }

        return "OTP sent to your email.";
    }

    // Helper method to generate 6-digit OTP
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }


    @Transactional
    public String verifyOtp(int transactionId, String userEnteredOtp) {
        CryptoDepositWithdrawal transaction = cryptoDepositRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found."));

        if (transaction.isOtpVerified()) {
            return "This transaction is already verified.";
        }

        if (!userEnteredOtp.equals(transaction.getGeneratedOtp())) {
            throw new RuntimeException("Invalid OTP.");
        }

        long elapsedTime = new Date().getTime() - transaction.getOtpGeneratedTime().getTime();
        if (elapsedTime > 5 * 60 * 1000) { // 5 minutes
            throw new RuntimeException("OTP has expired.");
        }

        transaction.setOtpVerified(true);

        // Recalculate and update package if needed
        Balance balance = transaction.getBalance();
        double updatedDipositB = balance.getDipositB();

        if (updatedDipositB <= 100) {
            balance.setPackages("1");
        } else if (updatedDipositB <= 500) {
            balance.setPackages("2");
        } else if (updatedDipositB <= 1000) {
            balance.setPackages("3");
        } else {
            balance.setPackages("4");
        }

        balanceRepository.save(balance);
        cryptoDepositRepository.save(transaction);

        return "OTP verified and withdrawal confirmed successfully.";
    }


}
