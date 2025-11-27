-- Script SQL để thêm dữ liệu mẫu sản phẩm điện tử với nhiều phân loại
-- Sử dụng để test hệ thống

-- 1. Thêm Categories (Danh mục)
INSERT INTO categories (id, name, description, slug, parent_id, is_active, created_at, updated_at) VALUES
(1, 'Điện Thoại & Phụ Kiện', 'Điện thoại di động và phụ kiện', 'dien-thoai-phu-kien', NULL, true, NOW(), NOW()),
(2, 'Laptop & Máy Tính', 'Laptop, PC và linh kiện', 'laptop-may-tinh', NULL, true, NOW(), NOW()),
(3, 'Tablet', 'Máy tính bảng các loại', 'tablet', NULL, true, NOW(), NOW()),
(4, 'Tai Nghe & Loa', 'Thiết bị âm thanh', 'tai-nghe-loa', NULL, true, NOW(), NOW()),
(5, 'Smartwatch', 'Đồng hồ thông minh', 'smartwatch', NULL, true, NOW(), NOW()),
(6, 'Camera & Máy Ảnh', 'Camera an ninh và máy ảnh', 'camera-may-anh', NULL, true, NOW(), NOW()),
(7, 'Gaming', 'Thiết bị gaming', 'gaming', NULL, true, NOW(), NOW()),
(8, 'Phụ Kiện', 'Các loại phụ kiện điện tử', 'phu-kien', NULL, true, NOW(), NOW())
ON DUPLICATE KEY UPDATE updated_at = NOW();

-- 2. Thêm Products (Sản phẩm)

-- Điện thoại
INSERT INTO products (name, description, price, compare_at_price, stock_quantity, sku, category_id, is_active, is_featured, created_by, created_at, updated_at) VALUES
-- iPhone Series
('iPhone 15 Pro Max 256GB', 'iPhone 15 Pro Max với chip A17 Pro, camera 48MP, màn hình Dynamic Island', 33990000, 35990000, 50, 'IP15PM-256-NAT', 1, true, true, 1, NOW(), NOW()),
('iPhone 15 Pro 128GB', 'iPhone 15 Pro thiết kế titan, camera chuyên nghiệp', 28990000, 30990000, 45, 'IP15P-128-BLK', 1, true, true, 1, NOW(), NOW()),
('iPhone 15 Plus 128GB', 'iPhone 15 Plus màn hình lớn 6.7 inch', 24990000, 26990000, 60, 'IP15PL-128-BLU', 1, true, false, 1, NOW(), NOW()),
('iPhone 14 Pro Max 256GB', 'iPhone 14 Pro Max giá tốt, còn bảo hành', 29990000, 32990000, 30, 'IP14PM-256-PUR', 1, true, true, 1, NOW(), NOW()),

-- Samsung Series
('Samsung Galaxy S24 Ultra 12GB/256GB', 'Galaxy S24 Ultra với S Pen tích hợp, camera 200MP', 31990000, 33990000, 40, 'SS24U-256-BLK', 1, true, true, 1, NOW(), NOW()),
('Samsung Galaxy S24 Plus 12GB/256GB', 'Galaxy S24+ màn hình AMOLED 6.7 inch', 25990000, 27990000, 55, 'SS24P-256-VIO', 1, true, false, 1, NOW(), NOW()),
('Samsung Galaxy Z Fold5 12GB/256GB', 'Điện thoại gập cao cấp Galaxy Z Fold5', 40990000, 44990000, 20, 'SZFO5-256-BLK', 1, true, true, 1, NOW(), NOW()),
('Samsung Galaxy Z Flip5 8GB/256GB', 'Galaxy Z Flip5 thiết kế gập độc đáo', 24990000, 26990000, 25, 'SZFL5-256-CRM', 1, true, true, 1, NOW(), NOW()),
('Samsung Galaxy A54 8GB/128GB', 'Galaxy A54 5G giá tốt, camera chất lượng', 10490000, 11990000, 100, 'SA54-128-BLK', 1, true, false, 1, NOW(), NOW()),
('Samsung Galaxy A34 8GB/128GB', 'Galaxy A34 5G phân khúc tầm trung', 7990000, 8990000, 120, 'SA34-128-VIO', 1, true, false, 1, NOW(), NOW()),

