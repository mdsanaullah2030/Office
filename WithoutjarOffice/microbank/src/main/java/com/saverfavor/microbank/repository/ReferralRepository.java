package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.Referral;
import com.saverfavor.microbank.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ReferralRepository extends JpaRepository<Referral, Integer> {

}

