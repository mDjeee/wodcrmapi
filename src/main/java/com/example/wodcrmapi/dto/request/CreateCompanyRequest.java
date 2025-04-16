package com.example.wodcrmapi.dto.request;

import jakarta.validation.constraints.*;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreateCompanyRequest {

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Domain is required")
    @Pattern(regexp = "^([a-z0-9]+(-[a-z0-9]+)*\\.)+[a-z]{2,}$",
            message = "Invalid domain format")
    private String domain;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9\\-\\s()]{7,20}$",
            message = "Invalid phone number format")
    private String phone;

    @NotNull(message = "Subscription start date is required")
    @FutureOrPresent(message = "Subscription start date must be today or in the future")
    private LocalDate subscriptionStartDate;

    @NotNull(message = "Subscription end date is required")
    @Future(message = "Subscription end date must be in the future")
    private LocalDate subscriptionEndDate;

    @AssertTrue(message = "End date must be after start date")
    private boolean isEndDateAfterStartDate() {
        if (subscriptionStartDate == null || subscriptionEndDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return subscriptionEndDate.isAfter(subscriptionStartDate);
    }

    private boolean active;
}
