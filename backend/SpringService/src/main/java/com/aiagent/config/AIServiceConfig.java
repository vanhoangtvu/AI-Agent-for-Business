package com.aiagent.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * AI Service Configuration
 * Configuration for communicating with Python AI Service
 */
@Configuration
@ConfigurationProperties(prefix = "app.ai-service")
@Data
public class AIServiceConfig {

    private String url;
    private Integer timeout;
    private Endpoints endpoints;

    @Data
    public static class Endpoints {
        private String ragQuery;
        private String processDocument;
        private String embedText;
        private String geminiChat;
        private String strategicAnalysis;
    }

    @Bean
    public WebClient aiServiceWebClient() {
        return WebClient.builder()
                .baseUrl(url)
                .build();
    }

}

