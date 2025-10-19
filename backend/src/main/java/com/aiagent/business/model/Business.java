package com.aiagent.business.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Business Entity - Bảng doanh nghiệp
 */
@Entity
@Table(name = "businesses", indexes = {
    @Index(name = "idx_owner_id", columnList = "owner_id"),
    @Index(name = "idx_slug", columnList = "slug"),
    @Index(name = "idx_status", columnList = "status")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "ward")
    private String ward;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusinessType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusinessStatus status;

    @Column(name = "subscription_plan")
    @Enumerated(EnumType.STRING)
    private SubscriptionPlan subscriptionPlan = SubscriptionPlan.FREE;

    @Column(name = "subscription_expires_at")
    private LocalDateTime subscriptionExpiresAt;

    @Column(name = "total_customers", nullable = false)
    private Integer totalCustomers = 0;

    @Column(name = "total_orders", nullable = false)
    private Integer totalOrders = 0;

    @Column(name = "total_revenue", nullable = false)
    private Double totalRevenue = 0.0;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public enum BusinessType {
        ECOMMERCE, RESTAURANT, RETAIL, SERVICE, EDUCATION, HEALTHCARE, OTHER
    }

    public enum BusinessStatus {
        ACTIVE, SUSPENDED, INACTIVE, PENDING
    }

    public enum SubscriptionPlan {
        FREE, BASIC, PREMIUM, ENTERPRISE
    }
}

