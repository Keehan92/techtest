package com.example.techtest.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
public class Trading {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private int userId;

    private String pairType;

    private String tradeType;

    private double fiatAmount;

    private double cryptoAmount;

    private double cryptoPrice;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPairType() {
        return pairType;
    }

    public void setPairType(String pairType) {
        this.pairType = pairType;
    }

    public double getFiatAmount() {
        return fiatAmount;
    }

    public void setFiatAmount(double fiatAmount) {
        this.fiatAmount = fiatAmount;
    }

    public double getCryptoAmount() {
        return cryptoAmount;
    }

    public void setCryptoAmount(double cryptoAmount) {
        this.cryptoAmount = cryptoAmount;
    }

    public double getCryptoPrice() {
        return cryptoPrice;
    }

    public void setCryptoPrice(double cryptoPrice) {
        this.cryptoPrice = cryptoPrice;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}
