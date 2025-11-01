package com.aiagent.service.impl;

import com.aiagent.exception.ResourceNotFoundException;
import com.aiagent.model.dto.DocumentRequest;
import com.aiagent.model.dto.DocumentResponse;
import com.aiagent.model.entity.Document;
import com.aiagent.model.entity.User;
import com.aiagent.repository.DocumentRepository;
import com.aiagent.service.DocumentService;
import com.aiagent.service.UserService;
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
import java.util.List;
import java.util.UUID;

/**
 * Document Service Implementation
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final UserService userService;

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    @Override
    public DocumentResponse uploadDocument(MultipartFile file, DocumentRequest request) {
        try {
            // Validate file
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            // Get current user
            User currentUser = userService.getCurrentUser();

            // Create upload directory if not exists
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null ? 
                    originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
            Path filePath = uploadPath.resolve(uniqueFilename);

            // Save file
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Create document entity
            Document document = Document.builder()
                    .fileName(originalFilename)
                    .filePath(filePath.toString())
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .category(request.getCategory())
                    .tags(request.getTags())
                    .status(Document.ProcessingStatus.PENDING)
                    .vectorized(false)
                    .user(currentUser)
                    .build();

            document = documentRepository.save(document);
            log.info("Document uploaded: {} by user: {}", document.getFileName(), currentUser.getEmail());

            // TODO: Send to AI service for processing
            // aiClientService.processDocument(document);

            return mapToResponse(document);

        } catch (IOException e) {
            log.error("Error uploading file: ", e);
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }

    @Override
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document", "id", id));
    }

    @Override
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @Override
    public Page<Document> getDocumentsByUser(Long userId, Pageable pageable) {
        User user = userService.getUserById(userId);
        return documentRepository.findByUser(user, pageable);
    }

    @Override
    public List<Document> getDocumentsByCategory(String category) {
        return documentRepository.findByCategory(category);
    }

    @Override
    public DocumentResponse updateDocument(Long id, DocumentRequest request) {
        Document document = getDocumentById(id);

        if (request.getTitle() != null) {
            document.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            document.setDescription(request.getDescription());
        }
        if (request.getCategory() != null) {
            document.setCategory(request.getCategory());
        }
        if (request.getTags() != null) {
            document.setTags(request.getTags());
        }

        document = documentRepository.save(document);
        log.info("Document updated: {}", document.getId());

        return mapToResponse(document);
    }

    @Override
    public void deleteDocument(Long id) {
        Document document = getDocumentById(id);

        // Delete physical file
        try {
            Path filePath = Paths.get(document.getFilePath());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("Error deleting file: ", e);
        }

        documentRepository.delete(document);
        log.info("Document deleted: {}", id);
    }

    @Override
    public Page<Document> searchDocuments(String keyword, Pageable pageable) {
        User currentUser = userService.getCurrentUser();
        return documentRepository.searchByUserAndKeyword(currentUser.getId(), keyword, pageable);
    }

    @Override
    public List<String> getAllCategories() {
        return documentRepository.findAllCategories();
    }

    private DocumentResponse mapToResponse(Document document) {
        return DocumentResponse.builder()
                .id(document.getId())
                .fileName(document.getFileName())
                .fileType(document.getFileType())
                .fileSize(document.getFileSize())
                .title(document.getTitle())
                .description(document.getDescription())
                .category(document.getCategory())
                .tags(document.getTags())
                .status(document.getStatus())
                .chunkCount(document.getChunkCount())
                .vectorized(document.getVectorized())
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt())
                .processedAt(document.getProcessedAt())
                .build();
    }

}

