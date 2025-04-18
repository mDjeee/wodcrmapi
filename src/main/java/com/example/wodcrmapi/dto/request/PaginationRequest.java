package com.example.wodcrmapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
public class PaginationRequest {
    @Min(value = 0, message = "Page number must not be less than zero")
    private int page = 0;

    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size must not exceed 100")
    private int size = 20;

    @Schema(
            example = "id",
            description = "Optional search term to filter results",
            nullable = true
    )
    private String sortBy = "id";

    @Schema(
            example = "asc",
            description = "Optional search term to filter results",
            nullable = true
    )
    private String sortDirection = "asc";

    @Size(max = 100, message = "Search term must not exceed 100 characters")
    @Schema(
            example = " ",
            description = "Optional search term to filter results",
            nullable = true
    )
    private String search;

    public Pageable toPageable() {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Sort sort = Sort.by(direction, sortBy);
        return PageRequest.of(page, size, sort);
    }

    public boolean hasSearchTerm() {
        return search != null && !search.trim().isEmpty();
    }

    public PaginationRequest() {}
}