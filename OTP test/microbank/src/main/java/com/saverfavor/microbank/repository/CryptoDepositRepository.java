package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.CryptoDepositWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoDepositRepository extends JpaRepository<CryptoDepositWithdrawal,Integer> {
}
