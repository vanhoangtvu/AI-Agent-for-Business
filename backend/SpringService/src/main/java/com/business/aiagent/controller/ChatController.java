package com.business.aiagent.controller;

import com.business.aiagent.dto.ChatRequest;
import com.business.aiagent.dto.ConversationResponse;
import com.business.aiagent.dto.MessageResponse;
import com.business.aiagent.entity.Message.MessageRole;
import com.business.aiagent.service.ConversationService;
import com.business.aiagent.service.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "💬 Chat & Conversations", description = "API chat với AI và quản lý cuộc trò chuyện")
public class ChatController {
    
    private final ConversationService conversationService;
    private final MessageService messageService;
    
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
        
        // Save user message
        MessageResponse userMessage = messageService.saveMessage(
                request.getConversationId(),
                authentication.getName(),
                request.getMessage(),
                MessageRole.USER
        );
        
        // TODO: Call Python AI service to get AI response
        // For now, return a simple echo response
        String aiResponse = "AI response processing will be implemented in Python service integration.";
        
        MessageResponse assistantMessage = messageService.saveMessage(
                request.getConversationId(),
                authentication.getName(),
                aiResponse,
                MessageRole.AI
        );
        
        return ResponseEntity.ok(assistantMessage);
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
