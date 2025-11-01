package com.aiagent.service;

import com.aiagent.model.dto.ChatRequest;
import com.aiagent.model.dto.ChatResponse;
import com.aiagent.model.entity.Conversation;

import java.util.List;

/**
 * Chat Service Interface
 */
public interface ChatService {
    
    Conversation createConversation(String title);
    
    Conversation getConversationById(Long id);
    
    List<Conversation> getUserConversations();
    
    ChatResponse sendMessage(ChatRequest request);
    
    List<Conversation.Message> getConversationMessages(Long conversationId);
    
    void deleteConversation(Long id);
    
}

