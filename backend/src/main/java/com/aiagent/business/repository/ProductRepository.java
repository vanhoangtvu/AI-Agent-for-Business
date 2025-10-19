package com.aiagent.business.repository;

import com.aiagent.business.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Page<Product> findByBusinessId(Long businessId, Pageable pageable);
    
    Page<Product> findByBusinessIdAndIsActiveTrue(Long businessId, Pageable pageable);
    
    Optional<Product> findByIdAndBusinessId(Long id, Long businessId);
    
    Optional<Product> findByBusinessIdAndId(Long businessId, Long id);
    
    Optional<Product> findByBusinessIdAndSku(Long businessId, String sku);
    
    Optional<Product> findByBusinessIdAndSlug(Long businessId, String slug);
    
    Page<Product> findByBusinessIdAndProductNameContainingOrSkuContaining(
        Long businessId, String productName, String sku, Pageable pageable);
    
    Page<Product> findByBusinessIdAndCategoryId(Long businessId, Long categoryId, Pageable pageable);
    
    long countByBusinessId(Long businessId);
}

