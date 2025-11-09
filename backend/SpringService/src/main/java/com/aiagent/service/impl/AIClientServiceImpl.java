package com.aiagent.service.impl;

import com.aiagent.model.dto.ChatRequest;
import com.aiagent.model.dto.ChatResponse;
import com.aiagent.model.dto.StrategicAnalysisRequest;
import com.aiagent.model.dto.StrategicAnalysisResponse;
import com.aiagent.service.AIClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of AIClientService
 * Communicates with Python AI Service via HTTP
 */
@Slf4j
@Service
public class AIClientServiceImpl implements AIClientService {
    
    private final WebClient webClient;
    private final String aiServiceUrl;
    
    public AIClientServiceImpl(
            @Value("${app.ai-service.url}") String aiServiceUrl,
            @Value("${app.ai-service.timeout:30000}") int timeout,
            WebClient.Builder webClientBuilder
    ) {
        this.aiServiceUrl = aiServiceUrl;
        this.webClient = webClientBuilder
                .baseUrl(aiServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        
        log.info("AIClientService initialized with URL: {}", aiServiceUrl);
    }
    
    @Override
    public ChatResponse sendChatMessage(ChatRequest request) {
        log.info("Sending chat message to AI service for user: {}, conversation: {}", 
                request.getUserId(), request.getConversationId());
        
        try {
            // Prepare request body for Python API
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("message", request.getMessage());
            
            if (request.getConversationHistory() != null && !request.getConversationHistory().isEmpty()) {
                requestBody.put("conversation_history", request.getConversationHistory());
            }
            
            log.debug("Request body: {}", requestBody);
            
            // Call Python /api/chat/quick-test endpoint
            ChatResponse response = webClient.post()
                    .uri("/api/chat/quick-test")
                    .bodyValue(requestBody)
                    .retrieve()
                    .onStatus(status -> status.isError(),
                            clientResponse -> {
                                log.error("Error status from AI service: {}", clientResponse.statusCode());
                                return clientResponse.bodyToMono(String.class)
                                        .flatMap(body -> {
                                            log.error("Error response body: {}", body);
                                            return Mono.error(new RuntimeException("AI service error: " + body));
                                        });
                            })
                    .bodyToMono(ChatResponse.class)
                    .timeout(Duration.ofSeconds(30))
                    .doOnError(error -> log.error("Error details: ", error))
                    .block();
            
            if (response != null) {
                log.info("Received AI response. Processing time: {}s", response.getProcessingTime());
                response.setSuccess(true);
                return response;
            } else {
                log.error("Received null response from AI service");
                return buildErrorResponse("No response from AI service");
            }
            
        } catch (Exception e) {
            log.error("Error calling AI service: {}", e.getMessage(), e);
            return buildErrorResponse("Error communicating with AI service: " + e.getMessage());
        }
    }
    
    @Override
    public ChatResponse quickChat(String message) {
        log.info("Quick chat: {}", message);
        
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("message", message);
            
            ChatResponse response = webClient.post()
                    .uri("/api/chat/quick-test")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(ChatResponse.class)
                    .timeout(Duration.ofSeconds(30))
                    .block();
            
            return response != null ? response : buildErrorResponse("No response");
            
        } catch (Exception e) {
            log.error("Quick chat error: {}", e.getMessage());
            return buildErrorResponse(e.getMessage());
        }
    }
    
    @Override
    public StrategicAnalysisResponse analyzeStrategy(StrategicAnalysisRequest request) {
        log.info("Strategic analysis for user: {}, business: {}", 
                request.getUserId(), request.getBusinessName());
        
        try {
            StrategicAnalysisResponse response = webClient.post()
                    .uri("/api/strategic/analyze")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(StrategicAnalysisResponse.class)
                    .timeout(Duration.ofSeconds(60)) // Strategic analysis takes longer
                    .block();
            
            if (response != null) {
                log.info("Strategic analysis completed. Processing time: {}s", response.getProcessingTime());
                return response;
            } else {
                log.error("Received null response from strategic analysis");
                return buildStrategicErrorResponse("No response from AI service");
            }
            
        } catch (Exception e) {
            log.error("Strategic analysis error: {}", e.getMessage(), e);
            return buildStrategicErrorResponse(e.getMessage());
        }
    }
    
    @Override
    public boolean isServiceHealthy() {
        try {
            Map<String, Object> health = webClient.get()
                    .uri("/health")
                    .retrieve()
                    .bodyToMono(Map.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();
            
            if (health != null && "healthy".equals(health.get("status"))) {
                log.debug("AI service is healthy");
                return true;
            }
            
            log.warn("AI service health check failed: {}", health);
            return false;
            
        } catch (Exception e) {
            log.error("AI service health check error: {}", e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean processDocument(Long documentId, String filePath) {
        log.info("Processing document: {} at path: {}", documentId, filePath);
        
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("document_id", documentId);
            requestBody.put("file_path", filePath);
            
            Map<String, Object> response = webClient.post()
                    .uri("/api/documents/process")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .timeout(Duration.ofSeconds(120)) // Document processing can take time
                    .block();
            
            if (response != null && Boolean.TRUE.equals(response.get("success"))) {
                log.info("Document processed successfully: {}", documentId);
                return true;
            }
            
            log.error("Document processing failed: {}", response);
            return false;
            
        } catch (Exception e) {
            log.error("Document processing error: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Build error response for chat
     */
    private ChatResponse buildErrorResponse(String errorMessage) {
        return ChatResponse.builder()
                .message("")
                .response("Xin lỗi, tôi không thể xử lý yêu cầu của bạn lúc này. Vui lòng thử lại sau.")
                .error(errorMessage)
                .success(false)
                .sourcesCount(0)
                .processingTime(0.0)
                .build();
    }
    
    /**
     * Build error response for strategic analysis
     */
    private StrategicAnalysisResponse buildStrategicErrorResponse(String errorMessage) {
        return StrategicAnalysisResponse.builder()
                .error(errorMessage)
                .success(false)
                .processingTime(0.0)
                .build();
    }
}
