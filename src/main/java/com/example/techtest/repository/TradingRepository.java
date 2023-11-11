package com.example.techtest.repository;

import com.example.techtest.entity.Pricing;
import com.example.techtest.entity.Trading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradingRepository extends JpaRepository<Trading, Long> {

    List<Trading> findAllByOrderByTimestampDesc();

}
