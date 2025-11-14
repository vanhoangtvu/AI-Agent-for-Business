package com.business.aiagent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * DTO cho Business Analytics Dashboard
 * Dùng cho role BUSINESS xem phân tích doanh nghiệp của mình
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessAnalyticsResponse {
    
    // Tổng quan
    private OverviewStats overview;
    
    // Phân tích doanh thu
    private RevenueAnalytics revenue;
    
    // Phân tích sản phẩm
    private ProductAnalytics products;
    
    // Phân tích đơn hàng
    private OrderAnalytics orders;
    
    // Phân tích khách hàng
    private CustomerAnalytics customers;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OverviewStats {
        private Integer totalProducts;
        private Integer activeProducts;
        private Integer lowStockProducts;
        private Integer totalOrders;
        private Integer pendingOrders;
        private Integer totalCustomers;
        private BigDecimal totalRevenue;
        private BigDecimal monthlyRevenue;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RevenueAnalytics {
        private BigDecimal today;
        private BigDecimal thisWeek;
        private BigDecimal thisMonth;
        private BigDecimal lastMonth;
        private BigDecimal thisYear;
        private BigDecimal growthRate; // Tỷ lệ tăng trưởng (%)
        private List<DailyRevenue> dailyRevenues; // 30 ngày gần nhất
        private List<MonthlyRevenue> monthlyRevenues; // 12 tháng gần nhất
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyRevenue {
        private String date;
        private BigDecimal revenue;
        private Integer orderCount;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyRevenue {
        private String month;
        private BigDecimal revenue;
        private Integer orderCount;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductAnalytics {
        private Integer totalProducts;
        private Integer activeProducts;
        private Integer outOfStock;
        private List<TopProduct> topSelling; // Top 10 sản phẩm bán chạy
        private List<TopProduct> topRevenue; // Top 10 sản phẩm doanh thu cao
        private List<TopProduct> lowStock; // Sản phẩm sắp hết hàng
        private Map<String, Integer> categoryDistribution; // Phân bố theo danh mục
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopProduct {
        private Long productId;
        private String productName;
        private String imageUrl;
        private Integer quantitySold;
        private BigDecimal revenue;
        private Integer stockQuantity;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderAnalytics {
        private Integer totalOrders;
        private Integer pendingOrders;
        private Integer processingOrders;
        private Integer shippingOrders;
        private Integer completedOrders;
        private Integer cancelledOrders;
        private BigDecimal averageOrderValue;
        private Map<String, Integer> orderStatusDistribution;
        private List<RecentOrder> recentOrders;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentOrder {
        private Long orderId;
        private String orderNumber;
        private String customerName;
        private BigDecimal total;
        private String status;
        private String createdAt;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerAnalytics {
        private Integer totalCustomers;
        private Integer newCustomersThisMonth;
        private Integer activeCustomers; // Khách hàng có đơn trong 30 ngày
        private List<TopCustomer> topCustomers; // Top khách hàng mua nhiều nhất
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopCustomer {
        private Long customerId;
        private String customerName;
        private String email;
        private Integer orderCount;
        private BigDecimal totalSpent;
    }
}
