package com.example.wodcrmapi.dto.request;


import lombok.Data;

import java.util.List;

@Data
public class CreateRoleRequest {
    private String name;
    private String displayName;
    private List<Long> permissions;
    private Long companyId;
}