-- Xiaomi Series
('Xiaomi 14 Ultra 16GB/512GB', 'Xiaomi 14 Ultra camera Leica đỉnh cao', 27990000, 29990000, 35, 'MI14U-512-BLK', 1, true, true, 1, NOW(), NOW()),
('Xiaomi 13T Pro 12GB/256GB', 'Xiaomi 13T Pro sạc nhanh 120W', 12990000, 14990000, 70, 'MI13TP-256-BLU', 1, true, false, 1, NOW(), NOW()),
('Redmi Note 13 Pro+ 8GB/256GB', 'Redmi Note 13 Pro+ camera 200MP', 8990000, 9990000, 90, 'RN13PP-256-BLK', 1, true, false, 1, NOW(), NOW()),

-- Laptop
('MacBook Pro 14" M3 Pro 18GB/512GB', 'MacBook Pro 14 inch với chip M3 Pro mạnh mẽ', 54990000, 57990000, 25, 'MBP14-M3P-512', 2, true, true, 1, NOW(), NOW()),
('MacBook Air 15" M2 8GB/256GB', 'MacBook Air 15 inch siêu mỏng nhẹ', 34990000, 36990000, 40, 'MBA15-M2-256', 2, true, true, 1, NOW(), NOW()),
('Dell XPS 13 Plus i7/16GB/512GB', 'Dell XPS 13 Plus thiết kế hiện đại', 42990000, 45990000, 20, 'DXPS13-I7-512', 2, true, true, 1, NOW(), NOW()),
('Asus ROG Zephyrus G14 RTX4060', 'Laptop gaming ROG Zephyrus mỏng nhẹ', 45990000, 48990000, 15, 'ROG-G14-4060', 2, true, true, 1, NOW(), NOW()),
('HP Pavilion 15 i5/8GB/512GB', 'HP Pavilion 15 đa năng văn phòng', 15990000, 17990000, 50, 'HP15-I5-512', 2, true, false, 1, NOW(), NOW()),
('Lenovo ThinkPad X1 Carbon Gen 11', 'ThinkPad X1 Carbon doanh nhân cao cấp', 48990000, 51990000, 18, 'TPX1-G11-512', 2, true, true, 1, NOW(), NOW()),

-- Tablet
('iPad Pro 12.9" M2 WiFi 128GB', 'iPad Pro 12.9 inch với chip M2', 29990000, 31990000, 30, 'IPP129-M2-128W', 3, true, true, 1, NOW(), NOW()),
('iPad Air 5 M1 WiFi 64GB', 'iPad Air 5 với chip M1 mạnh mẽ', 16990000, 18990000, 45, 'IPA5-M1-64W', 3, true, false, 1, NOW(), NOW()),
('Samsung Galaxy Tab S9 Ultra 12GB/256GB', 'Galaxy Tab S9 Ultra màn hình khổng lồ', 28990000, 30990000, 25, 'SGTS9U-256-BLK', 3, true, true, 1, NOW(), NOW()),
('Xiaomi Pad 6 8GB/256GB', 'Xiaomi Pad 6 giải trí đa phương tiện', 8990000, 9990000, 60, 'MIPAD6-256-GRY', 3, true, false, 1, NOW(), NOW()),

-- Tai nghe & Loa
('AirPods Pro 2 (USB-C)', 'AirPods Pro thế hệ 2 với chip H2', 6490000, 6990000, 100, 'APP2-USBC-WHT', 4, true, true, 1, NOW(), NOW()),
('Sony WH-1000XM5 Wireless', 'Tai nghe chống ồn cao cấp Sony', 8990000, 9990000, 50, 'SONY-XM5-BLK', 4, true, true, 1, NOW(), NOW()),
('Samsung Galaxy Buds2 Pro', 'Tai nghe Galaxy Buds2 Pro ANC', 4490000, 4990000, 80, 'SGB2P-VIO', 4, true, false, 1, NOW(), NOW()),
('JBL Flip 6 Portable Speaker', 'Loa bluetooth JBL Flip 6 chống nước', 3290000, 3590000, 70, 'JBL-FL6-BLK', 4, true, false, 1, NOW(), NOW()),
('Harman Kardon Onyx Studio 8', 'Loa bluetooth cao cấp Harman Kardon', 6990000, 7490000, 40, 'HK-OS8-BLK', 4, true, true, 1, NOW(), NOW()),

