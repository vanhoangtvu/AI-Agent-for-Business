package com.aiagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * AI Agent for Business - Main Application
 * 
 * Đồ Án Chuyên Ngành
 * Sinh viên: Nguyễn Văn Hoàng - MSSV: 110122078
 * Trường: Đại Học Trà Vinh
 * Giáo viên hướng dẫn: ThS. TS. Nguyễn Bảo Ân
 * 
 * @author Nguyễn Văn Hoàng
 * @version 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableAsync
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("========================================");
        System.out.println("🤖 AI Agent for Business is running!");
        System.out.println("📚 API Docs: http://localhost:8080/swagger-ui.html");
        System.out.println("❤️  Made by Nguyễn Văn Hoàng - Đại Học Trà Vinh");
        System.out.println("========================================");
    }

}

