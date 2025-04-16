package com.example.wodcrmapi.config;

import jakarta.annotation.PostConstruct;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;

import com.example.wodcrmapi.aop.CheckPermission;
import com.example.wodcrmapi.entity.Permission;
import com.example.wodcrmapi.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PermissionLoaderConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final ApplicationContext applicationContext;
    private final PermissionRepository permissionRepository;

    @PostConstruct
    public void init() {
        System.out.println("PermissionLoaderConfig initialized!");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(RestController.class);
        System.out.println("onApplicationEvent initialized!");


        System.out.println(controllers.values());

        for (Object controller : controllers.values()) {
            Class<?> controllerClass = AopUtils.getTargetClass(controller);

            CheckPermission classAnnotation = AnnotationUtils.findAnnotation(controllerClass, CheckPermission.class);

            if (classAnnotation != null) {
                processPermission(classAnnotation);
            }

            for (Method method : controllerClass.getDeclaredMethods()) {
                CheckPermission methodAnnotation = AnnotationUtils.findAnnotation(method, CheckPermission.class);
                if (methodAnnotation != null) {
                    processPermission(methodAnnotation);
                }
            }
        }
    }

    private void processPermission(CheckPermission annotation) {
        String name = annotation.value().toLowerCase(); // e.g., "user:create"
        String description = annotation.description();
        String displayName = annotation.displayName();
        System.out.println("kjhkhkhj");

        Permission permission = permissionRepository.findByName(name).orElseGet(Permission::new);

        permission.setName(name);
        permission.setDescription(description);
        permission.setDisplayName(displayName);

        String[] parts = name.split(":");
        if (parts.length == 2) {
            try {
                permission.setActionType(Permission.ActionType.valueOf(parts[1].toUpperCase()));
                permission.setResource(parts[0].toLowerCase());
            } catch (IllegalArgumentException e) {
                // Invalid action type, maybe log it
                System.err.println("Unknown action type for permission: " + name);
            }
        }

        try {
            permissionRepository.save(permission);
            System.out.println("Saved permission: " + name);
        } catch (Exception e) {
            System.err.println("Failed to save permission " + name + ": " + e.getMessage());
        }    }
}
