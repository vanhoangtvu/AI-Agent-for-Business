package com.business.aiagent.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 100)
    private String username;
    
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(length = 100)
    private String fullName;
    
    @Column(length = 20)
    private String phone;
    
    @Column(length = 500)
    private String address;
    
    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;
    
    // Business Owner Information (Chỉ cho role BUSINESS)
    @Column(length = 200)
    private String businessName;  // Tên doanh nghiệp
    
    @Column(length = 100)
    private String businessType;  // Loại hình: Retail, Manufacturing, Service, etc.
    
    @Column(length = 500)
    private String businessDescription;  // Mô tả doanh nghiệp
    
    @Column(length = 200)
    private String businessAddress;  // Địa chỉ doanh nghiệp
    
    @Column(length = 20)
    private String businessPhone;  // SĐT doanh nghiệp
    
    @Column(length = 100)
    private String businessEmail;  // Email doanh nghiệp
    
    @Column(length = 50)
    private String taxCode;  // Mã số thuế
    
    @Column(length = 200)
    private String businessLogo;  // URL logo doanh nghiệp
    
    private Boolean isBusinessVerified = false;  // Xác thực doanh nghiệp
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // Helper methods
    @Transient
    public boolean hasRole(Role.RoleName roleName) {
        return roles.stream().anyMatch(role -> role.getName().equals(roleName));
    }
    
    @Transient
    public boolean isAdmin() {
        return hasRole(Role.RoleName.ADMIN);
    }
    
    @Transient
    public boolean isBusiness() {
        return hasRole(Role.RoleName.BUSINESS);
    }
    
    @Transient
    public boolean isCustomer() {
        return hasRole(Role.RoleName.CUSTOMER);
    }
    
    @Transient
    public boolean hasPermission(Role.Permission permission) {
        return roles.stream()
            .anyMatch(role -> role.hasPermission(permission));
    }
}
