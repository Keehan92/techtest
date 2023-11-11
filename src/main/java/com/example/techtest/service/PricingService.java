package com.example.techtest.service;

import com.example.techtest.entity.Pricing;
import com.example.techtest.repository.PricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PricingService {

    private final PricingRepository pricingRepository;

    @Autowired
    public PricingService(PricingRepository pricingRepository) {
        this.pricingRepository = pricingRepository;
    }

    @Transactional
    public Pricing savePrice(Pricing price) {
        return pricingRepository.save(price);
    }

    public Pricing getLatestPrice() {
        return pricingRepository.findFirstByOrderByTimestampDesc();
    }

    public double getLatestPriceByPairTypeAndTradeType(String pairType, String tradeType) {

        Pricing pricing = pricingRepository.findFirstByOrderByTimestampDesc();
        if(pairType.equals("BTC") && tradeType.equals("B")){
            return pricing.getBtcUsdtBuyPrice();
        }
        if(pairType.equals("BTC") && tradeType.equals("S")){
            return pricing.getBtcUsdtSellPrice();
        }
        if(pairType.equals("ETH") && tradeType.equals("B")){
            return pricing.getEthUsdtBuyPrice();
        }
        if(pairType.equals("ETH") && tradeType.equals("S")){
            return pricing.getEthUsdtSellPrice();
        }
        return 0;
   }
}
