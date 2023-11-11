package com.example.techtest.service;

import com.example.techtest.entity.Account;
import com.example.techtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Account getUserWalletBalance(int userId) {
        Account existingUser = userRepository.findById((long) userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return existingUser;
    }

    public double getUserFiatBalance(int userId){
        Account existingUser = userRepository.findById((long) userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return existingUser.getUsdtBalance();
    }

    public double getUserEthBalance(int userId){
        Account existingUser = userRepository.findById((long) userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return existingUser.getEthBalance();
    }

    public double getUserBtcBalance(int userId){
        Account existingUser = userRepository.findById((long) userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return existingUser.getBtcBalance();
    }

    public boolean updateUserWallet(int userId, double usdtBalance, double ethBalance, double btcBalance) {
        Account existingUser = userRepository.findById((long) userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        existingUser.setUsdtBalance(usdtBalance);
        existingUser.setEthBalance(ethBalance);
        existingUser.setBtcBalance(btcBalance);
        userRepository.save(existingUser);
        return true;
    }
}
