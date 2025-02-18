package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.Loan;
import com.saverfavor.microbank.service.BalanceService;
import com.saverfavor.microbank.service.LoanService;
import com.saverfavor.microbank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")  // Corrected the path
public class LoanController {

    @Autowired
    private LoanService loanService;
    @Autowired
   private UserService userService;
    @Autowired
    private BalanceService balanceService;

    @PostMapping("/save")
    public ResponseEntity<String> saveLoan(@RequestBody Loan loan) {


        try {
            loanService.saveLoan(loan);
            return ResponseEntity.ok("Loan saved successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get")
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/{id}")
    public Loan getLoanById(@PathVariable Integer id) {
        return loanService.getLoanById(id);
    }
}
