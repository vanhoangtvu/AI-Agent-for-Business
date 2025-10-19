package com.aiagent.business.repository;

import com.aiagent.business.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Page<Customer> findByBusinessId(Long businessId, Pageable pageable);
    
    Optional<Customer> findByIdAndBusinessId(Long id, Long businessId);
    
    Optional<Customer> findByBusinessIdAndId(Long businessId, Long id);
    
    Optional<Customer> findByBusinessIdAndEmail(Long businessId, String email);
    
    Optional<Customer> findByBusinessIdAndPhone(Long businessId, String phone);
    
    Page<Customer> findByBusinessIdAndFullNameContainingOrEmailContainingOrPhoneContaining(
        Long businessId, String fullName, String email, String phone, Pageable pageable);
    
    Page<Customer> findByBusinessIdAndSegment(Long businessId, String segment, Pageable pageable);
    
    long countByBusinessId(Long businessId);
}

