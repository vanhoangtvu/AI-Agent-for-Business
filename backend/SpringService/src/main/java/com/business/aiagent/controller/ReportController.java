package com.business.aiagent.controller;

import com.business.aiagent.dto.ReportRequest;
import com.business.aiagent.dto.StrategicReportResponse;
import com.business.aiagent.service.StrategicReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "📊 Strategic Reports", description = "Create and manage strategic reports")
public class ReportController {
    
    private final StrategicReportService reportService;
    
    @PostMapping("/generate")
    public ResponseEntity<StrategicReportResponse> generateReport(
            @RequestBody ReportRequest request,
            Authentication authentication) {
        
        StrategicReportResponse response = reportService.generateReport(
                authentication.getName(),
                request.getTitle(),
                request.getReportType(),
                request.getContext()
        );
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<Page<StrategicReportResponse>> getReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        
        Page<StrategicReportResponse> reports = reportService.getUserReports(
                authentication.getName(),
                PageRequest.of(page, size)
        );
        return ResponseEntity.ok(reports);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<StrategicReportResponse> getReport(
            @PathVariable Long id,
            Authentication authentication) {
        
        StrategicReportResponse report = reportService.getReport(id, authentication.getName());
        return ResponseEntity.ok(report);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(
            @PathVariable Long id,
            Authentication authentication) {
        
        reportService.deleteReport(id, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
