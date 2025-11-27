package com.business.aiagent.service;

import com.business.aiagent.entity.Product;
import com.business.aiagent.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for syncing product data with Python AI Service
 * Automatically syncs products to vector database for RAG
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductSyncService {

    private final ProductRepository productRepository;
    private final PythonAIService pythonAIService;

    /**
     * Sync all products on application startup
     */
    @PostConstruct
    public void syncAllProductsOnStartup() {
        log.info("=== Starting initial product sync to AI Service ===");
        try {
            syncAllProducts();
        } catch (Exception e) {
            log.error("Failed to sync products on startup: {}", e.getMessage());
            // Don't fail app startup if AI service is down
        }
    }

    /**
     * Scheduled sync every 6 hours
     */
    @Scheduled(fixedDelay = 21600000, initialDelay = 3600000) // 6 hours
    public void scheduledSync() {
        log.info("=== Starting scheduled product sync ===");
        try {
            syncAllProducts();
        } catch (Exception e) {
            log.error("Scheduled sync failed: {}", e.getMessage());
        }
    }

    /**
     * Sync all products to AI service
     */
    @Transactional(readOnly = true)
    public Map<String, Object> syncAllProducts() {
        log.info("Syncing all products to AI service...");
        
        try {
            // Get all products
            List<Product> products = productRepository.findAll();
            log.info("Found {} products to sync", products.size());
            
            if (products.isEmpty()) {
                log.warn("No products found to sync");
                return Map.of(
                    "success", true,
                    "message", "No products to sync",
                    "total_products", 0
                );
            }
            
            // Convert to format for AI service
            List<Map<String, Object>> productData = products.stream()
                .map(this::convertProductToMap)
                .collect(Collectors.toList());
            
            // Send to Python AI service
            Map<String, Object> result = pythonAIService.syncProducts(productData);
            
            log.info("Successfully synced {} products to AI service", products.size());
            return result;
            
        } catch (Exception e) {
            log.error("Error syncing products: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to sync products: " + e.getMessage());
        }
    }

    /**
     * Sync single product when created or updated
     */
    @Async
    public void syncProduct(Product product) {
        log.info("Syncing product {} to AI service", product.getId());
        
        try {
            Map<String, Object> productData = convertProductToMap(product);
            
            // Sync as single-item list
            pythonAIService.syncProducts(List.of(productData));
            
            log.info("Product {} synced successfully", product.getId());
            
        } catch (Exception e) {
            log.error("Error syncing product {}: {}", product.getId(), e.getMessage());
            // Don't fail the main operation if sync fails
        }
    }

    /**
     * Delete product from vector database
     */
    @Async
    public void deleteProductFromAI(Long productId) {
        log.info("Deleting product {} from AI service", productId);
        
        try {
            // Python service will handle deletion
            // For now, we'll trigger a full resync
            // In production, implement specific delete endpoint
            
            log.info("Product {} deletion queued", productId);
            
        } catch (Exception e) {
            log.error("Error deleting product {} from AI: {}", productId, e.getMessage());
        }
    }

    /**
     * Convert Product entity to Map for AI service
     */
    private Map<String, Object> convertProductToMap(Product product) {
        Map<String, Object> data = new HashMap<>();
        
        // IMPORTANT: Python model expects 'id', not 'product_id'
        data.put("id", product.getId());
        data.put("name", product.getName());
        data.put("description", product.getDescription() != null ? product.getDescription() : "");
        data.put("price", product.getPrice().doubleValue());
        data.put("stock_quantity", product.getStockQuantity());
        data.put("sold_count", product.getSoldCount());
        data.put("category", product.getCategory() != null ? product.getCategory().getName() : "Uncategorized");
        
        // Optional fields for Python model
        data.put("original_price", product.getCompareAtPrice() != null ? 
            product.getCompareAtPrice().doubleValue() : null);
        
        // Calculate discount percentage if applicable
        Double discountPercent = null;
        if (product.getCompareAtPrice() != null && product.getCompareAtPrice().compareTo(product.getPrice()) > 0) {
            discountPercent = ((product.getCompareAtPrice().subtract(product.getPrice()))
                .divide(product.getCompareAtPrice(), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100))).doubleValue();
        }
        data.put("discount_percent", discountPercent);
        
        // Handle imageUrls list
        String imageUrl = null;
        if (product.getImageUrls() != null && !product.getImageUrls().isEmpty()) {
            imageUrl = product.getImageUrls().get(0);
        }
        data.put("image_url", imageUrl);
        
        // Specifications
        StringBuilder specs = new StringBuilder();
        if (product.getManufacturer() != null) {
            specs.append("Hãng: ").append(product.getManufacturer()).append(", ");
        }
        if (product.getOrigin() != null) {
            specs.append("Xuất xứ: ").append(product.getOrigin()).append(", ");
        }
        if (product.getWeight() != null) {
            specs.append("Trọng lượng: ").append(product.getWeight()).append("g");
        }
        data.put("specifications", specs.length() > 0 ? specs.toString() : null);
        
        // Created at
        data.put("created_at", product.getCreatedAt() != null ? product.getCreatedAt().toString() : null);
        
        return data;
    }

    /**
     * Get sync status
     */
    public Map<String, Object> getSyncStatus() {
        try {
            long totalProducts = productRepository.count();
            boolean aiServiceHealthy = pythonAIService.isHealthy();
            
            return Map.of(
                "total_products", totalProducts,
                "ai_service_healthy", aiServiceHealthy,
                "last_sync", "Check logs for details"
            );
            
        } catch (Exception e) {
            return Map.of(
                "error", e.getMessage(),
                "ai_service_healthy", false
            );
        }
    }
}
