package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.Balance;
import com.saverfavor.microbank.service.BalanceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@CrossOrigin


public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    // Get all ManuItems
    @GetMapping("/api/Balance/get")
    public List<Balance> getAllManuItems() {
        return balanceService.getAllManuItems();
    }

    // Get ManuItem by ID
    @GetMapping("/api/Balance/get/{id}")
    public Balance getManuItemById(@PathVariable int id) {
        return balanceService.getManuItemById(id);


    }

    // Save a new ManuItem
    @PostMapping("/api/Balance/save")
    public ResponseEntity<String> saveManuItem(@RequestBody Balance balance) {


        try {
            balanceService.saveManuItem(balance);
            return ResponseEntity.ok("Balance saved successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/Balance/getByUser/{userId}")
    public ResponseEntity<List<Balance>> getBalanceByUser(@PathVariable long userId) {
        List<Balance> balances = balanceService.getBalanceByUser(userId);
        return ResponseEntity.ok(balances);
    }

}