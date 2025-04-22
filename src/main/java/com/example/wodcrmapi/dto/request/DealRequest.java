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

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    private Long companyId;
}