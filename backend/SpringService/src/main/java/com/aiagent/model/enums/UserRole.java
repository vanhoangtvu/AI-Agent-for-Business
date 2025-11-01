package com.aiagent.model.enums;

/**
 * User Role Enum
 * 
 * ADMIN - Quản trị viên hệ thống
 * BUSINESS - Doanh nghiệp (chủ business, quản lý nhân viên)
 * CUSTOMER - Khách hàng (người dùng cuối)
 */
public enum UserRole {
    
    /**
     * Quản trị viên hệ thống
     * - Quản lý toàn bộ hệ thống
     * - Quản lý users, businesses
     * - Xem tất cả dữ liệu
     * - Cấu hình hệ thống
     */
    ADMIN("ADMIN", "Quản trị viên"),
    
    /**
     * Doanh nghiệp
     * - Quản lý tài liệu doanh nghiệp
     * - Quản lý khách hàng của mình
     * - Xem báo cáo, phân tích
     * - Cấu hình chatbot
     * - Quản lý nhân viên
     */
    BUSINESS("BUSINESS", "Doanh nghiệp"),
    
    /**
     * Khách hàng
     * - Chat với AI
     * - Xem tài liệu được chia sẻ
     * - Xem thông tin cá nhân
     */
    CUSTOMER("CUSTOMER", "Khách hàng");
    
    private final String code;
    private final String displayName;
    
    UserRole(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Get UserRole from string
     */
    public static UserRole fromString(String role) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.code.equalsIgnoreCase(role)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + role);
    }
    
}

