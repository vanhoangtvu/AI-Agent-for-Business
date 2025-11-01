package com.aiagent.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Customer Dashboard Response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer Dashboard - Thông tin khách hàng")
public class CustomerDashboardResponse {

    @Schema(description = "Thông tin khách hàng")
    private CustomerInfo customerInfo;

    @Schema(description = "Số conversations của khách hàng")
    private Long totalConversations;

    @Schema(description = "Số messages đã gửi")
    private Long totalMessages;

    @Schema(description = "Conversations gần đây")
    private List<RecentConversation> recentConversations;

    @Schema(description = "Documents được chia sẻ")
    private List<SharedDocument> sharedDocuments;

    @Schema(description = "Quick actions khả dụng")
    private List<String> availableActions;

    @Schema(description = "Support info")
    private SupportInfo supportInfo;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerInfo {
        private String fullName;
        private String email;
        private String phoneNumber;
        private LocalDateTime joinedDate;
        private String status;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentConversation {
        private Long id;
        private String title;
        private LocalDateTime lastMessageAt;
        private Integer messageCount;
        private Boolean active;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SharedDocument {
        private Long id;
        private String title;
        private String category;
        private String fileType;
        private LocalDateTime sharedAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SupportInfo {
        private String supportEmail;
        private String supportPhone;
        private String businessHours;
        private Boolean chatAvailable;
    }

}

