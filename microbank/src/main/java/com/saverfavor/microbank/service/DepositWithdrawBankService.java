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

    // Save deposit/withdraw transaction (with OTP generation)
    public String saveTransaction(DepositWithdrawBank transaction) {
        User user = transaction.getUserRegistration();
        if (user != null && user.getId() != 0) {
            user = userRepository.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            transaction.setUserRegistration(user);
        }

        if (transaction.getBalance() != null && transaction.getBalance().getId() != 0) {
            Balance balance = balanceRepository.findById(transaction.getBalance().getId())
                    .orElseThrow(() -> new RuntimeException("Balance record not found"));
            transaction.setBalance(balance);

            // Validate amount
            double currentWithdrawAmount = balance.getDipositwithdra();
            if (transaction.getDipositwithdrawamount() > currentWithdrawAmount) {
                throw new RuntimeException("Withdraw amount exceeds the available balance: " + currentWithdrawAmount);
            }

            // Generate OTP
            String otp = generateOtp();
            transaction.setOtp(otp);
            transaction.setOtpVerified(false);
            transaction.setOtpGeneratedTime(new Date());

            // Save transaction (awaiting OTP verification)
            depositWithdrawBankRepository.save(transaction);

            try {
                emailService.sendSimpleEmail(
                        user.getEmail(),
                        "Withdrawal OTP Verification",
                        "Dear " + user.getName() + ",\n\n" +
                                "Your one-time password (OTP) for processing your withdrawal request on FINSYS is: " + otp + "\n\n" +
                                "Please use this OTP to complete the transaction. For any assistance, feel free to reach out to our support team at help@getfinsys.com.\n\n" +
                                "Thank you for choosing FINSYS.\n\n" +
                                "© Financial System Solutions"
                );


            } catch (MessagingException e) {
                throw new RuntimeException("Failed to send OTP email");
            }

            return "OTP sent to your email.";
        }

        throw new RuntimeException("Invalid transaction data.");
    }

    // Confirm OTP and process withdrawal
    public String confirmOtpAndWithdraw(int transactionId, String userEnteredOtp) {
        DepositWithdrawBank transaction = depositWithdrawBankRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (transaction.isOtpVerified()) {
            return "OTP already verified, withdrawal already processed.";
        }

        long elapsedTime = new Date().getTime() - transaction.getOtpGeneratedTime().getTime();
        if (elapsedTime > 5 * 60 * 1000) {
            throw new RuntimeException("OTP has expired. Please request a new withdrawal.");
        }

        if (!transaction.getOtp().equals(userEnteredOtp)) {
            throw new RuntimeException("Invalid OTP.");
        }

        transaction.setOtpVerified(true);

        Balance balance = transaction.getBalance();
        double currentAmount = balance.getDipositwithdra();
        if (transaction.getDipositwithdrawamount() > currentAmount) {
            throw new RuntimeException("Insufficient balance.");
        }

        balance.setDipositwithdra(currentAmount - transaction.getDipositwithdrawamount());
        balanceRepository.save(balance);

        transaction.setWithdrawbalance(String.valueOf(currentAmount));
        depositWithdrawBankRepository.save(transaction);

        return "Withdrawal successful.";
    }



    // Helper method to generate 6-digit OTP
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
