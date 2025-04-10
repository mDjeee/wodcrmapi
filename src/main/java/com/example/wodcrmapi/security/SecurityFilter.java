package com.example.wodcrmapi.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


import com.example.wodcrmapi.entity.User;
import com.example.wodcrmapi.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final JWTUtil util;

    private final UserService userSevrice;

    public SecurityFilter(JWTUtil util, UserService userSevrice) {
        this.util = util;
        this.userSevrice = userSevrice;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Reading Token from Authorization Header
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && !request.getRequestURI().contains("/auth/")) {
            String token = authHeader.replace("Bearer ", "");
            String username = util.getSubject(token);
            //if username is not null & Context Authentication must be null
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                boolean isValid = util.isValidToken(token, username);
                if(isValid) {
                    User user = userSevrice.getUserByUserName(username);
                    UsernamePasswordAuthenticationToken authToken = getUsernamePasswordAuthenticationToken(user);

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private static UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(User user) {
        UserPrincipal principal = new UserPrincipal(user);

        return new UsernamePasswordAuthenticationToken(
                principal,
                user.getPassword(),
                principal.getAuthorities()
        );
    }

}