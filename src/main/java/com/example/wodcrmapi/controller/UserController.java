package com.example.wodcrmapi.controller;

import com.example.wodcrmapi.dto.request.CreateUserRequest;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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
    @Operation(summary = "Get all users", description = "Returns a list of all users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Adds a new user")
    public User createUser(@RequestBody CreateUserRequest user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id", description = "Returns user")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user by id", description = "Updates and return user")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id", description = "Deletes user")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
