package com.aiagent.service;

import com.aiagent.model.dto.ChatRequest;
import com.aiagent.model.dto.ChatResponse;
import com.aiagent.model.dto.StrategicAnalysisRequest;
import com.aiagent.model.dto.StrategicAnalysisResponse;

/**
 * Service interface for communicating with Python AI Service
 * Provides methods to call Google Gemini API through Python backend
 */
public interface AIClientService {
    
    /**
     * Send chat message to AI (with RAG if conversation has documents)
     * 
     * @param request Chat request with message and conversation context
     * @return AI response from Google Gemini
     */
    ChatResponse sendChatMessage(ChatRequest request);
    
    /**
     * Quick chat test (no RAG, simple Gemini API call)
     * 
     * @param message User message
     * @return AI response
     */
    ChatResponse quickChat(String message);
    
    /**
     * Perform strategic business analysis
     * 
     * @param request Analysis request with business metrics
     * @return Strategic analysis with SWOT, recommendations, etc.
     */
    StrategicAnalysisResponse analyzeStrategy(StrategicAnalysisRequest request);
    
    /**
     * Check if Python AI service is healthy
     * 
     * @return true if service is up and running
     */
    boolean isServiceHealthy();
    
    /**
     * Process document and generate embeddings
     * 
     * @param documentId Document ID to process
     * @param filePath File path on server
     * @return Processing result
     */
    boolean processDocument(Long documentId, String filePath);
}
