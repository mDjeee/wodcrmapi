package com.example.wodcrmapi.controller;

import com.example.wodcrmapi.dto.request.LoginRequest;
import com.example.wodcrmapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Controller", description = "Endpoints for authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Sign in", description = "Login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
      return authService.login(request);
    }
}
