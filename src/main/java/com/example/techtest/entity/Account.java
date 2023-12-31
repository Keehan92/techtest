package com.example.techtest.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
public class Account {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String name;

    private double usdtBalance;

    private double btcBalance;

    private double ethBalance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUsdtBalance() {
        return usdtBalance;
    }

    public void setUsdtBalance(double usdtBalance) {
        this.usdtBalance = usdtBalance;
    }

    public double getBtcBalance() {
        return btcBalance;
    }

    public void setBtcBalance(double btcBalance) {
        this.btcBalance = btcBalance;
    }

    public double getEthBalance() {
        return ethBalance;
    }

    public void setEthBalance(double ethBalance) {
        this.ethBalance = ethBalance;
    }
}
