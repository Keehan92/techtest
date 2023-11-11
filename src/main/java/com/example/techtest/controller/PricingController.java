package com.example.techtest.controller;

import com.example.techtest.entity.Pricing;
import com.example.techtest.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PricingController {

    private final PricingService pricingService;

    @Autowired
    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @PostMapping("/createprice")
    public Pricing createPrice(@RequestBody Pricing pricing) {
        return pricingService.savePrice(pricing);
    }

    @GetMapping("/price")
    public Pricing getPrice() {
        return pricingService.getLatestPrice();
    }
}
