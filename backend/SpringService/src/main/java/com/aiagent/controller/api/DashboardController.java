package com.aiagent.controller.api;

import com.aiagent.model.dto.AdminDashboardResponse;
import com.aiagent.model.dto.BusinessDashboardResponse;
import com.aiagent.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Dashboard Controller
 * Cung cấp dashboard data cho Admin và Business
 * 
 * @author Nguyễn Văn Hoàng
 */
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8100"})
@Tag(name = "Dashboard", description = "API Dashboard - Tổng quan hệ thống")
@SecurityRequirement(name = "Bearer Authentication")
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(
            summary = "Admin Dashboard",
            description = """
                Dashboard cho Admin - Tổng quan toàn hệ thống
                
                Bao gồm:
                - Thống kê users (total, new, by role)
                - Thống kê documents (total, by status)
                - Thống kê conversations & messages
                - Storage usage
                - Active users
                - System health
                - Top businesses
                - Recent activities
                """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AdminDashboardResponse.class)
            )
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<AdminDashboardResponse> getAdminDashboard() {
        log.info("Admin dashboard request");
        AdminDashboardResponse dashboard = dashboardService.getAdminDashboard();
        return ResponseEntity.ok(dashboard);
    }

    @Operation(
            summary = "Business Dashboard",
            description = """
                Dashboard cho Business - Thống kê doanh nghiệp
                
                Bao gồm:
                - Documents của business (total, processed, pending)
                - Customers statistics (total, new)
                - Conversations & messages
                - Average messages per day
                - Top categories
                - Sentiment analysis
                - Customer satisfaction score
                - Response rate
                - Active conversations
                """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BusinessDashboardResponse.class)
            )
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'BUSINESS')")
    @GetMapping("/business")
    public ResponseEntity<BusinessDashboardResponse> getBusinessDashboard() {
        log.info("Business dashboard request");
        BusinessDashboardResponse dashboard = dashboardService.getBusinessDashboard();
        return ResponseEntity.ok(dashboard);
    }

    @Operation(
            summary = "Quick Stats",
            description = "Lấy quick stats cho bất kỳ authenticated user nào"
    )
    @GetMapping("/quick-stats")
    public ResponseEntity<Map<String, Object>> getQuickStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("status", "OK");
        stats.put("message", "Quick stats endpoint");
        return ResponseEntity.ok(stats);
    }

}

