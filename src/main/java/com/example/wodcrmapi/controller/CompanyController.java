package com.example.wodcrmapi.controller;

import com.example.wodcrmapi.aop.CheckPermission;
import com.example.wodcrmapi.aop.PaginationParams;
import com.example.wodcrmapi.dto.request.CreateCompanyRequest;
import com.example.wodcrmapi.dto.request.PaginationRequest;
import com.example.wodcrmapi.dto.response.PaginatedResponse;
import com.example.wodcrmapi.entity.Company;
import com.example.wodcrmapi.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "*")
@Tag(name = "Company Management", description = "Endpoints for managing companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    @CheckPermission(value = "COMPANY:READALL", description = "Get all companies", displayName = "Получение списка компаний")
    @Operation(summary = "Get all companies", description = "Returns a list of all companies")
    public ResponseEntity<PaginatedResponse<Company>> getAllCompanies(
            @PaginationParams PaginationRequest paginationReques
    ) {
        return companyService.getAllCompanies(paginationReques);
    }

    @PostMapping
    @CheckPermission(value = "COMPANY:CREATE", description = "Create company", displayName = "Добавление компаний")
    @Operation(summary = "Create a new company", description = "Adds a new company")
    public Company createCompany(@Valid @RequestBody CreateCompanyRequest request) throws BadRequestException {
        return companyService.createCompany(request);
    }

    @GetMapping("/{id}")
    @CheckPermission(value = "COMPANY:READ", description = "Get company", displayName = "Получение компаний")
    @Operation(summary = "Get company by id", description = "Returns company by ID")
    public Company getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    @PutMapping("/{id}")
    @CheckPermission(value = "COMPANY:UPDATE", description = "Update company", displayName = "Обновление компаний")
    @Operation(summary = "Update company by id", description = "Updates and returns company")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company company) {
        return companyService.updateCompany(id, company);
    }

    @DeleteMapping("/{id}")
    @CheckPermission(value = "COMPANY:DELETE", description = "DElete company", displayName = "Удаление компаний")
    @Operation(summary = "Delete company by id", description = "Deletes company by ID")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }
}
