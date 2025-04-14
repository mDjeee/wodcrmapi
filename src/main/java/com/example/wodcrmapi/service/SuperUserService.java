package com.example.wodcrmapi.service;


import com.example.wodcrmapi.entity.Role;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.repository.RoleRepository;
import com.example.wodcrmapi.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@Service
public class SuperUserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;


    public SuperUserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User initSuperUser() throws BadRequestException {

        Boolean isExists = userRepository.existsByUsername("superAdmin");
        if(isExists) {
            throw new BadRequestException("Username already exists");
        }

        User owner = new User();
        owner.setUsername("superAdmin");
        owner.setPassword(passwordEncoder.encode("super123"));
        owner.setFirstName("Islam");
        owner.setLastName("Seytniyazov");
        owner.setPhone("998913721426");

        Set<Role> roles = Set.of(
                getOrCreateRole(Role.RoleName.ROLE_APPLICATION_OWNER)
        );

        owner.setRoles(roles);

        return userRepository.save(owner);
    }

    private Role getOrCreateRole(Role.RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}
