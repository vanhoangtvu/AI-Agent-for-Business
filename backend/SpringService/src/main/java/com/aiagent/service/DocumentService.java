package com.aiagent.service;

import com.aiagent.model.dto.DocumentRequest;
import com.aiagent.model.dto.DocumentResponse;
import com.aiagent.model.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Document Service Interface
 */
public interface DocumentService {
    
    DocumentResponse uploadDocument(MultipartFile file, DocumentRequest request);
    
    Document getDocumentById(Long id);
    
    List<Document> getAllDocuments();
    
    Page<Document> getDocumentsByUser(Long userId, Pageable pageable);
    
    List<Document> getDocumentsByCategory(String category);
    
    DocumentResponse updateDocument(Long id, DocumentRequest request);
    
    void deleteDocument(Long id);
    
    Page<Document> searchDocuments(String keyword, Pageable pageable);
    
    List<String> getAllCategories();
    
}

