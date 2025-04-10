package com.example.wodcrmapi.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class CreateCompanyRequest {
    private String name;
    private String domain;
    private String address;
    private String phone;
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    private boolean active;
}
