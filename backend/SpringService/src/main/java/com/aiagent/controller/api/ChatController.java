package com.aiagent.controller.api;

import com.aiagent.model.dto.ChatRequest;
import com.aiagent.model.dto.ChatResponse;
import com.aiagent.model.entity.Conversation;
import com.aiagent.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Chat Controller
 * Quản lý conversations và messages
 * 
 * @author Nguyễn Văn Hoàng
 */
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8100"})
@Tag(name = "Chat", description = "API chat với AI chatbot")
@SecurityRequirement(name = "Bearer Authentication")
public class ChatController {

    private final ChatService chatService;

    @Operation(
            summary = "Tạo conversation mới",
            description = "Tạo cuộc hội thoại mới với AI"
    )
    @PostMapping("/conversations")
    public ResponseEntity<Conversation> createConversation(@RequestParam String title) {
        Conversation conversation = chatService.createConversation(title);
        return ResponseEntity.ok(conversation);
    }

    @Operation(
            summary = "Lấy danh sách conversations",
            description = "Lấy tất cả conversations của user hiện tại"
    )
    @GetMapping("/conversations")
    public ResponseEntity<List<Conversation>> getUserConversations() {
        List<Conversation> conversations = chatService.getUserConversations();
        return ResponseEntity.ok(conversations);
    }

    @Operation(
            summary = "Lấy chi tiết conversation",
            description = "Lấy thông tin conversation theo ID"
    )
    @GetMapping("/conversations/{id}")
    public ResponseEntity<Conversation> getConversationById(@PathVariable Long id) {
        Conversation conversation = chatService.getConversationById(id);
        return ResponseEntity.ok(conversation);
    }

    @Operation(
            summary = "Gửi message",
            description = "Gửi message trong conversation và nhận response từ AI"
    )
    @PostMapping("/message")
    public ResponseEntity<ChatResponse> sendMessage(@Valid @RequestBody ChatRequest request) {
        ChatResponse response = chatService.sendMessage(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Lấy messages trong conversation",
            description = "Lấy tất cả messages trong một conversation"
    )
    @GetMapping("/conversations/{id}/messages")
    public ResponseEntity<List<Conversation.Message>> getConversationMessages(@PathVariable Long id) {
        List<Conversation.Message> messages = chatService.getConversationMessages(id);
        return ResponseEntity.ok(messages);
    }

    @Operation(
            summary = "Xóa conversation",
            description = "Xóa conversation và tất cả messages"
    )
    @DeleteMapping("/conversations/{id}")
    public ResponseEntity<Map<String, String>> deleteConversation(@PathVariable Long id) {
        chatService.deleteConversation(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Conversation deleted successfully");
        return ResponseEntity.ok(response);
    }

}

