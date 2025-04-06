package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.DepositWithdrawBank;
import com.saverfavor.microbank.service.DepositWithdrawBankService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@AllArgsConstructor
@CrossOrigin

public class DepositWithdrawBankController {


    @Autowired
    private DepositWithdrawBankService service;

    // Save a new transaction
    @PostMapping("/api/transactions/save")
    public DepositWithdrawBank saveTransaction(@RequestBody DepositWithdrawBank transaction) {
        return service.saveTransaction(transaction);
    }

    // Get all transactions
    @GetMapping("/all")
    public List<DepositWithdrawBank> getAllTransactions() {
        return service.getAllTransactions();
    }

    // Get transaction by ID
    @GetMapping("/{id}")
    public Optional<DepositWithdrawBank> getTransactionById(@PathVariable int id) {
        return service.getTransactionById(id);
    }
}
