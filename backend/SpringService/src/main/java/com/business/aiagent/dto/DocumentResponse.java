package com.business.aiagent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse {
    
    private Long id;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String status;
    private String category;
    private String tags;
    private Boolean vectorized;
    private Integer chunkCount;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
}
