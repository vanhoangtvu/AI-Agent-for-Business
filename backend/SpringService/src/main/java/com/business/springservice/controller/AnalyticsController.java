package com.business.springservice.controller;

import com.business.springservice.dto.SystemAnalyticsDataDTO;
import com.business.springservice.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics & AI Integration", description = "Comprehensive data analytics API for AI/RAG services")
public class AnalyticsController {
    
    private final AnalyticsService analyticsService;
    
    @GetMapping("/system-data")
    @Operation(
        summary = "Get complete system analytics data", 
        description = "Retrieve comprehensive data including users, products, orders, revenue, and business performance. " +
                     "This endpoint is designed for AI/RAG services to analyze business data, provide recommendations, " +
                     "forecast revenue, and offer strategic insights. Requires ADMIN or BUSINESS role."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved system analytics data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
        @ApiResponse(responseCode = "403", description = "Access denied - Only ADMIN and BUSINESS roles allowed")
    })
    public ResponseEntity<SystemAnalyticsDataDTO> getSystemAnalyticsData() {
        return ResponseEntity.ok(analyticsService.getSystemAnalyticsData());
    }
}