-- Smartwatch
('Apple Watch Series 9 GPS 45mm', 'Apple Watch Series 9 màn hình sáng đỉnh', 11490000, 11990000, 60, 'AWS9-45-MID', 5, true, true, 1, NOW(), NOW()),
('Apple Watch Ultra 2 GPS+Cellular', 'Apple Watch Ultra 2 cho thể thao mạo hiểm', 21990000, 22990000, 30, 'AWSU2-49-TIT', 5, true, true, 1, NOW(), NOW()),
('Samsung Galaxy Watch6 Classic 47mm', 'Galaxy Watch6 Classic thiết kế sang trọng', 9990000, 10990000, 45, 'SGW6C-47-BLK', 5, true, false, 1, NOW(), NOW()),
('Xiaomi Watch S3 46mm', 'Xiaomi Watch S3 pin 15 ngày', 4490000, 4990000, 70, 'MIWS3-46-BLK', 5, true, false, 1, NOW(), NOW()),

-- Camera
('GoPro Hero 12 Black', 'Action camera GoPro Hero 12 Black', 10990000, 11990000, 35, 'GP-H12-BLK', 6, true, true, 1, NOW(), NOW()),
('DJI Osmo Action 4', 'Camera hành trình DJI Osmo Action 4', 9990000, 10990000, 40, 'DJI-OA4-BLK', 6, true, false, 1, NOW(), NOW()),
('Camera IP WiFi Xiaomi C400', 'Camera giám sát Xiaomi 2K', 890000, 990000, 150, 'MIC400-WHT', 6, true, false, 1, NOW(), NOW()),

-- Gaming
('PlayStation 5 Slim Digital Edition', 'PS5 Slim phiên bản Digital', 12990000, 13990000, 20, 'PS5S-DIG', 7, true, true, 1, NOW(), NOW()),
('Xbox Series X 1TB', 'Xbox Series X console 1TB', 13990000, 14990000, 18, 'XBSX-1TB', 7, true, true, 1, NOW(), NOW()),
('Nintendo Switch OLED', 'Nintendo Switch phiên bản OLED', 8990000, 9490000, 50, 'NSW-OLED-WHT', 7, true, true, 1, NOW(), NOW()),
('Logitech G Pro X Superlight 2', 'Chuột gaming không dây siêu nhẹ', 3590000, 3990000, 60, 'LG-GPXS2-BLK', 7, true, false, 1, NOW(), NOW()),

-- Phụ kiện
('Anker PowerBank 20000mAh 65W', 'Sạc dự phòng Anker 20000mAh sạc nhanh', 1490000, 1690000, 200, 'ANK-PB20K-BLK', 8, true, false, 1, NOW(), NOW()),
('Baseus GaN 100W 4-Port Charger', 'Sạc nhanh GaN 100W 4 cổng', 890000, 990000, 150, 'BAS-GAN100-WHT', 8, true, false, 1, NOW(), NOW()),
('Apple USB-C to Lightning Cable 1m', 'Cáp sạc Apple chính hãng', 490000, 590000, 300, 'APL-USBC-WHT', 8, true, false, 1, NOW(), NOW()),
('SanDisk Extreme Pro 512GB microSD', 'Thẻ nhớ SanDisk 512GB tốc độ cao', 2390000, 2690000, 100, 'SD-EXP512-RED', 8, true, false, 1, NOW(), NOW());

-- 3. Cập nhật product_images (thêm ảnh cho sản phẩm)
-- Thêm ảnh chính cho mỗi sản phẩm
INSERT INTO product_images (product_id, image_url) 
SELECT 
  id,
  CONCAT('https://via.placeholder.com/600x600/0ea5e9/ffffff?text=', REPLACE(name, ' ', '+'))
