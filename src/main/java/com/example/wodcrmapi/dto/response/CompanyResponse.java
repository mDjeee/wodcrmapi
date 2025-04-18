package com.example.wodcrmapi.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanyResponse {
    private Long id;
    private String name;
    private String domain;
    private String address;
    private String phone;
    private String subscriptionStartDate;
    private String subscriptionEndDate;
    private Boolean active;
    private UserResponse createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
