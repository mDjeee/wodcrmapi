package com.example.wodcrmapi.controller;

import com.example.wodcrmapi.entity.Company;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.service.SuperUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/super-user")
@CrossOrigin(origins = "*")
@Tag(name = "Super User Management", description = "Endpoints for managing super user")
public class SuperUserController {

    private final SuperUserService superUserService;

    public SuperUserController(SuperUserService superUserService) {
        this.superUserService = superUserService;
    }


    @GetMapping("/init")
    @Operation(summary = "Create super user", description = "Creates super user")
    public User getAllCompanies() throws BadRequestException {
        return superUserService.initSuperUser();
    }
}
