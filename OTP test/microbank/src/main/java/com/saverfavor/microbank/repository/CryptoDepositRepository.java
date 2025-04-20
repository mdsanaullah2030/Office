package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.CryptoDepositWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoDepositRepository extends JpaRepository<CryptoDepositWithdrawal,Integer> {

    //User id all Loan data show//
    List<CryptoDepositWithdrawal> findByUserRegistrationId(long userId);
}
