package com.business.aiagent.security;

import com.business.aiagent.entity.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation để kiểm tra role trước khi thực thi method
 * Sử dụng: @RequireRole(Role.RoleName.ADMIN)
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireRole {
    Role.RoleName[] value();
    
    /**
     * Nếu true, user cần có TẤT CẢ roles
     * Nếu false (default), user chỉ cần có ÍT NHẤT 1 role
     */
    boolean requireAll() default false;
}
