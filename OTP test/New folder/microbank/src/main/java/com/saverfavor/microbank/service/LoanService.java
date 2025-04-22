package com.saverfavor.microbank.service;
import com.saverfavor.microbank.entity.Balance;
import com.saverfavor.microbank.entity.Loan;
import com.saverfavor.microbank.repository.BalanceRepository;
import com.saverfavor.microbank.repository.LoanRepository;
import com.saverfavor.microbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private EmailService emailService;


    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Integer id) {  // Changed Long to Integer
        Optional<Loan> loan = loanRepository.findById(id);
        return loan.orElseThrow(() -> new RuntimeException("Loan not found with ID: " + id));
    }





    public Loan saveLoan(Loan loan) {
        Long userId = loan.getUserRegistration().getId();

        // Get latest balance
        Optional<Balance> balanceOpt = balanceRepository.findTopByUserRegistrationIdOrderByIdDesc(userId);

        if (balanceOpt.isEmpty()) {
            throw new RuntimeException("Balance not found for user ID: " + userId);
        }

        Balance balance = balanceOpt.get();
        double baseEligibleAmount = balance.getDipositB() * 0.8; // 80% of deposit balance

        // Get all unpaid loans of the user
        List<Loan> existingLoans = loanRepository.findByUserRegistrationId(userId)
                .stream()
                .filter(l -> l.getStatus() == null || !l.getStatus().equalsIgnoreCase("PAID"))
                .toList();

        double totalUnpaidLoan = existingLoans.stream()
                .mapToDouble(Loan::getLoanamuont)
                .sum();

        // Calculate current eligible balance
        double currentEligible = baseEligibleAmount - totalUnpaidLoan;

        if (currentEligible <= 0) {
            throw new RuntimeException("No eligible balance remaining for new loan.");
        }

        if (loan.getLoanamuont() > currentEligible) {
            throw new RuntimeException("Loan amount exceeds remaining eligible balance. Available: " + currentEligible);
        }

        loan.setEligeblebalancey((int) currentEligible); // store current eligible balance

        // Calculate totalpay based on package type
        double packageMultiplier = switch (balance.getPackages()) {
            case "1" -> 0.02;
            case "2" -> 0.03;
            case "3" -> 0.045;
            case "4" -> 0.055;
            default -> throw new RuntimeException("Invalid package type: " + balance.getPackages());
        };

        // Apply formula: totalpay = (packageMultiplier + 0.014) * loanAmount + loanAmount
        double totalPay = (packageMultiplier + 0.014) * loan.getLoanamuont() + loan.getLoanamuont();
        loan.setTotalpay(totalPay);

        double weeklyPay = totalPay / loan.getTenure();
        loan.setWeeklypay(weeklyPay);

        return loanRepository.save(loan);
    }


    //User Id loan data get//
    public List<Loan> getLoansByUserId(long userId) {
        return loanRepository.findByUserRegistrationId(userId);
    }






    @Scheduled(fixedRate = 23460000000L) // every 23460000000L 600001 minute
    public void checkAndSendLoanReminders() {
        List<Loan> loans = loanRepository.findAll();

        for (Loan loan : loans) {
            if (!loan.isEmailSent()) {
                Date now = new Date();
                long diff = now.getTime() - loan.getRequestdate().getTime();

                if (diff >= 3 * 60 * 1000) { // 3 minutes
                    try {
                        String email = loan.getUserRegistration().getEmail(); // Assuming this field exists
                        String subject = "Loan Weekly Payment Reminder";
                        String text = String.format("Dear User,\n\nYour weekly loan payment is: %.2f\nLoan ID: %d\n\nThank you.",
                                loan.getWeeklypay(), loan.getId());

                        emailService.sendSimpleEmail(email, subject, text);

                        loan.setEmailSent(true);
                        loanRepository.save(loan);

                    } catch (Exception e) {
                        System.out.println("Error sending email for Loan ID: " + loan.getId());
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}