package com.example.wodcrmapi.controller;

import com.example.wodcrmapi.dto.request.CreateCompanyRequest;
import com.example.wodcrmapi.entity.Company;
import com.example.wodcrmapi.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
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
    @Operation(summary = "Get all companies", description = "Returns a list of all companies")
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @PostMapping
    @Operation(summary = "Create a new company", description = "Adds a new company")
    public Company createCompany(@RequestBody CreateCompanyRequest request) throws BadRequestException {
        return companyService.createCompany(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get company by id", description = "Returns company by ID")
    public Company getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update company by id", description = "Updates and returns company")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company company) {
        return companyService.updateCompany(id, company);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete company by id", description = "Deletes company by ID")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }
}
