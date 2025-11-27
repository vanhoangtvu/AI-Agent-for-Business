package com.business.aiagent.controller;

import com.business.aiagent.dto.*;
import com.business.aiagent.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping
    public ResponseEntity<?> createPromotion(@Valid @RequestBody PromotionRequest request) {
        try {
            PromotionResponse response = promotionService.createPromotion(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            log.error("Error creating promotion: {}", e.getMessage());
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error creating promotion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal server error"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePromotion(
            @PathVariable Long id,
            @Valid @RequestBody PromotionRequest request) {
        try {
            PromotionResponse response = promotionService.updatePromotion(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Error updating promotion: {}", e.getMessage());
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error updating promotion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal server error"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPromotionById(@PathVariable Long id) {
        try {
            PromotionResponse response = promotionService.getPromotionById(id);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Promotion not found: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error getting promotion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal server error"));
        }
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getPromotionByCode(@PathVariable String code) {
        try {
            PromotionResponse response = promotionService.getPromotionByCode(code);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Promotion not found: {}", code);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error getting promotion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal server error"));
        }
    }

    @GetMapping
    public ResponseEntity<List<PromotionResponse>> getAllPromotions() {
        List<PromotionResponse> promotions = promotionService.getAllPromotions();
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/active")
    public ResponseEntity<List<PromotionResponse>> getActivePromotions() {
        List<PromotionResponse> promotions = promotionService.getActivePromotions();
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/public")
    public ResponseEntity<List<PromotionResponse>> getPublicActivePromotions() {
        List<PromotionResponse> promotions = promotionService.getPublicActivePromotions();
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<PromotionResponse>> getUpcomingPromotions() {
        List<PromotionResponse> promotions = promotionService.getUpcomingPromotions();
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/expired")
    public ResponseEntity<List<PromotionResponse>> getExpiredPromotions() {
        List<PromotionResponse> promotions = promotionService.getExpiredPromotions();
        return ResponseEntity.ok(promotions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePromotion(@PathVariable Long id) {
        try {
            promotionService.deletePromotion(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Promotion deleted successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Promotion not found: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error deleting promotion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal server error"));
        }
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<?> togglePromotionStatus(@PathVariable Long id) {
        try {
            PromotionResponse response = promotionService.togglePromotionStatus(id);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Promotion not found: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error toggling promotion status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Internal server error"));
        }
    }

    @PostMapping("/apply")
    public ResponseEntity<DiscountCalculationResponse> applyPromotion(
            @Valid @RequestBody ApplyPromotionRequest request) {
        DiscountCalculationResponse response = promotionService.applyPromotion(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/calculate")
    public ResponseEntity<DiscountCalculationResponse> calculateDiscount(
            @Valid @RequestBody ApplyPromotionRequest request) {
        DiscountCalculationResponse response = promotionService.calculateDiscount(request);
        return ResponseEntity.ok(response);
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("error", message);
        return error;
    }
}
