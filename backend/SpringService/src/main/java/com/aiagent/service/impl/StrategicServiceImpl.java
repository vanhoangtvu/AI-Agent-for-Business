package com.aiagent.service.impl;

import com.aiagent.model.dto.StrategicAnalysisRequest;
import com.aiagent.model.dto.StrategicAnalysisResponse;
import com.aiagent.service.AIClientService;
import com.aiagent.service.StrategicService;
import com.aiagent.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Strategic Analysis Service Implementation
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StrategicServiceImpl implements StrategicService {

    private final AIClientService aiClientService;
    private final UserService userService;

    @Override
    public StrategicAnalysisResponse analyzeStrategy(StrategicAnalysisRequest request) {
        log.info("Analyzing strategy: {}", request.getAnalysisType());
        
        // Set user ID from current context if not set
        if (request.getUserId() == null) {
            request.setUserId(userService.getCurrentUser().getId());
        }
        
        // Use metrics from request, fallback to businessMetrics if needed
        if (request.getMetrics() == null && request.getBusinessMetrics() != null) {
            request.setMetrics(request.getBusinessMetrics());
        }
        
        // Call AI Service for real analysis
        StrategicAnalysisResponse response = aiClientService.analyzeStrategy(request);
        
        // Set additional fields
        response.setReportId(System.currentTimeMillis());
        response.setGeneratedAt(LocalDateTime.now());
        
        log.info("Strategic analysis completed. Processing time: {}s", response.getProcessingTime());
        
        return response;
    }

    @Override
    public StrategicAnalysisResponse getReportById(Long id) {
        // TODO: Implement report retrieval from database
        throw new UnsupportedOperationException("Not implemented yet");
    }

}

