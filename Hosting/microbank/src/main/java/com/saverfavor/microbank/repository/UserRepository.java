package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.Balance;
import com.saverfavor.microbank.entity.Nominee;
import com.saverfavor.microbank.entity.Referral;
import com.saverfavor.microbank.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserRegistration, Integer> {

}
