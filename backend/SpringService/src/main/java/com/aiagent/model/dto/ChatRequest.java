package com.aiagent.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Boolean useContext;

    private Integer maxResults;

    @Builder.Default
    private Boolean streamResponse = false;

}

