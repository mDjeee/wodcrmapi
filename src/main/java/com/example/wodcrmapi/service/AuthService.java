package com.example.wodcrmapi.service;

import com.example.wodcrmapi.dto.request.LoginRequest;
import com.example.wodcrmapi.dto.response.JwtAuthenticationResponse;
import com.example.wodcrmapi.dto.response.UserResponse;
import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.exception.NotFoundException;
import com.example.wodcrmapi.exception.PasswordMismatchException;
import com.example.wodcrmapi.mapper.UserMapper;
import com.example.wodcrmapi.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public ResponseEntity<?> login(LoginRequest request) {
        try {

            User user = userService.findByUsername(request.getUsername());
            if (user == null) {
                throw new NotFoundException("Not Found");
            }

            if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new PasswordMismatchException("Неверный пароль!");
            }

            UserResponse userResponse = userMapper.mapToUserResponse(user);
            String token = jwtUtil.generateToken(request.getUsername());

            return ResponseEntity.ok(new JwtAuthenticationResponse(token, userResponse));
        }
        catch (NotFoundException e) {
            throw new NotFoundException("user.not_found");
        }
        catch (PasswordMismatchException e) {
            throw new PasswordMismatchException("Неверный пароль!");
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Неверные учетные данные.");
        }
    }
}