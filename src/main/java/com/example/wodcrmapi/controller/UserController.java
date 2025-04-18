package com.example.wodcrmapi.controller;

import com.example.wodcrmapi.aop.CheckPermission;
import com.example.wodcrmapi.dto.request.CreateUserRequest;
import com.example.wodcrmapi.dto.request.PaginationRequest;
import com.example.wodcrmapi.dto.response.PaginatedResponse;
import com.example.wodcrmapi.dto.response.UserResponse;
import com.example.wodcrmapi.service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Tag(name = "User Management", description = "Endpoints for managing users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @CheckPermission(value = "USER:READALL", description = "GET all users", displayName = "Получение списка полльзователей")
    @Operation(summary = "Get all users", description = "Returns a list of all users")
    public ResponseEntity<PaginatedResponse<UserResponse>> getAllUsers(
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        return userService.getAllUsers(paginationRequest);
    }

    @PostMapping
    @CheckPermission(value = "USER:CREATE", description = "Create user", displayName = "Создание полльзователя")
    @Operation(summary = "Create a new user", description = "Adds a new user")
    public UserResponse createUser(@RequestBody CreateUserRequest user) throws BadRequestException {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    @CheckPermission(value = "USER:READ", description = "Get user", displayName = "Получение полльзователя")
    @Operation(summary = "Get user by id", description = "Returns user")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    @CheckPermission(value = "USER:UPDATE", description = "Update user", displayName = "Обновление полльзователя")
    @Operation(summary = "Update user by id", description = "Updates and return user")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody CreateUserRequest user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @CheckPermission(value = "USER:DELETE", description = "Delete user", displayName = "Удаление пользователя")
    @Operation(summary = "Delete user by id", description = "Deletes user")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
