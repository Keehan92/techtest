package com.example.techtest.service;

import com.example.techtest.entity.Pricing;
import com.example.techtest.repository.PricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.function.Function;

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
        //getLatestPriceByPairTypeAndTradeType
        Pricing pricing = pricingRepository.findFirstByOrderByTimestampDesc();

        Map<String, Function<Pricing, Double>> priceMapping = Map.of(
                "BTCB", Pricing::getBtcUsdtBuyPrice,
                "BTCS", Pricing::getBtcUsdtSellPrice,
                "ETHB", Pricing::getEthUsdtBuyPrice,
                "ETHS", Pricing::getEthUsdtSellPrice
        );

        String key = pairType + tradeType;
        return priceMapping.getOrDefault(key, p -> 0.0).apply(pricing);
    }

}
