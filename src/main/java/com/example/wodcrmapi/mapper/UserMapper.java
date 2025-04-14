package com.example.wodcrmapi.mapper;

import com.example.wodcrmapi.dto.response.UserResponse;
import com.example.wodcrmapi.entity.Permission;
import com.example.wodcrmapi.entity.Role;
import com.example.wodcrmapi.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());

        // Map role names
        response.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList()));

        // Map permissions
        Set<Permission> permissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(this::mapPermissionToDto)
                .collect(Collectors.toSet());

        response.setPermissions(new ArrayList<>(permissions));
        return response;
    }

    private Permission mapPermissionToDto(Permission permission) {
        Permission dto = new Permission();
        dto.setId(permission.getId());
        dto.setName(permission.getName());
        dto.setDescription(permission.getDescription());
        dto.setActionType(permission.getActionType());
        dto.setDisplayName(permission.getDisplayName());
        dto.setResource(permission.getResource());
        return dto;
    }
}