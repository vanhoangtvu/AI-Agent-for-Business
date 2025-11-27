package com.business.aiagent.controller;

import com.business.aiagent.entity.Role;
import com.business.aiagent.security.RequirePermission;
import com.business.aiagent.service.ProductSyncService;
import com.business.aiagent.service.PythonAIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for AI Service integration and synchronization
 */
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "AI Service Integration", description = "Manage AI service integration and data sync")
public class AIServiceController {

    private final PythonAIService pythonAIService;
    private final ProductSyncService productSyncService;

    @GetMapping("/health")
    @Operation(summary = "Check AI service health")
    public ResponseEntity<Map<String, Object>> checkHealth() {
        boolean healthy = pythonAIService.isHealthy();
        
        Map<String, Object> response = new HashMap<>();
        response.put("ai_service_healthy", healthy);
        response.put("status", healthy ? "connected" : "disconnected");
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/products/sync")
    @Operation(summary = "Manual product sync to AI service")
    @RequirePermission(Role.Permission.PRODUCT_UPDATE)
    public ResponseEntity<Map<String, Object>> syncProducts() {
        log.info("Manual product sync triggered");
        
        try {
            Map<String, Object> result = productSyncService.syncAllProducts();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Product sync failed: {}", e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @GetMapping("/products/sync/status")
    @Operation(summary = "Get product sync status")
    @RequirePermission(Role.Permission.PRODUCT_READ)
    public ResponseEntity<Map<String, Object>> getSyncStatus() {
        Map<String, Object> status = productSyncService.getSyncStatus();
        return ResponseEntity.ok(status);
    }

    @PostMapping("/search/semantic")
    @Operation(summary = "Semantic search products")
    public ResponseEntity<Map<String, Object>> semanticSearch(
        @RequestParam String query,
        @RequestParam(defaultValue = "5") int topK
    ) {
        try {
            Map<String, Object> results = pythonAIService.semanticSearch(query, null, topK);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            log.error("Semantic search failed: {}", e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("results", new Object[]{});
            return ResponseEntity.status(500).body(error);
        }
    }
}
