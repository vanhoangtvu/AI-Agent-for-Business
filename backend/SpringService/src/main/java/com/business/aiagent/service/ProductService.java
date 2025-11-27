package com.business.aiagent.service;

import com.business.aiagent.dto.CategoryResponse;
import com.business.aiagent.dto.ProductRequest;
import com.business.aiagent.dto.ProductResponse;
import com.business.aiagent.entity.Category;
import com.business.aiagent.entity.Product;
import com.business.aiagent.entity.User;
import com.business.aiagent.repository.CategoryRepository;
import com.business.aiagent.repository.ProductRepository;
import com.business.aiagent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductSyncService productSyncService;
    
    public Page<ProductResponse> getAllProducts(
            int page, int size, String sortBy, String sortDir,
            Long categoryId, String keyword, BigDecimal minPrice, BigDecimal maxPrice) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Product> products;
        
        // Apply filters based on available repository methods
        if (categoryId != null && minPrice != null && maxPrice != null) {
            products = productRepository.findByCategoryAndPriceRange(categoryId, minPrice, maxPrice, pageable);
        } else if (categoryId != null) {
            products = productRepository.findByCategory_IdAndIsActiveTrue(categoryId, pageable);
        } else if (keyword != null && !keyword.isEmpty()) {
            products = productRepository.searchByKeyword(keyword, pageable);
        } else if (minPrice != null && maxPrice != null) {
            products = productRepository.findByPriceRange(minPrice, maxPrice, pageable);
        } else {
            products = productRepository.findByIsActiveTrue(pageable);
        }
        
        return products.map(this::convertToResponse);
    }
    
    @Transactional
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        // Increment view count (handle null)
        Integer currentViewCount = product.getViewCount();
        product.setViewCount(currentViewCount != null ? currentViewCount + 1 : 1);
        productRepository.save(product);
        
        return convertToResponse(product);
    }
    
    @Transactional
    public ProductResponse createProduct(ProductRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .compareAtPrice(request.getCompareAtPrice())
                .stockQuantity(request.getStockQuantity())
                .sku(request.getSku())
                .barcode(request.getBarcode())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .isFeatured(request.getIsFeatured() != null ? request.getIsFeatured() : false)
                .imageUrls(request.getImageUrls())
                .tags(request.getTags())
                .weight(request.getWeight())
                .manufacturer(request.getManufacturer())
                .origin(request.getOrigin())
                .businessOwner(user)
                .createdBy(user)
                .rating(BigDecimal.ZERO)
                .reviewCount(0)
                .viewCount(0)
                .soldCount(0)
                .build();
        
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }
        
        Product savedProduct = productRepository.save(product);
        
        // Auto sync to AI Service
        try {
            productSyncService.syncProduct(savedProduct);
        } catch (Exception e) {
            // Log but don't fail the operation
            System.err.println("Failed to sync product to AI: " + e.getMessage());
        }
        
        return convertToResponse(savedProduct);
    }
    
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request, String username) {
        // Use regular findById to avoid lazy-load issues with @ElementCollection
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        // Check ownership
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // ADMIN can update any product
        // BUSINESS can update their own products
        // BUSINESS can also claim/update products without owner (legacy data)
        if (!user.isAdmin()) {
            if (product.getBusinessOwner() != null && 
                !product.getBusinessOwner().getId().equals(user.getId())) {
                throw new RuntimeException("You don't have permission to update this product");
            }
            // If product has no owner and user is BUSINESS, auto-assign ownership
            if (product.getBusinessOwner() == null && user.isBusiness()) {
                product.setBusinessOwner(user);
                if (product.getCreatedBy() == null) {
                    product.setCreatedBy(user);
                }
            }
        }
        
        // Update fields
        if (request.getName() != null) product.setName(request.getName());
        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getCompareAtPrice() != null) product.setCompareAtPrice(request.getCompareAtPrice());
        if (request.getStockQuantity() != null) product.setStockQuantity(request.getStockQuantity());
        if (request.getSku() != null) product.setSku(request.getSku());
        if (request.getBarcode() != null) product.setBarcode(request.getBarcode());
        if (request.getIsActive() != null) product.setIsActive(request.getIsActive());
        if (request.getIsFeatured() != null) product.setIsFeatured(request.getIsFeatured());
        
        // Update collections by replacing entirely (don't access existing to avoid lazy-load)
        if (request.getImageUrls() != null) {
            product.setImageUrls(new ArrayList<>(request.getImageUrls()));
        }
        if (request.getTags() != null) {
            product.setTags(new ArrayList<>(request.getTags()));
        }
        
        if (request.getWeight() != null) product.setWeight(request.getWeight());
        if (request.getManufacturer() != null) product.setManufacturer(request.getManufacturer());
        if (request.getOrigin() != null) product.setOrigin(request.getOrigin());
        
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }
        
        Product updatedProduct = productRepository.save(product);
        
        // Auto sync to AI Service
        try {
            productSyncService.syncProduct(updatedProduct);
        } catch (Exception e) {
            System.err.println("Failed to sync product update to AI: " + e.getMessage());
        }
        
        return convertToResponse(updatedProduct);
    }
    
    @Transactional
    public void deleteProduct(Long id, String username) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        // Check ownership
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // ADMIN can delete any product
        // BUSINESS can only delete their own products
        if (!user.isAdmin()) {
            if (product.getBusinessOwner() == null || 
                !product.getBusinessOwner().getId().equals(user.getId())) {
                throw new RuntimeException("You don't have permission to delete this product");
            }
        }
        
        // Soft delete
        product.setIsActive(false);
        productRepository.save(product);
        
        // Remove from AI Service
        try {
            productSyncService.deleteProductFromAI(id);
        } catch (Exception e) {
            System.err.println("Failed to delete product from AI: " + e.getMessage());
        }
    }
    
    public Page<ProductResponse> getMyProducts(String username, int page, int size) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Product> products = productRepository.findAll(pageable);
        
        // Filter by user
        return products
                .map(this::convertToResponse)
                .map(response -> {
                    Product p = productRepository.findById(response.getId()).orElse(null);
                    if (p != null && p.getBusinessOwner().getId().equals(user.getId())) {
                        return response;
                    }
                    return null;
                })
                .map(response -> response);
    }
    
    public List<ProductResponse> getTopSellingProducts(int limit) {
        List<Product> products = productRepository.findTop10ByIsActiveTrueOrderBySoldCountDesc();
        return products.stream()
                .limit(limit)
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public Page<ProductResponse> getFeaturedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByIsFeaturedTrueAndIsActiveTrue(pageable)
                .map(this::convertToResponse);
    }
    
    public List<ProductResponse> getLowStockProducts(int threshold) {
        return productRepository.findByStockQuantityLessThanEqualAndIsActiveTrue(threshold)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    private ProductResponse convertToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .compareAtPrice(product.getCompareAtPrice())
                .stockQuantity(product.getStockQuantity())
                .sku(product.getSku())
                .barcode(product.getBarcode())
                .category(product.getCategory() != null ? convertCategoryToResponse(product.getCategory()) : null)
                .isActive(product.getIsActive())
                .isFeatured(product.getIsFeatured())
                .imageUrls(product.getImageUrls())
                .tags(product.getTags())
                .weight(product.getWeight())
                .manufacturer(product.getManufacturer())
                .origin(product.getOrigin())
                .rating(product.getRating() != null ? product.getRating() : BigDecimal.ZERO)
                .reviewCount(product.getReviewCount() != null ? product.getReviewCount() : 0)
                .viewCount(product.getViewCount() != null ? product.getViewCount() : 0)
                .soldCount(product.getSoldCount() != null ? product.getSoldCount() : 0)
                .inStock(product.getStockQuantity() > 0)
                .onSale(product.getCompareAtPrice() != null && 
                        product.getCompareAtPrice().compareTo(product.getPrice()) > 0)
                .discountPercentage(calculateDiscountPercentage(product))
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
    
    private CategoryResponse convertCategoryToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .imageUrl(category.getImageUrl())
                .isActive(category.getIsActive())
                .productCount(category.getProducts() != null ? category.getProducts().size() : 0)
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
    
    private BigDecimal calculateDiscountPercentage(Product product) {
        if (product.getCompareAtPrice() == null || 
            product.getCompareAtPrice().compareTo(product.getPrice()) <= 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal discount = product.getCompareAtPrice().subtract(product.getPrice());
        return discount.divide(product.getCompareAtPrice(), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
