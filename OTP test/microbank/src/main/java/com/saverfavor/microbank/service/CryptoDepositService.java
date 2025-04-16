package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.CryptoDepositWithdrawal;
import com.saverfavor.microbank.repository.BalanceRepository;
import com.saverfavor.microbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CryptoDepositService {
   @Autowired
   private CryptoDepositWithdrawal cryptoDepositWithdrawal;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceRepository balanceRepository;
}
