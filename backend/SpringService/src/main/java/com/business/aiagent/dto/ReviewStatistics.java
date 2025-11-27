package com.business.aiagent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewStatistics {
    
    private Long totalReviews;
    
    private BigDecimal averageRating;
    
    @Builder.Default
    private Map<Integer, Long> ratingDistribution = new HashMap<>(); // {5: 100, 4: 50, 3: 20, 2: 5, 1: 2}
    
    private Double verifiedPurchasePercentage;
}
