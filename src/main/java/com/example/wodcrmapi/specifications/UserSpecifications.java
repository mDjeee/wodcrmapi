package com.example.wodcrmapi.specifications;

import com.example.wodcrmapi.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

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

    public static Specification<User> withRoles(Set<Long> roleIds) {
        return (root, query, cb) -> {
            if (roleIds == null || roleIds.isEmpty()) {
                return cb.conjunction();
            }
            return root.join("roles").get("id").in(roleIds);
        };
    }

    public static Specification<User> withCompany(Long companyId) {
        return (root, query, cb) -> {
            if (companyId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("companyId"), companyId);
        };
    }

    public static Specification<User> isSuperAdmin(Boolean isSuperAdmin) {
        return (root, query, cb) -> {
            if (isSuperAdmin == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("superAdmin"), isSuperAdmin);
        };
    }
}
