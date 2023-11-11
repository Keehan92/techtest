package com.example.techtest.service;

import com.example.techtest.entity.Pricing;
import com.example.techtest.entity.Trading;
import com.example.techtest.repository.TradingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradingService {

    private final TradingRepository tradingRepository;

    @Autowired
    public TradingService(TradingRepository tradingRepository) {
        this.tradingRepository = tradingRepository;
    }

    public List<Trading> getTradingHistory() {
        return tradingRepository.findAllByOrderByTimestampDesc();
    }

    public boolean saveTransactionRecord(String pairType, String tradeType, int userId, double fiatAmount, double cryptoAmount, double cryptoPrice) {
        Trading entity = new Trading();
        entity.setPairType(pairType);
        entity.setTradeType(tradeType);
        entity.setUserId(userId);
        entity.setFiatAmount(fiatAmount);
        entity.setCryptoAmount(cryptoAmount);
        entity.setCryptoPrice(cryptoPrice);
        tradingRepository.save(entity);
        return true;
    }
}
