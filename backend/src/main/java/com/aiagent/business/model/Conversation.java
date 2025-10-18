package com.aiagent.business.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Conversation Entity - Cuộc hội thoại
 * 
 * Hỗ trợ đa kênh: WEBSITE, ZALO_OA, ZALO_PERSONAL
 * channel_metadata chứa thông tin đặc thù của từng kênh (đã gộp zalo_conversations)
 */
@Entity
@Table(name = "conversations", indexes = {
    @Index(name = "idx_business", columnList = "business_id"),
    @Index(name = "idx_customer", columnList = "customer_id"),
    @Index(name = "idx_channel", columnList = "channel, channel_conversation_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_last_message_at", columnList = "last_message_at")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_id", nullable = false)
    private Long businessId;

    @Column(name = "customer_id")
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false)
    private Channel channel;

    @Column(name = "channel_conversation_id")
    private String channelConversationId;

    /**
     * Channel metadata - JSON field (đã gộp zalo_conversations)
     * 
     * For ZALO:
     * {
     *   "zalo_user_id": "1234567890",
     *   "zalo_user_name": "Nguyễn Văn A",
     *   "zalo_avatar_url": "https://...",
     *   "source": "ZALO_PERSONAL",
     *   "source_config_id": 5
     * }
     * 
     * For WEBSITE:
     * {
     *   "ip_address": "192.168.1.1",
     *   "user_agent": "Chrome/120.0",
     *   "referrer": "https://google.com"
     * }
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "channel_metadata", columnDefinition = "json")
    private Map<String, Object> channelMetadata;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.ACTIVE;

    @Column(name = "assigned_to")
    private Long assignedTo;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "ai_mode", nullable = false)
    private AiMode aiMode = AiMode.AUTO;

    @Column(name = "ai_handled_count", nullable = false)
    private Integer aiHandledCount = 0;

    @Column(name = "human_handled_count", nullable = false)
    private Integer humanHandledCount = 0;

    @Column(name = "last_message_at")
    private LocalDateTime lastMessageAt;

    @Column(name = "last_message_preview", columnDefinition = "TEXT")
    private String lastMessagePreview;

    @Column(name = "unread_count", nullable = false)
    private Integer unreadCount = 0;

    @Column(name = "tags")
    private String tags;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority = Priority.NORMAL;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt = LocalDateTime.now();

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @Column(name = "archived_at")
    private LocalDateTime archivedAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum Channel {
        WEBSITE, ZALO_OA, ZALO_PERSONAL
    }

    public enum Status {
        ACTIVE, RESOLVED, ARCHIVED
    }

    public enum AiMode {
        AUTO, SUGGESTION, NOTIFICATION, DISABLED
    }

    public enum Priority {
        LOW, NORMAL, HIGH, URGENT
    }
}

