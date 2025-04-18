package com.example.wodcrmapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DealFilterRequest {
    @Schema(description = "Filter by active status", example = "true", nullable = true)
    private Boolean active;

    @Schema(description = "Filter by company ID", example = "1", nullable = true)
    private Long companyId;

    @Schema(description = "Minimum price filter", example = "100.0", nullable = true)
    private Double minPrice;

    @Schema(description = "Maximum price filter", example = "1000.0", nullable = true)
    private Double maxPrice;

    @Schema(description = "Filter by currency", example = "USD", nullable = true)
    private String currency;
}