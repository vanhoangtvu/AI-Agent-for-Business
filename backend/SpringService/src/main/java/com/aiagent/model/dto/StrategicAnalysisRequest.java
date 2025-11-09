package com.aiagent.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

/**
 * Strategic Analysis Request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategicAnalysisRequest {

    /**
     * User ID (for Python API)
     */
    @JsonProperty("user_id")
    private Long userId;
    
    /**
     * Business name (optional)
     */
    @JsonProperty("business_name")
    private String businessName;
    
    /**
     * Business metrics for analysis
     * Example: {"revenue": 50000000, "customers": 200, "growth_rate": 15}
     */
    private Map<String, Object> metrics;
    
    /**
     * Analysis type: "comprehensive", "quick", "swot"
     */
    @JsonProperty("analysis_type")
    @Builder.Default
    private String analysisType = "comprehensive";

    // Legacy fields (keep for backward compatibility)
    private LocalDate startDate;
    private LocalDate endDate;
    private Map<String, Object> businessMetrics;
    private String industry;
    private String additionalContext;

    // Legacy enum (deprecated but kept for compatibility)
    @Deprecated
    public enum AnalysisType {
        SWOT,
        MARKET_OPPORTUNITY,
        RISK_ASSESSMENT,
        COMPETITIVE_ANALYSIS,
        GROWTH_STRATEGY,
        FULL_STRATEGIC
    }

}

