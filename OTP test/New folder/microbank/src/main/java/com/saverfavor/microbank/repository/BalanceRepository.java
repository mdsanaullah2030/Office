package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.ProfitReferralWithdrawalBank;
import com.saverfavor.microbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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


    @Query("SELECT b FROM Balance b WHERE b.id IN (SELECT MAX(b2.id) FROM Balance b2 GROUP BY b2.userRegistration.id)")
    List<Balance> findLatestBalanceForAllUsers();


//ProfitReferralWithdrawalBankService//



}