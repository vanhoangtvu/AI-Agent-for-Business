package com.business.aiagent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatistics {
    private Long totalProducts;
    private Long totalOrders;
    private Long totalDocuments;
    private BigDecimal totalRevenue;
    private Long totalUsers;
    private Long totalReviews;
    private Long pendingOrders;
    private Long completedOrders;
}
