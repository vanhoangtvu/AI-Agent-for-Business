package com.aiagent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Database Configuration
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.aiagent.repository")
@EnableTransactionManagement
public class DatabaseConfig {
    // Spring Boot auto-configures DataSource from application.yml
    // Note: @EnableJpaAuditing is in Application.java
}

