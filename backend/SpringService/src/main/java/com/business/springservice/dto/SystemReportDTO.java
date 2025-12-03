package com.business.springservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Báo cáo tổng hợp hệ thống")
public class SystemReportDTO {
    
    @Schema(description = "Thời gian tạo báo cáo")
    private LocalDateTime reportTime;
    
    @Schema(description = "Tổng quan doanh thu")
    private RevenueOverview revenueOverview;
    
    @Schema(description = "Thống kê sản phẩm")
    private ProductStatistics productStatistics;
    
    @Schema(description = "Thống kê đơn hàng")
    private OrderStatistics orderStatistics;
    
    @Schema(description = "Thống kê khách hàng")
    private CustomerStatistics customerStatistics;
    
    @Schema(description = "Thống kê doanh nghiệp")
    private BusinessStatistics businessStatistics;
    
    @Schema(description = "Top sản phẩm bán chạy")
    private List<TopProductDTO> topSellingProducts;
    
    @Schema(description = "Doanh thu theo ngày (7 ngày gần nhất)")
    private List<DailyRevenueDTO> dailyRevenue;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RevenueOverview {
        @Schema(description = "Tổng doanh thu")
        private BigDecimal totalRevenue;
        
        @Schema(description = "Doanh thu hôm nay")
        private BigDecimal todayRevenue;
        
        @Schema(description = "Doanh thu tuần này")
        private BigDecimal weekRevenue;
        
        @Schema(description = "Doanh thu tháng này")
        private BigDecimal monthRevenue;
        
        @Schema(description = "Tăng trưởng so với tháng trước (%)")
        private Double growthRate;
        
        @Schema(description = "Doanh thu trung bình/đơn")
        private BigDecimal avgOrderValue;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductStatistics {
        @Schema(description = "Tổng số sản phẩm")
        private Long totalProducts;
        
        @Schema(description = "Sản phẩm đang bán")
        private Long activeProducts;
        
        @Schema(description = "Sản phẩm hết hàng")
        private Long outOfStockProducts;
        
        @Schema(description = "Sản phẩm sắp hết hàng (<10)")
        private Long lowStockProducts;
        
        @Schema(description = "Tổng giá trị tồn kho")
        private BigDecimal totalInventoryValue;
        
        @Schema(description = "Số lượng sản phẩm đã bán")
        private Long totalProductsSold;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderStatistics {
        @Schema(description = "Tổng số đơn hàng")
        private Long totalOrders;
        
        @Schema(description = "Đơn chờ xử lý")
        private Long pendingOrders;
        
        @Schema(description = "Đơn đang xử lý")
        private Long processingOrders;
        
        @Schema(description = "Đơn đang giao")
        private Long shippingOrders;
        
        @Schema(description = "Đơn đã giao")
        private Long deliveredOrders;
        
        @Schema(description = "Đơn đã hủy")
        private Long cancelledOrders;
        
        @Schema(description = "Tỷ lệ giao hàng thành công (%)")
        private Double successRate;
        
        @Schema(description = "Số đơn hôm nay")
        private Long todayOrders;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerStatistics {
        @Schema(description = "Tổng số khách hàng")
        private Long totalCustomers;
        
        @Schema(description = "Khách hàng mới tháng này")
        private Long newCustomersThisMonth;
        
        @Schema(description = "Khách hàng đã mua hàng")
        private Long customersWithOrders;
        
        @Schema(description = "Tỷ lệ chuyển đổi (%)")
        private Double conversionRate;
        
        @Schema(description = "Giá trị trung bình/khách hàng")
        private BigDecimal avgCustomerValue;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BusinessStatistics {
        @Schema(description = "Tổng số doanh nghiệp")
        private Long totalBusinesses;
        
        @Schema(description = "Doanh nghiệp hoạt động")
        private Long activeBusinesses;
        
        @Schema(description = "Tổng số sản phẩm của doanh nghiệp")
        private Long totalBusinessProducts;
        
        @Schema(description = "Doanh nghiệp có doanh thu cao nhất")
        private String topBusiness;
        
        @Schema(description = "Doanh thu doanh nghiệp top")
        private BigDecimal topBusinessRevenue;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopProductDTO {
        @Schema(description = "ID sản phẩm")
        private Long productId;
        
        @Schema(description = "Tên sản phẩm")
        private String productName;
        
        @Schema(description = "Số lượng đã bán")
        private Long soldQuantity;
        
        @Schema(description = "Doanh thu")
        private BigDecimal revenue;
        
        @Schema(description = "Tên doanh nghiệp")
        private String businessName;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyRevenueDTO {
        @Schema(description = "Ngày")
        private String date;
        
        @Schema(description = "Số đơn")
        private Long orders;
        
        @Schema(description = "Doanh thu")
        private BigDecimal revenue;
    }
}
