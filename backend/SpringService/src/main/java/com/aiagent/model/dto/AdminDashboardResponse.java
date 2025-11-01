package com.aiagent.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Admin Dashboard Response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Admin Dashboard - Tổng quan toàn hệ thống")
public class AdminDashboardResponse {

    @Schema(description = "Tổng số users trong hệ thống")
    private Long totalUsers;

    @Schema(description = "Số users mới trong tháng")
    private Long newUsersThisMonth;

    @Schema(description = "Users theo role")
    private Map<String, Long> usersByRole;

    @Schema(description = "Tổng số documents")
    private Long totalDocuments;

    @Schema(description = "Documents theo status")
    private Map<String, Long> documentsByStatus;

    @Schema(description = "Tổng số conversations")
    private Long totalConversations;

    @Schema(description = "Messages hôm nay")
    private Long messagesToday;

    @Schema(description = "Tổng dung lượng files (bytes)")
    private Long totalStorageUsed;

    @Schema(description = "Active users hôm nay")
    private Long activeUsersToday;

    @Schema(description = "System health status")
    private String systemHealth;

    @Schema(description = "Top 5 active businesses")
    private Object topBusinesses;

    @Schema(description = "Recent activities")
    private Object recentActivities;

}

