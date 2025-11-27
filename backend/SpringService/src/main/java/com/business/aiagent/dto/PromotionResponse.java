package com.business.aiagent.dto;

import com.business.aiagent.entity.Promotion.PromotionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionResponse {
    
    private Long id;
    private String code;
    private String name;
    private String description;
    private PromotionType type;
    private BigDecimal discountValue;
    private BigDecimal maxDiscountAmount;
    private BigDecimal minOrderAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer usageLimit;
    private Integer usedCount;
    private Boolean isActive;
    private Boolean isPublic;
    private Integer buyQuantity;
    private Integer getQuantity;
    private Set<Long> applicableProductIds;
    private Set<Long> applicableCategoryIds;
    private Long createdById; // ID của user tạo
    private String createdByName; // Tên user tạo
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Calculated fields
    private Boolean isValid;
    private Boolean isExpired;
    private Boolean isUpcoming;
    private Integer remainingUsage;
    private Long daysRemaining;
}
