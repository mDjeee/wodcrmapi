package com.example.wodcrmapi.service;


import com.example.wodcrmapi.dto.response.UserResponse;
import com.example.wodcrmapi.entity.Permission;
import com.example.wodcrmapi.entity.Role;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.mapper.UserMapper;
import com.example.wodcrmapi.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SuperUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserMapper userMapper;


    public SuperUserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoleService roleService,
            UserMapper userMapper
            ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userMapper = userMapper;
    }

    public UserResponse initSuperUser() throws BadRequestException {

        Role superAdminRole = roleService.ensureSuperAdminRoleExists();

        User user = userRepository.findByUsername("super")
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUsername("super");
                    return newUser;
                });

        user.getRoles().clear();
        Set<Role> roles = new HashSet<>();
        roles.add(superAdminRole);
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode("super123"));
        user.setFirstName("Islam");
        user.setLastName("Seytniyazov");
        user.setPhone("998913721426");
        user.setSuperAdmin(true);

        User savedUser = userRepository.save(user);

        return userMapper.mapToUserResponse(savedUser);
    }
}
