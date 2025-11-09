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
import java.util.HashSet;
import java.util.Set;

/**
 * Document Entity
 */
@Entity
@Table(name = "documents")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String fileName;

    @Column(nullable = false, length = 500)
    private String filePath;

    @Column(nullable = false, length = 100)
    private String fileType;

    @Column(nullable = false)
    private Long fileSize;

    @Column(length = 500)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String category;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "document_tags", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "tag")
    @Builder.Default
    private Set<String> tags = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ProcessingStatus status = ProcessingStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String extractedText;

    @Column
    private Integer chunkCount;

    @Column
    private Boolean vectorized;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime processedAt;

    /**
     * Processing Status Enum
     */
    public enum ProcessingStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED
    }

    /**
     * Helper method to add tag
     */
    public void addTag(String tag) {
        if (this.tags == null) {
            this.tags = new HashSet<>();
        }
        this.tags.add(tag);
    }

    /**
     * Helper method to remove tag
     */
    public void removeTag(String tag) {
        if (this.tags != null) {
            this.tags.remove(tag);
        }
    }

}

