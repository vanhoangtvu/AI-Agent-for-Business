package com.aiagent.business.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Message Entity - Tin nhắn
 * 
 * metadata chứa attachments (đã gộp chat_attachments)
 */
@Entity
@Table(name = "messages", indexes = {
    @Index(name = "idx_conversation", columnList = "conversation_id"),
    @Index(name = "idx_created_at", columnList = "created_at"),
    @Index(name = "idx_sender", columnList = "sender_type, sender_id")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conversation_id", nullable = false)
    private Long conversationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "sender_type", nullable = false)
    private SenderType senderType;

    @Column(name = "sender_id")
    private Long senderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false)
    private MessageType messageType = MessageType.TEXT;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    /**
     * Metadata - JSON field (đã gộp chat_attachments)
     * 
     * {
     *   "attachments": [
     *     {
     *       "file_name": "image.jpg",
     *       "file_type": "image/jpeg",
     *       "file_size": 1024000,
     *       "file_url": "https://...",
     *       "thumbnail_url": "https://..."
     *     }
     *   ],
     *   "mentions": [...],
     *   "reactions": [...]
     * }
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "json")
    private Map<String, Object> metadata;

    @Column(name = "is_ai_generated", nullable = false)
    private Boolean isAiGenerated = false;

    @Column(name = "ai_confidence", precision = 5, scale = 2)
    private Double aiConfidence;

    @Column(name = "ai_intent")
    private String aiIntent;

    @Column(name = "used_rag", nullable = false)
    private Boolean usedRag = false;

    /**
     * RAG sources - JSON field
     * [
     *   {
     *     "document_id": 1,
     *     "chunk_id": 5,
     *     "similarity": 0.89
     *   }
     * ]
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "rag_sources", columnDefinition = "json")
    private List<Map<String, Object>> ragSources;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Column(name = "external_message_id")
    private String externalMessageId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public enum SenderType {
        CUSTOMER, BUSINESS, AI
    }

    public enum MessageType {
        TEXT, IMAGE, FILE, AUDIO, VIDEO, LOCATION, STICKER
    }
}

