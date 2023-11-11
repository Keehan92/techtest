package com.example.techtest.repository;

import com.example.techtest.entity.Pricing;
import com.example.techtest.entity.Trading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingRepository extends JpaRepository<Trading, Long> {

    Trading findFirstByOrderByTimestampDesc();

}
