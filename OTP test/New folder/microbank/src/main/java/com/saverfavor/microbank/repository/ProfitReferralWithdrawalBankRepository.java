package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.ProfitReferralWithdrawalBank;
import com.saverfavor.microbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfitReferralWithdrawalBankRepository extends JpaRepository<ProfitReferralWithdrawalBank,Integer> {

    List<ProfitReferralWithdrawalBank> findByUserRegistrationOrderByRequestdateDesc(User user);

    Optional<ProfitReferralWithdrawalBank> findTopByUserRegistrationIdOrderByRequestdateDesc(Long userId);

}

