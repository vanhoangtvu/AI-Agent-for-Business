package com.aiagent.service.impl;

import com.aiagent.exception.ResourceNotFoundException;
import com.aiagent.model.dto.ChatRequest;
import com.aiagent.model.dto.ChatResponse;
import com.aiagent.model.entity.Conversation;
import com.aiagent.model.entity.User;
import com.aiagent.repository.ConversationRepository;
import com.aiagent.repository.MessageRepository;
import com.aiagent.service.ChatService;
import com.aiagent.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Chat Service Implementation
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChatServiceImpl implements ChatService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;
    // TODO: Inject AIClientService when ready

    @Override
    public Conversation createConversation(String title) {
        User currentUser = userService.getCurrentUser();

        Conversation conversation = Conversation.builder()
                .title(title)
                .user(currentUser)
                .active(true)
                .build();

        conversation = conversationRepository.save(conversation);
        log.info("Created conversation: {} for user: {}", conversation.getId(), currentUser.getEmail());

        return conversation;
    }

    @Override
    public Conversation getConversationById(Long id) {
        return conversationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation", "id", id));
    }

    @Override
    public List<Conversation> getUserConversations() {
        User currentUser = userService.getCurrentUser();
        return conversationRepository.findByUserIdAndActiveTrue(currentUser.getId());
    }

    @Override
    public ChatResponse sendMessage(ChatRequest request) {
        // Get conversation
        Conversation conversation = getConversationById(request.getConversationId());

        // Create user message
        Conversation.Message userMessage = Conversation.Message.builder()
                .conversation(conversation)
                .role(Conversation.Message.MessageRole.USER)
                .content(request.getMessage())
                .build();

        conversation.addMessage(userMessage);
        userMessage = messageRepository.save(userMessage);

        // TODO: Call AI Service to get response
        // For now, return a mock response
        String aiResponseContent = generateMockResponse(request.getMessage());

        // Create AI message
        Conversation.Message aiMessage = Conversation.Message.builder()
                .conversation(conversation)
                .role(Conversation.Message.MessageRole.ASSISTANT)
                .content(aiResponseContent)
                .sentiment("NEUTRAL")
                .confidence(0.85)
                .build();

        conversation.addMessage(aiMessage);
        aiMessage = messageRepository.save(aiMessage);

        // Save conversation
        conversationRepository.save(conversation);

        log.info("Message sent and response generated for conversation: {}", conversation.getId());

        // Build response
        return ChatResponse.builder()
                .messageId(aiMessage.getId())
                .response(aiResponseContent)
                .sentiment("NEUTRAL")
                .confidence(0.85)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public List<Conversation.Message> getConversationMessages(Long conversationId) {
        return messageRepository.findByConversationIdOrderByCreatedAt(conversationId);
    }

    @Override
    public void deleteConversation(Long id) {
        Conversation conversation = getConversationById(id);
        conversationRepository.delete(conversation);
        log.info("Deleted conversation: {}", id);
    }

    /**
     * Mock AI response - will be replaced with actual AI service call
     */
    private String generateMockResponse(String userMessage) {
        return "Cảm ơn bạn đã liên hệ. Đây là phản hồi tự động. " +
               "AI Service sẽ được tích hợp để trả lời câu hỏi: \"" + userMessage + "\"";
    }

}

