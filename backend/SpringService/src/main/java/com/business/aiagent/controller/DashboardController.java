package com.business.aiagent.controller;

import com.business.aiagent.dto.DashboardStatistics;
import com.business.aiagent.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;
    
    @Value("${sync.api.key:ai-agent-sync-secure-key-2025-XyZ9#mN8!pQ7}")
    private String syncApiKey;

    @GetMapping("/statistics")
    @PreAuthorize("hasAnyRole('BUSINESS', 'ADMIN')")
    public ResponseEntity<DashboardStatistics> getStatistics() {
        return ResponseEntity.ok(dashboardService.getStatistics());
    }
    
    /**
     * Internal endpoint for Python service to get statistics without authentication
     * Protected by API Key instead of JWT token
     */
    @GetMapping("/internal/statistics")
    public ResponseEntity<?> getInternalStatistics(
            @RequestHeader(value = "X-API-Key", required = false) String apiKey
    ) {
        // Verify API Key
        if (apiKey == null || apiKey.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("API Key required. Please provide X-API-Key header."));
        }
        
        if (!syncApiKey.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Invalid API Key. Access denied."));
        }
        
        // Return statistics without JWT requirement
        return ResponseEntity.ok(dashboardService.getStatistics());
    }
    
    // Simple error response class
    private static class ErrorResponse {
        public String detail;
        
        public ErrorResponse(String detail) {
            this.detail = detail;
        }
        
        public String getDetail() {
            return detail;
        }
    }
}
