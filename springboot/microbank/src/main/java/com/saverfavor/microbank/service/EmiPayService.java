package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.EmiPay;
import com.saverfavor.microbank.entity.Loan;
import com.saverfavor.microbank.repository.BalanceRepository;
import com.saverfavor.microbank.repository.EmiPayRepository;
import com.saverfavor.microbank.repository.LoanRepository;
import com.saverfavor.microbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmiPayService {

    @Autowired
    private EmiPayRepository emiPayRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private  EmailService emailService;


    @Transactional
    public void EmiPaymentSave(EmiPay emiPay) {
        // Save the EMI payment first (optional, if you want to keep record before processing loan)
        emiPayRepository.save(emiPay);

        // Get user ID from emiPay
        Long userId = emiPay.getUserRegistration().getId();

        // Get the loan for the user
        Loan loan = loanRepository.findByUserRegistrationId(userId)
                .stream()
                .filter(l -> l.getStatus() == null || !l.getStatus().equalsIgnoreCase("PAID"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Active loan not found for user id: " + userId));

        // If already paid
        if (loan.getTotalpay() <= 0) {
            loan.setStatus("PAID");
            loanRepository.save(loan);
            throw new RuntimeException("Loan is already fully paid.");
        }

        // Subtract EMI amount
        double updatedTotal = loan.getTotalpay() - emiPay.getEmiamount();
        loan.setTotalpay(Math.max(0, updatedTotal)); // prevent going negative

        // If paid fully now
        if (loan.getTotalpay() == 0) {
            loan.setStatus("PAID");
            // You can also send an email notification using emailService here
        }

        // Save updated loan
        loanRepository.save(loan);
    }


}
