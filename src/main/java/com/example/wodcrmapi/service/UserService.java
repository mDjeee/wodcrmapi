package com.example.wodcrmapi.service;

import com.example.wodcrmapi.dto.request.CreateUserRequest;
import com.example.wodcrmapi.dto.request.PaginationRequest;
import com.example.wodcrmapi.dto.response.CompanyResponse;
import com.example.wodcrmapi.dto.response.PaginatedResponse;
import com.example.wodcrmapi.dto.response.UserResponse;
import com.example.wodcrmapi.entity.Company;
import com.example.wodcrmapi.entity.Role;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.exception.NotFoundException;
import com.example.wodcrmapi.mapper.UserMapper;
import com.example.wodcrmapi.repository.RoleRepository;
import com.example.wodcrmapi.repository.UserRepository;
import com.example.wodcrmapi.security.SecurityUtils;
import com.example.wodcrmapi.specifications.CompanySpecifications;
import com.example.wodcrmapi.specifications.UserSpecifications;
import org.apache.coyote.BadRequestException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private SecurityUtils securityUtils;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       @Lazy PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository,
                       SecurityUtils securityUtils
                       ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.securityUtils = securityUtils;
    }

    public UserResponse createUser(CreateUserRequest request) throws BadRequestException {
        Boolean isExists = userRepository.existsByUsername(request.getUsername());
        if(isExists) {
            throw new BadRequestException("Username already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        User currentUser = securityUtils.getCurrentUser();
        Long companyId = currentUser.getCompanyId();

        if(companyId != null) {
            user.setCompanyId(companyId);
        } else {
            user.setCompanyId(request.getCompanyId());
        }

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(request.getRoles()));
            user.setRoles(roles);
        }

        User savedUser = userRepository.save(user);

        return userMapper.mapToUserResponse(savedUser);
    }

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::mapToUserResponse)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public ResponseEntity<PaginatedResponse<UserResponse>> getAllUsers(PaginationRequest paginationRequest) {
        Specification<User> spec = UserSpecifications.withSearch(paginationRequest.getSearch());
        Page<User> pageResult = userRepository.findAll(spec, paginationRequest.toPageable());
        Page<UserResponse> responsePage = pageResult
                .map(userMapper::mapToUserResponse);

        return ResponseEntity.ok(new PaginatedResponse<>(responsePage));
    }

    public UserResponse updateUser(Long id, CreateUserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        existingUser.setUsername(request.getUsername());
        existingUser.setPassword(request.getPassword());
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setPhone(request.getPhone());

        User currentUser = securityUtils.getCurrentUser();
        Long companyId = currentUser.getCompanyId();

        if(companyId != null) {
            existingUser.setCompanyId(companyId);
        } else {
            existingUser.setCompanyId(request.getCompanyId());
        }

        User updatedUser = userRepository.save(existingUser);

        return userMapper.mapToUserResponse(updatedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}