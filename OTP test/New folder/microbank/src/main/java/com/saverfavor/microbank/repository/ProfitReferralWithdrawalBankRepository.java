package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.ProfitReferralWithdrawalBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfitReferralWithdrawalBankRepository extends JpaRepository<ProfitReferralWithdrawalBank,Integer> {

}
