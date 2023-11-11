package com.example.techtest.controller;

import com.example.techtest.entity.Trading;
import com.example.techtest.service.PricingService;
import com.example.techtest.service.TradingService;
import com.example.techtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TradingController {

    private final TradingService tradingService;
    private final PricingService pricingService;
    private final UserService userService;

    @Autowired
    public TradingController(TradingService tradingService, PricingService pricingService,UserService userService) {
        this.tradingService = tradingService;
        this.pricingService = pricingService;
        this.userService = userService;
    }

    @PostMapping("/trade")
    public ResponseEntity<BooleanResponse> executeTrade(@RequestBody TradeRequest request) {
        String pairType = request.getPairType();
        double cryptoAmount = request.getAmount();
        String tradeType = request.getTradeType();
        int userId = 1;

        double fiatBalance = userService.getUserFiatBalance(userId);
        double ethBalance = userService.getUserEthBalance(userId);
        double btcBalance = userService.getUserBtcBalance(userId);

        double cryptoPrice = pricingService.getLatestPriceByPairTypeAndTradeType(pairType, tradeType);
        double totalPrice = calculateTotalPrice(pairType, tradeType, cryptoAmount);

        double userCurrentBalance = fiatBalance;
        double userCurrentEthBalance = ethBalance;
        double userCurrentBtcBalance = btcBalance;

        if (tradeType.equals("B")) {
            boolean isAllowedToBuy = isValidBalanceFiat(userCurrentBalance, totalPrice);
            if (!isAllowedToBuy) {
                return new ResponseEntity<>(new BooleanResponse(false, "Invalid Fiat Balance"), HttpStatus.OK);
            } else {
                fiatBalance -= totalPrice;

                if (pairType.equals("BTC")) {
                    btcBalance += cryptoAmount;
                } else {
                    ethBalance += cryptoAmount;
                }
            }
        } else {
            if (pairType.equals("BTC")) {
                boolean isAllowedToSell = isValidBalanceCrypto(userCurrentBtcBalance, cryptoAmount);
                if (!isAllowedToSell) {
                    return new ResponseEntity<>(new BooleanResponse(false, "Invalid BTC Balance"), HttpStatus.OK);
                } else {
                    fiatBalance += totalPrice;
                    btcBalance -= cryptoAmount;
                }
            } else {
                boolean isAllowedToSell = isValidBalanceCrypto(userCurrentEthBalance, cryptoAmount);
                if (!isAllowedToSell) {
                    return new ResponseEntity<>(new BooleanResponse(false, "Invalid ETH Balance"), HttpStatus.OK);
                } else {
                    fiatBalance += totalPrice;
                    ethBalance -= cryptoAmount;
                }
            }
        }

        boolean result = tradingService.saveTransactionRecord(pairType, tradeType, userId, totalPrice, cryptoAmount, cryptoPrice);
        boolean updateUserBalanceStatus = userService.updateUserWallet(userId, fiatBalance, ethBalance, btcBalance);
        System.out.println("updateUserBalanceStatus " +updateUserBalanceStatus);//log down to check incase of error
        BooleanResponse response = new BooleanResponse(result, "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/history")
    public List<Trading> getTrading() {
        return tradingService.getTradingHistory();
    }

    // Request DTO
    public static class TradeRequest {
        private String pairType;
        private double amount;
        private String tradeType;

        public String getPairType() {
            return pairType;
        }

        public void setPairType(String pairType) {
            this.pairType = pairType;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }
    }

    // Response DTO
    public static class BooleanResponse {
        private final boolean result;
        private final String errorMsg;
        public BooleanResponse(boolean result, String errorMsg) {
            this.result = result; this.errorMsg = errorMsg;
        }

        public boolean isResult() {
            return result;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

    }

    public double calculateTotalPrice(String pairType,String tradeType, double cryptoAmount){
        double cryptoPrice = pricingService.getLatestPriceByPairTypeAndTradeType(pairType,tradeType);
        System.out.println(("Best "+tradeType + " " +pairType +" = " + cryptoPrice));
        System.out.println(("Total Price  = " + cryptoPrice*cryptoAmount));
        return cryptoPrice*cryptoAmount;
    }

    public boolean isValidBalanceFiat(double currentBalance, double totalPrice){
        boolean status = false;

        if(currentBalance >= totalPrice){
            status = true;
        }
        return status;
    }

    public boolean isValidBalanceCrypto(double currentAmount, double sellingAmount){
        boolean status = false;

        if(currentAmount >= sellingAmount){
            status = true;
        }
        return status;
    }

}
