package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.CryptoProfitWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoProfitRepository extends JpaRepository<CryptoProfitWithdrawal, Integer> {

    Optional<CryptoProfitWithdrawal> findTopByUserRegistrationIdOrderByRequestdateDesc(Long userId);

}
