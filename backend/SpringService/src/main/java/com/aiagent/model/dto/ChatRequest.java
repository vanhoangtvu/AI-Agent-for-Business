package com.aiagent.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Chat Request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {

    @NotBlank(message = "Message is required")
    private String message;

    @NotNull(message = "Conversation ID is required")
    private Long conversationId;
    
    /**
     * User ID (populated from auth context)
     */
    private Long userId;
    
    /**
     * Conversation history for AI context
     */
    private List<Map<String, String>> conversationHistory;
    
    /**
     * Document IDs for RAG
     */
    private List<Long> documentIds;

    @Builder.Default
    private Boolean useContext = true;
    
    @Builder.Default
    private Boolean useRag = false;

    private Integer maxResults;
    
    @Builder.Default
    private Integer maxTokens = 2000;

    @Builder.Default
    private Boolean streamResponse = false;

}

