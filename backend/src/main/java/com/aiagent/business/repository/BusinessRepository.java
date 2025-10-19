package com.aiagent.business.repository;

import com.aiagent.business.model.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    
    Optional<Business> findBySlug(String slug);
    
    boolean existsBySlug(String slug);
    
    Optional<Business> findByOwnerId(Long ownerId);
    
    Page<Business> findByStatus(Business.BusinessStatus status, Pageable pageable);
}

