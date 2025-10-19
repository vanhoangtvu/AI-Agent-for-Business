package com.aiagent.business.controller;

import com.aiagent.business.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")
@Tag(name = "Analytics & AI Insights", description = "Phân tích dữ liệu và AI insights")
@SecurityRequirement(name = "bearerAuth")
public class AnalyticsController {

    @GetMapping("/overview")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Tổng quan kinh doanh", 
               description = "Dashboard overview: doanh thu, đơn hàng, khách hàng, tin nhắn")
    public ResponseEntity<ApiResponse<?>> getOverview(
            @Parameter(description = "Từ ngày (YYYY-MM-DD)") @RequestParam(required = false) String startDate,
            @Parameter(description = "Đến ngày (YYYY-MM-DD)") @RequestParam(required = false) String endDate
    ) {
        return ResponseEntity.ok(ApiResponse.success("Lấy tổng quan thành công", null));
    }

    @GetMapping("/revenue")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Phân tích doanh thu", description = "Biểu đồ doanh thu theo ngày/tuần/tháng")
    public ResponseEntity<ApiResponse<?>> getRevenueAnalytics(
            @Parameter(description = "Khoảng thời gian: DAY, WEEK, MONTH, YEAR") @RequestParam String period,
            @Parameter(description = "Từ ngày") @RequestParam(required = false) String startDate,
            @Parameter(description = "Đến ngày") @RequestParam(required = false) String endDate
    ) {
        return ResponseEntity.ok(ApiResponse.success("Lấy phân tích thành công", null));
    }

    @GetMapping("/products/top-selling")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Sản phẩm bán chạy", description = "Top sản phẩm có doanh số cao nhất")
    public ResponseEntity<ApiResponse<?>> getTopSellingProducts(
            @Parameter(description = "Số lượng top") @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "Từ ngày") @RequestParam(required = false) String startDate,
            @Parameter(description = "Đến ngày") @RequestParam(required = false) String endDate
    ) {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách thành công", null));
    }

    @GetMapping("/customers/rfm")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Phân tích RFM khách hàng", 
               description = "RFM Analysis: Recency, Frequency, Monetary. Phân loại khách hàng VIP, REGULAR, POTENTIAL, AT_RISK")
    public ResponseEntity<ApiResponse<?>> getRFMAnalysis() {
        return ResponseEntity.ok(ApiResponse.success("Phân tích RFM thành công", null));
    }

    @GetMapping("/customers/churn-prediction")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Dự đoán khách hàng rời bỏ", 
               description = "AI dự đoán khách hàng có nguy cơ churn cao")
    public ResponseEntity<ApiResponse<?>> getChurnPrediction() {
        return ResponseEntity.ok(ApiResponse.success("Dự đoán thành công", null));
    }

    @GetMapping("/chatbot/performance")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Hiệu suất chatbot", 
               description = "Số tin nhắn, thời gian phản hồi, tỷ lệ giải quyết, customer satisfaction")
    public ResponseEntity<ApiResponse<?>> getChatbotPerformance(
            @Parameter(description = "Từ ngày") @RequestParam(required = false) String startDate,
            @Parameter(description = "Đến ngày") @RequestParam(required = false) String endDate
    ) {
        return ResponseEntity.ok(ApiResponse.success("Lấy thống kê thành công", null));
    }

    @GetMapping("/ai-insights")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "AI Insights & Recommendations", 
               description = "AI phân tích và đưa ra đề xuất chiến lược kinh doanh")
    public ResponseEntity<ApiResponse<?>> getAIInsights() {
        return ResponseEntity.ok(ApiResponse.success("Lấy insights thành công", null));
    }

    @GetMapping("/forecast/revenue")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Dự báo doanh thu", 
               description = "AI dự đoán doanh thu tháng tới dựa trên dữ liệu lịch sử")
    public ResponseEntity<ApiResponse<?>> forecastRevenue(
            @Parameter(description = "Số tháng dự báo") @RequestParam(defaultValue = "3") int months
    ) {
        return ResponseEntity.ok(ApiResponse.success("Dự báo thành công", null));
    }

    @GetMapping("/export")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Export báo cáo", description = "Export analytics report ra Excel/PDF")
    public ResponseEntity<ApiResponse<?>> exportReport(
            @Parameter(description = "Format: EXCEL, PDF") @RequestParam String format,
            @Parameter(description = "Loại báo cáo: OVERVIEW, REVENUE, PRODUCTS, CUSTOMERS") @RequestParam String reportType,
            @Parameter(description = "Từ ngày") @RequestParam(required = false) String startDate,
            @Parameter(description = "Đến ngày") @RequestParam(required = false) String endDate
    ) {
        return ResponseEntity.ok(ApiResponse.success("Export thành công", null));
    }
}

