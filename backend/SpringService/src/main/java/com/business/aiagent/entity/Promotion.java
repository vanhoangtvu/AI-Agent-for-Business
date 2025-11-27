package com.business.aiagent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "promotions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String code;
    
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PromotionType type; // PERCENTAGE, FIXED_AMOUNT, BUY_X_GET_Y
    
    @Column(nullable = false)
    private BigDecimal discountValue; // Giá trị giảm (% hoặc số tiền)
    
    private BigDecimal maxDiscountAmount; // Giá trị giảm tối đa (cho % discount)
    
    private BigDecimal minOrderAmount; // Giá trị đơn hàng tối thiểu
    
    @Column(nullable = false)
    private LocalDateTime startDate;
    
    @Column(nullable = false)
    private LocalDateTime endDate;
    
    private Integer usageLimit; // Số lần sử dụng tối đa (null = unlimited)
    
    @Builder.Default
    private Integer usedCount = 0; // Số lần đã sử dụng
    
    @Builder.Default
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @Builder.Default
    @Column(nullable = false)
    private Boolean isPublic = true; // True = hiển thị công khai, False = chỉ admin có thể áp dụng
    
    // Buy X Get Y fields
    private Integer buyQuantity; // Mua X sản phẩm
    private Integer getQuantity; // Được tặng Y sản phẩm
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "promotion_products",
        joinColumns = @JoinColumn(name = "promotion_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> applicableProducts = new HashSet<>(); // Sản phẩm áp dụng (null = tất cả)
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "promotion_categories",
        joinColumns = @JoinColumn(name = "promotion_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> applicableCategories = new HashSet<>(); // Danh mục áp dụng
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Business logic methods
    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return isActive 
            && now.isAfter(startDate) 
            && now.isBefore(endDate)
            && (usageLimit == null || usedCount < usageLimit);
    }
    
    public BigDecimal calculateDiscount(BigDecimal orderAmount) {
        if (!isValid()) {
            return BigDecimal.ZERO;
        }
        
        // Check minimum order amount
        if (minOrderAmount != null && orderAmount.compareTo(minOrderAmount) < 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal discount = BigDecimal.ZERO;
        
        switch (type) {
            case PERCENTAGE:
                discount = orderAmount.multiply(discountValue).divide(BigDecimal.valueOf(100));
                // Apply max discount limit
                if (maxDiscountAmount != null && discount.compareTo(maxDiscountAmount) > 0) {
                    discount = maxDiscountAmount;
                }
                break;
                
            case FIXED_AMOUNT:
                discount = discountValue;
                // Discount cannot exceed order amount
                if (discount.compareTo(orderAmount) > 0) {
                    discount = orderAmount;
                }
                break;
                
            case BUY_X_GET_Y:
                // This needs to be calculated based on cart items
                // Will be handled separately
                break;
        }
        
        return discount;
    }
    
    public enum PromotionType {
        PERCENTAGE,      // Giảm theo %
        FIXED_AMOUNT,    // Giảm số tiền cố định
        BUY_X_GET_Y     // Mua X tặng Y
    }
}
