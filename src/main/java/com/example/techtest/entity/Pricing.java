package com.example.techtest.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
public class Pricing {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private double ethUsdtBuyPrice;

    private double ethUsdtSellPrice;

    private double btcUsdtBuyPrice;

    private double btcUsdtSellPrice;

    @Column(name="timestamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Timestamp timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getEthUsdtBuyPrice() {
        return ethUsdtBuyPrice;
    }

    public void setEthUsdtBuyPrice(double ethUsdtBuyPrice) {
        this.ethUsdtBuyPrice = ethUsdtBuyPrice;
    }

    public double getEthUsdtSellPrice() {
        return ethUsdtSellPrice;
    }

    public void setEthUsdtSellPrice(double ethUsdtSellPrice) {
        this.ethUsdtSellPrice = ethUsdtSellPrice;
    }

    public double getBtcUsdtBuyPrice() {
        return btcUsdtBuyPrice;
    }

    public void setBtcUsdtBuyPrice(double btcUsdtBuyPrice) {
        this.btcUsdtBuyPrice = btcUsdtBuyPrice;
    }

    public double getBtcUsdtSellPrice() {
        return btcUsdtSellPrice;
    }

    public void setBtcUsdtSellPrice(double btcUsdtSellPrice) {
        this.btcUsdtSellPrice = btcUsdtSellPrice;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}