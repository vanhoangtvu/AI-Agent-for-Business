package com.business.aiagent.dto;

import com.business.aiagent.entity.Message.MessageRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private Long id;
    private MessageRole role;
    private String content;
    private LocalDateTime timestamp;
}
