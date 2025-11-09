package com.aiagent.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Conversation Response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationResponse {

    private Long id;
    
    private String title;
    
    private Boolean active;
    
    private LocalDateTime lastMessageAt;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private Integer messageCount;

}
