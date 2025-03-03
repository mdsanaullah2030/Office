package com.saverfavor.microbank.service;

import com.saverfavor.microbank.entity.Balance;
import com.saverfavor.microbank.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceService {
    @Autowired
    private BalanceRepository balanceRepository;

    // Retrieve all ManuItems
    public List<Balance> getAllManuItems() {
        return balanceRepository.findAll();
    }

    // Retrieve ManuItem by ID
    public Balance getManuItemById(int id) {
        return balanceRepository.findById(id).orElseThrow(
                () -> new RuntimeException("ManuItems not found with id: " + id)
        );
    }

    // Save or update a ManuItem
    public void saveManuItem(Balance manuItem) {
        Balance existingItem = balanceRepository.findById(manuItem.getId()).orElse(null);

        double depositBalance;
        if (existingItem != null) {
            depositBalance = existingItem.getDipositB() + manuItem.getAddBalance();
        } else {
            depositBalance = manuItem.getAddBalance();
        }

        manuItem.setDipositB(depositBalance);

        // Determine package
        String packageType;
        if (depositBalance <= 100) {
            packageType = "1";
        } else if (depositBalance <= 1000) {
            packageType = "2";
        } else if (depositBalance <= 5000) {
            packageType = "3";
        } else {
            packageType = "4";
        }

        manuItem.setPackages(packageType);
        manuItem.setProfitB(calculateProfit(depositBalance, packageType));

        // Update withdrawB balance
        updateWithdrawBalance(manuItem);

        balanceRepository.save(manuItem);
    }




    // Scheduled task to update profitB and withdrawB for all ManuItems daily
    @Scheduled(fixedRate = 86400000 ) // 24 hours in milliseconds
    public void updateProfitDaily() {
        List<Balance> allItems = balanceRepository.findAll();
        for (Balance item : allItems) {
            double depositBalance = item.getDipositB();

            // Calculate profit based on the package
            double profit = calculateProfit(depositBalance, item.getPackages());
            item.setProfitB(item.getProfitB() + profit);

            // Update withdrawB balance
            updateWithdrawBalance(item);

            balanceRepository.save(item);
        }
    }









    // Scheduled task to update profitB and withdrawB for packageType 1 every 30 days
    @Scheduled(fixedRate = 2592000000L) // 30 days in milliseconds
    public void updateWithdrawBPackageType1() {
        List<Balance> allItems = balanceRepository.findAll();
        for (Balance item : allItems) {
            if ("1".equals(item.getPackages())) {
                double depositBalance = item.getDipositB();
                // Calculate profit based on the package
                double profit = calculateProfit(depositBalance, item.getPackages());
                item.setProfitB(item.getProfitB() + profit);

                // Update withdrawB balance
                updateWithdrawBalance(item);

                balanceRepository.save(item);
            }
        }
    }

    // Scheduled task to update profitB and withdrawB for packageType 2 every 15 days
    @Scheduled(fixedRate = 1296000000L) // 15 days in milliseconds
    public void updateWithdrawBPackageType2() {
        List<Balance> allItems = balanceRepository.findAll();
        for (Balance item : allItems) {
            if ("2".equals(item.getPackages())) {
                double depositBalance = item.getDipositB();
                // Calculate profit based on the package
                double profit = calculateProfit(depositBalance, item.getPackages());
                item.setProfitB(item.getProfitB() + profit);

                // Update withdrawB balance
                updateWithdrawBalance(item);

                balanceRepository.save(item);
            }
        }
    }

    // Scheduled task to update profitB and withdrawB for packageType 3 every 7 days
    @Scheduled(fixedRate = 604800000L) // 7 days in milliseconds
    public void updateWithdrawBPackageType3() {
        List<Balance> allItems = balanceRepository.findAll();
        for (Balance item : allItems) {
            if ("3".equals(item.getPackages())) {
                double depositBalance = item.getDipositB();
                // Calculate profit based on the package
                double profit = calculateProfit(depositBalance, item.getPackages());
                item.setProfitB(item.getProfitB() + profit);

                // Update withdrawB balance
                updateWithdrawBalance(item);

                balanceRepository.save(item);
            }
        }
    }

    // Scheduled task to update profitB and withdrawB for packageType 4 every 1 day
    @Scheduled(fixedRate = 86400000L) // 1 day in milliseconds
    public void updateWithdrawBPackageType4() {
        List<Balance> allItems = balanceRepository.findAll();
        for (Balance item : allItems) {
            if ("4".equals(item.getPackages())) {
                double depositBalance = item.getDipositB();
                // Calculate profit based on the package
                double profit = calculateProfit(depositBalance, item.getPackages());
                item.setProfitB(item.getProfitB() + profit);

                // Update withdrawB balance
                updateWithdrawBalance(item);

                balanceRepository.save(item);
            }
        }
    }














    // Helper method to calculate profit based on package
    private double calculateProfit(double depositBalance, String packageType) {
        double profitPercentage;
        switch (packageType) {
            case "1":
                profitPercentage = 0.0002; // 0.02%
                break;
            case "2":
                profitPercentage = 0.0003; // 0.03%
                break;
            case "3":
                profitPercentage = 0.00045; // 0.045%
                break;
            case "4":
                profitPercentage = 0.00055; // 0.055%
                break;
            default:
                profitPercentage = 0.0;
        }
        return depositBalance * profitPercentage;
    }

    // Helper method to update withdrawB
    private void updateWithdrawBalance(Balance manuItem) {
        manuItem.setWithdrawB(manuItem.getDipositB() + manuItem.getProfitB() + manuItem.getReferralB());
    }
}


