package com.example.wodcrmapi.controller;


import com.example.wodcrmapi.aop.CheckPermission;
import com.example.wodcrmapi.entity.Permission;
import com.example.wodcrmapi.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@CrossOrigin(origins = "*")
@Tag(name = "Permission Management", description = "Endpoints for permission companies")
public class PermissionController {

    @Autowired
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    @Operation(summary = "Get all clients", description = "Returns a list of all clients")
    public List<Permission> getAllPermissions() {
        return permissionService.getAllPermissions();
    }
}
