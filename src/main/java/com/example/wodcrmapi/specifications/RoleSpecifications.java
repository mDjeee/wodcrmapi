package com.example.wodcrmapi.specifications;

import com.example.wodcrmapi.entity.Role;

import org.springframework.data.jpa.domain.Specification;

public class RoleSpecifications {

    public static Specification<Role> withSearch(String searchTerm) {
        return (root, query, cb) -> {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return cb.conjunction();
            }

            String term = "%" + searchTerm.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), term),
                    cb.like(cb.lower(root.get("displayName")), term),
                    cb.like(cb.lower(root.get("lastName")), term),
                    cb.like(root.get("phone"), term)
            );
        };
    }

}
