package com.example.wodcrmapi.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

@Aspect
@Component
public class CheckPermissionAspect {

    @Before("@annotation(checkPermission)")
    public void checkPermission(JoinPoint joinPoint, CheckPermission checkPermission) {
        String requiredPermission = checkPermission.value();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean hasPermission = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals(requiredPermission.toLowerCase()));

        if (!hasPermission) {
            throw new AccessDeniedException("Permission denied: " + requiredPermission);
        }
    }
}