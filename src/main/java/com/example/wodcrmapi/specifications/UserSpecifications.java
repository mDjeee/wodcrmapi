package com.example.wodcrmapi.specifications;

import com.example.wodcrmapi.entity.User;
import jakarta.persistence.criteria.Path;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> withSearch(String searchTerm) {
        return (root, query, cb) -> {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return cb.conjunction();
            }

            String term = "%" + searchTerm.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("username")), term),
                    cb.like(cb.lower(root.get("firstName")), term),
                    cb.like(cb.lower(root.get("lastName")), term),
                    cb.like(root.get("phone"), term)
            );
        };
    }

    public static Specification<User> withSorting(String sortBy, String sortDirection) {
        return (root, query, cb) -> {
            if (sortBy != null && !sortBy.trim().isEmpty()) {
                Sort.Direction direction = Sort.Direction.fromString(sortDirection);
                Path<Object> fieldPath = root.get(sortBy);

                query.orderBy(
                        direction.isAscending() ?
                                cb.asc(fieldPath) :
                                cb.desc(fieldPath)
                );
            }
            return cb.conjunction(); // No filtering, only sorting
        };
    }

    /**
     * Combines search and sorting into a single Specification.
     */
    public static Specification<User> withSearchAndSorting(
            String searchTerm,
            String sortBy,
            String sortDirection
    ) {
        return withSearch(searchTerm).and(withSorting(sortBy, sortDirection));
    }
}
