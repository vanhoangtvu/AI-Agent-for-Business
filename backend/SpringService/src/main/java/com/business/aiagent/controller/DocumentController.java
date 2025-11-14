package com.business.aiagent.controller;

import com.business.aiagent.dto.DocumentResponse;
import com.business.aiagent.service.DocumentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Tag(name = "Documents")
public class DocumentController {
    
    private final DocumentService documentService;
    
    @PostMapping("/upload")
    public ResponseEntity<DocumentResponse> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "tags", required = false) String tags,
            Authentication authentication) {
        
        DocumentResponse response = documentService.uploadDocument(
                file, 
                authentication.getName(), 
                category, 
                tags
        );
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<Page<DocumentResponse>> getDocuments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        
        Page<DocumentResponse> documents = documentService.getUserDocuments(
                authentication.getName(),
                PageRequest.of(page, size)
        );
        return ResponseEntity.ok(documents);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponse> getDocument(
            @PathVariable Long id,
            Authentication authentication) {
        
        DocumentResponse document = documentService.getDocument(id, authentication.getName());
        return ResponseEntity.ok(document);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable Long id,
            Authentication authentication) {
        
        documentService.deleteDocument(id, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
