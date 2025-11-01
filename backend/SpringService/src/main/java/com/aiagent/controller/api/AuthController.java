package com.aiagent.controller.api;

import com.aiagent.model.dto.AuthRequest;
import com.aiagent.model.dto.AuthResponse;
import com.aiagent.model.dto.RegisterRequest;
import com.aiagent.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Authentication Controller
 * Handles user registration, login, and token refresh
 * 
 * @author Nguyễn Văn Hoàng
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8100"})
@Tag(name = "Authentication", description = "API xác thực người dùng (Đăng ký, Đăng nhập, Logout)")
public class AuthController {

    private final AuthService authService;

    /**
     * Register new user
     */
    @Operation(
            summary = "Đăng ký tài khoản mới",
            description = """
                Tạo tài khoản người dùng mới với username, email và password.
                
                **Roles có thể chọn:**
                - `CUSTOMER` (default) - Khách hàng
                - `BUSINESS` - Doanh nghiệp
                - `ADMIN` - Quản trị viên
                
                Nếu không chọn role, mặc định sẽ là CUSTOMER.
                """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Đăng ký thành công",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class),
                            examples = @ExampleObject(value = """
                                {
                                    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                    "type": "Bearer",
                                    "userId": 1,
                                    "username": "hoangvan",
                                    "email": "110122078@st.tvu.edu.vn",
                                    "roles": ["USER"]
                                }
                            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Email hoặc username đã tồn tại",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                {
                                    "error": "Email already exists"
                                }
                            """)
                    )
            )
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            log.info("Register request for email: {}", request.getEmail());
            AuthResponse response = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Registration error: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse(e.getMessage()));
        }
    }

    /**
     * Login user
     */
    @Operation(
            summary = "Đăng nhập",
            description = "Đăng nhập bằng email và password để nhận JWT token"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Đăng nhập thành công",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class),
                            examples = @ExampleObject(value = """
                                {
                                    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                    "type": "Bearer",
                                    "userId": 1,
                                    "username": "hoangvan",
                                    "email": "110122078@st.tvu.edu.vn",
                                    "roles": ["USER"]
                                }
                            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Email hoặc password không đúng",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                {
                                    "error": "Invalid email or password"
                                }
                            """)
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        try {
            log.info("Login request for email: {}", request.getEmail());
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Login error: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Invalid email or password"));
        }
    }

    /**
     * Refresh token
     */
    @Operation(
            summary = "Làm mới token",
            description = "Sử dụng refresh token để lấy access token mới"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Token mới được tạo thành công",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Refresh token không hợp lệ",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                {
                                    "error": "Invalid refresh token"
                                }
                            """)
                    )
            )
    })
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        try {
            String refreshToken = request.get("refreshToken");
            AuthResponse response = authService.refreshToken(refreshToken);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Token refresh error: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(createErrorResponse("Invalid refresh token"));
        }
    }

    /**
     * Logout user
     */
    @Operation(
            summary = "Đăng xuất",
            description = "Đăng xuất người dùng (cần Bearer token)",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Đăng xuất thành công",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                {
                                    "message": "Logged out successfully"
                                }
                            """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Lỗi khi đăng xuất"
            )
    })
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.substring(7); // Remove "Bearer " prefix
            authService.logout(jwt);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Logged out successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Logout error: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse(e.getMessage()));
        }
    }

    /**
     * Health check endpoint
     */
    @Operation(
            summary = "Health Check",
            description = "Kiểm tra trạng thái hoạt động của Authentication Service"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Service đang hoạt động bình thường",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                        {
                            "status": "OK",
                            "service": "AI Agent Authentication Service",
                            "author": "Nguyễn Văn Hoàng - Đại Học Trà Vinh"
                        }
                    """)
            )
    )
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("service", "AI Agent Authentication Service");
        response.put("author", "Nguyễn Văn Hoàng - Đại Học Trà Vinh");
        return ResponseEntity.ok(response);
    }

    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return error;
    }

}

