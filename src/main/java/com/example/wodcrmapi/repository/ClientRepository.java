package com.example.wodcrmapi.repository;

import com.example.wodcrmapi.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByCompanyId(Long companyId);
    List<Client> findByCompanyIdAndActive(Long companyId, boolean active);
    Optional<Client> findByIdAndCompanyId(Long id, Long companyId);
    List<Client> findBySubscriptionEndDateBetweenAndActiveTrue(LocalDate start, LocalDate end);
    List<Client> findBySubscriptionEndDateBeforeAndActiveTrue(LocalDate date);
    Boolean existsClientByPhone(String phoneNumber);

    Page<Client> findAll(Specification<Client> spec, Pageable pageable);
}
