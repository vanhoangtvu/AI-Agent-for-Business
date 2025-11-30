package com.business.springservice.repository;

import com.business.springservice.entity.BusinessDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessDocumentRepository extends JpaRepository<BusinessDocument, Long> {
    
    List<BusinessDocument> findByBusinessId(Long businessId);
    
    List<BusinessDocument> findByBusinessIdOrderByUploadedAtDesc(Long businessId);
}
