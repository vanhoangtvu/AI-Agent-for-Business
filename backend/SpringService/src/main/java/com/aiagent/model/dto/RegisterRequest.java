package com.aiagent.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Registration Request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Thông tin đăng ký tài khoản")
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(description = "Tên đăng nhập", example = "hoangvan", required = true)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Email", example = "110122078@st.tvu.edu.vn", required = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema(description = "Mật khẩu (tối thiểu 6 ký tự)", example = "123456", required = true)
    private String password;

    @Schema(description = "Họ và tên đầy đủ", example = "Nguyễn Văn Hoàng")
    private String fullName;

    @Schema(description = "Số điện thoại", example = "0123456789")
    private String phoneNumber;

    @Pattern(regexp = "ADMIN|BUSINESS|CUSTOMER", message = "Role must be ADMIN, BUSINESS or CUSTOMER")
    @Schema(
            description = "Vai trò người dùng: ADMIN (Quản trị), BUSINESS (Doanh nghiệp), CUSTOMER (Khách hàng)",
            example = "CUSTOMER",
            allowableValues = {"ADMIN", "BUSINESS", "CUSTOMER"},
            defaultValue = "CUSTOMER"
    )
    private String role;

    @Schema(description = "Tên doanh nghiệp (chỉ dành cho role BUSINESS)", example = "Công ty ABC")
    private String companyName;

}

