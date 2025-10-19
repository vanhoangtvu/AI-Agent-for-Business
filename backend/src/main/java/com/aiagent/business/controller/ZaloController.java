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
@RequestMapping("/zalo")
@Tag(name = "Zalo Integration", description = "Tích hợp Zalo OA và Zalo Personal")
@SecurityRequirement(name = "bearerAuth")
public class ZaloController {

    // Zalo Official Account (OA)
    @PostMapping("/oa/config")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Cấu hình Zalo OA", description = "Business cấu hình Zalo Official Account")
    public ResponseEntity<ApiResponse<?>> configZaloOA(@RequestBody Object zaloOAConfigDto) {
        return ResponseEntity.ok(ApiResponse.success("Cấu hình Zalo OA thành công", null));
    }

    @GetMapping("/oa/status")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Trạng thái Zalo OA", description = "Kiểm tra trạng thái kết nối Zalo OA")
    public ResponseEntity<ApiResponse<?>> getZaloOAStatus() {
        return ResponseEntity.ok(ApiResponse.success("Lấy trạng thái thành công", null));
    }

    @GetMapping("/oa/conversations")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Danh sách hội thoại Zalo OA", description = "Tất cả hội thoại từ Zalo OA")
    public ResponseEntity<ApiResponse<?>> getZaloOAConversations(
            @Parameter(description = "Trang") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Số lượng") @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách thành công", null));
    }

    @PostMapping("/oa/send-message")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Gửi tin nhắn Zalo OA", description = "Gửi tin nhắn qua Zalo OA")
    public ResponseEntity<ApiResponse<?>> sendZaloOAMessage(@RequestBody Object messageDto) {
        return ResponseEntity.ok(ApiResponse.success("Gửi tin nhắn thành công", null));
    }

    // Zalo Personal Account
    @PostMapping("/personal/generate-qr")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Tạo QR Code đăng nhập Zalo Personal", 
               description = "Business tạo QR code để đăng nhập Zalo cá nhân. Quét QR từ app Zalo để kết nối.")
    public ResponseEntity<ApiResponse<?>> generateZaloPersonalQR() {
        return ResponseEntity.ok(ApiResponse.success("Tạo QR code thành công", null));
    }

    @GetMapping("/personal/login-status/{sessionId}")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Kiểm tra trạng thái đăng nhập", description = "Polling để kiểm tra đã quét QR chưa")
    public ResponseEntity<ApiResponse<?>> checkLoginStatus(
            @Parameter(description = "Session ID từ QR generation") @PathVariable String sessionId
    ) {
        return ResponseEntity.ok(ApiResponse.success("Kiểm tra trạng thái thành công", null));
    }

    @PostMapping("/personal/config")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Cấu hình AI cho Zalo Personal", 
               description = "Chọn chế độ: AUTO (AI tự trả lời), SUGGESTION (AI gợi ý), NOTIFICATION (chỉ thông báo)")
    public ResponseEntity<ApiResponse<?>> configZaloPersonalAI(@RequestBody Object aiConfigDto) {
        return ResponseEntity.ok(ApiResponse.success("Cấu hình AI thành công", null));
    }

    @GetMapping("/personal/conversations")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Danh sách hội thoại Zalo Personal", description = "Tất cả hội thoại từ Zalo cá nhân")
    public ResponseEntity<ApiResponse<?>> getZaloPersonalConversations(
            @Parameter(description = "Trang") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Số lượng") @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách thành công", null));
    }

    @PostMapping("/personal/send-message")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Gửi tin nhắn Zalo Personal", description = "Gửi tin nhắn qua Zalo cá nhân đã kết nối")
    public ResponseEntity<ApiResponse<?>> sendZaloPersonalMessage(@RequestBody Object messageDto) {
        return ResponseEntity.ok(ApiResponse.success("Gửi tin nhắn thành công", null));
    }

    @DeleteMapping("/personal/disconnect")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Ngắt kết nối Zalo Personal", description = "Logout khỏi Zalo cá nhân")
    public ResponseEntity<ApiResponse<?>> disconnectZaloPersonal() {
        return ResponseEntity.ok(ApiResponse.success("Ngắt kết nối thành công", null));
    }
}

