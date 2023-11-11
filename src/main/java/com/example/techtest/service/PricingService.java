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
}
