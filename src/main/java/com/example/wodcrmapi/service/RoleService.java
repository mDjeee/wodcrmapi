package com.example.wodcrmapi.service;

import com.example.wodcrmapi.dto.request.CreateRoleRequest;
import com.example.wodcrmapi.entity.Permission;
import com.example.wodcrmapi.entity.Role;
import com.example.wodcrmapi.exception.NotFoundException;
import com.example.wodcrmapi.repository.PermissionRepository;
import com.example.wodcrmapi.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    /**
     * Create a new role
     * @param request the role to create
     * @return the created role
     */
    @Transactional
    public Role createRole(CreateRoleRequest request) {
        Role role = new Role();
        role.setName(request.getName());
        role.setDisplayName(request.getDisplayName());

        if (request.getPermissions() != null && !request.getPermissions().isEmpty()) {
            Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(request.getPermissions()));
            role.setPermissions(permissions);
        }

        return roleRepository.save(role);
    }

    /**
     * Get all roles
     * @return list of all roles
     */
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    /**
     * Get role by ID
     * @param id the role ID
     * @return the found role
     * @throws NotFoundException if role not found
     */
    public Role getRoleById(Long id) throws NotFoundException {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found with id: " + id));
    }

    @Transactional
    public Role ensureSuperAdminRoleExists() {
        Set<Permission> allPermissions = new HashSet<>(
                permissionRepository.findAll()
        );

        Role superAdmin = roleRepository.findByName("super_admin")
                .orElseGet(() -> {
                    // Create new role if it doesn't exist
                    return Role.builder()
                            .name("super_admin")
                            .displayName("Super Administrator")
                            .permissions(new HashSet<>())
                            .build();
                });

        // Ensure the role has all permissions
        if (!superAdmin.getPermissions().containsAll(allPermissions)) {
            superAdmin.setPermissions(allPermissions);
            superAdmin = roleRepository.save(superAdmin);
        }

        return superAdmin;
    }

    /**
     * Get role by name, create it if it doesn't exist
     * @param roleName the role name to find or create
     * @param displayName the display name to use if creating new role
     * @return the existing or newly created role
     */
    @Transactional
    public Role getOrCreateRole(String roleName, String displayName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role newRole = Role.builder()
                            .name(roleName)
                            .displayName(displayName)
                            .permissions(new HashSet<>())
                            .build();
                    return roleRepository.save(newRole);
                });
    }

    /**
     * Update an existing role
     * @param id the role ID to update
     * @param roleDetails the role details to update
     * @return the updated role
     * @throws NotFoundException if role not found
     */
    @Transactional
    public Role updateRole(Long id, CreateRoleRequest roleDetails) throws NotFoundException {
        Role role = getRoleById(id);

        if(role == null) {
            throw new NotFoundException("Role not found with id: " + id);
        }

        role.setName(roleDetails.getName());
        role.setDisplayName(roleDetails.getDisplayName());

        if (roleDetails.getPermissions() != null && !roleDetails.getPermissions().isEmpty()) {
            Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(roleDetails.getPermissions()));
            role.setPermissions(permissions);
        }

        return roleRepository.save(role);
    }

    /**
     * Delete a role by ID
     * @param id the role ID to delete
     * @throws NotFoundException if role not found
     */
    @Transactional
    public void deleteRole(Long id) throws NotFoundException {
        Role role = getRoleById(id);
        roleRepository.delete(role);
    }

    /**
     * Add permissions to a role
     * @param roleId the role ID
     * @param permissions the permissions to add
     * @return the updated role
     */
    @Transactional
    public Role addPermissionsToRole(Long roleId, Set<Permission> permissions) {
        Role role = getRoleById(roleId);
        role.getPermissions().addAll(permissions);
        return roleRepository.save(role);
    }

    /**
     * Remove permissions from a role
     * @param roleId the role ID
     * @param permissions the permissions to remove
     * @return the updated role
     */
    @Transactional
    public Role removePermissionsFromRole(Long roleId, Set<Permission> permissions) {
        Role role = getRoleById(roleId);
        role.getPermissions().removeAll(permissions);
        return roleRepository.save(role);
    }
}