package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.DepositWithdrawBank;
import com.saverfavor.microbank.entity.Loan;
import com.saverfavor.microbank.service.DepositWithdrawBankService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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




    @GetMapping("/api/transactions/get")
    public List<DepositWithdrawBank> getAllLoans() {
        return service.getAllTransactions();
    }



    @GetMapping("/api/transactions/getByUser/{userId}")
    public ResponseEntity<List<DepositWithdrawBank>> gettransactionsByUser(@PathVariable long userId) {
        List<DepositWithdrawBank> DepositWithdraw = service.getDepositWithdrawBank(userId);
        return ResponseEntity.ok(DepositWithdraw);
    }




    // Get transaction by ID
    @GetMapping("/api/transactions/{id}")
    public Optional<DepositWithdrawBank> getTransactionById(@PathVariable int id) {
        return service.getTransactionById(id);
    }



    @PostMapping("/api/transactions/save")
    public ResponseEntity<String> saveTransactionWithOtp(@RequestBody DepositWithdrawBank transaction,
                                                         @RequestParam(required = false) String otp) {
        try {
            String result = service.saveTransaction(transaction, otp); // Pass OTP properly
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unexpected error occurred. Please try again later.");
        }
    }


    @PostMapping("/api/confirm-otp")
    public ResponseEntity<String> confirmOtp(@RequestParam int transactionId,
                                             @RequestParam String otp) {
        return ResponseEntity.ok(service.verifyOtp(transactionId, otp));
    }


}
