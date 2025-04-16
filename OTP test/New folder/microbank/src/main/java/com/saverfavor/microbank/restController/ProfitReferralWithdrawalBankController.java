package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.DepositWithdrawBank;
import com.saverfavor.microbank.entity.ProfitReferralWithdrawalBank;
import com.saverfavor.microbank.service.ProfitReferralWithdrawalBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfitReferralWithdrawalBankController {

    @Autowired
    private ProfitReferralWithdrawalBankService withdrawalService;

//    @PostMapping("/api/Withdrawal/save")
//    public String submitWithdrawalRequest(@RequestBody ProfitReferralWithdrawalBank request) {
//        withdrawalService.saveWithdrawalRequest(request);
//        return "Withdrawal request submitted successfully!";
//    }

    @PostMapping("/api/Withdrawal/save")
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

    @PostMapping("/api/Withdrawal/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam Long userId, @RequestParam String otp) {
        try {
            boolean isVerified = withdrawalService.verifyOtp(userId, otp);
            if (isVerified) {
                return ResponseEntity.ok("✅ OTP verified successfully! Withdrawal will be processed.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ Invalid or expired OTP.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

}
