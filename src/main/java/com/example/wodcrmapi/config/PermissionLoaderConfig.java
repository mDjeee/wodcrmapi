package com.example.wodcrmapi.config;

import org.springframework.context.ApplicationContext;

import com.example.wodcrmapi.aop.CheckPermission;
import com.example.wodcrmapi.entity.Permission;
import com.example.wodcrmapi.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PermissionLoaderConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final ApplicationContext applicationContext;
    private final PermissionRepository permissionRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(RestController.class);

        for (Object controller : controllers.values()) {
            Class<?> controllerClass = controller.getClass();

            // Check if class itself has annotation
            if (controllerClass.isAnnotationPresent(CheckPermission.class)) {
                processPermission(controllerClass.getAnnotation(CheckPermission.class));
            }

            // Check methods
            for (Method method : controllerClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(CheckPermission.class)) {
                    processPermission(method.getAnnotation(CheckPermission.class));
                }
            }
        }
    }

    private void processPermission(CheckPermission annotation) {
        String name = annotation.value().toLowerCase(); // e.g., "user:create"
        String description = annotation.description();
        String displayName = annotation.displayName();

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

        permissionRepository.save(permission);
    }
}
