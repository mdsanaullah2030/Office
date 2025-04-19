package com.saverfavor.microbank.repository;

import com.saverfavor.microbank.entity.EmiPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmiPayRepository extends JpaRepository<EmiPay,Integer> {

}
