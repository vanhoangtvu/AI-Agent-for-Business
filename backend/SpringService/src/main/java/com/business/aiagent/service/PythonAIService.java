package com.business.aiagent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for integrating with Python AI Service
 * Handles all communication between Spring Boot and FastAPI
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PythonAIService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    @Value("${ai.service.timeout:30000}")
    private int timeout;

    /**
     * Process document through Python AI Service
     * Extracts text, chunks, generates embeddings, stores in ChromaDB
     */
    public Map<String, Object> processDocument(Long documentId, String filePath, Long userId) {
        log.info("Processing document {} through AI service", documentId);
        
        try {
            String url = aiServiceUrl + "/api/documents/process";
            
            Map<String, Object> request = new HashMap<>();
            request.put("document_id", documentId);
            request.put("file_path", filePath);
            request.put("user_id", userId);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Map.class
            );
            
            Map<String, Object> result = response.getBody();
            log.info("Document {} processed successfully. Chunks: {}", 
                documentId, result.get("total_chunks"));
            
            return result;
            
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error processing document {}: {} - {}", 
                documentId, e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("AI Service error: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error processing document {}: {}", documentId, e.getMessage());
            throw new RuntimeException("Failed to process document: " + e.getMessage());
        }
    }

    /**
     * Delete document from vector database
     */
    public void deleteDocument(Long documentId) {
        log.info("Deleting document {} from vector store", documentId);
        
        try {
            String url = aiServiceUrl + "/api/documents/" + documentId;
            
            restTemplate.delete(url);
            
            log.info("Document {} deleted from vector store", documentId);
            
        } catch (Exception e) {
            log.error("Error deleting document {} from vector store: {}", 
                documentId, e.getMessage());
            // Don't throw exception, just log - document might not exist in vector store
        }
    }

    /**
     * Generate AI answer using RAG pipeline
     */
    public Map<String, Object> generateAnswer(
        String question,
        Long userId,
        Long conversationId,
        boolean useRag,
        int topK
    ) {
        log.info("Generating AI answer for conversation {}", conversationId);
        
        try {
            String url = aiServiceUrl + "/api/chat/answer";
            
            Map<String, Object> request = new HashMap<>();
            request.put("question", question);
            request.put("user_id", userId);
            request.put("conversation_id", conversationId);
            request.put("use_rag", useRag);
            request.put("top_k", topK);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Map.class
            );
            
            Map<String, Object> result = response.getBody();
            log.info("AI answer generated successfully. Sources: {}", 
                ((List<?>) result.get("sources")).size());
            
            return result;
            
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error generating answer: {} - {}", 
                e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("AI Service error: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error generating answer: {}", e.getMessage());
            throw new RuntimeException("Failed to generate answer: " + e.getMessage());
        }
    }

    /**
     * Perform semantic search
     */
    public Map<String, Object> semanticSearch(String query, Long userId, int topK) {
        log.info("Performing semantic search: {}", query);
        
        try {
            String url = aiServiceUrl + "/api/chat/search/semantic";
            
            Map<String, Object> request = new HashMap<>();
            request.put("query", query);
            request.put("user_id", userId);
            request.put("top_k", topK);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Map.class
            );
            
            return response.getBody();
            
        } catch (Exception e) {
            log.error("Error performing semantic search: {}", e.getMessage());
            throw new RuntimeException("Failed to perform search: " + e.getMessage());
        }
    }

    /**
     * Sync all products to AI service for RAG
     */
    public Map<String, Object> syncProducts(List<Map<String, Object>> products) {
        log.info("Syncing {} products to AI service", products.size());
        
        try {
            String url = aiServiceUrl + "/api/business/products/sync";
            
            Map<String, Object> request = new HashMap<>();
            request.put("products", products);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Map.class
            );
            
            Map<String, Object> result = response.getBody();
            log.info("Products synced successfully: {}", result);
            
            return result;
            
        } catch (Exception e) {
            log.error("Error syncing products: {}", e.getMessage());
            throw new RuntimeException("Failed to sync products: " + e.getMessage());
        }
    }

    /**
     * Generate strategic business analysis
     */
    public Map<String, Object> analyzeBusinessMetrics(Map<String, Object> metrics) {
        log.info("Generating strategic business analysis");
        
        try {
            String url = aiServiceUrl + "/api/strategic/analyze";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(metrics, headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Map.class
            );
            
            return response.getBody();
            
        } catch (Exception e) {
            log.error("Error analyzing business metrics: {}", e.getMessage());
            throw new RuntimeException("Failed to analyze metrics: " + e.getMessage());
        }
    }

    /**
     * Generate SWOT analysis
     */
    public Map<String, Object> generateSWOT(Map<String, Object> data) {
        log.info("Generating SWOT analysis");
        
        try {
            String url = aiServiceUrl + "/api/strategic/swot";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(data, headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Map.class
            );
            
            return response.getBody();
            
        } catch (Exception e) {
            log.error("Error generating SWOT: {}", e.getMessage());
            throw new RuntimeException("Failed to generate SWOT: " + e.getMessage());
        }
    }

    /**
     * Get product recommendations for customer
     */
    public Map<String, Object> getProductRecommendations(
        String query,
        String category,
        Double minPrice,
        Double maxPrice,
        int topK
    ) {
        log.info("Getting product recommendations for query: {}", query);
        
        try {
            String url = aiServiceUrl + "/api/business/products/recommend";
            
            Map<String, Object> request = new HashMap<>();
            request.put("query", query);
            if (category != null) request.put("category", category);
            if (minPrice != null) request.put("min_price", minPrice);
            if (maxPrice != null) request.put("max_price", maxPrice);
            request.put("top_k", topK);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Map.class
            );
            
            return response.getBody();
            
        } catch (Exception e) {
            log.error("Error getting recommendations: {}", e.getMessage());
            throw new RuntimeException("Failed to get recommendations: " + e.getMessage());
        }
    }

    /**
     * Analyze customer conversation for insights
     */
    public Map<String, Object> analyzeConversation(Long conversationId, List<String> messages) {
        log.info("Analyzing conversation {}", conversationId);
        
        try {
            String url = aiServiceUrl + "/api/business/conversation/analyze";
            
            Map<String, Object> request = new HashMap<>();
            request.put("conversation_id", conversationId);
            request.put("messages", messages);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Map.class
            );
            
            return response.getBody();
            
        } catch (Exception e) {
            log.error("Error analyzing conversation: {}", e.getMessage());
            throw new RuntimeException("Failed to analyze conversation: " + e.getMessage());
        }
    }

    /**
     * Check if AI service is healthy
     */
    public boolean isHealthy() {
        try {
            String url = aiServiceUrl + "/health";
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            log.error("AI service health check failed: {}", e.getMessage());
            return false;
        }
    }
}
