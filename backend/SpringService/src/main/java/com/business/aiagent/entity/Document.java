package com.business.aiagent.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false, length = 255)
    private String fileName;
    
    @Column(length = 500)
    private String filePath;
    
    @Column(length = 50)
    private String fileType;
    
    private Long fileSize;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Builder.Default
    private ProcessStatus status = ProcessStatus.PENDING;
    
    @Column(length = 100)
    private String category;
    
    @Column(length = 500)
    private String tags;
    
    @Builder.Default
    private Boolean vectorized = false;
    
    @Builder.Default
    private Integer chunkCount = 0;
    
    @Builder.Default
    private Integer totalTokens = 0;
    
    private LocalDateTime processedAt;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    public enum ProcessStatus {
        PENDING, PROCESSING, COMPLETED, FAILED
    }
}
