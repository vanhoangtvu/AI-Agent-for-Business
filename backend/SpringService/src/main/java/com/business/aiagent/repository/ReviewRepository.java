package com.business.aiagent.repository;

import com.business.aiagent.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    // Tìm tất cả review theo sản phẩm (có phân trang)
    Page<Review> findByProductIdOrderByCreatedAtDesc(Long productId, Pageable pageable);
    
    // Tìm tất cả review theo user
    Page<Review> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    
    // Đếm số review theo sản phẩm
    Long countByProductId(Long productId);
    
    // Tính rating trung bình theo sản phẩm
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.productId = :productId")
    Double calculateAverageRating(@Param("productId") Long productId);
    
    // Đếm số review theo từng rating (cho biểu đồ phân bố)
    @Query("SELECT r.rating, COUNT(r) FROM Review r WHERE r.productId = :productId GROUP BY r.rating")
    List<Object[]> countByRatingAndProductId(@Param("productId") Long productId);
    
    // Kiểm tra user đã review sản phẩm này chưa
    boolean existsByProductIdAndUserId(Long productId, Long userId);
    
    // Tìm review của user cho sản phẩm cụ thể
    Optional<Review> findByProductIdAndUserId(Long productId, Long userId);
    
    // Tìm review verified purchase
    Page<Review> findByProductIdAndVerifiedPurchaseOrderByCreatedAtDesc(
        Long productId, Boolean verifiedPurchase, Pageable pageable
    );
    
    // Tìm review theo rating
    Page<Review> findByProductIdAndRatingOrderByCreatedAtDesc(
        Long productId, Integer rating, Pageable pageable
    );
}
