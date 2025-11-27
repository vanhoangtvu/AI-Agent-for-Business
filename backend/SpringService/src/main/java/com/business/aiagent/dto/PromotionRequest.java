package com.business.aiagent.dto;

import com.business.aiagent.entity.Promotion.PromotionType;
import lombok.Data;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PromotionRequest {
    
    @NotBlank(message = "Promotion code is required")
    @Size(min = 3, max = 50, message = "Code must be between 3 and 50 characters")
    private String code;
    
    @NotBlank(message = "Promotion name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    private String name;
    
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    
    @NotNull(message = "Promotion type is required")
    private PromotionType type;
    
    @NotNull(message = "Discount value is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount value must be greater than 0")
    private BigDecimal discountValue;
    
    @DecimalMin(value = "0.0", message = "Max discount amount must be non-negative")
    private BigDecimal maxDiscountAmount;
    
    @DecimalMin(value = "0.0", message = "Min order amount must be non-negative")
    private BigDecimal minOrderAmount;
    
    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;
    
    @NotNull(message = "End date is required")
    private LocalDateTime endDate;
    
    @Min(value = 1, message = "Usage limit must be at least 1")
    private Integer usageLimit;
    
    private Boolean isActive = true;
    
    private Boolean isPublic = true;
    
    // For BUY_X_GET_Y type
    @Min(value = 1, message = "Buy quantity must be at least 1")
    private Integer buyQuantity;
    
    @Min(value = 1, message = "Get quantity must be at least 1")
    private Integer getQuantity;
    
    private Set<Long> applicableProductIds;
    
    private Set<Long> applicableCategoryIds;
    
    private Long createdById; // ID của user tạo promotion
    
    @AssertTrue(message = "End date must be after start date")
    public boolean isValidDateRange() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null validation
        }
        return endDate.isAfter(startDate);
    }
    
    @AssertTrue(message = "Buy and Get quantities are required for BUY_X_GET_Y type")
    public boolean isValidBuyXGetY() {
        if (type == PromotionType.BUY_X_GET_Y) {
            return buyQuantity != null && getQuantity != null;
        }
        return true;
    }
}
