package com.example.wodcrmapi.dto.response;

import com.example.wodcrmapi.entity.Permission;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private List<String> roles;
    private List<Permission> permissions;
    private Boolean superAdmin;
    private Long companyId;
}
