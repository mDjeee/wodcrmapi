package com.example.wodcrmapi.dto.response;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private UserResponse userResponse;  // Add this field


    public JwtAuthenticationResponse(String accessToken, UserResponse userResponse) {
        this.accessToken = accessToken;
        this.userResponse = userResponse;
    }
}