package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface LoanRepository extends JpaRepository<Loan,Integer> {

//User id all Loan data show//
    List<Loan> findByUserRegistrationId(long userId);

}