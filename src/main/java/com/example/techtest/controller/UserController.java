package com.example.techtest.controller;

import com.example.techtest.entity.Account;
import com.example.techtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/wallet")
    public Account getWalletBalance() {
        int id = 1;//hardcode
        return userService.getUserWalletBalance(id);
    }

}