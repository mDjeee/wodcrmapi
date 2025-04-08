package com.example.wodcrmapi.dto.response;

import com.example.wodcrmapi.entity.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class CustomUserDetails {
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private Set<Role> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CustomUserDetails(
            String username,
            String firstName,
            String lastName,
            String phone,
            Set<Role> roles,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
            ) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.roles = roles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
