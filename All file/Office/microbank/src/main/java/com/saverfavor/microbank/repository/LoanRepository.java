package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface LoanRepository extends JpaRepository<Loan,Integer> {



}
