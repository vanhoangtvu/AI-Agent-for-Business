package com.business.aiagent.repository;

import com.business.aiagent.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    
    Optional<Promotion> findByCode(String code);
    
    @Query("SELECT p FROM Promotion p WHERE p.isActive = true " +
           "AND p.startDate <= :now AND p.endDate >= :now " +
           "AND (p.usageLimit IS NULL OR p.usedCount < p.usageLimit)")
    List<Promotion> findActivePromotions(@Param("now") LocalDateTime now);
    
    @Query("SELECT p FROM Promotion p WHERE p.isActive = true " +
           "AND p.isPublic = true " +
           "AND p.startDate <= :now AND p.endDate >= :now " +
           "AND (p.usageLimit IS NULL OR p.usedCount < p.usageLimit)")
    List<Promotion> findPublicActivePromotions(@Param("now") LocalDateTime now);
    
    @Query("SELECT p FROM Promotion p WHERE p.code = :code " +
           "AND p.isActive = true " +
           "AND p.startDate <= :now AND p.endDate >= :now " +
           "AND (p.usageLimit IS NULL OR p.usedCount < p.usageLimit)")
    Optional<Promotion> findValidPromotionByCode(@Param("code") String code, @Param("now") LocalDateTime now);
    
    List<Promotion> findByIsActive(Boolean isActive);
    
    @Query("SELECT p FROM Promotion p WHERE p.endDate < :now")
    List<Promotion> findExpiredPromotions(@Param("now") LocalDateTime now);
    
    @Query("SELECT p FROM Promotion p WHERE p.startDate > :now AND p.isActive = true")
    List<Promotion> findUpcomingPromotions(@Param("now") LocalDateTime now);
    
    boolean existsByCode(String code);
}
