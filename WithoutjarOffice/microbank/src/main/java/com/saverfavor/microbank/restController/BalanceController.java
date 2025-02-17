package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.Balance;
import com.saverfavor.microbank.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/Balance")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    // Get all ManuItems
    @GetMapping("/get")
    public List<Balance> getAllManuItems() {
        return balanceService.getAllManuItems();
    }

    // Get ManuItem by ID
    @GetMapping("/{id}")
    public Balance getManuItemById(@PathVariable int id) {
        return balanceService.getManuItemById(id);


    }

    // Save a new ManuItem
    @PostMapping("/save")
    public ResponseEntity<String> saveManuItem(@RequestBody Balance manuItem) {


        try {
            balanceService.saveManuItem(manuItem);
            return ResponseEntity.ok("Balance saved successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
