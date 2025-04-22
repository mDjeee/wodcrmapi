package com.example.wodcrmapi.service;

import com.example.wodcrmapi.dto.request.DealFilterRequest;
import com.example.wodcrmapi.dto.request.DealRequest;
import com.example.wodcrmapi.dto.request.PaginationRequest;
import com.example.wodcrmapi.dto.response.DealResponse;
import com.example.wodcrmapi.dto.response.PaginatedResponse;
import com.example.wodcrmapi.entity.Company;
import com.example.wodcrmapi.entity.Deal;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.exception.NotFoundException;
import com.example.wodcrmapi.mapper.DealMapper;
import com.example.wodcrmapi.repository.CompanyRepository;
import com.example.wodcrmapi.repository.DealRepository;
import com.example.wodcrmapi.security.SecurityUtils;
import com.example.wodcrmapi.specifications.DealSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealRepository dealRepository;
    private final CompanyRepository companyRepository;
    private final DealMapper dealMapper;
    private final SecurityUtils securityUtils;

    @Transactional
    public ResponseEntity<DealResponse> createDeal(DealRequest request) {
        User currentUser = securityUtils.getCurrentUser();

        Long companyId = Optional.ofNullable(request.getCompanyId())
                .orElseGet(currentUser::getCompanyId);

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company not found with id: " + companyId));


        Deal deal = new Deal();
        deal.setName(request.getName());
        deal.setPrice(request.getPrice());
        deal.setDurationMonths(request.getDurationMonths());
        deal.setCompany(company);


        Deal savedDeal = dealRepository.save(deal);
        DealResponse response = dealMapper.toResponse(savedDeal);
        return ResponseEntity.ok(response);
    }

    public DealResponse getDealById(Long id) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Deal not found"));
        return dealMapper.toResponse(deal);
    }

    public PaginatedResponse<DealResponse> getAllDeals(
            PaginationRequest paginationRequest,
            DealFilterRequest filterRequest) {

        Specification<Deal> spec = Specification.where(DealSpecifications.withSearch(paginationRequest.getSearch()))
                .and(DealSpecifications.withCompany(filterRequest.getCompanyId()))
                .and(DealSpecifications.withPriceRange(filterRequest.getMinPrice(), filterRequest.getMaxPrice()))
                .and(DealSpecifications.withCurrency(filterRequest.getCurrency()));

        Page<Deal> pageResult = dealRepository.findAll(spec, paginationRequest.toPageable());
        Page<DealResponse> responsePage = pageResult.map(dealMapper::toResponse);

        return new PaginatedResponse<>(responsePage);
    }

    @Transactional
    public DealResponse updateDeal(Long id, DealRequest request) {
        Deal existingDeal = dealRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Deal not found with id: " + id));

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company not found with id: " + request.getCompanyId()));

        // Update fields from request, ignoring the ones we want to manage manually
        dealMapper.updateEntity(request, existingDeal);
        existingDeal.setCompany(company);

        Deal updatedDeal = dealRepository.save(existingDeal);
        return dealMapper.toResponse(updatedDeal);
    }

    @Transactional
    public void deleteDeal(Long id) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Deal not found"));
        dealRepository.delete(deal);
    }
}