package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.saverfavor.microbank.entity.Balance;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {
    List<Balance> findByUserRegistrationId(Integer userId);



    //****/
    Optional<Balance> findByUserRegistration(UserRegistration userRegistration);

}

