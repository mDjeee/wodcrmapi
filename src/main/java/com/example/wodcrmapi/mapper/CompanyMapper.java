package com.example.wodcrmapi.mapper;

import com.example.wodcrmapi.dto.response.CompanyResponse;
import com.example.wodcrmapi.entity.Company;
import com.example.wodcrmapi.entity.Role;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyResponse toResponse(Company company);

    default List<String> mapRolesToStrings(Set<Role> roles) {
        if (roles == null) {
            return Collections.emptyList();
        }
        return roles.stream()
                .map(Role::getName) // Assuming Role has a getName() method
                .collect(Collectors.toList());
    }
}