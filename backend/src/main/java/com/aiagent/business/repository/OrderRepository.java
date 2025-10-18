package com.aiagent.business.repository;

import com.aiagent.business.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    Page<Order> findByBusinessId(Long businessId, Pageable pageable);
    
    Optional<Order> findByBusinessIdAndId(Long businessId, Long id);
    
    Optional<Order> findByOrderNumber(String orderNumber);
    
    Page<Order> findByBusinessIdAndCustomerId(Long businessId, Long customerId, Pageable pageable);
    
    long countByBusinessId(Long businessId);
    
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.businessId = :businessId AND o.paymentStatus = 'PAID'")
    Double getTotalRevenueByBusinessId(Long businessId);
}

