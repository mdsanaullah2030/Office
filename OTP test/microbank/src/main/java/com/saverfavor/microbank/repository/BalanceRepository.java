package com.saverfavor.microbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.saverfavor.microbank.entity.Balance;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {
    List<Balance> findByUserRegistrationId(long userId);



    Optional<Balance> findTopByUserRegistrationIdOrderByDateDesc(long userId);


    // Fetch the latest balance for a user based on the most recent entry
    Optional<Balance> findTopByUserRegistrationIdOrderByIdDesc(long userId);
}