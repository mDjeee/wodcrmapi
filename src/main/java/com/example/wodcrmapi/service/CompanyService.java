package com.example.wodcrmapi.service;

import com.example.wodcrmapi.dto.request.CreateCompanyRequest;
import com.example.wodcrmapi.dto.request.PaginationRequest;
import com.example.wodcrmapi.dto.response.CompanyResponse;
import com.example.wodcrmapi.dto.response.PaginatedResponse;
import com.example.wodcrmapi.entity.Company;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.exception.NotFoundException;
import com.example.wodcrmapi.mapper.CompanyMapper;
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

    private final CompanyMapper companyMapper;


    public CompanyService(
            CompanyRepository companyRepository,
            ModelMapper modelMapper,
            SecurityUtils securityUtils,
            CompanyMapper companyMapper
    ) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.securityUtils = securityUtils;
        this.companyMapper = companyMapper;
    }

    public CompanyResponse createCompany(CreateCompanyRequest request) throws BadRequestException {
        boolean isExists = companyRepository.existsByName(request.getName());
        if (isExists) {
            throw new BadRequestException("Company name already exists");
        }

        User currentUser = securityUtils.getCurrentUser();

        Company company = modelMapper.map(request, Company.class);
        company.setCreatedBy(currentUser);

        Company savedCompany = companyRepository.save(company);

        return modelMapper.map(savedCompany, CompanyResponse.class);
    }

    public CompanyResponse getCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new NotFoundException("Company not found"));
        return modelMapper.map(company, CompanyResponse.class);
    }

    public ResponseEntity<PaginatedResponse<CompanyResponse>> getAllCompanies(PaginationRequest paginationRequest) {
        Specification<Company> spec = CompanySpecifications.withSearch(paginationRequest.getSearch());
        Page<Company> pageResult = companyRepository.findAll(spec, paginationRequest.toPageable());
        Page<CompanyResponse> responsePage = pageResult.map(companyMapper::toResponse);

        return ResponseEntity.ok(new PaginatedResponse<>(responsePage));
    }

    public CompanyResponse updateCompany(Long id, CreateCompanyRequest company) {
        return companyRepository.findById(id)
                .map(existingCompany -> {
                    existingCompany.setName(company.getName());
                    existingCompany.setAddress(company.getAddress());
                    existingCompany.setPhone(company.getPhone());
                    existingCompany.setDomain(company.getDomain());
                    // Add other fields as needed
                    Company updatedCompany = companyRepository.save(existingCompany);
                    CompanyResponse companyResponse = modelMapper.map(updatedCompany, CompanyResponse.class);

                    return companyResponse;
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
