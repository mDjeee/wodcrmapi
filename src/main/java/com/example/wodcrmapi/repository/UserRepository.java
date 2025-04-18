package com.example.wodcrmapi.repository;

import com.example.wodcrmapi.entity.Company;
import com.example.wodcrmapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
