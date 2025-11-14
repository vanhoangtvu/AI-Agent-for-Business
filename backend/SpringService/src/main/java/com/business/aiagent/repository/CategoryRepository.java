package com.business.aiagent.repository;

import com.business.aiagent.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    Optional<Category> findBySlug(String slug);
    
    Optional<Category> findByName(String name);
    
    List<Category> findByIsActiveTrueOrderByDisplayOrderAsc();
    
    List<Category> findByParentIsNullAndIsActiveTrueOrderByDisplayOrderAsc();
    
    List<Category> findByParent_IdAndIsActiveTrueOrderByDisplayOrderAsc(Long parentId);
    
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.children WHERE c.parent IS NULL AND c.isActive = true ORDER BY c.displayOrder")
    List<Category> findAllParentCategoriesWithChildren();
    
    boolean existsByName(String name);
    
    boolean existsBySlug(String slug);
}
