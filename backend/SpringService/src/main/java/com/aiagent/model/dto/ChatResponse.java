package com.aiagent.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    
    /**
     * User's original message
     */
    private String message;
    
    /**
     * AI response text
     */
    private String response;
    
    /**
     * Model used (e.g., "gemini-2.0-flash-exp")
     */
    private String model;
    
    /**
     * Processing time in seconds
     */
    @JsonProperty("processing_time")
    private Double processingTime;
    
    /**
     * Number of source documents used (for RAG)
     */
    @JsonProperty("sources_count")
    @Builder.Default
    private Integer sourcesCount = 0;
    
    /**
     * Relevant documents (Spring Boot format)
     */
    private List<RelevantDocument> relevantDocuments;
    
    /**
     * Sources from Python AI (list of document names)
     */
    private List<String> sources;
    
    /**
     * Sentiment analysis
     */
    private String sentiment;
    
    /**
     * Confidence score
     */
    private Double confidence;
    
    /**
     * Timestamp
     */
    private LocalDateTime timestamp;
    
    /**
     * Error message if any
     */
    private String error;
    
    /**
     * Success flag
     */
    @Builder.Default
    private Boolean success = true;

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

