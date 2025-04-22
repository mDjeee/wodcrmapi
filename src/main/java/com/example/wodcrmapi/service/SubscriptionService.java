package com.example.wodcrmapi.service;

import com.example.wodcrmapi.dto.request.BindSubscriptionRequest;
import com.example.wodcrmapi.entity.Client;
import com.example.wodcrmapi.entity.Deal;
import com.example.wodcrmapi.entity.Payment;
import com.example.wodcrmapi.entity.Subscription;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.exception.NotFoundException;
import com.example.wodcrmapi.repository.ClientRepository;
import com.example.wodcrmapi.repository.DealRepository;
import com.example.wodcrmapi.repository.PaymentRepository;
import com.example.wodcrmapi.repository.SubscriptionRepository;
import com.example.wodcrmapi.security.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final ClientRepository clientRepository;
    private final DealRepository dealRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;
    private final SecurityUtils securityUtils;

    @Transactional
    public Subscription bindSubscriptionWithPayment(BindSubscriptionRequest request) {
        // Fetch client and deal
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new NotFoundException("Client not found"));
        Deal deal = dealRepository.findById(request.getDealId())
                .orElseThrow(() -> new NotFoundException("Deal not found"));

        // Calculate end date
        LocalDate endDate = request.getStartDate().plusMonths(deal.getDurationMonths());

        User currentUser = securityUtils.getCurrentUser();

        // Create subscription
        Subscription subscription = Subscription.builder()
                .client(client)
                .deal(deal)
                .startDate(request.getStartDate())
                .endDate(endDate)
                .companyId(currentUser.getCompanyId())
                .active(true)
                .build();

        // Create payment
        Payment payment = Payment.builder()
                .subscription(subscription)
                .amount(deal.getPrice())
                .paymentDate(LocalDate.now())
                .companyId(currentUser.getCompanyId())
                .paymentMethod(request.getPaymentMethod())
                .paid(true)
                .build();

        // Save both
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        paymentRepository.save(payment);

        // Update client's subscription dates
        client.setSubscriptionStartDate(request.getStartDate());
        client.setSubscriptionEndDate(endDate);
        client.setActive(true);
        clientRepository.save(client);

        return savedSubscription;
    }
}
