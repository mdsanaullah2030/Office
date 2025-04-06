package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.Balance;
import com.saverfavor.microbank.entity.DepositWithdrawBank;
import com.saverfavor.microbank.entity.User;
import com.saverfavor.microbank.repository.BalanceRepository;
import com.saverfavor.microbank.repository.DepositWithdrawBankRepository;
import com.saverfavor.microbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepositWithdrawBankService {

    @Autowired
    private DepositWithdrawBankRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BalanceRepository balanceRepository;

    // Save deposit/withdraw transaction
    public DepositWithdrawBank saveTransaction(DepositWithdrawBank transaction) {
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

            // Get available withdrawable balance
            double withdrawableAmount = balance.getDipositwithdra();

            // Validate dipositwithdrawamount doesn't exceed withdrawable balance
            if (transaction.getDipositwithdrawamount() > withdrawableAmount) {
                throw new RuntimeException(" Withdraw amount exceeds the available balance: " + withdrawableAmount);
            }



            // Set withdraw balance
            transaction.setWithdrawbalance(String.valueOf(withdrawableAmount));

            // Optionally update the Balance if the withdrawal is successful
            balance.setDipositwithdra(withdrawableAmount - transaction.getDipositwithdrawamount());
            balanceRepository.save(balance);
        }

        return repository.save(transaction);
    }




    // Get all transactions
    public List<DepositWithdrawBank> getAllTransactions() {
        return repository.findAll();
    }

    // Get transaction by ID
    public Optional<DepositWithdrawBank> getTransactionById(int id) {
        return repository.findById(id);
    }
}
