package com.example.wodcrmapi.service;

import com.example.wodcrmapi.dto.request.LoginRequest;
import com.example.wodcrmapi.dto.response.CustomUserDetails;
import com.example.wodcrmapi.dto.response.JwtAuthenticationResponse;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.exception.NotFoundException;
import com.example.wodcrmapi.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    public ResponseEntity<?> login(LoginRequest request) {
        log.info("Attempting login for user: {}", request.getUsername());
        
        try {
            
            User user = userService.findByUsername(request.getUsername());
            if (user == null) {
                throw new NotFoundException("Пользователь не найден.");
            }

            log.info("Login successful for user: {}", request.getUsername());
            CustomUserDetails userDetails = new CustomUserDetails(
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPhone(),
                    user.getRoles(),
                    user.getCreatedAt(),
                    user.getUpdatedAt()
            );
            String token = jwtUtil.generateToken(request.getUsername());

            return ResponseEntity.ok(new JwtAuthenticationResponse(token, userDetails));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Неверные учетные данные.");
        }
    }
}