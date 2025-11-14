package com.business.aiagent.service;

import com.business.aiagent.dto.DocumentResponse;
import com.business.aiagent.entity.Document;
import com.business.aiagent.entity.User;
import com.business.aiagent.repository.DocumentRepository;
import com.business.aiagent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {
    
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    
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
        
        // Delete physical file
        try {
            Path filePath = Paths.get(document.getFilePath());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Log error but continue
        }
        
        documentRepository.delete(document);
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
