package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.DepositWithdrawBank;
import com.saverfavor.microbank.entity.ProfitReferralWithdrawalBank;
import com.saverfavor.microbank.service.ProfitReferralWithdrawalBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProfitReferralWithdrawalBankController {

    @Autowired
    private ProfitReferralWithdrawalBankService withdrawalService;
    @Autowired
    private ProfitReferralWithdrawalBankService profitReferralWithdrawalBankService;


    @GetMapping("/api/ProfitWithdrawalBank/get")
    public List<ProfitReferralWithdrawalBank> getAllLoans() {
        return withdrawalService.getAllTransactions();
    }


    // Get transaction by ID
    @GetMapping("/api/ProfitWithdrawalBank/{id}")
    public Optional<ProfitReferralWithdrawalBank> getTransaction(@PathVariable int id) {
        return withdrawalService.getProfitById(id);
    }


    @GetMapping("/api/ProfitWithdrawalBank/getByUser/{userId}")
    public ResponseEntity<List<ProfitReferralWithdrawalBank>> gettransactionsByUser(@PathVariable long userId) {
        List<ProfitReferralWithdrawalBank> ProfitWithdrawal = withdrawalService.getProfitReferraWithdrawBank(userId);
        return ResponseEntity.ok(ProfitWithdrawal);
    }




    @PostMapping("/api/ProfitWithdrawalBank/save")
    public ResponseEntity<String> saveTransactionWithOtp(@RequestBody ProfitReferralWithdrawalBank request  ) {

        try {
            String result =  withdrawalService.saveWithdrawalRequest(request); // Pass OTP properly
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unexpected error occurred. Please try again later.");
        }
    }


    @PostMapping("api/ProfitWithdrawalBank/verify")
    public ResponseEntity<String> verifyOtp(@RequestBody ProfitReferralWithdrawalBank request) {
        boolean success = profitReferralWithdrawalBankService.verifyOtpById(request.getId(), request.getGeneratedOtp());
        if (success) {
            return ResponseEntity.ok("OTP verified successfully!");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP for ID: " + request.getId() + " with OTP: " + request.getGeneratedOtp());
        }
    }







}
