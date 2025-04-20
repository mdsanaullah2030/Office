package com.saverfavor.microbank.restController;


import com.saverfavor.microbank.entity.CryptoProfitWithdrawal;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin
public class CryptoProfitController {
    @Autowired
    private  CryptoProfitService cryptoProfitService;




    @PostMapping("/api/withdraw/CryptoProfit")
    public ResponseEntity<String> createWithdrawal(@RequestBody CryptoProfitWithdrawal request) {

        try {
            String result = cryptoProfitService.saveWithdrawalRequest(request); // Pass OTP properly
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
           return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unexpected error occurred. Please try again later.");
        }

    }



}
