package com.aiagent.model.dto;

import com.aiagent.model.entity.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Document Response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse {

    private Long id;
    
    private String fileName;
    
    private String fileType;
    
    private Long fileSize;
    
    private String title;
    
    private String description;
    
    private String category;
    
    private Set<String> tags;
    
    private Document.ProcessingStatus status;
    
    private Integer chunkCount;
    
    private Boolean vectorized;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private LocalDateTime processedAt;

}

