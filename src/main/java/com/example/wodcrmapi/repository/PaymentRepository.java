package com.example.wodcrmapi.repository;

import com.example.wodcrmapi.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}