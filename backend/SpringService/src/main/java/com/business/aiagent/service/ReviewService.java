package com.business.aiagent.service;

import com.business.aiagent.dto.ReviewRequest;
import com.business.aiagent.dto.ReviewResponse;
import com.business.aiagent.dto.ReviewStatistics;
import com.business.aiagent.entity.*;
import com.business.aiagent.repository.OrderRepository;
import com.business.aiagent.repository.ProductRepository;
import com.business.aiagent.repository.ReviewRepository;
import com.business.aiagent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    
    /**
     * Tạo review mới và tự động cập nhật rating của product
     */
    @Transactional
    public ReviewResponse createReview(ReviewRequest request, Long userId) {
        // Validate
        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new IllegalArgumentException("Rating phải từ 1 đến 5");
        }
        
        // Kiểm tra sản phẩm có tồn tại không
        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        
        // Kiểm tra user có tồn tại không
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        
        // Kiểm tra user đã review sản phẩm này chưa
        if (reviewRepository.existsByProductIdAndUserId(request.getProductId(), userId)) {
            throw new IllegalArgumentException("Bạn đã đánh giá sản phẩm này rồi");
        }
        
        // Kiểm tra verified purchase
        boolean verifiedPurchase = checkVerifiedPurchase(request.getProductId(), userId);
        
        // Tạo review
        Review review = new Review();
        review.setProductId(request.getProductId());
        review.setUserId(userId);
        review.setRating(request.getRating());
        review.setTitle(request.getTitle());
        review.setComment(request.getComment());
        review.setImages(request.getImages() != null ? request.getImages() : List.of());
        review.setVerifiedPurchase(verifiedPurchase);
        
        Review savedReview = reviewRepository.save(review);
        
        // Cập nhật rating và reviewCount của product
        updateProductRating(request.getProductId());
        
        return convertToResponse(savedReview, user);
    }
    
    /**
     * Cập nhật review
     */
    @Transactional
    public ReviewResponse updateReview(Long reviewId, ReviewRequest request, Long userId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy đánh giá"));
        
        // Kiểm tra quyền sở hữu
        if (!review.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Bạn không có quyền chỉnh sửa đánh giá này");
        }
        
        // Validate rating
        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new IllegalArgumentException("Rating phải từ 1 đến 5");
        }
        
        // Cập nhật
        review.setRating(request.getRating());
        review.setTitle(request.getTitle());
        review.setComment(request.getComment());
        review.setImages(request.getImages() != null ? request.getImages() : List.of());
        
        Review updatedReview = reviewRepository.save(review);
        
        // Cập nhật lại rating của product
        updateProductRating(review.getProductId());
        
        User user = userRepository.findById(userId).orElse(null);
        return convertToResponse(updatedReview, user);
    }
    
    /**
     * Xóa review
     */
    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy đánh giá"));
        
        // Kiểm tra quyền sở hữu
        if (!review.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Bạn không có quyền xóa đánh giá này");
        }
        
        Long productId = review.getProductId();
        reviewRepository.delete(review);
        
        // Cập nhật lại rating của product
        updateProductRating(productId);
    }
    
    /**
     * Lấy danh sách review theo sản phẩm
     */
    public Page<ReviewResponse> getReviewsByProduct(Long productId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByProductIdOrderByCreatedAtDesc(productId, pageable);
        return reviews.map(review -> {
            User user = userRepository.findById(review.getUserId()).orElse(null);
            return convertToResponse(review, user);
        });
    }
    
    /**
     * Lấy thống kê review của sản phẩm
     */
    public ReviewStatistics getReviewStatistics(Long productId) {
        Long totalReviews = reviewRepository.countByProductId(productId);
        Double avgRating = reviewRepository.calculateAverageRating(productId);
        List<Object[]> ratingCounts = reviewRepository.countByRatingAndProductId(productId);
        
        // Tạo map phân bố rating
        Map<Integer, Long> distribution = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            distribution.put(i, 0L);
        }
        
        for (Object[] result : ratingCounts) {
            Integer rating = (Integer) result[0];
            Long count = (Long) result[1];
            distribution.put(rating, count);
        }
        
        // Tính % verified purchase
        // (Cần query riêng nếu muốn chính xác)
        
        return ReviewStatistics.builder()
            .totalReviews(totalReviews)
            .averageRating(avgRating != null ? BigDecimal.valueOf(avgRating).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO)
            .ratingDistribution(distribution)
            .verifiedPurchasePercentage(0.0) // TODO: Tính từ DB
            .build();
    }
    
    /**
     * Đánh dấu review hữu ích
     */
    @Transactional
    public void markHelpful(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy đánh giá"));
        
        review.setHelpful(review.getHelpful() + 1);
        reviewRepository.save(review);
    }
    
    /**
     * Đánh dấu review không hữu ích
     */
    @Transactional
    public void markUnhelpful(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy đánh giá"));
        
        review.setUnhelpful(review.getUnhelpful() + 1);
        reviewRepository.save(review);
    }
    
    /**
     * Kiểm tra user đã mua sản phẩm này chưa
     */
    private boolean checkVerifiedPurchase(Long productId, Long userId) {
        // Kiểm tra trong bảng orders xem user có đơn hàng chứa sản phẩm này và đã hoàn thành không
        List<Order> orders = orderRepository.findByUser_IdAndStatusOrderByCreatedAtDesc(userId, Order.OrderStatus.COMPLETED);
        
        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                if (item.getProduct().getId().equals(productId)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Cập nhật rating và reviewCount của product
     */
    private void updateProductRating(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        
        Long reviewCount = reviewRepository.countByProductId(productId);
        Double avgRating = reviewRepository.calculateAverageRating(productId);
        
        product.setReviewCount(reviewCount.intValue());
        product.setRating(avgRating != null ? BigDecimal.valueOf(avgRating).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
        
        productRepository.save(product);
    }
    
    /**
     * Convert Review entity sang ReviewResponse DTO
     */
    private ReviewResponse convertToResponse(Review review, User user) {
        return ReviewResponse.builder()
            .id(review.getId())
            .productId(review.getProductId())
            .userId(review.getUserId())
            .userName(user != null ? user.getFullName() : "Unknown")
            .userAvatar(null) // TODO: Thêm avatar field trong User entity
            .rating(review.getRating())
            .title(review.getTitle())
            .comment(review.getComment())
            .images(review.getImages())
            .verifiedPurchase(review.getVerifiedPurchase())
            .helpful(review.getHelpful())
            .unhelpful(review.getUnhelpful())
            .isEdited(review.getIsEdited())
            .createdAt(review.getCreatedAt())
            .updatedAt(review.getUpdatedAt())
            .build();
    }
}
