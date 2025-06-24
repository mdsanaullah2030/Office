package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.CryptoDepositWithdrawal;
import com.saverfavor.microbank.entity.CryptoProfitWithdrawal;
import com.saverfavor.microbank.entity.DepositWithdrawBank;
import com.saverfavor.microbank.repository.CryptoProfitRepository;
import com.saverfavor.microbank.service.CryptoProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class CryptoProfitController {
    @Autowired
    private  CryptoProfitService cryptoProfitService;




    //All transactions data Get  CryptoDeposit//
    @GetMapping("/api/CryptoProfit/get")
    public List<CryptoProfitWithdrawal> getAllTransactions() {
        return cryptoProfitService.getAllTransactions();
    }





    @GetMapping("/api/CryptoProfit/{id}")
    public ResponseEntity<CryptoProfitWithdrawal> getTransaction(@PathVariable int id) {
        return cryptoProfitService.getProfitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }





    @GetMapping("/api/CryptoProfit/getByUser/{userId}")
    public ResponseEntity<List<CryptoProfitWithdrawal>> getCryptoDepositByUser(@PathVariable long userId) {
        List<CryptoProfitWithdrawal> CryptoDeposit = cryptoProfitService.getProfitReferraWithdrawBank(userId);
        return ResponseEntity.ok(CryptoDeposit);
    }




    @PostMapping("/api/withdraw/CryptoProfit/save")
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
