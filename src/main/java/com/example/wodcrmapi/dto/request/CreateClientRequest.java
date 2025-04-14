package com.example.wodcrmapi.dto.request;

import lombok.Data;

@Data
public class CreateClientRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Long companyId;
}
