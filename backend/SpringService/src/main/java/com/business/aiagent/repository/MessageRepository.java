package com.business.aiagent.repository;

import com.business.aiagent.entity.Conversation;
import com.business.aiagent.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    List<Message> findByConversationOrderByCreatedAtAsc(Conversation conversation);
    
    Page<Message> findByConversationOrderByCreatedAtAsc(Conversation conversation, Pageable pageable);
    
    List<Message> findByConversation(Conversation conversation);
}
