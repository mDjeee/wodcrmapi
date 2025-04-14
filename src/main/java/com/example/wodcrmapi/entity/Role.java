package com.example.wodcrmapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    public Role(RoleName name, Set<Permission> permissions) {
        this.name = name;
        this.displayName = name.toString();
        this.permissions = permissions != null ? permissions : new HashSet<>();
    }

    public Role(RoleName name, String displayName) {
        this.name = name;
        this.displayName = displayName;
        this.permissions = new HashSet<>();
    }

    public Role(RoleName name) {
        this.name = name;
        this.displayName = name.toString();
        this.permissions = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleName name;

    @Column(unique = true, nullable = false)
    private String displayName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    public enum RoleName {
        ROLE_APPLICATION_OWNER,
        ROLE_COMPANY_ADMIN,
        ROLE_COMPANY_USER
    }
}
