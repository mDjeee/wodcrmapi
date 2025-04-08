package com.example.wodcrmapi.repository;

import com.example.wodcrmapi.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByDomain(String domain);
    List<Company> findBySubscriptionEndDateBetween(LocalDate start, LocalDate end);
    List<Company> findBySubscriptionEndDateBeforeAndActiveTrue(LocalDate date);
}
