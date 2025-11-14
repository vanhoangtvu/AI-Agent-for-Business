-- =====================================================
-- Migration Script: Role System Update
-- Date: 2025-11-15
-- Description: Cập nhật hệ thống phân quyền với 3 roles và permissions
-- =====================================================

-- 1. Xóa dữ liệu cũ trong bảng roles (nếu có)
DELETE FROM user_roles;
DELETE FROM role_permissions;
DELETE FROM roles;

-- 2. Insert 3 roles chính
INSERT INTO roles (id, name, description) VALUES
(1, 'ADMIN', 'Quản trị viên hệ thống - Toàn quyền quản lý'),
(2, 'BUSINESS', 'Chủ doanh nghiệp - Quản lý sản phẩm, đơn hàng, phân tích'),
(3, 'CUSTOMER', 'Khách hàng - Mua hàng và xem đơn hàng');

-- 3. Insert permissions cho ADMIN role (toàn quyền)
INSERT INTO role_permissions (role_id, permission) VALUES
-- User Management
(1, 'USER_READ'),
(1, 'USER_CREATE'),
(1, 'USER_UPDATE'),
(1, 'USER_DELETE'),

-- Product Management
(1, 'PRODUCT_READ'),
(1, 'PRODUCT_CREATE'),
(1, 'PRODUCT_UPDATE'),
(1, 'PRODUCT_DELETE'),
(1, 'PRODUCT_MANAGE_ALL'),

-- Category Management
(1, 'CATEGORY_READ'),
(1, 'CATEGORY_CREATE'),
(1, 'CATEGORY_UPDATE'),
(1, 'CATEGORY_DELETE'),

-- Order Management
(1, 'ORDER_READ'),
(1, 'ORDER_CREATE'),
(1, 'ORDER_UPDATE'),
(1, 'ORDER_DELETE'),
(1, 'ORDER_MANAGE_ALL'),

-- Document Management
(1, 'DOCUMENT_READ'),
(1, 'DOCUMENT_CREATE'),
(1, 'DOCUMENT_UPDATE'),
(1, 'DOCUMENT_DELETE'),
(1, 'DOCUMENT_MANAGE_ALL'),

-- Analytics & Reports
(1, 'ANALYTICS_VIEW'),
(1, 'ANALYTICS_VIEW_ALL'),
(1, 'REPORT_GENERATE'),
(1, 'REPORT_EXPORT'),

-- System
(1, 'SYSTEM_CONFIG'),
(1, 'SYSTEM_LOGS'),

-- Chat & AI
(1, 'CHAT_USE'),
(1, 'CHAT_VIEW_HISTORY'),

-- Role Management
(1, 'ROLE_READ'),
(1, 'ROLE_MANAGE');

-- 4. Insert permissions cho BUSINESS role
INSERT INTO role_permissions (role_id, permission) VALUES
-- Product Management (chỉ sản phẩm của mình)
(2, 'PRODUCT_READ'),
(2, 'PRODUCT_CREATE'),
(2, 'PRODUCT_UPDATE'),
(2, 'PRODUCT_DELETE'),

-- Category Management (read only)
(2, 'CATEGORY_READ'),

-- Order Management (đơn hàng sản phẩm của mình)
(2, 'ORDER_READ'),
(2, 'ORDER_UPDATE'),

-- Document Management (tài liệu của mình)
(2, 'DOCUMENT_READ'),
(2, 'DOCUMENT_CREATE'),
(2, 'DOCUMENT_UPDATE'),
(2, 'DOCUMENT_DELETE'),

-- Analytics & Reports (chỉ data của mình)
(2, 'ANALYTICS_VIEW'),
(2, 'REPORT_GENERATE'),
(2, 'REPORT_EXPORT'),

-- Chat & AI
(2, 'CHAT_USE'),
(2, 'CHAT_VIEW_HISTORY');

-- 5. Insert permissions cho CUSTOMER role
INSERT INTO role_permissions (role_id, permission) VALUES
-- Product (chỉ đọc)
(3, 'PRODUCT_READ'),
(3, 'CATEGORY_READ'),

-- Order (tạo và xem đơn hàng của mình)
(3, 'ORDER_READ'),
(3, 'ORDER_CREATE'),

-- Chat & AI
(3, 'CHAT_USE'),
(3, 'CHAT_VIEW_HISTORY');

