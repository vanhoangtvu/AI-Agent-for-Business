package com.business.aiagent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyPromotionRequest {
    
    @NotBlank(message = "Promotion code is required")
    private String promotionCode;
    
    @NotNull(message = "Order amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Order amount must be greater than 0")
    private BigDecimal orderAmount;
    
    private Long userId;
}
