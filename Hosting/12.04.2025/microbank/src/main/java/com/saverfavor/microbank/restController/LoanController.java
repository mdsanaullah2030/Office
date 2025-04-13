package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.Loan;
import com.saverfavor.microbank.service.BalanceService;
import com.saverfavor.microbank.service.LoanService;
import com.saverfavor.microbank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@CrossOrigin

public class LoanController {

    @Autowired
    private LoanService loanService;
    @Autowired
    private UserService userService;
    @Autowired
    private BalanceService balanceService;

    @PostMapping("/api/loan/save")
    public ResponseEntity<String> saveLoan(@RequestBody Loan loan) {


        try {
            loanService.saveLoan(loan);
            return ResponseEntity.ok("Loan saved successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/loan/get")
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/api/loan/get/{id}")
    public Loan getLoanById(@PathVariable Integer id) {
        return loanService.getLoanById(id);
    }


    @GetMapping("/api/loan/getByUser/{userId}")
    public ResponseEntity<List<Loan>> getLoansByUser(@PathVariable long userId) {
        List<Loan> loans = loanService.getLoansByUserId(userId);
        return ResponseEntity.ok(loans);
    }
}