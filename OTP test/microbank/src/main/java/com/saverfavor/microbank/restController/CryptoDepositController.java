package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.CryptoDepositWithdrawal;
import com.saverfavor.microbank.entity.Loan;
import com.saverfavor.microbank.service.CryptoDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class CryptoDepositController {


    @Autowired
    private CryptoDepositService cryptoDepositService;


    //All transactions data Get  CryptoDeposit//
    @GetMapping("/api/CryptoDeposit/get")
    public List<CryptoDepositWithdrawal> getAllTransactions() {
        return cryptoDepositService.getAllTransactions();
    }

    @GetMapping("/api/CryptoDeposit/{id}")
    public ResponseEntity<CryptoDepositWithdrawal> getTransaction(@PathVariable int id) {
        return cryptoDepositService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/withdraw/CryptoDeposit")
    public ResponseEntity<String> createWithdrawal(@RequestBody CryptoDepositWithdrawal transaction) {
        return ResponseEntity.ok(cryptoDepositService.saveTransaction(transaction, null));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam int transactionId, @RequestParam String otp) {
        return ResponseEntity.ok(cryptoDepositService.verifyOtp(transactionId, otp));
    }


    @GetMapping("/api/CryptoDeposit/getByUser/{userId}")
    public ResponseEntity<List<CryptoDepositWithdrawal>> getCryptoDepositByUser(@PathVariable long userId) {
        List<CryptoDepositWithdrawal> CryptoDeposit = cryptoDepositService.getCryptoDepositByUserId(userId);
        return ResponseEntity.ok(CryptoDeposit);
    }
}
