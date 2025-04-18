package com.example.wodcrmapi.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class UserFilterRequest {
    private Set<Long> roleIds;
    private Long companyId;
    private Boolean isSuperAdmin;
    private String search;
}