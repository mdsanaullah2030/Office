package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.EmiPay;
import com.saverfavor.microbank.entity.Loan;
import com.saverfavor.microbank.service.EmiPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class EmiPayController {

@Autowired
private EmiPayService emiPayService;

    @PostMapping("/api/EmiPay/save")
    public ResponseEntity<String> EmiPaymentSave(@RequestBody EmiPay emiPay) {


        try {
            emiPayService.EmiPaymentSave(emiPay);
            return ResponseEntity.ok("Loan saved successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
