package com.business.aiagent.dto;

import lombok.Data;

@Data
public class ReportRequest {
    private String title;
    private String reportType;
    private String context;
}
