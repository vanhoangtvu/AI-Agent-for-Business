package com.business.aiagent.dto;

import com.business.aiagent.entity.ActivityLog.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLogResponse {
    private Long id;
    private ActivityType activityType;
    private String description;
    private String ipAddress;
    private LocalDateTime timestamp;
}
