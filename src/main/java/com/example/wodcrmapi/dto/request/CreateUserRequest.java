package com.example.wodcrmapi.dto.request;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String firstName;
    private String username;
    private String email;
    private String password;
}