package com.business.aiagent.controller;

import com.business.aiagent.dto.BusinessAnalyticsResponse;
import com.business.aiagent.entity.Role;
import com.business.aiagent.entity.User;
import com.business.aiagent.security.RequirePermission;
import com.business.aiagent.security.RequireRole;
import com.business.aiagent.service.BusinessAnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Controller cho Business Analytics
 * Chỉ dành cho BUSINESS và ADMIN role
 */
@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "Business Analytics", description = "APIs phân tích dữ liệu doanh nghiệp")
@SecurityRequirement(name = "Bearer Authentication")
public class BusinessAnalyticsController {
    
    private final BusinessAnalyticsService analyticsService;
    
    /**
     * Lấy tổng quan analytics cho business owner
     * BUSINESS: Chỉ xem data của mình
     * ADMIN: Có thể xem data của bất kỳ business nào
     */
    @GetMapping("/business/overview")
    @Operation(summary = "Lấy tổng quan phân tích doanh nghiệp")
    @RequireRole({Role.RoleName.BUSINESS, Role.RoleName.ADMIN})
    @RequirePermission(Role.Permission.ANALYTICS_VIEW)
    public ResponseEntity<BusinessAnalyticsResponse> getBusinessOverview(
        @AuthenticationPrincipal User currentUser
    ) {
        BusinessAnalyticsResponse analytics = analyticsService.getBusinessAnalytics(currentUser);
        return ResponseEntity.ok(analytics);
    }
    
    /**
     * ADMIN: Xem analytics của business cụ thể
     */
    @GetMapping("/business/{businessId}/overview")
    @Operation(summary = "Admin xem phân tích của business cụ thể")
    @RequireRole(Role.RoleName.ADMIN)
    @RequirePermission(Role.Permission.ANALYTICS_VIEW_ALL)
    public ResponseEntity<BusinessAnalyticsResponse> getBusinessOverviewByAdmin(
        @PathVariable Long businessId,
        @AuthenticationPrincipal User currentUser
    ) {
        // TODO: Implement - Get business user by ID and return analytics
        return ResponseEntity.ok(
            BusinessAnalyticsResponse.builder().build()
        );
    }
    
    /**
     * Xuất báo cáo analytics
     */
    @GetMapping("/export")
    @Operation(summary = "Xuất báo cáo phân tích")
    @RequirePermission(Role.Permission.REPORT_EXPORT)
    public ResponseEntity<String> exportAnalytics(
        @AuthenticationPrincipal User currentUser,
        @RequestParam(defaultValue = "PDF") String format
    ) {
        // TODO: Implement export functionality
        return ResponseEntity.ok("Export feature coming soon");
    }
}
