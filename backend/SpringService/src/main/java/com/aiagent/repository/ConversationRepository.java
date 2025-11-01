package com.aiagent.repository;

import com.aiagent.model.entity.Conversation;
import com.aiagent.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Conversation Repository
 */
@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    List<Conversation> findByUser(User user);

    Page<Conversation> findByUser(User user, Pageable pageable);

    List<Conversation> findByUserId(Long userId);

    List<Conversation> findByUserIdAndActiveTrue(Long userId);

    Page<Conversation> findByUserIdAndActiveTrue(Long userId, Pageable pageable);

    @Query("SELECT c FROM Conversation c WHERE c.user.id = :userId ORDER BY c.lastMessageAt DESC")
    List<Conversation> findRecentConversationsByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Conversation c WHERE c.user.id = :userId AND c.title LIKE %:keyword%")
    List<Conversation> searchByUserAndKeyword(@Param("userId") Long userId, @Param("keyword") String keyword);

    List<Conversation> findByLastMessageAtBetween(LocalDateTime start, LocalDateTime end);

    Long countByUserId(Long userId);

    Long countByUserIdAndActiveTrue(Long userId);

}

