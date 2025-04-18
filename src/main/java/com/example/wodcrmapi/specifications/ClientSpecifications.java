package com.example.wodcrmapi.specifications;

import com.example.wodcrmapi.entity.Client;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecifications {
    public static Specification<Client> withSearch(String searchTerm) {
        return (root, query, cb) -> {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return cb.conjunction();
            }

            String term = "%" + searchTerm.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), term),
                    cb.like(cb.lower(root.get("lastName")), term),
                    cb.like(cb.lower(root.get("email")), term),
                    cb.like(root.get("phone"), term)
            );
        };
    }
}
