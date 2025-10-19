package com.aiagent.business.controller;

import com.aiagent.business.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Management", description = "Quản trị hệ thống (ADMIN role only)")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    @GetMapping("/businesses")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Danh sách tất cả doanh nghiệp", description = "Admin xem tất cả businesses trong hệ thống")
    public ResponseEntity<ApiResponse<?>> getAllBusinesses(
            @Parameter(description = "Trang") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Số lượng") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Trạng thái: ACTIVE, SUSPENDED, INACTIVE") @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách thành công", null));
    }

    @GetMapping("/businesses/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Chi tiết doanh nghiệp", description = "Xem chi tiết business bất kỳ")
    public ResponseEntity<ApiResponse<?>> getBusinessById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin thành công", null));
    }

    @PutMapping("/businesses/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cập nhật doanh nghiệp", description = "Admin cập nhật thông tin business")
    public ResponseEntity<ApiResponse<?>> updateBusiness(@PathVariable Long id, @RequestBody Object businessDto) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", null));
    }

    @DeleteMapping("/businesses/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Xóa doanh nghiệp", description = "Admin xóa business (cảnh báo: xóa tất cả dữ liệu liên quan)")
    public ResponseEntity<ApiResponse<?>> deleteBusiness(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Xóa doanh nghiệp thành công", null));
    }

    @PutMapping("/businesses/{id}/suspend")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Tạm ngưng doanh nghiệp", description = "Admin suspend business")
    public ResponseEntity<ApiResponse<?>> suspendBusiness(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Tạm ngưng thành công", null));
    }

    @PutMapping("/businesses/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Kích hoạt lại doanh nghiệp", description = "Admin activate business")
    public ResponseEntity<ApiResponse<?>> activateBusiness(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Kích hoạt thành công", null));
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Thống kê toàn hệ thống", description = "Tổng số businesses, users, orders, revenue")
    public ResponseEntity<ApiResponse<?>> getSystemStats() {
        return ResponseEntity.ok(ApiResponse.success("Lấy thống kê thành công", null));
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Danh sách tất cả users", description = "Admin xem tất cả users")
    public ResponseEntity<ApiResponse<?>> getAllUsers(
            @Parameter(description = "Role: ADMIN, BUSINESS, CUSTOMER") @RequestParam(required = false) String role,
            @Parameter(description = "Trang") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Số lượng") @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách thành công", null));
    }

    @GetMapping("/logs")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "System logs", description = "Xem application logs và audit trail")
    public ResponseEntity<ApiResponse<?>> getSystemLogs(
            @Parameter(description = "Level: INFO, WARN, ERROR") @RequestParam(required = false) String level,
            @Parameter(description = "Từ ngày (YYYY-MM-DD)") @RequestParam(required = false) String startDate,
            @Parameter(description = "Đến ngày (YYYY-MM-DD)") @RequestParam(required = false) String endDate
    ) {
        return ResponseEntity.ok(ApiResponse.success("Lấy logs thành công", null));
    }

    @PostMapping("/broadcast")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Gửi thông báo toàn hệ thống", description = "Admin broadcast notification đến tất cả users")
    public ResponseEntity<ApiResponse<?>> broadcastNotification(@RequestBody Object notificationDto) {
        return ResponseEntity.ok(ApiResponse.success("Gửi thông báo thành công", null));
    }
}

