package com.example.wodcrmapi.service;

import com.example.wodcrmapi.dto.request.CreateUserRequest;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.exception.AppException;
import com.example.wodcrmapi.repository.CompanyUserRepository;
import com.example.wodcrmapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CompanyUserRepository companyUserRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository,
                       CompanyUserRepository companyUserRepository,
                       ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.companyUserRepository = companyUserRepository;
        this.modelMapper = modelMapper;
    }

    public User createUser(CreateUserRequest request) {
        Boolean isExists = userRepository.existsByUsername(request.getUsername());
        if(isExists) {
            throw new AppException("Username already exists");
        }
        User user = userRepository.save(modelMapper.map(request, User.class));
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(user.getUsername());
                    existingUser.setPassword(user.getPassword());
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    existingUser.setPhone(user.getPhone());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}