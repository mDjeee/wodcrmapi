package com.example.wodcrmapi.specifications;

import com.example.wodcrmapi.entity.Message;
import org.springframework.data.jpa.domain.Specification;

public class MessageSpecifications {

    public static Specification<Message> withSearch(String searchTerm) {
        return (root, query, cb) -> {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return cb.conjunction();
            }

            String term = "%" + searchTerm.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("key")), term),
                    cb.like(cb.lower(root.get("ru")), term),
                    cb.like(cb.lower(root.get("uz")), term),
                    cb.like(root.get("kaa"), term)
            );
        };
    }
}
