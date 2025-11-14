package com.business.aiagent.repository;

import com.business.aiagent.entity.Conversation;
import com.business.aiagent.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    
    List<Conversation> findByUserOrderByUpdatedAtDesc(User user);
    
    Page<Conversation> findByUserOrderByUpdatedAtDesc(User user, Pageable pageable);
    
    List<Conversation> findByUser(User user);
}
