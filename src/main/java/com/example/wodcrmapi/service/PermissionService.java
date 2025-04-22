package com.example.wodcrmapi.service;

import com.example.wodcrmapi.entity.Permission;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.repository.PermissionRepository;
import com.example.wodcrmapi.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final SecurityUtils securityUtils;

    public PermissionService(
            PermissionRepository permissionRepository,
            SecurityUtils securityUtils
            ) {
        this.permissionRepository = permissionRepository;
        this.securityUtils = securityUtils;
    }

    public List<Permission> getAllPermissions() {
        User currentUser = securityUtils.getCurrentUser();

        if (currentUser.getSuperAdmin()) {
            return permissionRepository.findAll();
        } else {
            return permissionRepository.findNonSuperPermissions();
        }
    }

    public Permission getPermissionById(Long id) {
        return permissionRepository.findById(id).orElse(null);
    }
}
