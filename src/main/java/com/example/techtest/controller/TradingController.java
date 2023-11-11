package com.example.techtest.controller;

import com.example.techtest.entity.Pricing;
import com.example.techtest.entity.Trading;
import com.example.techtest.service.PricingService;
import com.example.techtest.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradingController {

    private final TradingService tradingService;
    private final PricingService pricingService;

    @Autowired
    public TradingController(TradingService tradingService, PricingService pricingService) {
        this.tradingService = tradingService;
        this.pricingService = pricingService;
    }

//    @PostMapping("/buy")
//    public Trading createTrade(@RequestBody Pricing pricing) {
//        return pricingService.savePrice(pricing);
//    }
    @PostMapping("/buy")
    public ResponseEntity<BooleanResponse> printParams(@RequestBody YourRequest request) {
        // Save the input parameters to the database
        String pairType = request.getParam1();
        System.out.println("pairType " +pairType);
        String cryptoAmountString = request.getParam2();
        String tradeType = request.getParam3();
        double fiatAmount = 0;
        double cryptoPrice = pricingService.getLatestPriceByPairTypeAndTradeType(pairType,tradeType);
        double cryptoAmount = Double.parseDouble(cryptoAmountString);
        double totalPrice = calculateTotalPrice(pairType, tradeType, cryptoAmount);
        int userId = 1;
        boolean result = tradingService.saveTransactionRecord(pairType, tradeType,userId, totalPrice, cryptoAmount, cryptoPrice);

        // Create a response object
        BooleanResponse response = new BooleanResponse(result);

        // Return a ResponseEntity with the response object and HTTP status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/history")
    public Trading getTrading() {
        return tradingService.getTradingHistory();
    }

    // Request DTO
    public static class YourRequest {
        private String param1;
        private String param2;
        private String param3;

        public String getParam1() {
            return param1;
        }

        public void setParam1(String param1) {
            this.param1 = param1;
        }

        public String getParam2() {
            return param2;
        }

        public void setParam2(String param2) {
            this.param2 = param2;
        }

        public String getParam3() {
            return param3;
        }

        public void setParam3(String param3) {
            this.param3 = param3;
        }
    }

    // Response DTO
    public static class BooleanResponse {
        private final boolean result;

        public BooleanResponse(boolean result) {
            this.result = result;
        }

        public boolean isResult() {
            return result;
        }
    }

    public double calculateTotalPrice(String pairType,String tradeType, double cryptoAmount){
        double cryptoPrice = pricingService.getLatestPriceByPairTypeAndTradeType(pairType,tradeType);
        System.out.println(("Best "+tradeType + " " +pairType +" = " + cryptoPrice));
        System.out.println(("Total Price  = " + cryptoPrice*cryptoAmount));
        return cryptoPrice*cryptoAmount;
    }

}
