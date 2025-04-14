package com.example.wodcrmapi.service;

import com.example.wodcrmapi.entity.Permission;
import com.example.wodcrmapi.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionRegistryService {
    private final PermissionRepository permissionRepository;

    // Standard CRUD permissions for any entity
    private static final List<String> CRUD_ACTIONS = List.of("create", "read", "update", "delete");

    public void registerPermissionsForEntity(String entityName) {
        CRUD_ACTIONS.forEach(action -> {
            String permissionName = entityName.toLowerCase() + ":" + action;
            permissionRepository.findByName(permissionName).orElseGet(() -> {
                Permission permission = new Permission();
                permission.setName(permissionName);
                permission.setDescription("Allows " + action + " on " + entityName);
                return permissionRepository.save(permission);
            });
        });
    }
}