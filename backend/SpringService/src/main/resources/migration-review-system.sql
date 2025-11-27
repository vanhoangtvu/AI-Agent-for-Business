-- Migration: Tạo bảng reviews và review_images
-- Date: 2025-11-15

-- Tạo bảng reviews
CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    title VARCHAR(200),
    comment TEXT NOT NULL,
    verified_purchase BOOLEAN DEFAULT FALSE,
    helpful INT DEFAULT 0,
    unhelpful INT DEFAULT 0,
    is_edited BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    
    INDEX idx_product_id (product_id),
    INDEX idx_user_id (user_id),
    INDEX idx_created_at (created_at),
    INDEX idx_rating (rating),
    
    CONSTRAINT unique_user_product_review UNIQUE (product_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tạo bảng review_images (relationship: one review -> many images)
CREATE TABLE IF NOT EXISTS review_images (
    review_id BIGINT NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    
    FOREIGN KEY (review_id) REFERENCES reviews(id) ON DELETE CASCADE,
    
    INDEX idx_review_id (review_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Thêm dữ liệu test (10 reviews cho các sản phẩm)
INSERT INTO reviews (product_id, user_id, rating, title, comment, verified_purchase, helpful, unhelpful, created_at) VALUES
-- iPhone 15 Pro Max (product_id = 1)
(1, 2, 5, 'Sản phẩm tuyệt vời!', 'iPhone 15 Pro Max chất lượng rất tốt, màn hình đẹp, camera sắc nét. Giao hàng nhanh, đóng gói cẩn thận. Rất hài lòng với sản phẩm này!', TRUE, 15, 2, DATE_SUB(NOW(), INTERVAL 5 DAY)),
(1, 3, 4, 'Tốt nhưng hơi đắt', 'Sản phẩm ok, cấu hình mạnh mẽ nhưng giá hơi cao. Nên mua khi có khuyến mãi.', TRUE, 8, 1, DATE_SUB(NOW(), INTERVAL 10 DAY)),

-- Samsung Galaxy S24 Ultra (product_id = 2)
(2, 2, 5, 'Camera đỉnh cao', 'S Pen rất hữu ích cho công việc. Camera zoom 100x rất ấn tượng, màn hình đẹp lung linh!', TRUE, 12, 1, DATE_SUB(NOW(), INTERVAL 3 DAY)),
(2, 3, 4, 'Sản phẩm tốt', 'Máy chạy mượt, thiết kế đẹp. Nhưng hơi nặng so với các dòng khác.', TRUE, 6, 0, DATE_SUB(NOW(), INTERVAL 7 DAY)),

-- MacBook Pro M3 (product_id = 3)
(3, 3, 5, 'Laptop tốt nhất cho developer', 'Mình là developer và đây là chiếc laptop hoàn hảo. Compile code cực nhanh, pin trâu cả ngày không cần sạc!', TRUE, 25, 1, DATE_SUB(NOW(), INTERVAL 2 DAY)),
(3, 2, 5, 'Xứng đáng từng đồng', 'Màn hình Retina đẹp xuất sắc, bàn phím gõ êm, trackpad lớn và nhạy. Hoàn hảo cho công việc!', TRUE, 18, 0, DATE_SUB(NOW(), INTERVAL 8 DAY)),

-- iPad Pro 12.9 inch (product_id = 4)
(4, 2, 4, 'Tuyệt vời cho vẽ vời và giải trí', 'Apple Pencil rất mượt, màn hình lớn và đẹp. Nhưng giá hơi cao nếu chỉ dùng để giải trí.', TRUE, 10, 2, DATE_SUB(NOW(), INTERVAL 4 DAY)),

-- AirPods Pro Gen 2 (product_id = 5)
(5, 3, 5, 'Chống ồn tuyệt vời', 'Chất lượng âm thanh tốt, chống ồn hiệu quả. Pin lâu, kết nối ổn định với iPhone!', TRUE, 14, 0, DATE_SUB(NOW(), INTERVAL 6 DAY)),
(5, 2, 4, 'Sản phẩm ok', 'Tai nghe nhẹ, đeo thoải mái cả ngày. Chống ồn tốt nhưng không xuất sắc bằng Sony.', TRUE, 7, 1, DATE_SUB(NOW(), INTERVAL 12 DAY));

-- Thêm hình ảnh cho reviews
INSERT INTO review_images (review_id, image_url) VALUES
-- Images cho review 1 (iPhone 15 Pro Max)
(1, 'https://cdn.tgdd.vn/Products/Images/42/305658/iphone-15-pro-max-blue-thumbnew-600x600.jpg'),
(1, 'https://cdn.tgdd.vn/Products/Images/42/305658/iphone-15-pro-max-1-1.jpg'),
(1, 'https://cdn.tgdd.vn/Products/Images/42/305658/iphone-15-pro-max-2.jpg'),

-- Images cho review 2 (iPhone 15 Pro Max)
(2, 'https://cdn.tgdd.vn/Products/Images/42/305658/iphone-15-pro-max-black-thumbnew-600x600.jpg'),

-- Images cho review 3 (Samsung Galaxy S24 Ultra)
(3, 'https://cdn.tgdd.vn/Products/Images/42/307174/samsung-galaxy-s24-ultra-grey-thumbnew-600x600.jpg'),
(3, 'https://cdn.tgdd.vn/Products/Images/42/307174/samsung-galaxy-s24-ultra-1.jpg'),

-- Images cho review 5 (MacBook Pro M3)
(5, 'https://cdn.tgdd.vn/Products/Images/44/309016/macbook-pro-14-inch-m3-2023-16gb-512gb-thumb-600x600.jpg'),

-- Images cho review 8 (AirPods Pro Gen 2)
(8, 'https://cdn.tgdd.vn/Products/Images/54/289780/tai-nghe-bluetooth-airpods-pro-2nd-gen-usbc-charge-apple-thumb-1-600x600.jpg');

-- Verify data
SELECT 
    r.id,
    r.product_id,
    p.name AS product_name,
    r.user_id,
    u.username,
    r.rating,
    r.title,
    r.verified_purchase,
    r.helpful,
    r.created_at
FROM reviews r
JOIN products p ON r.product_id = p.id
JOIN users u ON r.user_id = u.id
ORDER BY r.created_at DESC;
