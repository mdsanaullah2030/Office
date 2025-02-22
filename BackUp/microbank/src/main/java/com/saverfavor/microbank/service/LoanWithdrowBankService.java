package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.LoanWithdrowBank;
import com.saverfavor.microbank.repository.LoanWithdrowBankRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanWithdrowBankService {

    private final LoanWithdrowBankRepository loanWithdrowBankRep;

    @Autowired // ✅ Corrected Constructor-based Injection
    public LoanWithdrowBankService(LoanWithdrowBankRepository loanWithdrowBankRep) {
        this.loanWithdrowBankRep = loanWithdrowBankRep;
    }

    public List<LoanWithdrowBank> getLoanWithdrowBanks() {
        return loanWithdrowBankRep.findAll(); // ✅ Fetch all records
    }

    public LoanWithdrowBank getLoanWithdrowBank(Integer id) {
        return loanWithdrowBankRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan Withdrow Bank not found with ID: " + id));
    }

    public LoanWithdrowBank saveLoanWithdrowBank(LoanWithdrowBank loanWithdrowBank) {
        return loanWithdrowBankRep.save(loanWithdrowBank); // ✅ Save to database
    }

    public void deleteLoanWithdrowBank(Integer id) {
        loanWithdrowBankRep.deleteById(id); // ✅ Delete by ID
    }
}
