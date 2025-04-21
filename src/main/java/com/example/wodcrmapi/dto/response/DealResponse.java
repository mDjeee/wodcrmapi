package com.example.wodcrmapi.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DealResponse {
    private Long id;
    private String name;
    private Integer durationMonths;
    private Integer billingDay;
    private Double price;
    private String currency;
    private Long companyId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
