package com.aiagent.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Chat Response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    private Long messageId;
    
    private String response;
    
    private List<RelevantDocument> relevantDocuments;
    
    private String sentiment;
    
    private Double confidence;
    
    private LocalDateTime timestamp;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RelevantDocument {
        private Long documentId;
        private String title;
        private String excerpt;
        private Double relevanceScore;
    }

}

