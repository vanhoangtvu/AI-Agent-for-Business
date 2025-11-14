package com.business.aiagent.service;

import com.business.aiagent.dto.MessageResponse;
import com.business.aiagent.entity.Conversation;
import com.business.aiagent.entity.Message;
import com.business.aiagent.entity.Message.MessageRole;
import com.business.aiagent.repository.ConversationRepository;
import com.business.aiagent.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    
    @Transactional
    public MessageResponse saveMessage(Long conversationId, String username, String content, MessageRole role) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        
        if (!conversation.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        
        Message message = new Message();
        message.setConversation(conversation);
        message.setRole(role);
        message.setContent(content);
        
        Message saved = messageRepository.save(message);
        return mapToResponse(saved);
    }
    
    public Page<MessageResponse> getConversationMessages(Long conversationId, String username, Pageable pageable) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        
        if (!conversation.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        
        return messageRepository.findByConversationOrderByCreatedAtAsc(conversation, pageable)
                .map(this::mapToResponse);
    }
    
    public List<MessageResponse> getAllConversationMessages(Long conversationId, String username) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        
        if (!conversation.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        
        return messageRepository.findByConversationOrderByCreatedAtAsc(conversation)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    private MessageResponse mapToResponse(Message message) {
        return new MessageResponse(
                message.getId(),
                message.getRole(),
                message.getContent(),
                message.getCreatedAt()
        );
    }
}
