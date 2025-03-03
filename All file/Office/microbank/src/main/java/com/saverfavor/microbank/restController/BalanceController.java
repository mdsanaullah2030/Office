package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.Balance;
import com.saverfavor.microbank.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void saveManuItem(@RequestBody Balance manuItem) {
        balanceService.saveManuItem(manuItem);
    }
}
