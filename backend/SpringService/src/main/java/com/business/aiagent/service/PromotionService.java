package com.business.aiagent.service;

import com.business.aiagent.dto.*;
import com.business.aiagent.entity.Category;
import com.business.aiagent.entity.Product;
import com.business.aiagent.entity.Promotion;
import com.business.aiagent.entity.User;
import com.business.aiagent.repository.CategoryRepository;
import com.business.aiagent.repository.ProductRepository;
import com.business.aiagent.repository.PromotionRepository;
import com.business.aiagent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public PromotionResponse createPromotion(PromotionRequest request) {
        // Check if code already exists
        if (promotionRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Promotion code already exists: " + request.getCode());
        }

        Promotion promotion = new Promotion();
        updatePromotionFromRequest(promotion, request);
        
        Promotion saved = promotionRepository.save(promotion);
        log.info("Created promotion: {} ({})", saved.getName(), saved.getCode());
        
        return convertToResponse(saved);
    }

    @Transactional
    public PromotionResponse updatePromotion(Long id, PromotionRequest request) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found: " + id));

        // Check if code is changed and already exists
        if (!promotion.getCode().equals(request.getCode()) && 
            promotionRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Promotion code already exists: " + request.getCode());
        }

        updatePromotionFromRequest(promotion, request);
        
        Promotion saved = promotionRepository.save(promotion);
        log.info("Updated promotion: {} ({})", saved.getName(), saved.getCode());
        
        return convertToResponse(saved);
    }

    @Transactional(readOnly = true)
    public PromotionResponse getPromotionById(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found: " + id));
        return convertToResponse(promotion);
    }

    @Transactional(readOnly = true)
    public PromotionResponse getPromotionByCode(String code) {
        Promotion promotion = promotionRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found: " + code));
        return convertToResponse(promotion);
    }

    @Transactional(readOnly = true)
    public List<PromotionResponse> getAllPromotions() {
        return promotionRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PromotionResponse> getActivePromotions() {
        return promotionRepository.findActivePromotions(LocalDateTime.now()).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PromotionResponse> getPublicActivePromotions() {
        return promotionRepository.findPublicActivePromotions(LocalDateTime.now()).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PromotionResponse> getUpcomingPromotions() {
        return promotionRepository.findUpcomingPromotions(LocalDateTime.now()).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PromotionResponse> getExpiredPromotions() {
        return promotionRepository.findExpiredPromotions(LocalDateTime.now()).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePromotion(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found: " + id));
        
        promotionRepository.delete(promotion);
        log.info("Deleted promotion: {} ({})", promotion.getName(), promotion.getCode());
    }

    @Transactional
    public PromotionResponse togglePromotionStatus(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found: " + id));
        
        promotion.setIsActive(!promotion.getIsActive());
        Promotion saved = promotionRepository.save(promotion);
        
        log.info("Toggled promotion status: {} ({}), isActive: {}", 
                saved.getName(), saved.getCode(), saved.getIsActive());
        
        return convertToResponse(saved);
    }

    @Transactional
    public DiscountCalculationResponse applyPromotion(ApplyPromotionRequest request) {
        Optional<Promotion> promotionOpt = promotionRepository.findValidPromotionByCode(
                request.getPromotionCode(), LocalDateTime.now());

        if (promotionOpt.isEmpty()) {
            return DiscountCalculationResponse.builder()
                    .isValid(false)
                    .originalAmount(request.getOrderAmount())
                    .discountAmount(BigDecimal.ZERO)
                    .finalAmount(request.getOrderAmount())
                    .errorMessage("Promotion code is invalid or expired")
                    .build();
        }

        Promotion promotion = promotionOpt.get();
        
        // Check minimum order amount
        if (promotion.getMinOrderAmount() != null && 
            request.getOrderAmount().compareTo(promotion.getMinOrderAmount()) < 0) {
            return DiscountCalculationResponse.builder()
                    .isValid(false)
                    .promotionCode(promotion.getCode())
                    .promotionName(promotion.getName())
                    .originalAmount(request.getOrderAmount())
                    .discountAmount(BigDecimal.ZERO)
                    .finalAmount(request.getOrderAmount())
                    .errorMessage(String.format("Minimum order amount is %s", 
                            promotion.getMinOrderAmount()))
                    .build();
        }

        // Calculate discount
        BigDecimal discountAmount = promotion.calculateDiscount(request.getOrderAmount());
        BigDecimal finalAmount = request.getOrderAmount().subtract(discountAmount);

        // Increment usage count
        promotion.setUsedCount(promotion.getUsedCount() + 1);
        promotionRepository.save(promotion);

        log.info("Applied promotion: {} to order amount: {}, discount: {}", 
                promotion.getCode(), request.getOrderAmount(), discountAmount);

        return DiscountCalculationResponse.builder()
                .isValid(true)
                .promotionCode(promotion.getCode())
                .promotionName(promotion.getName())
                .originalAmount(request.getOrderAmount())
                .discountAmount(discountAmount)
                .finalAmount(finalAmount)
                .message(String.format("You saved %s with promotion %s!", 
                        discountAmount, promotion.getName()))
                .build();
    }

    @Transactional(readOnly = true)
    public DiscountCalculationResponse calculateDiscount(ApplyPromotionRequest request) {
        Optional<Promotion> promotionOpt = promotionRepository.findValidPromotionByCode(
                request.getPromotionCode(), LocalDateTime.now());

        if (promotionOpt.isEmpty()) {
            return DiscountCalculationResponse.builder()
                    .isValid(false)
                    .originalAmount(request.getOrderAmount())
                    .discountAmount(BigDecimal.ZERO)
                    .finalAmount(request.getOrderAmount())
                    .errorMessage("Promotion code is invalid or expired")
                    .build();
        }

        Promotion promotion = promotionOpt.get();
        
        if (promotion.getMinOrderAmount() != null && 
            request.getOrderAmount().compareTo(promotion.getMinOrderAmount()) < 0) {
            return DiscountCalculationResponse.builder()
                    .isValid(false)
                    .promotionCode(promotion.getCode())
                    .promotionName(promotion.getName())
                    .originalAmount(request.getOrderAmount())
                    .discountAmount(BigDecimal.ZERO)
                    .finalAmount(request.getOrderAmount())
                    .errorMessage(String.format("Minimum order amount is %s", 
                            promotion.getMinOrderAmount()))
                    .build();
        }

        BigDecimal discountAmount = promotion.calculateDiscount(request.getOrderAmount());
        BigDecimal finalAmount = request.getOrderAmount().subtract(discountAmount);

        return DiscountCalculationResponse.builder()
                .isValid(true)
                .promotionCode(promotion.getCode())
                .promotionName(promotion.getName())
                .originalAmount(request.getOrderAmount())
                .discountAmount(discountAmount)
                .finalAmount(finalAmount)
                .message(String.format("You will save %s with promotion %s!", 
                        discountAmount, promotion.getName()))
                .build();
    }

    private void updatePromotionFromRequest(Promotion promotion, PromotionRequest request) {
        promotion.setCode(request.getCode());
        promotion.setName(request.getName());
        promotion.setDescription(request.getDescription());
        promotion.setType(request.getType());
        promotion.setDiscountValue(request.getDiscountValue());
        promotion.setMaxDiscountAmount(request.getMaxDiscountAmount());
        promotion.setMinOrderAmount(request.getMinOrderAmount());
        promotion.setStartDate(request.getStartDate());
        promotion.setEndDate(request.getEndDate());
        promotion.setUsageLimit(request.getUsageLimit());
        promotion.setIsActive(request.getIsActive());
        promotion.setIsPublic(request.getIsPublic());
        promotion.setBuyQuantity(request.getBuyQuantity());
        promotion.setGetQuantity(request.getGetQuantity());
        
        // Set created by user
        if (request.getCreatedById() != null) {
            User creator = userRepository.findById(request.getCreatedById())
                    .orElse(null);
            promotion.setCreatedBy(creator);
        }

        // Set applicable products
        if (request.getApplicableProductIds() != null && !request.getApplicableProductIds().isEmpty()) {
            Set<Product> products = new HashSet<>(productRepository.findAllById(request.getApplicableProductIds()));
            promotion.setApplicableProducts(products);
        } else {
            promotion.setApplicableProducts(new HashSet<>());
        }

        // Set applicable categories
        if (request.getApplicableCategoryIds() != null && !request.getApplicableCategoryIds().isEmpty()) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.getApplicableCategoryIds()));
            promotion.setApplicableCategories(categories);
        } else {
            promotion.setApplicableCategories(new HashSet<>());
        }
    }

    private PromotionResponse convertToResponse(Promotion promotion) {
        LocalDateTime now = LocalDateTime.now();
        
        return PromotionResponse.builder()
                .id(promotion.getId())
                .code(promotion.getCode())
                .name(promotion.getName())
                .description(promotion.getDescription())
                .type(promotion.getType())
                .discountValue(promotion.getDiscountValue())
                .maxDiscountAmount(promotion.getMaxDiscountAmount())
                .minOrderAmount(promotion.getMinOrderAmount())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .usageLimit(promotion.getUsageLimit())
                .usedCount(promotion.getUsedCount())
                .isActive(promotion.getIsActive())
                .isPublic(promotion.getIsPublic())
                .buyQuantity(promotion.getBuyQuantity())
                .getQuantity(promotion.getGetQuantity())
                .applicableProductIds(promotion.getApplicableProducts().stream()
                        .map(Product::getId)
                        .collect(Collectors.toSet()))
                .applicableCategoryIds(promotion.getApplicableCategories().stream()
                        .map(Category::getId)
                        .collect(Collectors.toSet()))
                .createdById(promotion.getCreatedBy() != null ? promotion.getCreatedBy().getId() : null)
                .createdByName(promotion.getCreatedBy() != null ? promotion.getCreatedBy().getUsername() : null)
                .createdAt(promotion.getCreatedAt())
                .updatedAt(promotion.getUpdatedAt())
                .isValid(promotion.isValid())
                .isExpired(now.isAfter(promotion.getEndDate()))
                .isUpcoming(now.isBefore(promotion.getStartDate()))
                .remainingUsage(promotion.getUsageLimit() != null ? 
                        promotion.getUsageLimit() - promotion.getUsedCount() : null)
                .daysRemaining(ChronoUnit.DAYS.between(now, promotion.getEndDate()))
                .build();
    }
}
