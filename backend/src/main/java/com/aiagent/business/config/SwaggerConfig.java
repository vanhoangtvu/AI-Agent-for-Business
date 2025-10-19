package com.aiagent.business.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.port:8087}")
    private String serverPort;

    @Value("${server.servlet.context-path:/api/v1}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AI Agent for Business - REST API")
                        .description("""
                                ## Hệ thống Trợ lý AI thông minh cho doanh nghiệp
                                
                                ### Tính năng chính:
                                - 🤖 AI Chatbot với RAG (Retrieval-Augmented Generation)
                                - 💬 Tích hợp đa kênh: Website Widget, Zalo OA, Zalo Personal
                                - 👥 CRM - Quản lý khách hàng
                                - 📦 Quản lý sản phẩm & đơn hàng
                                - 📊 Analytics & AI Insights
                                - 🔐 RBAC: Admin, Business, Customer
                                
                                ### Authentication:
                                1. Đăng ký/Đăng nhập tại `/auth/register` hoặc `/auth/login`
                                2. Copy JWT token từ response
                                3. Click nút "Authorize" ở trên
                                4. Paste token vào (không cần thêm "Bearer ")
                                5. Click "Authorize" và bắt đầu test API
                                
                                ### Demo Accounts:
                                **Admin:** admin@aiagent.com / admin123  
                                **Business:** business@shop.com / business123  
                                **Customer:** customer@email.com / customer123
                                
                                ### Tech Stack:
                                - Backend: Spring Boot 3.2.0 + Java 21
                                - Database: MySQL 8.0 + Redis
                                - AI: Google Gemini API + LangChain
                                - Vector DB: Pinecone / pgvector
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("AI Agent Support")
                                .email("support@aiagent.com")
                                .url("https://github.com/vanhoangtvu/AI-Agent-for-Business"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort + contextPath)
                                .description("Local Development Server"),
                        new Server()
                                .url("http://192.168.1.10:" + serverPort + contextPath)
                                .description("LAN Server"),
                        new Server()
                                .url("http://113.170.159.180:" + serverPort + contextPath)
                                .description("Public Server (requires port forwarding)")
                ))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT token từ /auth/login hoặc /auth/register")));
    }
}

