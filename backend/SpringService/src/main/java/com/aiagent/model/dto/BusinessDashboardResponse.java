package com.aiagent.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Business Dashboard Response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Business Dashboard - Thống kê doanh nghiệp")
public class BusinessDashboardResponse {

    @Schema(description = "Tên doanh nghiệp")
    private String businessName;

    @Schema(description = "Tổng số documents của business")
    private Long totalDocuments;

    @Schema(description = "Documents được xử lý")
    private Long processedDocuments;

    @Schema(description = "Documents đang chờ")
    private Long pendingDocuments;

    @Schema(description = "Tổng số khách hàng")
    private Long totalCustomers;

    @Schema(description = "Khách hàng mới trong tháng")
    private Long newCustomersThisMonth;

    @Schema(description = "Tổng conversations")
    private Long totalConversations;

    @Schema(description = "Messages hôm nay")
    private Long messagesToday;

    @Schema(description = "Messages trong tuần")
    private Long messagesThisWeek;

    @Schema(description = "Trung bình messages/ngày")
    private Double avgMessagesPerDay;

    @Schema(description = "Top 5 categories documents")
    private Map<String, Long> topCategories;

    @Schema(description = "Sentiment phân tích")
    private Map<String, Long> sentimentAnalysis;

    @Schema(description = "Customer satisfaction score (0-100)")
    private Double satisfactionScore;

    @Schema(description = "Response rate (%)")
    private Double responseRate;

    @Schema(description = "Active conversations")
    private Long activeConversations;

}

