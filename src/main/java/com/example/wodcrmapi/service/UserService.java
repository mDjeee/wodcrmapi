package com.example.wodcrmapi.service;

import com.example.wodcrmapi.dto.request.CreateUserRequest;
import com.example.wodcrmapi.dto.response.UserResponse;
import com.example.wodcrmapi.entity.Role;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.exception.NotFoundException;
import com.example.wodcrmapi.mapper.UserMapper;
import com.example.wodcrmapi.repository.RoleRepository;
import com.example.wodcrmapi.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.context.annotation.Lazy;
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

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       @Lazy PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User createUser(CreateUserRequest request) throws BadRequestException {
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

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(request.getRoles()));
            user.setRoles(roles);
        }

        return userRepository.save(user);
    }

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::mapToUserResponse)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));


        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPhone(user.getPhone());
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