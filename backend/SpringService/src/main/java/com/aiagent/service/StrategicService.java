package com.aiagent.service;

import com.aiagent.model.dto.StrategicAnalysisRequest;
import com.aiagent.model.dto.StrategicAnalysisResponse;

/**
 * Strategic Analysis Service Interface
 */
public interface StrategicService {
    
    StrategicAnalysisResponse analyzeStrategy(StrategicAnalysisRequest request);
    
    StrategicAnalysisResponse getReportById(Long id);
    
}

