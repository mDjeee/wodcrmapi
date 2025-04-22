package com.example.wodcrmapi.controller;

import com.example.wodcrmapi.aop.CheckPermission;
import com.example.wodcrmapi.dto.request.CompanyFilterRequest;
import com.example.wodcrmapi.dto.request.CreateCompanyRequest;
import com.example.wodcrmapi.dto.request.PaginationRequest;
import com.example.wodcrmapi.dto.response.CompanyResponse;
import com.example.wodcrmapi.dto.response.PaginatedResponse;
import com.example.wodcrmapi.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @CheckPermission(
            value = "COMPANY:READALL",
            description = "Get all companies",
            displayName = "Получение списка компаний",
            type = "admin"
    )
    @Operation(summary = "Get all companies", description = "Returns a list of all companies")
    public ResponseEntity<PaginatedResponse<CompanyResponse>> getAllCompanies(
            @ModelAttribute PaginationRequest paginationParams,
            @ModelAttribute CompanyFilterRequest companyFilterRequest
    ) {
        return companyService.getAllCompanies(paginationParams, companyFilterRequest);
    }

    @PostMapping
    @CheckPermission(
            value = "COMPANY:CREATE",
            description = "Create company",
            displayName = "Добавление компаний",
            type = "admin"
    )
    @Operation(summary = "Create a new company", description = "Adds a new company")
    public CompanyResponse createCompany(@Valid @RequestBody CreateCompanyRequest request) throws BadRequestException {
        return companyService.createCompany(request);
    }

    @GetMapping("/{id}")
    @CheckPermission(
            value = "COMPANY:READ",
            description = "Get company",
            displayName = "Получение компаний",
            type = "admin"
    )
    @Operation(summary = "Get company by id", description = "Returns company by ID")
    public CompanyResponse getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    @PutMapping("/{id}")
    @CheckPermission(
            value = "COMPANY:UPDATE",
            description = "Update company",
            displayName = "Обновление компаний",
            type = "admin"
    )
    @Operation(summary = "Update company by id", description = "Updates and returns company")
    public CompanyResponse updateCompany(@PathVariable Long id, @RequestBody CreateCompanyRequest company) {
        return companyService.updateCompany(id, company);
    }

    @DeleteMapping("/{id}")
    @CheckPermission(
            value = "COMPANY:DELETE",
            description = "Delete company",
            displayName = "Удаление компаний",
            type = "admin"
    )
    @Operation(summary = "Delete company by id", description = "Deletes company by ID")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }
}
