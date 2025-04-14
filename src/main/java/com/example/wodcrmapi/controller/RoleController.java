package com.example.wodcrmapi.controller;


import com.example.wodcrmapi.aop.CheckPermission;
import com.example.wodcrmapi.dto.request.CreateRoleRequest;
import com.example.wodcrmapi.entity.Role;
import com.example.wodcrmapi.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
@Tag(name = "Role Management", description = "Endpoints for managing roles")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @CheckPermission(value = "ROLE:READALL", description = "GET all roles", displayName = "Получение списка ролей")
    @Operation(summary = "Get all roles", description = "Returns a list of all roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    @CheckPermission(value = "ROLE:READ", description = "GET role", displayName = "Получение роли")
    @Operation(summary = "Get role", description = "Returns a role")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @PostMapping
    @CheckPermission(value = "ROLE:CREATE", description = "Create role", displayName = "Добавление роли")
    @Operation(summary = "Create role", description = "Creates a role")
    public Role createRole(@RequestBody CreateRoleRequest role) {
        return roleService.createRole(role);
    }

    @PutMapping("/{id}")
    @CheckPermission(value = "ROLE:UPDATE", description = "Update role", displayName = "Обновление роли")
    @Operation(summary = "Update role", description = "Updates a role")
    public Role createRole(@PathVariable Long id, @RequestBody CreateRoleRequest role) {
        return roleService.updateRole(id, role);
    }

    @DeleteMapping("/{id}")
    @CheckPermission(value = "ROLE:DELETE", description = "Delete role", displayName = "Удаление роли")
    @Operation(summary = "Delete role", description = "Deletes a role")
    public void deleteRoleById(@PathVariable Long id) {
        roleService.deleteRole(id);
    }
}
