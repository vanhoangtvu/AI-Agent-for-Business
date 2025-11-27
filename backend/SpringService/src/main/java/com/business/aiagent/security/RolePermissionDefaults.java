package com.business.aiagent.security;

import com.business.aiagent.entity.Role;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Central place to define default permissions for each role.
 * Used during data initialization and runtime fallback repairs.
 */
public final class RolePermissionDefaults {
    
    private static final Set<Role.Permission> ADMIN_PERMISSIONS =
        Collections.unmodifiableSet(EnumSet.allOf(Role.Permission.class));
    
    private static final Set<Role.Permission> BUSINESS_PERMISSIONS =
        Collections.unmodifiableSet(EnumSet.of(
            // Product Management
            Role.Permission.PRODUCT_READ,
            Role.Permission.PRODUCT_CREATE,
            Role.Permission.PRODUCT_UPDATE,
            Role.Permission.PRODUCT_DELETE,
            
            // Category Management
            Role.Permission.CATEGORY_READ,
            Role.Permission.CATEGORY_CREATE,
            Role.Permission.CATEGORY_UPDATE,
            Role.Permission.CATEGORY_DELETE,
            
            // Order Management
            Role.Permission.ORDER_READ,
            Role.Permission.ORDER_CREATE,
            Role.Permission.ORDER_UPDATE,
            
            // Document Management
            Role.Permission.DOCUMENT_READ,
            Role.Permission.DOCUMENT_CREATE,
            Role.Permission.DOCUMENT_UPDATE,
            Role.Permission.DOCUMENT_DELETE,
            
            // Analytics & Reports
            Role.Permission.ANALYTICS_VIEW,
            Role.Permission.REPORT_GENERATE,
            Role.Permission.REPORT_EXPORT,
            
            // Chat & AI
            Role.Permission.CHAT_USE,
            Role.Permission.CHAT_VIEW_HISTORY
        ));
    
    private static final Set<Role.Permission> CUSTOMER_PERMISSIONS =
        Collections.unmodifiableSet(EnumSet.of(
            Role.Permission.PRODUCT_READ,
            Role.Permission.CATEGORY_READ,
            Role.Permission.ORDER_READ,
            Role.Permission.ORDER_CREATE,
            Role.Permission.CHAT_USE,
            Role.Permission.CHAT_VIEW_HISTORY
        ));
    
    private RolePermissionDefaults() {
    }
    
    public static Set<Role.Permission> getPermissionsForRole(Role.RoleName roleName) {
        return switch (roleName) {
            case ADMIN -> ADMIN_PERMISSIONS;
            case BUSINESS -> BUSINESS_PERMISSIONS;
            case CUSTOMER -> CUSTOMER_PERMISSIONS;
        };
    }
}
