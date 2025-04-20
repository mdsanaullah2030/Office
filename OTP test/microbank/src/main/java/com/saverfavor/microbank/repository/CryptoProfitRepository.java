package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.CryptoProfitWithdrawal;
import com.saverfavor.microbank.entity.DepositWithdrawBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CryptoProfitRepository extends JpaRepository<CryptoProfitWithdrawal, Integer> {

    Optional<CryptoProfitWithdrawal> findTopByUserRegistrationIdOrderByRequestdateDesc(Long userId);

    List<CryptoProfitWithdrawal> findByUserRegistrationId(long userId);
}
