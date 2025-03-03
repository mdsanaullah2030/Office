package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.LoanWithdrowBank;
import com.saverfavor.microbank.service.LoanWithdrowBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loanWithdrowBank")
public class LoanWithdrowBankContro {

    private final LoanWithdrowBankService loanWithdrowBankService;

    @Autowired
    public LoanWithdrowBankContro(LoanWithdrowBankService loanWithdrowBankService) {
        this.loanWithdrowBankService = loanWithdrowBankService;
    }

    @GetMapping
    public List<LoanWithdrowBank> getAllLoanWithdrowBanks() {
        return loanWithdrowBankService.getLoanWithdrowBanks();
    }

    @GetMapping("/{id}")
    public LoanWithdrowBank getLoanWithdrowBankById(@PathVariable Integer id) {
        return loanWithdrowBankService.getLoanWithdrowBank(id);
    }

    @PostMapping
    public LoanWithdrowBank createLoanWithdrowBank(@RequestBody LoanWithdrowBank loanWithdrowBank) {
        return loanWithdrowBankService.saveLoanWithdrowBank(loanWithdrowBank);
    }

    @DeleteMapping("/{id}")
    public void deleteLoanWithdrowBank(@PathVariable Integer id) {
        loanWithdrowBankService.deleteLoanWithdrowBank(id);
    }
}
