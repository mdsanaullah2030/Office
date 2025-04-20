package com.saverfavor.microbank.service;
import com.saverfavor.microbank.entity.Balance;
import com.saverfavor.microbank.entity.Loan;
import com.saverfavor.microbank.repository.BalanceRepository;
import com.saverfavor.microbank.repository.LoanRepository;
import com.saverfavor.microbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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


    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
//get loan ID//
    public Loan getLoanById(Integer id) {  // Changed Long to Integer
        Optional<Loan> loan = loanRepository.findById(id);
        return loan.orElseThrow(() -> new RuntimeException("Loan not found with ID: " + id));
    }


    //User Id loan data get//
    public List<Loan> getLoansByUserId(long userId) {
        return loanRepository.findByUserRegistrationId(userId);
    }



    public Loan saveLoan(Loan loan) {
        // Retrieve the user's balance
        Optional<Balance> balanceOpt = balanceRepository.findTopByUserRegistrationIdOrderByIdDesc(loan.getUserRegistration().getId());
//        Optional<Balance> balanceOpt = balanceRepository.findByUserRegistrationId(loan.getUserRegistration().getId())
//                .stream()
//                .findFirst();

        if (balanceOpt.isPresent()) {
            Balance balance = balanceOpt.get();
            double eligibleAmount = balance.getDipositB() * 0.8; // 80% of deposit balance

            // Set the eligible balance in the Loan entity
            loan.setEligeblebalancey((int) eligibleAmount); // Convert to int if necessary

            // Validate that the loan amount does not exceed eligible balance
            if (loan.getLoanamuont() > eligibleAmount) {
                throw new RuntimeException("Loan amount exceeds eligible balance. Eligible amount: " + eligibleAmount);
            }

            // Calculate totalpay based on package type
            double packageMultiplier = 0;
            switch (balance.getPackages()) {
                case "1":
                    packageMultiplier = 0.02;
                    break;
                case "2":
                    packageMultiplier = 0.03;
                    break;
                case "3":
                    packageMultiplier = 0.045;
                    break;
                case "4":
                    packageMultiplier = 0.055;
                    break;
                default:
                    throw new RuntimeException("Invalid package type: " + balance.getPackages());
            }

            // Apply formula: totalpay = (packageMultiplier + 0.014) * loanAmount + loanAmount
            double totalPay = (packageMultiplier + 0.014) * loan.getLoanamuont() + loan.getLoanamuont();
            loan.setTotalpay(totalPay);

            double weeklyPay = totalPay / loan.getTenure();
            loan.setWeeklypay(weeklyPay);

            // Save the Loan entity with updated totalpay and weeklypay
            return loanRepository.save(loan);
        } else {
            throw new RuntimeException("Balance not found for user ID: " + loan.getUserRegistration().getId());
        }
    }


}