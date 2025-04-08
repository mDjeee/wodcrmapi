package com.example.wodcrmapi.repository;

import com.example.wodcrmapi.entity.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {
}
