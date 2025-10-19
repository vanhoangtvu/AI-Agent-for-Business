package com.aiagent.business.repository;

import com.aiagent.business.model.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    
    Page<Conversation> findByBusinessId(Long businessId, Pageable pageable);
    
    Page<Conversation> findByCustomerId(Long customerId, Pageable pageable);
    
    Optional<Conversation> findByIdAndBusinessId(Long id, Long businessId);
    
    Optional<Conversation> findByIdAndCustomerId(Long id, Long customerId);
    
    Optional<Conversation> findByBusinessIdAndId(Long businessId, Long id);
    
    Optional<Conversation> findByChannelAndChannelConversationId(
        Conversation.Channel channel, 
        String channelConversationId
    );
    
    Page<Conversation> findByBusinessIdAndStatus(
        Long businessId, 
        Conversation.Status status, 
        Pageable pageable
    );
    
    long countByBusinessId(Long businessId);
}

