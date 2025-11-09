package com.aiagent.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
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
    
    /**
     * User ID
     */
    @JsonProperty("user_id")
    private Long userId;
    
    /**
     * Analysis type
     */
    @JsonProperty("analysis_type")
    private String analysisType;
    
    /**
     * Strategic recommendations from AI
     */
    private List<String> recommendations;
    
    /**
     * SWOT Analysis
     * Format: {"strengths": [...], "weaknesses": [...], "opportunities": [...], "threats": [...]}
     */
    @JsonProperty("swot_analysis")
    private Map<String, List<String>> swotAnalysis;
    
    /**
     * Market insights
     */
    @JsonProperty("market_insights")
    private Map<String, Object> marketInsights;
    
    /**
     * Risk assessment
     */
    @JsonProperty("risk_assessment")
    private Map<String, Object> riskAssessment;
    
    /**
     * Processing time in seconds
     */
    @JsonProperty("processing_time")
    private Double processingTime;
    
    /**
     * Model used (e.g., "gemini-2.0-flash-exp")
     */
    @JsonProperty("model_used")
    private String modelUsed;
    
    /**
     * Error message if any
     */
    private String error;
    
    /**
     * Success flag
     */
    @Builder.Default
    private Boolean success = true;
    
    // Legacy fields (keep for backward compatibility)
    private Map<String, Object> insights;
    private String summary;
    private Double confidenceScore;
    private LocalDateTime generatedAt;

}

