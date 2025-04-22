package com.example.wodcrmapi.dto.request;

import com.example.wodcrmapi.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BindSubscriptionRequest {
    private Long clientId;
    private Long dealId;
    private LocalDate startDate;
    private Payment.PaymentMethod paymentMethod;
}
