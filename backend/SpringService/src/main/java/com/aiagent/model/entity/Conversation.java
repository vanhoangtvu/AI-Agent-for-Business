package com.aiagent.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Conversation Entity
 */
@Entity
@Table(name = "conversations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt ASC")
    @Builder.Default
    private List<Message> messages = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column
    private LocalDateTime lastMessageAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Helper method to add message
     */
    public void addMessage(Message message) {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        this.messages.add(message);
        message.setConversation(this);
        this.lastMessageAt = LocalDateTime.now();
    }

    /**
     * Message Entity (Nested)
     */
    @Entity
    @Table(name = "messages")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EntityListeners(AuditingEntityListener.class)
    public static class Message {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "conversation_id", nullable = false)
        private Conversation conversation;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private MessageRole role;

        @Column(nullable = false, columnDefinition = "TEXT")
        private String content;

        @Column(columnDefinition = "TEXT")
        private String context;

        @Column
        private String sentiment;

        @Column
        private Double confidence;

        @CreatedDate
        @Column(nullable = false, updatable = false)
        private LocalDateTime createdAt;

        /**
         * Message Role Enum
         */
        public enum MessageRole {
            USER,
            ASSISTANT,
            SYSTEM
        }
    }

}

