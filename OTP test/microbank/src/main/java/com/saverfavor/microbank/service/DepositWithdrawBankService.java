package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.Balance;
import com.saverfavor.microbank.entity.DepositWithdrawBank;
import com.saverfavor.microbank.entity.User;
import com.saverfavor.microbank.repository.BalanceRepository;
import com.saverfavor.microbank.repository.DepositWithdrawBankRepository;
import com.saverfavor.microbank.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DepositWithdrawBankService {

    @Autowired
    private DepositWithdrawBankRepository depositWithdrawBankRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private EmailService emailService;





    // Get all transactions
    public List<DepositWithdrawBank> getAllTransactions() {
        return depositWithdrawBankRepository.findAll();
    }

    // Get transaction by ID
    public Optional<DepositWithdrawBank> getTransactionById(int id) {
        return depositWithdrawBankRepository.findById(id);
    }


    //User Id loan data get//
    public List<DepositWithdrawBank> getDepositWithdrawBank(long userId) {
        return depositWithdrawBankRepository.findByUserRegistrationId(userId);
    }





    public String saveTransaction(DepositWithdrawBank transaction, String userEnteredOtp)
    {
        User user = transaction.getUserRegistration();
        if (user != null && user.getId() != 0) {
            user = userRepository.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            transaction.setUserRegistration(user);
        } else {
            throw new RuntimeException("Invalid user.");
        }

        // Always fetch the latest balance based on userId
        Balance latestBalance = balanceRepository
                .findTopByUserRegistrationIdOrderByDateDesc(user.getId())
                .orElseThrow(() -> new RuntimeException("No balance record found for this user."));

        double currentWithdrawAmount = latestBalance.getDipositwithdra();

        if (transaction.getDipositwithdrawamount() > currentWithdrawAmount) {
            throw new RuntimeException("Withdraw amount exceeds available balance: " + currentWithdrawAmount);
        }


//




        // Set balance and withdrawbalance in the transaction
        transaction.setBalance(latestBalance);
//        transaction.setWithdrawbalance(String.valueOf(currentWithdrawAmount));


        // Subtract the amount from dipositB and dipositwithdra
        double withdrawAmounts = transaction.getDipositwithdrawamount();
        latestBalance.setDipositB(latestBalance.getDipositB() - withdrawAmounts);
        latestBalance.setDipositwithdra(latestBalance.getDipositwithdra() - withdrawAmounts);
        balanceRepository.save(latestBalance);




        // Generate OTP
        String otp = generateOtp();
        transaction.setGeneratedOtp(otp);  // Save OTP in the transaction
        transaction.setOtpVerified(false);
        transaction.setOtpGeneratedTime(new Date());

        depositWithdrawBankRepository.save(transaction);

        try {
            emailService.sendSimpleEmail(
                    user.getEmail(),
                    "Withdrawal OTP Verification",
                    "Dear " + user.getName() + ",\n\n" +
                            "Your one-time password (OTP) for processing your withdrawal request on FINSYS is: " + otp + "\n\n" +
                            "Please use this OTP to complete the transaction.\n\n" +
                            "Thank you for choosing FINSYS.\n\n" +
                            "© Financial System Solutions"
            );
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email");
        }

        // If user enters the OTP, compare it and verify
        if (userEnteredOtp != null && userEnteredOtp.equals(transaction.getGeneratedOtp())) {
            long elapsedTime = new Date().getTime() - transaction.getOtpGeneratedTime().getTime();
            if (elapsedTime > 5 * 60 * 1000) {
                throw new RuntimeException("OTP has expired.");
            }

            // OTP is valid and within the expiry time
            transaction.setOtpVerified(true);

            // Deduct the balance after OTP verification
            Balance balance = transaction.getBalance();
            double currentAmount = balance.getDipositwithdra();

            if (transaction.getDipositwithdrawamount() > currentAmount) {
                throw new RuntimeException("Insufficient balance.");
            }

            // Recalculate packages
            double updatedDipositB = balance.getDipositB();
            String packageType;

            if (updatedDipositB <= 100) {
                packageType = "1";
            } else if (updatedDipositB <= 101) {
                packageType = "2";
            } else if (updatedDipositB <= 1001) {
                packageType = "3";
            } else {
                packageType = "4";
            }

            balance.setPackages(packageType);




            balance.setDipositwithdra(currentAmount - transaction.getDipositwithdrawamount());
            balanceRepository.save(balance);
            depositWithdrawBankRepository.save(transaction);

            return "Withdrawal successful.";
        } else {
            throw new RuntimeException("Invalid OTP entered.");
        }
    }




    // Helper method to generate 6-digit OTP
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }



    public String verifyOtp(int transactionId, String userEnteredOtp) {
        DepositWithdrawBank transaction = depositWithdrawBankRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
//
//        if (transaction.getOtpVerified()) {
//            return "Transaction already verified.";
//        }

        if (!userEnteredOtp.equals(transaction.getGeneratedOtp())) {
            throw new RuntimeException("Invalid OTP.");
        }

        long elapsedTime = new Date().getTime() - transaction.getOtpGeneratedTime().getTime();
        if (elapsedTime > 5 * 60 * 1000) {
            throw new RuntimeException("OTP has expired.");
        }

        // OTP is valid → mark verified
        transaction.setOtpVerified(true);

        // Deduct balance
        Balance balance = transaction.getBalance();
        double current = balance.getDipositwithdra();
        double amount = transaction.getDipositwithdrawamount();

        if (amount > current) {
            throw new RuntimeException("Insufficient balance.");
        }

        balance.setDipositwithdra(current - amount);
        balanceRepository.save(balance);
        depositWithdrawBankRepository.save(transaction);

        return "OTP verified and withdrawal successful.";
    }


}
