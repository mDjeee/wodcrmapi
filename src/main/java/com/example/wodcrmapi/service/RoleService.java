package com.example.wodcrmapi.service;

import com.example.wodcrmapi.entity.Role;
import com.example.wodcrmapi.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> convertToRoles(Set<String> roleNames) {
        return roleNames.stream()
                .map(this::convertToRoleName)
                .map(this::getOrCreateRole)
                .collect(Collectors.toSet());
    }

    private Role.RoleName convertToRoleName(String roleName) {
        try {
            return Role.RoleName.valueOf(roleName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role name: " + roleName);
        }
    }

    private Role getOrCreateRole(Role.RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}