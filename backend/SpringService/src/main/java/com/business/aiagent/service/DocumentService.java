package com.business.aiagent.service;

import com.business.aiagent.dto.DocumentResponse;
import com.business.aiagent.entity.Document;
import com.business.aiagent.entity.User;
import com.business.aiagent.repository.DocumentRepository;
import com.business.aiagent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {
    
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final PythonAIService pythonAIService;
    
    @Value("${app.upload.directory:./uploads}")
    private String uploadDir;
    
    @Transactional
    public DocumentResponse uploadDocument(MultipartFile file, String username, String category, String tags) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Validate file
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        
        // Create upload directory if not exists
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
            Path filePath = uploadPath.resolve(uniqueFilename);
            
            // Save file
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            // Save metadata to database
            Document document = Document.builder()
                    .user(user)
                    .fileName(originalFilename)
                    .filePath(filePath.toString())
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .status(Document.ProcessStatus.PENDING)
                    .category(category)
                    .tags(tags)
                    .vectorized(false)
                    .chunkCount(0)
                    .build();
            
            document = documentRepository.save(document);
            
            // TODO: Process document asynchronously
            // Can be triggered manually or via scheduled job
            // For now, just return the upload response
            log.info("Document {} uploaded successfully. File: {}", 
                document.getId(), document.getFileName());
            
            return mapToResponse(document);
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }
    
    public Page<DocumentResponse> getUserDocuments(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return documentRepository.findByUser(user, pageable)
                .map(this::mapToResponse);
    }
    
    public DocumentResponse getDocument(Long id, String username) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        
        // Check ownership
        if (!document.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        
        return mapToResponse(document);
    }
    
    @Transactional
    public void deleteDocument(Long id, String username) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        
        // Check ownership
        if (!document.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        
        // Delete from vector database first
        if (document.getVectorized()) {
            try {
                log.info("Deleting document {} from vector database", id);
                pythonAIService.deleteDocument(id);
                log.info("Document {} deleted from vector database successfully", id);
            } catch (Exception e) {
                log.error("Error deleting document {} from vector database: {}", id, e.getMessage());
                // Continue with database deletion even if vector deletion fails
            }
        }
        
        // Delete physical file
        try {
            Path filePath = Paths.get(document.getFilePath());
            Files.deleteIfExists(filePath);
            log.info("Physical file deleted for document {}", id);
        } catch (IOException e) {
            log.error("Error deleting physical file for document {}: {}", id, e.getMessage());
            // Continue with database deletion
        }
        
        documentRepository.delete(document);
        log.info("Document {} deleted from database", id);
    }
    
    @Transactional
    public DocumentResponse updateDocumentStatus(Long id, Document.ProcessStatus status, 
                                                  Integer chunkCount, Boolean vectorized) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        
        document.setStatus(status);
        if (chunkCount != null) {
            document.setChunkCount(chunkCount);
        }
        if (vectorized != null) {
            document.setVectorized(vectorized);
        }
        if (status == Document.ProcessStatus.COMPLETED) {
            document.setProcessedAt(LocalDateTime.now());
        }
        
        document = documentRepository.save(document);
        return mapToResponse(document);
    }
    
    /**
     * Activate document: Vector hóa để sử dụng cho RAG
     * Chỉ documents ACTIVE mới được dùng trong RAG search
     */
    @Transactional
    public DocumentResponse activateDocument(Long id, String username) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        
        // Check ownership
        if (!document.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }
        
        // If already vectorized, just mark as active
        if (document.getVectorized() && document.getStatus() == Document.ProcessStatus.COMPLETED) {
            log.info("Document {} already vectorized, marking as active", id);
            return mapToResponse(document);
        }
        
        // Process with Python AI Service
        try {
            log.info("🔄 Activating document {}: {}", id, document.getFileName());
            log.info("   Calling Python service to vectorize...");
            
            Map<String, Object> result = pythonAIService.processDocument(
                document.getId(),
                document.getFilePath(),
                document.getUser().getId()
            );
            
            // Update status based on result
            if (result != null && result.get("success") != null && (Boolean) result.get("success")) {
                document.setStatus(Document.ProcessStatus.COMPLETED);
                document.setVectorized(true);
                document.setChunkCount((Integer) result.getOrDefault("total_chunks", 0));
                document.setProcessedAt(LocalDateTime.now());
                
                log.info("✅ Document {} activated successfully!", id);
                log.info("   Chunks: {}", document.getChunkCount());
                log.info("   Status: COMPLETED - Đã sẵn sàng cho RAG");
            } else {
                document.setStatus(Document.ProcessStatus.FAILED);
                log.error("❌ Document {} activation failed: {}", 
                    id, result.getOrDefault("error", "Unknown error"));
                throw new RuntimeException("Failed to vectorize document: " + result.getOrDefault("error", "Unknown error"));
            }
            
            document = documentRepository.save(document);
            return mapToResponse(document);
            
        } catch (Exception e) {
            log.error("❌ Error activating document {}: {}", id, e.getMessage());
            document.setStatus(Document.ProcessStatus.FAILED);
            documentRepository.save(document);
            throw new RuntimeException("Failed to activate document: " + e.getMessage());
        }
    }
    
    private DocumentResponse mapToResponse(Document document) {
        return DocumentResponse.builder()
                .id(document.getId())
                .fileName(document.getFileName())
                .fileType(document.getFileType())
                .fileSize(document.getFileSize())
                .status(document.getStatus().name())
                .category(document.getCategory())
                .tags(document.getTags())
                .vectorized(document.getVectorized())
                .chunkCount(document.getChunkCount())
                .createdAt(document.getCreatedAt())
                .processedAt(document.getProcessedAt())
                .build();
    }
}
