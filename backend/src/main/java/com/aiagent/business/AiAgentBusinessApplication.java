package com.aiagent.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main Application Class for AI Agent for Business
 * 
 * Features:
 * - Spring Boot 3.x
 * - JPA with MySQL 8.0+
 * - Redis Caching
 * - JWT Authentication
 * - WebSocket Support
 * - Google Gemini AI Integration
 * - Vector Database (Pinecone/pgvector)
 * - Zalo Integration (OA + Personal)
 * 
 * @author AI Agent for Business Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableAsync
@EnableScheduling
public class AiAgentBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiAgentBusinessApplication.class, args);
        System.out.println("=================================================");
        System.out.println("AI Agent for Business API is running");
        System.out.println("API Base URL: http://localhost:8088/api/v1");
        System.out.println("Swagger UI: http://localhost:8088/swagger-ui.html");
        System.out.println("=================================================");
    }
}

