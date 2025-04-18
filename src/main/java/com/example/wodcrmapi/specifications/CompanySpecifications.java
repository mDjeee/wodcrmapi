package com.example.wodcrmapi.specifications;

import com.example.wodcrmapi.entity.Company;
import org.springframework.data.jpa.domain.Specification;

public class CompanySpecifications {

    public static Specification<Company> withSearch(String searchTerm) {
        return (root, query, cb) -> {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return cb.conjunction();
            }

            String term = "%" + searchTerm.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), term),
                    cb.like(cb.lower(root.get("domain")), term),
                    cb.like(cb.lower(root.get("address")), term),
                    cb.like(root.get("phone"), term)
            );
        };
    }
}