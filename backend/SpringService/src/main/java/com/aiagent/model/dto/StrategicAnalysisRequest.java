package com.aiagent.model.dto;

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

    @NotNull(message = "Analysis type is required")
    private AnalysisType analysisType;

    private LocalDate startDate;

    private LocalDate endDate;

    private Map<String, Object> businessMetrics;

    private String industry;

    private String additionalContext;

    public enum AnalysisType {
        SWOT,
        MARKET_OPPORTUNITY,
        RISK_ASSESSMENT,
        COMPETITIVE_ANALYSIS,
        GROWTH_STRATEGY,
        FULL_STRATEGIC
    }

}

