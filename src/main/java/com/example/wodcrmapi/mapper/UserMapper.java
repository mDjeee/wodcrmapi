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
        response.setSuperAdmin(user.getSuperAdmin());
        response.setCompanyId(user.getCompanyId());

        // Map role names
        response.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList()));

        // Map permissions
        ArrayList<Permission> permissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(this::mapPermissionToDto)
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(Permission::getId, p -> p, (p1, p2) -> p1),
                        map -> new ArrayList<>(map.values())
                ));

        response.setPermissions(permissions);
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
        dto.setIsSuper(permission.getIsSuper());
        return dto;
    }
}