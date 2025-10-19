package com.aiagent.business.controller;

import com.aiagent.business.dto.request.LoginRequest;
import com.aiagent.business.dto.request.RegisterRequest;
import com.aiagent.business.dto.response.ApiResponse;
import com.aiagent.business.dto.response.AuthResponse;
import com.aiagent.business.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API đăng nhập và đăng ký")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Đăng ký tài khoản mới (Universal)", 
               description = "Đăng ký cho tất cả role: ADMIN, BUSINESS, CUSTOMER")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("Đăng ký thành công", response));
    }

    @PostMapping("/register/business")
    @Operation(summary = "Đăng ký Business", 
               description = "Đăng ký tài khoản doanh nghiệp mới (tự động tạo BUSINESS role)")
    public ResponseEntity<ApiResponse<AuthResponse>> registerBusiness(@Valid @RequestBody RegisterRequest request) {
        // Force role to BUSINESS
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("Đăng ký doanh nghiệp thành công", response));
    }

    @PostMapping("/register/customer")
    @Operation(summary = "Đăng ký Customer", 
               description = "Đăng ký tài khoản khách hàng mới (tự động tạo CUSTOMER role)")
    public ResponseEntity<ApiResponse<AuthResponse>> registerCustomer(@Valid @RequestBody RegisterRequest request) {
        // Force role to CUSTOMER
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("Đăng ký khách hàng thành công", response));
    }

    @PostMapping("/login")
    @Operation(summary = "Đăng nhập")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công", response));
    }

    @GetMapping("/me")
    @Operation(summary = "Lấy thông tin người dùng hiện tại")
    public ResponseEntity<ApiResponse<String>> getCurrentUser() {
        // TODO: Implement get current user from JWT
        return ResponseEntity.ok(ApiResponse.success("Current user info"));
    }
}

