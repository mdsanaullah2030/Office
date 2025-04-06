package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.DepositWithdrawBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositWithdrawBankRepository extends JpaRepository<DepositWithdrawBank, Integer> {
}
