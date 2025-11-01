package com.aiagent.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI/Swagger Configuration
 * 
 * Swagger UI: http://localhost:8100/swagger-ui.html
 * API Docs: http://localhost:8100/api-docs
 * 
 * @author Nguyễn Văn Hoàng
 */
@Configuration
public class OpenAPIConfig {

    @Value("${server.port:8100}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Development Server"),
                        new Server()
                                .url("https://api.aiagent.com")
                                .description("Production Server (Future)")
                ))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", securityScheme())
                )
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"));
    }

    private Info apiInfo() {
        return new Info()
                .title("AI Agent for Business API")
                .version("1.0.0")
                .description("""
                        # AI Agent for Business - Backend API
                        
                        ## Đồ Án Chuyên Ngành
                        
                        **Sinh viên thực hiện:** Nguyễn Văn Hoàng  
                        **MSSV:** 110122078  
                        **Trường:** Đại Học Trà Vinh  
                        **Khoa:** Công Nghệ Thông Tin  
                        **Giáo viên hướng dẫn:** ThS. TS. Nguyễn Bảo Ân
                        
                        ## Mô Tả
                        
                        Hệ thống AI thông minh hỗ trợ doanh nghiệp trong việc:
                        - 💬 Chăm sóc khách hàng tự động
                        - 📚 Tư vấn sản phẩm dựa trên knowledge base
                        - 📊 Phân tích và đề xuất chiến lược kinh doanh
                        - 🔍 Tìm kiếm thông minh với RAG (Retrieval-Augmented Generation)
                        
                        ## Công Nghệ
                        
                        - **Backend:** Spring Boot 3.2 + Java 17
                        - **Security:** JWT Authentication
                        - **Database:** MySQL 8.0
                        - **Cache:** Redis
                        - **AI Service:** Python FastAPI + Gemini API
                        - **Real-time:** WebSocket
                        
                        ## Authentication
                        
                        API sử dụng JWT Bearer Token. Để sử dụng:
                        
                        1. Đăng ký tài khoản: `POST /api/auth/register`
                        2. Đăng nhập: `POST /api/auth/login`
                        3. Copy token từ response
                        4. Click nút "Authorize" ở trên
                        5. Nhập: `Bearer YOUR_TOKEN_HERE`
                        
                        ## Support
                        
                        - **Email:** 110122078@st.tvu.edu.vn
                        - **GitHub:** https://github.com/vanhoangtvu/AI-Agent-for-Business
                        """)
                .contact(new Contact()
                        .name("Nguyễn Văn Hoàng")
                        .email("110122078@st.tvu.edu.vn")
                        .url("https://github.com/vanhoangtvu")
                )
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT")
                );
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .description("Enter JWT token with 'Bearer ' prefix. Example: Bearer eyJhbGc...");
    }

}

