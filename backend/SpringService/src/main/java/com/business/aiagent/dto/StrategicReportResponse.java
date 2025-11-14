package com.business.aiagent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrategicReportResponse {
    private Long id;
    private String title;
    private String reportType;
    private String swotAnalysis;
    private String recommendations;
    private String marketInsights;
    private String riskAssessment;
    private LocalDateTime createdAt;
}
