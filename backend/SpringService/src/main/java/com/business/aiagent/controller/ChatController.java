package com.business.aiagent.controller;

import com.business.aiagent.dto.ChatRequest;
import com.business.aiagent.dto.ConversationResponse;
import com.business.aiagent.dto.MessageResponse;
import com.business.aiagent.entity.Message.MessageRole;
import com.business.aiagent.entity.User;
import com.business.aiagent.repository.UserRepository;
import com.business.aiagent.service.ConversationService;
import com.business.aiagent.service.MessageService;
import com.business.aiagent.service.PythonAIService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Chat & Conversations", description = "Chat with AI and manage conversations")
public class ChatController {
    
    private final ConversationService conversationService;
    private final MessageService messageService;
    private final PythonAIService pythonAIService;
    private final UserRepository userRepository;
    
    @PostMapping("/conversations")
    public ResponseEntity<ConversationResponse> createConversation(
            @RequestParam(value = "title", required = false) String title,
            Authentication authentication) {
        
        ConversationResponse response = conversationService.createConversation(
                authentication.getName(),
                title
        );
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/conversations")
    public ResponseEntity<Page<ConversationResponse>> getConversations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        
        Page<ConversationResponse> conversations = conversationService.getUserConversations(
                authentication.getName(),
                PageRequest.of(page, size)
        );
        return ResponseEntity.ok(conversations);
    }
    
    @GetMapping("/conversations/{id}")
    public ResponseEntity<ConversationResponse> getConversation(
            @PathVariable Long id,
            Authentication authentication) {
        
        ConversationResponse conversation = conversationService.getConversation(
                id,
                authentication.getName()
        );
        return ResponseEntity.ok(conversation);
    }
    
    @DeleteMapping("/conversations/{id}")
    public ResponseEntity<Void> deleteConversation(
            @PathVariable Long id,
            Authentication authentication) {
        
        conversationService.deleteConversation(id, authentication.getName());
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/send")
    public ResponseEntity<MessageResponse> sendMessage(
            @RequestBody ChatRequest request,
            Authentication authentication) {
        
        log.info("User {} sending message to conversation {}", 
            authentication.getName(), request.getConversationId());
        
        // Get user
        User user = userRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Save user message
        MessageResponse userMessage = messageService.saveMessage(
                request.getConversationId(),
                authentication.getName(),
                request.getMessage(),
                MessageRole.USER
        );
        
        try {
            // Call Python AI service to get RAG response
            Map<String, Object> aiResult = pythonAIService.generateAnswer(
                request.getMessage(),
                user.getId(),
                request.getConversationId(),
                true, // use RAG
                5     // top 5 results
            );
            
            String aiResponse = (String) aiResult.get("answer");
            List<Map<String, Object>> sources = (List<Map<String, Object>>) aiResult.get("sources");
            
            log.info("AI response generated with {} sources", sources.size());
            
            // Add source information to response if available
            if (!sources.isEmpty()) {
                StringBuilder responseWithSources = new StringBuilder(aiResponse);
                responseWithSources.append("\n\n📚 **Nguồn tham khảo:**\n");
                for (Map<String, Object> source : sources) {
                    responseWithSources.append("• ")
                        .append(source.get("file_name"))
                        .append(" (độ chính xác: ")
                        .append(String.format("%.1f%%", (Double) source.get("similarity_score") * 100))
                        .append(")\n");
                }
                aiResponse = responseWithSources.toString();
            }
            
            // Save AI response
            MessageResponse assistantMessage = messageService.saveMessage(
                    request.getConversationId(),
                    authentication.getName(),
                    aiResponse,
                    MessageRole.AI
            );
            
            return ResponseEntity.ok(assistantMessage);
            
        } catch (Exception e) {
            log.error("Error getting AI response: {}", e.getMessage());
            
            // Fallback response
            String fallbackResponse = "Xin lỗi, tôi đang gặp sự cố kết nối với hệ thống AI. " +
                "Vui lòng thử lại sau. Lỗi: " + e.getMessage();
            
            MessageResponse errorMessage = messageService.saveMessage(
                    request.getConversationId(),
                    authentication.getName(),
                    fallbackResponse,
                    MessageRole.AI
            );
            
            return ResponseEntity.ok(errorMessage);
        }
    }
    
    @GetMapping("/conversations/{id}/messages")
    public ResponseEntity<List<MessageResponse>> getConversationMessages(
            @PathVariable Long id,
            Authentication authentication) {
        
        List<MessageResponse> messages = messageService.getAllConversationMessages(
                id,
                authentication.getName()
        );
        return ResponseEntity.ok(messages);
    }
}
