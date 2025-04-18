package com.example.wodcrmapi.service;

import com.example.wodcrmapi.dto.request.CreateCompanyRequest;
import com.example.wodcrmapi.dto.request.PaginationRequest;
import com.example.wodcrmapi.dto.response.PaginatedResponse;
import com.example.wodcrmapi.entity.Company;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.exception.NotFoundException;
import com.example.wodcrmapi.repository.CompanyRepository;
import com.example.wodcrmapi.security.SecurityUtils;
import com.example.wodcrmapi.specifications.CompanySpecifications;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final SecurityUtils securityUtils;
    private final MessageService messageService;

    public CompanyService(
            CompanyRepository companyRepository,
            ModelMapper modelMapper, SecurityUtils securityUtils,
            MessageService messageService
            ) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.securityUtils = securityUtils;
        this.messageService = messageService;
    }

    public Company createCompany(CreateCompanyRequest request) throws BadRequestException {
        boolean isExists = companyRepository.existsByName(request.getName());
        if (isExists) {
            throw new BadRequestException("Company name already exists");
        }

        User currentUser = securityUtils.getCurrentUser();

        Company company = modelMapper.map(request, Company.class);
        company.setCreatedBy(currentUser);

        return companyRepository.save(company);
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public ResponseEntity<PaginatedResponse<Company>> getAllCompanies(PaginationRequest paginationRequest) {
        Specification<Company> spec = CompanySpecifications.withSearch(paginationRequest.getSearch());
        Page<Company> pageResult = companyRepository.findAll(spec, paginationRequest.toPageable());
        return ResponseEntity.ok(new PaginatedResponse<>(pageResult));
    }

    public Company updateCompany(Long id, Company company) {
        return companyRepository.findById(id)
                .map(existingCompany -> {
                    existingCompany.setName(company.getName());
                    existingCompany.setAddress(company.getAddress());
                    existingCompany.setPhone(company.getPhone());
                    // Add other fields as needed
                    return companyRepository.save(existingCompany);
                })
                .orElseThrow(() -> new NotFoundException("Company not found"));
    }

    @Transactional
    public void deleteCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new NotFoundException("Company not found with id: " + id);
        }
        companyRepository.deleteById(id);
    }
}
