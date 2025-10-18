package com.aiagent.business.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Customer Entity - Bảng khách hàng
 */
@Entity
@Table(name = "customers", indexes = {
    @Index(name = "idx_business", columnList = "business_id"),
    @Index(name = "idx_email", columnList = "email"),
    @Index(name = "idx_phone", columnList = "phone"),
    @Index(name = "idx_segment", columnList = "segment"),
    @Index(name = "idx_source", columnList = "source, source_id")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_id", nullable = false)
    private Long businessId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "ward")
    private String ward;

    @Column(name = "rfm_score", nullable = false)
    private Integer rfmScore = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "segment", nullable = false)
    private CustomerSegment segment = CustomerSegment.NEW;

    @Column(name = "total_orders", nullable = false)
    private Integer totalOrders = 0;

    @Column(name = "total_spent", precision = 15, scale = 2, nullable = false)
    private Double totalSpent = 0.0;

    @Column(name = "average_order_value", precision = 15, scale = 2, nullable = false)
    private Double averageOrderValue = 0.0;

    @Column(name = "last_order_at")
    private LocalDateTime lastOrderAt;

    @Column(name = "last_contact_at")
    private LocalDateTime lastContactAt;

    @Column(name = "lifetime_value", precision = 15, scale = 2, nullable = false)
    private Double lifetimeValue = 0.0;

    @Column(name = "churn_probability", precision = 5, scale = 2, nullable = false)
    private Double churnProbability = 0.0;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "tags")
    private String tags; // CSV: 'vip,frequent-buyer'

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    private CustomerSource source = CustomerSource.MANUAL;

    @Column(name = "source_id")
    private String sourceId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public enum CustomerSegment {
        VIP, LOYAL, POTENTIAL, AT_RISK, LOST, NEW
    }

    public enum CustomerSource {
        WEBSITE, ZALO_OA, ZALO_PERSONAL, MANUAL, IMPORT
    }
}

