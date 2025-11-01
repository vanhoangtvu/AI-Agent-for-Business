package com.aiagent.repository;

import com.aiagent.model.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Message Repository
 */
@Repository
public interface MessageRepository extends JpaRepository<Conversation.Message, Long> {

    List<Conversation.Message> findByConversationId(Long conversationId);

    List<Conversation.Message> findByConversation(Conversation conversation);

    @Query("SELECT m FROM Conversation$Message m WHERE m.conversation.id = :conversationId ORDER BY m.createdAt ASC")
    List<Conversation.Message> findByConversationIdOrderByCreatedAt(@Param("conversationId") Long conversationId);

    @Query("SELECT m FROM Conversation$Message m WHERE m.conversation.id = :conversationId AND m.role = :role")
    List<Conversation.Message> findByConversationIdAndRole(@Param("conversationId") Long conversationId, 
                                                            @Param("role") Conversation.Message.MessageRole role);

    Long countByConversationId(Long conversationId);

}