FROM products;

-- Thêm ảnh phụ (2-3 ảnh cho mỗi sản phẩm)
INSERT INTO product_images (product_id, image_url) 
SELECT 
  id,
  CONCAT('https://via.placeholder.com/600x600/3b82f6/ffffff?text=', REPLACE(name, ' ', '+'), '+2')
FROM products;

INSERT INTO product_images (product_id, image_url) 
SELECT 
  id,
  CONCAT('https://via.placeholder.com/600x600/8b5cf6/ffffff?text=', REPLACE(name, ' ', '+'), '+3')
FROM products;

-- 4. Cập nhật một số thông số bổ sung
UPDATE products SET 
  weight = FLOOR(RAND() * 1000 + 100),
  manufacturer = CASE 
    WHEN name LIKE '%iPhone%' OR name LIKE '%MacBook%' OR name LIKE '%iPad%' OR name LIKE '%Apple%' THEN 'Apple Inc.'
    WHEN name LIKE '%Samsung%' OR name LIKE '%Galaxy%' THEN 'Samsung Electronics'
    WHEN name LIKE '%Xiaomi%' OR name LIKE '%Redmi%' OR name LIKE '%Mi%' THEN 'Xiaomi Corporation'
    WHEN name LIKE '%Dell%' THEN 'Dell Technologies'
    WHEN name LIKE '%HP%' THEN 'HP Inc.'
    WHEN name LIKE '%Asus%' OR name LIKE '%ROG%' THEN 'ASUSTeK Computer'
    WHEN name LIKE '%Lenovo%' THEN 'Lenovo Group'
    WHEN name LIKE '%Sony%' THEN 'Sony Corporation'
    WHEN name LIKE '%JBL%' THEN 'JBL (Harman International)'
    WHEN name LIKE '%GoPro%' THEN 'GoPro Inc.'
    WHEN name LIKE '%PlayStation%' THEN 'Sony Interactive Entertainment'
    WHEN name LIKE '%Xbox%' THEN 'Microsoft Corporation'
    WHEN name LIKE '%Nintendo%' THEN 'Nintendo Co., Ltd.'
    ELSE 'Generic Manufacturer'
  END,
  origin = CASE 
    WHEN name LIKE '%iPhone%' OR name LIKE '%MacBook%' OR name LIKE '%iPad%' OR name LIKE '%Apple%' THEN 'USA'
    WHEN name LIKE '%Dell%' OR name LIKE '%HP%' OR name LIKE '%GoPro%' OR name LIKE '%Xbox%' THEN 'USA'
    WHEN name LIKE '%Samsung%' OR name LIKE '%Galaxy%' THEN 'South Korea'
    WHEN name LIKE '%Xiaomi%' OR name LIKE '%Redmi%' OR name LIKE '%Mi%' OR name LIKE '%Lenovo%' THEN 'China'
    WHEN name LIKE '%Sony%' OR name LIKE '%PlayStation%' OR name LIKE '%Nintendo%' THEN 'Japan'
    WHEN name LIKE '%Asus%' OR name LIKE '%ROG%' THEN 'Taiwan'
    ELSE 'Vietnam'
  END
WHERE manufacturer IS NULL OR manufacturer = '';

-- 5. Kiểm tra kết quả
SELECT 
  c.name as category,
  COUNT(p.id) as total_products,
  SUM(p.stock_quantity) as total_stock,
  MIN(p.price) as min_price,
  MAX(p.price) as max_price
FROM categories c
LEFT JOIN products p ON c.id = p.category_id
GROUP BY c.id, c.name
ORDER BY c.id;

-- Hiển thị một vài sản phẩm mẫu
SELECT 
  p.id,
  p.name,
  p.price,
  p.stock_quantity,
  c.name as category,
  p.manufacturer,
  p.is_featured
FROM products p
LEFT JOIN categories c ON p.category_id = c.id
ORDER BY p.created_at DESC
LIMIT 20;
