package com.business.springservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueReportDTO {
    
    @Schema(description = "Period label", example = "2025-11-30 or Week 48 or November 2025")
    private String period;
    
    @Schema(description = "Total orders in period")
    private Long totalOrders;
    
    @Schema(description = "Number of delivered orders")
    private Long deliveredOrders;
    
    @Schema(description = "Total revenue from delivered orders")
    private BigDecimal revenue;
    
    @Schema(description = "Total products sold (quantity)")
    private Long productsSold;
}
