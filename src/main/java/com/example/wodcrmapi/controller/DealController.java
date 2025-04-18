package com.example.wodcrmapi.controller;

import com.example.wodcrmapi.aop.CheckPermission;
import com.example.wodcrmapi.dto.request.DealFilterRequest;
import com.example.wodcrmapi.dto.request.DealRequest;
import com.example.wodcrmapi.dto.request.PaginationRequest;
import com.example.wodcrmapi.dto.response.DealResponse;
import com.example.wodcrmapi.dto.response.PaginatedResponse;
import com.example.wodcrmapi.service.DealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deals")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Deal Management", description = "Endpoints for managing deals")
public class DealController {

    private final DealService dealService;

    @PostMapping
    @CheckPermission(value = "DEAL:CREATE", description = "Create deal", displayName = "Создание сделки")
    @Operation(summary = "Create a new deal",
            description = "Creates a new deal with the specified parameters")
    public ResponseEntity<DealResponse> createDeal(@Valid @RequestBody DealRequest request) {
        return new ResponseEntity<>(dealService.createDeal(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @CheckPermission(value = "DEAL:READ", description = "Get deal", displayName = "Получение сделки")
    @Operation(summary = "Get deal by ID",
            description = "Returns the deal details for the specified ID")
    public ResponseEntity<DealResponse> getDeal(@PathVariable Long id) {
        return ResponseEntity.ok(dealService.getDealById(id));
    }

    @GetMapping
    @CheckPermission(value = "DEAL:READALL", description = "Get all deals", displayName = "Получение списка сделок")
    @Operation(summary = "Get all deals (paginated)",
            description = "Returns a paginated list of deals with filtering options")
    public ResponseEntity<PaginatedResponse<DealResponse>> getAllDeals(
            @ModelAttribute PaginationRequest paginationRequest,
            @ModelAttribute DealFilterRequest filterRequest) {
        return ResponseEntity.ok(dealService.getAllDeals(paginationRequest, filterRequest));
    }

    @PutMapping("/{id}")
    @CheckPermission(value = "DEAL:UPDATE", description = "Update deal", displayName = "Обновление сделки")
    @Operation(summary = "Update deal by ID",
            description = "Updates the deal with the specified ID")
    public ResponseEntity<DealResponse> updateDeal(
            @PathVariable Long id,
            @Valid @RequestBody DealRequest request) {
        return ResponseEntity.ok(dealService.updateDeal(id, request));
    }

    @DeleteMapping("/{id}")
    @CheckPermission(value = "DEAL:DELETE", description = "Delete deal", displayName = "Удаление сделки")
    @Operation(summary = "Delete deal by ID",
            description = "Deletes the deal with the specified ID")
    public ResponseEntity<Void> deleteDeal(@PathVariable Long id) {
        dealService.deleteDeal(id);
        return ResponseEntity.noContent().build();
    }
}