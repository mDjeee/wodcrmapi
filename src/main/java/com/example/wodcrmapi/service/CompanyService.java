package com.example.wodcrmapi.service;

import com.example.wodcrmapi.dto.request.CreateCompanyRequest;
import com.example.wodcrmapi.entity.Company;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.exception.NotFoundException;
import com.example.wodcrmapi.repository.CompanyRepository;
import com.example.wodcrmapi.security.SecurityUtils;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final SecurityUtils securityUtils;

    public CompanyService(CompanyRepository companyRepository, ModelMapper modelMapper, SecurityUtils securityUtils) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.securityUtils = securityUtils;
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

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
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
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

    @Transactional
    public void deleteCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new NotFoundException("Company not found with id: " + id);
        }
        companyRepository.deleteById(id);
    }
}
