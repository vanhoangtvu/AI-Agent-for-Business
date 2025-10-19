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
@RequestMapping("/customer")
@Tag(name = "Customer Portal", description = "Cổng thông tin khách hàng (CUSTOMER role only)")
@SecurityRequirement(name = "bearerAuth")
public class CustomerController {

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Thông tin cá nhân", description = "Khách hàng xem thông tin của mình")
    public ResponseEntity<ApiResponse<?>> getProfile() {
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin thành công", null));
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Cập nhật thông tin", description = "Khách hàng cập nhật thông tin cá nhân")
    public ResponseEntity<ApiResponse<?>> updateProfile(@RequestBody Object profileDto) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", null));
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Đơn hàng của tôi", description = "Khách hàng xem đơn hàng của mình")
    public ResponseEntity<ApiResponse<?>> getMyOrders(
            @Parameter(description = "Trạng thái: PENDING, PROCESSING, COMPLETED, CANCELLED") @RequestParam(required = false) String status,
            @Parameter(description = "Trang") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Số lượng") @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách thành công", null));
    }

    @GetMapping("/orders/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Chi tiết đơn hàng", description = "Xem chi tiết đơn hàng của mình")
    public ResponseEntity<ApiResponse<?>> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin thành công", null));
    }

    @GetMapping("/conversations")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Lịch sử chat", description = "Khách hàng xem lịch sử chat với shop")
    public ResponseEntity<ApiResponse<?>> getMyConversations() {
        return ResponseEntity.ok(ApiResponse.success("Lấy lịch sử thành công", null));
    }

    @GetMapping("/conversations/{id}/messages")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Tin nhắn trong cuộc trò chuyện", description = "Xem tin nhắn trong conversation")
    public ResponseEntity<ApiResponse<?>> getMessages(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Lấy tin nhắn thành công", null));
    }

    @PostMapping("/conversations/{id}/messages")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Gửi tin nhắn", description = "Khách hàng gửi tin nhắn cho shop")
    public ResponseEntity<ApiResponse<?>> sendMessage(@PathVariable Long id, @RequestBody Object messageDto) {
        return ResponseEntity.ok(ApiResponse.success("Gửi tin nhắn thành công", null));
    }

    @GetMapping("/notifications")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Thông báo của tôi", description = "Xem thông báo về đơn hàng, khuyến mãi")
    public ResponseEntity<ApiResponse<?>> getNotifications() {
        return ResponseEntity.ok(ApiResponse.success("Lấy thông báo thành công", null));
    }

    @PutMapping("/notifications/{id}/read")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Đánh dấu đã đọc", description = "Mark notification as read")
    public ResponseEntity<ApiResponse<?>> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Đánh dấu thành công", null));
    }
}

