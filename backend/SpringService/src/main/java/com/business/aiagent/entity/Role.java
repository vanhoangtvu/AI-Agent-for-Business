package com.business.aiagent.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false, length = 50)
    private RoleName name;
    
    @Column(length = 200)
    private String description;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"))
    @Column(name = "permission")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<Permission> permissions = new HashSet<>();
    
    public enum RoleName {
        ADMIN,      // Quản trị viên hệ thống - toàn quyền
        BUSINESS,   // Chủ doanh nghiệp - quản lý sản phẩm, đơn hàng, phân tích
        CUSTOMER    // Khách hàng - mua hàng, xem đơn hàng
    }
    
    public enum Permission {
        // User Management
        USER_READ,
        USER_CREATE,
        USER_UPDATE,
        USER_DELETE,
        
        // Product Management
        PRODUCT_READ,
        PRODUCT_CREATE,
        PRODUCT_UPDATE,
        PRODUCT_DELETE,
        PRODUCT_MANAGE_ALL,  // ADMIN quản lý tất cả sản phẩm
        
        // Category Management
        CATEGORY_READ,
        CATEGORY_CREATE,
        CATEGORY_UPDATE,
        CATEGORY_DELETE,
        
        // Order Management
        ORDER_READ,
        ORDER_CREATE,
        ORDER_UPDATE,
        ORDER_DELETE,
        ORDER_MANAGE_ALL,    // ADMIN quản lý tất cả đơn hàng
        
        // Document Management
        DOCUMENT_READ,
        DOCUMENT_CREATE,
        DOCUMENT_UPDATE,
        DOCUMENT_DELETE,
        DOCUMENT_MANAGE_ALL, // ADMIN quản lý tất cả tài liệu
        
        // Analytics & Reports
        ANALYTICS_VIEW,       // Xem phân tích của mình (BUSINESS)
        ANALYTICS_VIEW_ALL,   // Xem tất cả phân tích (ADMIN)
        REPORT_GENERATE,
        REPORT_EXPORT,
        
        // System
        SYSTEM_CONFIG,
        SYSTEM_LOGS,
        
        // Chat & AI
        CHAT_USE,
        CHAT_VIEW_HISTORY,
        
        // Role Management
        ROLE_READ,
        ROLE_MANAGE
    }
    
    // Helper methods
    public boolean hasPermission(Permission permission) {
        return permissions != null && permissions.contains(permission);
    }
    
    public boolean isAdmin() {
        return RoleName.ADMIN.equals(this.name);
    }
    
    public boolean isBusiness() {
        return RoleName.BUSINESS.equals(this.name);
    }
    
    public boolean isCustomer() {
        return RoleName.CUSTOMER.equals(this.name);
    }
}
