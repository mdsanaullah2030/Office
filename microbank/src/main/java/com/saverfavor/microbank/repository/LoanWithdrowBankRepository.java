package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.LoanWithdrowBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanWithdrowBankRepository extends JpaRepository<LoanWithdrowBank, Integer> {
}
