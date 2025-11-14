package com.business.aiagent.security;

import com.business.aiagent.entity.Role;
import com.business.aiagent.entity.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Aspect để thực thi kiểm tra permission và role
 */
@Aspect
@Component
public class PermissionAspect {
    
    @Before("@annotation(com.business.aiagent.security.RequirePermission)")
    public void checkPermission(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequirePermission annotation = method.getAnnotation(RequirePermission.class);
        
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            throw new AccessDeniedException("User chưa đăng nhập");
        }
        
        Role.Permission[] requiredPermissions = annotation.value();
        boolean requireAll = annotation.requireAll();
        
        if (requireAll) {
            // Cần có TẤT CẢ permissions
            boolean hasAll = Arrays.stream(requiredPermissions)
                .allMatch(currentUser::hasPermission);
            
            if (!hasAll) {
                throw new AccessDeniedException(
                    "Không đủ quyền. Cần có tất cả: " + Arrays.toString(requiredPermissions)
                );
            }
        } else {
            // Chỉ cần ÍT NHẤT 1 permission
            boolean hasAny = Arrays.stream(requiredPermissions)
                .anyMatch(currentUser::hasPermission);
            
            if (!hasAny) {
                throw new AccessDeniedException(
                    "Không có quyền. Cần có ít nhất 1 trong: " + Arrays.toString(requiredPermissions)
                );
            }
        }
    }
    
    @Before("@annotation(com.business.aiagent.security.RequireRole)")
    public void checkRole(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequireRole annotation = method.getAnnotation(RequireRole.class);
        
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            throw new AccessDeniedException("User chưa đăng nhập");
        }
        
        Role.RoleName[] requiredRoles = annotation.value();
        boolean requireAll = annotation.requireAll();
        
        if (requireAll) {
            // Cần có TẤT CẢ roles
            boolean hasAll = Arrays.stream(requiredRoles)
                .allMatch(currentUser::hasRole);
            
            if (!hasAll) {
                throw new AccessDeniedException(
                    "Không đủ vai trò. Cần có tất cả: " + Arrays.toString(requiredRoles)
                );
            }
        } else {
            // Chỉ cần ÍT NHẤT 1 role
            boolean hasAny = Arrays.stream(requiredRoles)
                .anyMatch(currentUser::hasRole);
            
            if (!hasAny) {
                throw new AccessDeniedException(
                    "Không có vai trò phù hợp. Cần có ít nhất 1 trong: " + Arrays.toString(requiredRoles)
                );
            }
        }
    }
    
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }
}
