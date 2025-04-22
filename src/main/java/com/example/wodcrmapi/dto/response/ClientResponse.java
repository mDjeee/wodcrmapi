package com.example.wodcrmapi.dto.response;

import com.example.wodcrmapi.entity.Deal;
import com.example.wodcrmapi.entity.Subscription;
import com.example.wodcrmapi.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ClientResponse {
    private Long id;
    private Long companyId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<Subscription> subscriptions;
    private Deal deal;
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    private User createdBy;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
