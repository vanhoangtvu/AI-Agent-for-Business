package com.business.aiagent.repository;

import com.business.aiagent.entity.Document;
import com.business.aiagent.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    
    Page<Document> findByUser(User user, Pageable pageable);
    
    List<Document> findByUser(User user);
    
    Page<Document> findByUserAndCategory(User user, String category, Pageable pageable);
    
    List<Document> findByStatus(Document.ProcessStatus status);
}
