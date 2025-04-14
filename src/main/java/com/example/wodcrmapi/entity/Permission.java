package com.example.wodcrmapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", length = 50)
    private ActionType actionType;

    private String displayName;
    private String resource;

    public enum ActionType {
        CREATE,
        READALL,
        READ,
        UPDATE,
        DELETE,
        APPROVE, // Custom actions
        EXPORT
    }

    @PrePersist @PreUpdate
    private void parseNameToEnums() {
        if (this.name != null) {
            String[] parts = this.name.split(":");
            if (parts.length == 2) {
                this.actionType = ActionType.valueOf(parts[1].toUpperCase());
                this.resource = parts[0].toLowerCase();
            }
        }
    }
}
