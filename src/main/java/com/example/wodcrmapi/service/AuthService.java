package com.example.wodcrmapi.service;

import com.example.wodcrmapi.dto.request.LoginRequest;
import com.example.wodcrmapi.dto.response.CustomUserDetails;
import com.example.wodcrmapi.dto.response.JwtAuthenticationResponse;
import com.example.wodcrmapi.exception.AppException;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.exception.NotFoundException;
import com.example.wodcrmapi.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    public ResponseEntity<?> login(LoginRequest request) {
        User byUsername = userService.findByUsername(request.getUsername());
        if (byUsername == null) {
            throw new NotFoundException("Пользователь не найден.");
        }
        if (!byUsername.getPassword().equals(request.getPassword())) {
            throw new BadCredentialsException("Неверные учетные данные.");
        }

        CustomUserDetails userDetails = new CustomUserDetails(
                byUsername.getUsername(),
                byUsername.getFirstName(),
                byUsername.getLastName(),
                byUsername.getPhone(),
                byUsername.getRoles(),
                byUsername.getCreatedAt(),
                byUsername.getUpdatedAt()
        );
        String token = jwtUtil.generateToken(request.getUsername());

        return ResponseEntity.ok(new JwtAuthenticationResponse(token, userDetails));
    }
}