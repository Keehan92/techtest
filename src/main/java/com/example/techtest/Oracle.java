package com.example.techtest;

import com.example.techtest.entity.Pricing;
import com.example.techtest.service.PricingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.Iterator;

@Configuration
@EnableAsync
@EnableScheduling
public class Oracle {

    public static final String BINANCE_URL = "https://api.binance.com/api/v3/ticker/bookTicker";
    public static final String HUOBI_URL = "https://api.huobi.pro/market/tickers";
    @Autowired
    PricingService pricingService;

    @Scheduled(initialDelay = 1000, fixedRate = 5000)
    public void pricingUpdate() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "Fixed rate task with one second initial delay - " + now);

        try {
            // Binance API
            JsonNode binanceBtcPriceData = getApiData(BINANCE_URL, "BTCUSDT");
            JsonNode binanceEthPriceData = getApiData(BINANCE_URL, "ETHUSDT");

            // Huobi API
            JsonNode huobiBtcPriceData = getApiData(HUOBI_URL, "btcusdt");
            JsonNode huobiEthPriceData = getApiData(HUOBI_URL, "ethusdt");

            double bestBtcBuyPrice = 0;
            double bestBtcSellPrice = 0;
            double bestEthBuyPrice = 0;
            double bestEthSellPrice = 0;

            // Compare bid and ask prices for BTC
            if (binanceBtcPriceData != null && huobiBtcPriceData != null) {
                double binanceBidPrice = binanceBtcPriceData.get("bidPrice").asDouble();
                double binanceAskPrice = binanceBtcPriceData.get("askPrice").asDouble();

                double huobiBidPrice = huobiBtcPriceData.get("bid").asDouble();
                double huobiAskPrice = huobiBtcPriceData.get("ask").asDouble();

                // Compare and print results
                if (binanceBidPrice > huobiBidPrice) {
                    System.out.println("Binance has the higher bid price: " + binanceBidPrice);
                    bestBtcSellPrice = binanceBidPrice;
                } else {
                    System.out.println("Huobi has the higher bid price: " + huobiBidPrice);
                    bestBtcSellPrice = huobiBidPrice;
                }

                if (binanceAskPrice < huobiAskPrice) {
                    System.out.println("Binance has the lower ask price: " + binanceAskPrice);
                    bestBtcBuyPrice = binanceAskPrice;
                } else {
                    System.out.println("Huobi has the lower ask price: " + huobiAskPrice);
                    bestBtcBuyPrice = huobiAskPrice;
                }
            }

            // Compare bid and ask prices for BTC
            if (binanceEthPriceData != null && huobiEthPriceData != null) {
                double binanceBidPrice = binanceEthPriceData.get("bidPrice").asDouble();
                double binanceAskPrice = binanceEthPriceData.get("askPrice").asDouble();

                double huobiBidPrice = huobiEthPriceData.get("bid").asDouble();
                double huobiAskPrice = huobiEthPriceData.get("ask").asDouble();

                // Compare and print results
                if (binanceBidPrice > huobiBidPrice) {
                    System.out.println("Binance has the higher bid price: " + binanceBidPrice);
                    bestEthSellPrice = binanceBidPrice;
                } else {
                    System.out.println("Huobi has the higher bid price: " + huobiBidPrice);
                    bestEthSellPrice = huobiBidPrice;
                }

                if (binanceAskPrice < huobiAskPrice) {
                    System.out.println("Binance has the lower ask price: " + binanceAskPrice);
                    bestEthBuyPrice = binanceAskPrice;
                } else {
                    System.out.println("Huobi has the lower ask price: " + huobiAskPrice);
                    bestEthBuyPrice = huobiAskPrice;
                }
            }

            Pricing price = new Pricing();
            price.setBtcUsdtSellPrice(bestBtcSellPrice);
            price.setBtcUsdtBuyPrice(bestBtcBuyPrice);
            price.setEthUsdtSellPrice(bestEthSellPrice);
            price.setEthUsdtBuyPrice(bestEthBuyPrice);
            pricingService.savePrice(price);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static JsonNode getApiData(String apiUrl, String symbol) throws IOException {
        System.out.println(apiUrl);
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.toString());

            // Assuming the JSON structure is an array for Huobi
            if (jsonNode.has("data") && jsonNode.get("data").isArray()) {
                for (JsonNode element : jsonNode.get("data")) {
                    if (element.has("symbol") && element.get("symbol").asText().equalsIgnoreCase(symbol)) {
                        String bidPrice = element.get("bid").asText();
                        String askPrice = element.get("ask").asText();

                        System.out.println("Symbol: " + symbol);
                        System.out.println("Bid Price: " + bidPrice);
                        System.out.println("Ask Price: " + askPrice);
                        return element;
                    }
                }
            } else {
                if (jsonNode.isArray()) {
                    Iterator<JsonNode> elements = jsonNode.elements();

                    while (elements.hasNext()) {
                        JsonNode element = elements.next();

                        // Check if the object has the symbol BTCUSDT
                        if (element.has("symbol") && element.get("symbol").asText().equals(symbol)) {
                            // Extract and print relevant information
                            String bidPrice = element.get("bidPrice").asText();
                            String askPrice = element.get("askPrice").asText();

                            System.out.println("Symbol: " + symbol);
                            System.out.println("Bid Price: " + bidPrice);
                            System.out.println("Ask Price: " + askPrice);
                            return element;
                        }
                    }
                } else {
                    System.out.println("Unexpected JSON structure. Expected an array at the top level.");
                }
            }
        } else {
            System.out.println("Error response code: " + responseCode);
        }

        connection.disconnect();
        return null;
    }

}
