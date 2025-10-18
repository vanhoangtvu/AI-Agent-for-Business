package com.aiagent.business.repository;

import com.aiagent.business.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    Page<Message> findByConversationId(Long conversationId, Pageable pageable);
    
    long countByConversationIdAndIsReadFalse(Long conversationId);
}

