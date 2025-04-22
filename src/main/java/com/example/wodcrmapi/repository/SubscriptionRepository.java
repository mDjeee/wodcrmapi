package com.example.wodcrmapi.repository;

import com.example.wodcrmapi.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}