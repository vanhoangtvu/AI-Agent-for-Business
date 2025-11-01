package com.aiagent.repository;

import com.aiagent.model.entity.Document;
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
 * Document Repository
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByUser(User user);

    Page<Document> findByUser(User user, Pageable pageable);

    List<Document> findByUserId(Long userId);

    List<Document> findByCategory(String category);

    List<Document> findByStatus(Document.ProcessingStatus status);

    @Query("SELECT d FROM Document d WHERE d.user.id = :userId AND d.status = :status")
    List<Document> findByUserIdAndStatus(@Param("userId") Long userId, 
                                         @Param("status") Document.ProcessingStatus status);

    @Query("SELECT d FROM Document d WHERE d.title LIKE %:keyword% OR d.description LIKE %:keyword%")
    List<Document> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT d FROM Document d WHERE d.user.id = :userId AND " +
           "(d.title LIKE %:keyword% OR d.description LIKE %:keyword% OR d.extractedText LIKE %:keyword%)")
    Page<Document> searchByUserAndKeyword(@Param("userId") Long userId, 
                                          @Param("keyword") String keyword, 
                                          Pageable pageable);

    List<Document> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT DISTINCT d.category FROM Document d WHERE d.category IS NOT NULL")
    List<String> findAllCategories();

    Long countByUserId(Long userId);

    Long countByStatus(Document.ProcessingStatus status);

}

