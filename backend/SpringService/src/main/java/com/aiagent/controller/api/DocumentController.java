package com.aiagent.controller.api;

import com.aiagent.model.dto.DocumentRequest;
import com.aiagent.model.dto.DocumentResponse;
import com.aiagent.model.entity.Document;
import com.aiagent.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Document Management Controller
 * 
 * @author Nguyễn Văn Hoàng
 */
@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8100"})
@Tag(name = "Documents", description = "API quản lý tài liệu")
@SecurityRequirement(name = "Bearer Authentication")
public class DocumentController {

    private final DocumentService documentService;

    @Operation(
            summary = "Upload tài liệu",
            description = "Upload file PDF, DOC, DOCX, TXT, Excel (ADMIN & BUSINESS only)"
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'BUSINESS')")
    @PostMapping("/upload")
    public ResponseEntity<DocumentResponse> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String category) {
        
        DocumentRequest request = DocumentRequest.builder()
                .title(title != null ? title : file.getOriginalFilename())
                .description(description)
                .category(category)
                .build();

        DocumentResponse response = documentService.uploadDocument(file, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Lấy tất cả tài liệu", description = "Xem tất cả documents")
    @GetMapping
    public ResponseEntity<List<DocumentResponse>> getAllDocuments() {
        try {
            log.info("Fetching all documents...");
            List<Document> documents = documentService.getAllDocuments();
            log.info("Found {} documents", documents.size());
            List<DocumentResponse> responses = documents.stream()
                    .map(this::mapToResponse)
                    .toList();
            return ResponseEntity.ok(responses);
        } catch (Exception ex) {
            log.error("Error in getAllDocuments: ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @Operation(summary = "Lấy chi tiết tài liệu", description = "Xem chi tiết document theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponse> getDocumentById(@PathVariable Long id) {
        Document document = documentService.getDocumentById(id);
        return ResponseEntity.ok(mapToResponse(document));
    }

    @Operation(summary = "Lấy documents của user", description = "Lấy danh sách documents theo user ID")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<DocumentResponse>> getDocumentsByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        
        Page<Document> documents = documentService.getDocumentsByUser(userId, pageable);
        Page<DocumentResponse> responsePage = new PageImpl<>(
                documents.stream().map(this::mapToResponse).toList(),
                pageable,
                documents.getTotalElements()
        );
        return ResponseEntity.ok(responsePage);
    }

    @Operation(summary = "Lấy documents theo category", description = "Filter documents by category")
    @GetMapping("/category/{category}")
    public ResponseEntity<List<DocumentResponse>> getDocumentsByCategory(@PathVariable String category) {
        List<Document> documents = documentService.getDocumentsByCategory(category);
        List<DocumentResponse> responses = documents.stream()
                .map(this::mapToResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    // Utility mapper to convert entity -> DTO and avoid serializing lazy user
    private DocumentResponse mapToResponse(Document document) {
        return DocumentResponse.builder()
                .id(document.getId())
                .fileName(document.getFileName())
                .fileType(document.getFileType())
                .fileSize(document.getFileSize())
                .title(document.getTitle())
                .description(document.getDescription())
                .category(document.getCategory())
                .tags(document.getTags() != null ? document.getTags() : Set.of())
                .status(document.getStatus())
                .chunkCount(document.getChunkCount())
                .vectorized(document.getVectorized())
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt())
                .processedAt(document.getProcessedAt())
                .build();
    }

    @Operation(
            summary = "Cập nhật tài liệu",
            description = "Cập nhật thông tin document (ADMIN & BUSINESS only)"
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'BUSINESS')")
    @PutMapping("/{id}")
    public ResponseEntity<DocumentResponse> updateDocument(
            @PathVariable Long id,
            @Valid @RequestBody DocumentRequest request) {
        
        DocumentResponse response = documentService.updateDocument(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Xóa tài liệu",
            description = "Xóa document (ADMIN & BUSINESS only)"
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'BUSINESS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Document deleted successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Tìm kiếm tài liệu", description = "Tìm kiếm documents theo keyword")
    @GetMapping("/search")
    public ResponseEntity<Page<DocumentResponse>> searchDocuments(
            @RequestParam String keyword,
            Pageable pageable) {
        
        Page<Document> documents = documentService.searchDocuments(keyword, pageable);
        Page<DocumentResponse> responsePage = new PageImpl<>(
                documents.stream().map(this::mapToResponse).toList(),
                pageable,
                documents.getTotalElements()
        );
        return ResponseEntity.ok(responsePage);
    }

    @Operation(summary = "Lấy danh sách categories", description = "Lấy tất cả categories có trong hệ thống")
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = documentService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

}

