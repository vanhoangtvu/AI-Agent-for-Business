package com.business.aiagent.security;

import com.business.aiagent.entity.Role;
import com.business.aiagent.entity.User;
import com.business.aiagent.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Aspect để thực thi kiểm tra permission và role
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class PermissionAspect {

    private final RoleRepository roleRepository;

    private static final Set<Role.Permission> BUSINESS_FALLBACK_PERMISSIONS =
        RolePermissionDefaults.getPermissionsForRole(Role.RoleName.BUSINESS);
    
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

        ensureRolePermissionsInitialized(currentUser);

        if (hasFallbackPermission(currentUser, requiredPermissions, requireAll)) {
            return;
        }
        
        if (requireAll) {
            // Cần có TẤT CẢ permissions
            boolean hasAll = Arrays.stream(requiredPermissions)
                .allMatch(currentUser::hasPermission);
            
            if (!hasAll) {
                log.warn(
                    "Permission denied (requireAll) for user {}. Required: {}. Roles: {}",
                    currentUser.getUsername(),
                    Arrays.toString(requiredPermissions),
                    currentUser.getRoles().stream()
                        .map(role -> role.getName() + ":" + role.getPermissions())
                        .collect(Collectors.toList())
                );
                throw new AccessDeniedException(
                    "Không đủ quyền. Cần có tất cả: " + Arrays.toString(requiredPermissions)
                );
            }
        } else {
            // Chỉ cần ÍT NHẤT 1 permission
            boolean hasAny = Arrays.stream(requiredPermissions)
                .anyMatch(currentUser::hasPermission);
            
            if (!hasAny) {
                log.warn(
                    "Permission denied for user {}. Required any of: {}. Roles: {}",
                    currentUser.getUsername(),
                    Arrays.toString(requiredPermissions),
                    currentUser.getRoles().stream()
                        .map(role -> role.getName() + ":" + role.getPermissions())
                        .collect(Collectors.toList())
                );
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

        ensureRolePermissionsInitialized(currentUser);
        
        if (requireAll) {
            // Cần có TẤT CẢ roles
            boolean hasAll = Arrays.stream(requiredRoles)
                .allMatch(currentUser::hasRole);
            
            if (!hasAll) {
                log.warn(
                    "Role denied (requireAll) for user {}. Required: {}. Roles: {}",
                    currentUser.getUsername(),
                    Arrays.toString(requiredRoles),
                    currentUser.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toList())
                );
                throw new AccessDeniedException(
                    "Không đủ vai trò. Cần có tất cả: " + Arrays.toString(requiredRoles)
                );
            }
        } else {
            // Chỉ cần ÍT NHẤT 1 role
            boolean hasAny = Arrays.stream(requiredRoles)
                .anyMatch(currentUser::hasRole);
            
            if (!hasAny) {
                log.warn(
                    "Role denied for user {}. Required any of: {}. Roles: {}",
                    currentUser.getUsername(),
                    Arrays.toString(requiredRoles),
                    currentUser.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toList())
                );
                throw new AccessDeniedException(
                    "Không có vai trò phù hợp. Cần có ít nhất 1 trong: " + Arrays.toString(requiredRoles)
                );
            }
        }
    }
    
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            return ((UserPrincipal) authentication.getPrincipal()).getUser();
        }
        return null;
    }

    private boolean hasFallbackPermission(User user, Role.Permission[] requiredPermissions, boolean requireAll) {
        if (user.isAdmin()) {
            return true; // ADMIN always allowed
        }

        if (user.isBusiness()) {
            if (requireAll) {
                return Arrays.stream(requiredPermissions)
                    .allMatch(BUSINESS_FALLBACK_PERMISSIONS::contains);
            }
            return Arrays.stream(requiredPermissions)
                .anyMatch(BUSINESS_FALLBACK_PERMISSIONS::contains);
        }

        return false;
    }

    private void ensureRolePermissionsInitialized(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return;
        }

        user.getRoles().forEach(role -> {
            if (role.getPermissions() == null || role.getPermissions().isEmpty()) {
                Set<Role.Permission> defaults = RolePermissionDefaults.getPermissionsForRole(role.getName());
                if (defaults != null && !defaults.isEmpty()) {
                    role.setPermissions(EnumSet.copyOf(defaults));
                    roleRepository.save(role);
                    log.info("Auto-restored permissions for role {} (user {})", role.getName(), user.getUsername());
                }
            }
        });
    }
}