-- 6. Thêm cột business info vào bảng users
ALTER TABLE users
ADD COLUMN IF NOT EXISTS business_name VARCHAR(200),
ADD COLUMN IF NOT EXISTS business_type VARCHAR(100),
ADD COLUMN IF NOT EXISTS business_description VARCHAR(500),
ADD COLUMN IF NOT EXISTS business_address VARCHAR(200),
ADD COLUMN IF NOT EXISTS business_phone VARCHAR(20),
ADD COLUMN IF NOT EXISTS business_email VARCHAR(100),
ADD COLUMN IF NOT EXISTS tax_code VARCHAR(50),
ADD COLUMN IF NOT EXISTS business_logo VARCHAR(200),
ADD COLUMN IF NOT EXISTS is_business_verified BOOLEAN DEFAULT FALSE;

-- 7. Thêm cột business_owner_id vào bảng products
ALTER TABLE products
ADD COLUMN IF NOT EXISTS business_owner_id BIGINT,
ADD CONSTRAINT fk_product_business_owner 
    FOREIGN KEY (business_owner_id) REFERENCES users(id) ON DELETE SET NULL;

CREATE INDEX IF NOT EXISTS idx_products_business_owner ON products(business_owner_id);

-- 8. Thêm cột business_owner_id vào bảng documents
ALTER TABLE documents
ADD COLUMN IF NOT EXISTS business_owner_id BIGINT,
ADD COLUMN IF NOT EXISTS document_type VARCHAR(50),
ADD CONSTRAINT fk_document_business_owner 
    FOREIGN KEY (business_owner_id) REFERENCES users(id) ON DELETE SET NULL;

CREATE INDEX IF NOT EXISTS idx_documents_business_owner ON documents(business_owner_id);

-- 9. Tạo admin user mặc định (nếu chưa có)
INSERT INTO users (username, email, password, full_name, active, created_at, updated_at)
VALUES (
    'admin',
    'admin@aiagent.com',
    '$2a$10$XptfskLsT1l/bTxnP8/p8ONHhV7lJLCDHK5/bj5sKq5HpqmU7TSsO', -- password: admin123
    'System Administrator',
    true,
    NOW(),
    NOW()
)
ON CONFLICT (username) DO NOTHING;

-- 10. Gán role ADMIN cho user admin
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, 1
FROM users u
WHERE u.username = 'admin'
ON CONFLICT DO NOTHING;

-- 11. Tạo business demo user (optional)
INSERT INTO users (username, email, password, full_name, active, business_name, business_type, created_at, updated_at)
VALUES (
    'business_demo',
    'business@demo.com',
    '$2a$10$XptfskLsT1l/bTxnP8/p8ONHhV7lJLCDHK5/bj5sKq5HpqmU7TSsO', -- password: admin123
    'Business Owner Demo',
    true,
    'Demo Corporation',
    'Retail',
    NOW(),
    NOW()
)
ON CONFLICT (username) DO NOTHING;

-- 12. Gán role BUSINESS cho business demo user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, 2
FROM users u
WHERE u.username = 'business_demo'
ON CONFLICT DO NOTHING;

-- 13. Tạo customer demo user (optional)
INSERT INTO users (username, email, password, full_name, active, created_at, updated_at)
VALUES (
    'customer_demo',
    'customer@demo.com',
    '$2a$10$XptfskLsT1l/bTxnP8/p8ONHhV7lJLCDHK5/bj5sKq5HpqmU7TSsO', -- password: admin123
    'Customer Demo',
    true,
    NOW(),
    NOW()
)
ON CONFLICT (username) DO NOTHING;

-- 14. Gán role CUSTOMER cho customer demo user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, 3
FROM users u
WHERE u.username = 'customer_demo'
ON CONFLICT DO NOTHING;

-- 15. Indexes để tối ưu performance
CREATE INDEX IF NOT EXISTS idx_users_business_name ON users(business_name);
CREATE INDEX IF NOT EXISTS idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX IF NOT EXISTS idx_user_roles_role_id ON user_roles(role_id);

-- =====================================================
-- Migration Complete
-- =====================================================
-- Admin user: admin / admin123
-- Business user: business_demo / admin123
-- Customer user: customer_demo / admin123
-- =====================================================
