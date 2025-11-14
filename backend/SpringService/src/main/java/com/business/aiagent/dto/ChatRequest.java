package com.business.aiagent.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private Long conversationId;
    private String message;
}
