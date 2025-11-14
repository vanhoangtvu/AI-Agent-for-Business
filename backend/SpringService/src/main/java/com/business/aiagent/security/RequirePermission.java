package com.business.aiagent.security;

import com.business.aiagent.entity.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation để kiểm tra permission trước khi thực thi method
 * Sử dụng: @RequirePermission(Role.Permission.PRODUCT_CREATE)
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {
    Role.Permission[] value();
    
    /**
     * Nếu true, user cần có TẤT CẢ permissions
     * Nếu false (default), user chỉ cần có ÍT NHẤT 1 permission
     */
    boolean requireAll() default false;
}
