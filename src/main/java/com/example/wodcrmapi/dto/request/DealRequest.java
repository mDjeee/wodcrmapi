package com.example.wodcrmapi.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DealRequest {
    @NotBlank(message = "Deal name is required")
    @Size(max = 100, message = "Deal name cannot exceed 100 characters")
    private String name;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 month")
    @Max(value = 36, message = "Duration cannot exceed 36 months")
    private Integer durationMonths;

    @NotNull(message = "Billing day is required")
    @Min(value = 1, message = "Billing day must be between 1-31")
    @Max(value = 31, message = "Billing day must be between 1-31")
    private Integer billingDay;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    private String currency;

    @NotNull(message = "Active status is required")
    private Boolean active;

    @NotNull(message = "Company ID is required")
    private Long companyId;
}