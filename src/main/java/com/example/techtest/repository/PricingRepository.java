package com.example.techtest.repository;

import com.example.techtest.entity.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingRepository extends JpaRepository<Pricing, Long> {

    Pricing findFirstByOrderByTimestampDesc();

}