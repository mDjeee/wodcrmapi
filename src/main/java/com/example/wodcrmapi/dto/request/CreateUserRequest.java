package com.example.wodcrmapi.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String phone;
    private String password;
    private Long companyId;
    private List<Long> roles;
}