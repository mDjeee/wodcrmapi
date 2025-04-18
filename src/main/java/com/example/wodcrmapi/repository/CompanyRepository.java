package com.example.wodcrmapi.repository;

import com.example.wodcrmapi.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
    Optional<Company> findByDomain(String domain);
    List<Company> findBySubscriptionEndDateBetween(LocalDate start, LocalDate end);
    List<Company> findBySubscriptionEndDateBeforeAndActiveTrue(LocalDate date);

    boolean existsByName(String name);

    Page<Company> findAll(Specification<Company> spec, Pageable pageable);
}
