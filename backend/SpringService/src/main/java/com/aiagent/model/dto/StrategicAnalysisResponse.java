package com.aiagent.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Strategic Analysis Response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategicAnalysisResponse {

    private Long reportId;
    
    private String analysisType;
    
    private Map<String, Object> insights;
    
    private Map<String, Object> recommendations;
    
    private String summary;
    
    private Double confidenceScore;
    
    private LocalDateTime generatedAt;

}

