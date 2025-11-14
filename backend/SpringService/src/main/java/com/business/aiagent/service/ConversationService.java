package com.business.aiagent.service;

import com.business.aiagent.dto.ConversationResponse;
import com.business.aiagent.entity.Conversation;
import com.business.aiagent.entity.User;
import com.business.aiagent.repository.ConversationRepository;
import com.business.aiagent.repository.MessageRepository;
import com.business.aiagent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConversationService {
    
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public ConversationResponse createConversation(String username, String title) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Conversation conversation = new Conversation();
        conversation.setUser(user);
        conversation.setTitle(title != null && !title.isEmpty() ? title : "New Conversation");
        
        Conversation saved = conversationRepository.save(conversation);
        return mapToResponse(saved);
    }
    
    public Page<ConversationResponse> getUserConversations(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return conversationRepository.findByUserOrderByUpdatedAtDesc(user, pageable)
                .map(this::mapToResponse);
    }
    
    public ConversationResponse getConversation(Long id, String username) {
        Conversation conversation = conversationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        
        if (!conversation.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        
        return mapToResponse(conversation);
    }
    
    @Transactional
    public void deleteConversation(Long id, String username) {
        Conversation conversation = conversationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        
        if (!conversation.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        
        conversationRepository.delete(conversation);
    }
    
    private ConversationResponse mapToResponse(Conversation conversation) {
        int messageCount = messageRepository.findByConversation(conversation).size();
        return new ConversationResponse(
                conversation.getId(),
                conversation.getTitle(),
                messageCount,
                conversation.getCreatedAt(),
                conversation.getUpdatedAt()
        );
    }
}
