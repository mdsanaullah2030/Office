package com.saverfavor.microbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.saverfavor.microbank.entity.Balance;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {
    Optional<Balance> findByUserRegistrationId(Integer userId);
}

