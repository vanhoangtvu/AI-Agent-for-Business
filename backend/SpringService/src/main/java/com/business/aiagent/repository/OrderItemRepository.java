package com.business.aiagent.repository;

import com.business.aiagent.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    List<OrderItem> findByOrder_Id(Long orderId);
    
    List<OrderItem> findByProduct_Id(Long productId);
    
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.user.id = :userId")
    List<OrderItem> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT oi.product.id, SUM(oi.quantity) as totalQuantity " +
           "FROM OrderItem oi " +
           "WHERE oi.order.status = 'COMPLETED' " +
           "GROUP BY oi.product.id " +
           "ORDER BY totalQuantity DESC")
    List<Object[]> findBestSellingProducts();
}
