package com.example.wodcrmapi.controller;

import com.example.wodcrmapi.aop.CheckPermission;
import com.example.wodcrmapi.dto.request.BindSubscriptionRequest;
import com.example.wodcrmapi.entity.Subscription;
import com.example.wodcrmapi.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/bind")
    @CheckPermission(value = "SUBSCRIPTION:CREATE", description = "Create subscription", displayName = "Создание подписки")
    @Operation(summary = "Create subscription", description = "Creates a subscription")
    public ResponseEntity<Subscription> bindSubscription(@RequestBody BindSubscriptionRequest request) {
        Subscription subscription = subscriptionService.bindSubscriptionWithPayment(request);
        return ResponseEntity.ok(subscription);
    }
}