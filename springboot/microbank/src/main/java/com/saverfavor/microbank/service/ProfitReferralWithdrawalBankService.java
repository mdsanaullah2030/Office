package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.Balance;
import com.saverfavor.microbank.entity.DepositWithdrawBank;
import com.saverfavor.microbank.entity.ProfitReferralWithdrawalBank;
import com.saverfavor.microbank.entity.User;
import com.saverfavor.microbank.repository.BalanceRepository;
import com.saverfavor.microbank.repository.ProfitReferralWithdrawalBankRepository;
import com.saverfavor.microbank.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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





    // Get all transactions
    public List<ProfitReferralWithdrawalBank> getAllTransactions() {
        return withdrawalRepository.findAll();
    }

    // Get transaction by ID
    public Optional<ProfitReferralWithdrawalBank> getProfitById(int id) {
        return withdrawalRepository.findById(id);
    }


    //User Id loan data get//
    public List<ProfitReferralWithdrawalBank> getProfitReferraWithdrawBank(long userId) {
        return withdrawalRepository.findByUserRegistrationId(userId);
    }






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

        request.setOtpGeneratedTime(new Date());
        request.setBalance(balance);


        withdrawalRepository.save(request);

        // 7. Send email
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


    public boolean verifyOtpById(int id, String otp) {
        Optional<ProfitReferralWithdrawalBank> optional = withdrawalRepository.findById(id);

        if (optional.isPresent()) {
            ProfitReferralWithdrawalBank withdrawal = optional.get();

            // Debug log to check OTP and ID
            System.out.println("Verifying OTP: " + otp + " for Withdrawal ID: " + id);
            System.out.println("Stored OTP: " + withdrawal.getGeneratedOtp());

            if (!withdrawal.getGeneratedOtp().equals(otp)) {
                return false; // OTP mismatch
            }

            // Check if OTP is older than 5 minutes
            Date otpGeneratedDate = withdrawal.getOtpGeneratedTime();
            if (otpGeneratedDate != null) {
                LocalDateTime otpTime = otpGeneratedDate.toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDateTime();

                if (Duration.between(otpTime, LocalDateTime.now()).toMinutes() > 5) {
                    return false; // Expired OTP
                }
            }

            // OTP valid → update as verified
            withdrawal.setOtpVerified(true);
            withdrawalRepository.save(withdrawal); // ✅ only UPDATE, not insert again

            // ✅ Send confirmation email
            User user = withdrawal.getUserRegistration();
            String emailMessage = "Dear " + user.getName() + ",\n\n" +
                    "Your withdrawal request has been successfully verified.\n\n" +
                    "**Withdrawal Details**:\n" +
                    "Withdraw Amount: " + withdrawal.getWithdrawamount() + "\n" +
                    "Withdraw Balance: " + withdrawal.getWithdrawbalance() + "\n" +
                    "Bank Name: " + withdrawal.getBankname() + "\n" +
                    "Account Number: " + withdrawal.getAccountnumber() + "\n" +
                    "Routing Number: " + withdrawal.getRoutingnumber() + "\n" +
                    "Swift Code: " + withdrawal.getSwiftcode() + "\n" +
                    "Request Date: " + withdrawal.getRequestdate() + "\n\n" +
                    "Thanks,\nSaverFavor Microbank";

            try {
                emailService.sendSimpleEmail(
                        user.getEmail(),
                        "Withdrawal Confirmed",
                        emailMessage
                );
            } catch (MessagingException e) {
                throw new RuntimeException("Failed to send confirmation email.");
            }

            return true;
        }

        return false; // withdrawal not found
    }


}




