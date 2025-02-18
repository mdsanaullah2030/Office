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

    public void saveManuItem(Balance balance) {
        // Find all existing Balance entries for the same userId
        List<Balance> existingItems = balanceRepository.findByUserRegistrationId
                (balance.getUserRegistration().getId());

        double depositBalance = 0.0; // Initialize depositBalance to 0.0
        double totalDepositWithdra = 0.0;

        if (!existingItems.isEmpty()) {
            // Sum up all the dipositB and dipositwithdra for the user
            for (Balance existingItem : existingItems) {
                depositBalance += existingItem.getDipositB();
                totalDepositWithdra += existingItem.getDipositwithdra();
            }
            // Add the new balance to the existing depositBalance
            depositBalance += balance.getAddBalance();
            // Update dipositwithdra by adding the new addBalance to the total of previous dipositwithdra
            balance.setDipositwithdra(totalDepositWithdra + balance.getAddBalance());
        } else {
            // If no existing balance, set the depositBalance to the new addBalance
            depositBalance = balance.getAddBalance();
            balance.setDipositwithdra(depositBalance);
        }

        // Set the dipositB to the new total deposit balance
        balance.setDipositB(depositBalance);

        // Determine package based on depositBalance
        String packageType;
        if (depositBalance <= 100) {
            packageType = "1";
        } else if (depositBalance <= 101) {
            packageType = "2";
        } else if (depositBalance <= 1001) {
            packageType = "3";
        } else {
            packageType = "4";
        }

        balance.setPackages(packageType);
        balance.setProfitB(calculateProfit(depositBalance, packageType));

        // Update withdrawB balance
        updateWithdrawBalance(balance);

        // Save or update the Balance record
        balanceRepository.save(balance);
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
        manuItem.setDipositwithdra(manuItem.getDipositB());
    }

    @Scheduled(fixedRate = 2592000000L) // 30 days in milliseconds
    public void updateDipositWithdraForPackageType1() {
        List<Balance> allItems = balanceRepository.findAll();
        for (Balance item : allItems) {
            if ("1".equals(item.getPackages())) {
                if (item.getDipositB() != 2592000000L) {
                    // Log or send an alert
                    System.out.println("Alert: DipositB is not 2592000000 for package type 1.");
                    continue; // Skip the update if the condition is not met
                }
                item.setDipositwithdra(item.getDipositB()); // Update withdrawal balance
                balanceRepository.save(item);
            }
        }
    }


    @Scheduled(fixedRate = 1728000000L) // 20 days in milliseconds
    public void updateDipositWithdraForPackageType2() {
        List<Balance> allItems = balanceRepository.findAll();
        for (Balance item : allItems) {
            if ("2".equals(item.getPackages())) {
                if (item.getDipositB() != 1728000000L) {
                    // Log or send an alert
                    System.out.println("Alert: DipositB is not 1728000000 for package type 2.");
                    continue; // Skip the update if the condition is not met
                }
                item.setDipositwithdra(item.getDipositB());
                balanceRepository.save(item);
            }
        }
    }


    @Scheduled(fixedRate = 864000000L) // 10 days in milliseconds
    public void updateDipositWithdraForPackageType3() {
        List<Balance> allItems = balanceRepository.findAll();
        for (Balance item : allItems) {
            if ("3".equals(item.getPackages())) {
                if (item.getDipositB() != 864000000L) {
                    // Log or send an alert
                    System.out.println("Alert: DipositB is not 864000000 for package type 3.");
                    continue; // Skip the update if the condition is not met
                }
                item.setDipositwithdra(item.getDipositB());
                balanceRepository.save(item);
            }
        }
    }


//    // Scheduled task to update dipositwithdra for packageType 4 every 7 days
//    @Scheduled(fixedRate = 604800000L) // 7 days in milliseconds
//    public void updateDipositWithdraForPackageType4() {
//        List<Balance> allItems = balanceRepository.findAll();
//        for (Balance item : allItems) {
//            if ("4".equals(item.getPackages())) {
//                item.setDipositwithdra(item.getDipositB());
//                balanceRepository.save(item);
//            }
//        }
//    }



    @Scheduled(fixedRate = 604800000L) // 7 days in milliseconds
    public void updateDipositWithdraForPackageType4() {
        List<Balance> allItems = balanceRepository.findAll();
        for (Balance item : allItems) {
            if ("4".equals(item.getPackages())) {
                if (item.getDipositB() == 604800000L) {
                    item.setDipositwithdra(item.getDipositB());
                    balanceRepository.save(item);
                } else {
                    System.out.println("Alert: DipositB does not match the required interval for package type 4.");
                    // You can also throw an exception if needed
                    // throw new RuntimeException("DipositB does not match the required interval for package type 4.");
                }
            }
        }
    }

}
