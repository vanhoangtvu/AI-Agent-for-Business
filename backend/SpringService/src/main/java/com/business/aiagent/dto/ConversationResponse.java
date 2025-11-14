package com.business.aiagent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationResponse {
    private Long id;
    private String title;
    private int messageCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
