package com.business.springservice.repository;

import com.business.springservice.entity.Order;
import com.business.springservice.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByCustomerId(Long customerId);
    
    List<Order> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
    
    List<Order> findByStatus(OrderStatus status);
    
    List<Order> findAllByOrderByCreatedAtDesc();
}
