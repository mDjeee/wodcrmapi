package com.example.wodcrmapi.dto.response;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private CustomUserDetails userDetails;  // Add this field


    public JwtAuthenticationResponse(String accessToken, CustomUserDetails userDetails) {
        this.accessToken = accessToken;
        this.userDetails = userDetails;
    }
}