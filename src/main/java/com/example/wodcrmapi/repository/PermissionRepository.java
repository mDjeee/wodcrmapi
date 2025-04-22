package com.example.wodcrmapi.repository;

import com.example.wodcrmapi.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
    boolean existsByName(String name);

    @Query("SELECT p FROM Permission p WHERE p.isSuper = false")
    List<Permission> findNonSuperPermissions();
}
