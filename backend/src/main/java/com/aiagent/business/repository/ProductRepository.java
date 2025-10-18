package com.aiagent.business.repository;

import com.aiagent.business.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Page<Product> findByBusinessIdAndIsActiveTrue(Long businessId, Pageable pageable);
    
    Optional<Product> findByBusinessIdAndId(Long businessId, Long id);
    
    Optional<Product> findByBusinessIdAndSku(Long businessId, String sku);
    
    Optional<Product> findByBusinessIdAndSlug(Long businessId, String slug);
    
    long countByBusinessId(Long businessId);
}

