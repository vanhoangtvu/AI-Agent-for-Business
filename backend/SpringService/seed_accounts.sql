-- ========================================
-- AI Agent for Business - Test Accounts
-- Seed data cho testing
-- ========================================
-- Sinh viên: Nguyễn Văn Hoàng - MSSV: 110122078
-- Trường: Đại Học Trà Vinh
-- ========================================

USE AI_Agent_db;

-- Xóa data cũ (optional - chỉ dùng khi muốn reset)
-- DELETE FROM user_roles;
-- DELETE FROM users;

-- ========================================
-- 1. ADMIN ACCOUNTS
-- ========================================

-- Admin Principal
-- Email: admin@aiagent.com
-- Password: admin123 (BCrypt encoded)
INSERT INTO users (username, email, password, full_name, phone_number, active, email_verified, created_at, updated_at)
VALUES (
    'admin',
    'admin@aiagent.com',
    '$2a$10$5qE1L8K9YGXxQH3fZhZ0M.vQ8WZ5oQ5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5', -- admin123
    'Administrator',
    '0900000001',
    true,
    true,
    NOW(),
    NOW()
) ON DUPLICATE KEY UPDATE username=username;

INSERT INTO user_roles (user_id, role)
SELECT id, 'ADMIN' FROM users WHERE email = 'admin@aiagent.com'
ON DUPLICATE KEY UPDATE role=role;

-- Student Admin
-- Email: 110122078@st.tvu.edu.vn
-- Password: hoang123 (BCrypt encoded)
INSERT INTO users (username, email, password, full_name, phone_number, active, email_verified, created_at, updated_at)
VALUES (
    'hoangvan',
    '110122078@st.tvu.edu.vn',
    '$2a$10$YOUR_BCRYPT_HASH_HERE', -- hoang123
    'Nguyễn Văn Hoàng',
    '0123456789',
    true,
    true,
    NOW(),
    NOW()
) ON DUPLICATE KEY UPDATE username=username;

INSERT INTO user_roles (user_id, role)
SELECT id, 'ADMIN' FROM users WHERE email = '110122078@st.tvu.edu.vn'
ON DUPLICATE KEY UPDATE role=role;

-- ========================================
-- 2. BUSINESS ACCOUNTS
-- ========================================

-- Business Principal
-- Email: business@aiagent.com
-- Password: business123
INSERT INTO users (username, email, password, full_name, phone_number, active, email_verified, created_at, updated_at)
VALUES (
    'business',
    'business@aiagent.com',
    '$2a$10$YOUR_BCRYPT_HASH_HERE', -- business123
    'Công ty TNHH ABC',
    '0900000002',
    true,
    true,
    NOW(),
    NOW()
) ON DUPLICATE KEY UPDATE username=username;

INSERT INTO user_roles (user_id, role)
SELECT id, 'BUSINESS' FROM users WHERE email = 'business@aiagent.com'
ON DUPLICATE KEY UPDATE role=role;

-- Business 1
INSERT INTO users (username, email, password, full_name, phone_number, active, email_verified, created_at, updated_at)
VALUES (
    'business1',
    'business1@company.com',
    '$2a$10$YOUR_BCRYPT_HASH_HERE', -- business123
    'Công ty XYZ',
    '0901111111',
    true,
    true,
    NOW(),
    NOW()
) ON DUPLICATE KEY UPDATE username=username;

INSERT INTO user_roles (user_id, role)
SELECT id, 'BUSINESS' FROM users WHERE email = 'business1@company.com'
ON DUPLICATE KEY UPDATE role=role;

-- Business 2
INSERT INTO users (username, email, password, full_name, phone_number, active, email_verified, created_at, updated_at)
VALUES (
    'business2',
    'business2@company.com',
    '$2a$10$YOUR_BCRYPT_HASH_HERE', -- business123
    'Công ty 123',
    '0902222222',
    true,
    true,
    NOW(),
    NOW()
) ON DUPLICATE KEY UPDATE username=username;

INSERT INTO user_roles (user_id, role)
SELECT id, 'BUSINESS' FROM users WHERE email = 'business2@company.com'
ON DUPLICATE KEY UPDATE role=role;

-- ========================================
-- 3. CUSTOMER ACCOUNTS
-- ========================================

-- Customer Principal
-- Email: customer@aiagent.com
-- Password: customer123
INSERT INTO users (username, email, password, full_name, phone_number, active, email_verified, created_at, updated_at)
VALUES (
    'customer',
    'customer@aiagent.com',
    '$2a$10$YOUR_BCRYPT_HASH_HERE', -- customer123
    'Khách Hàng Test',
    '0900000003',
    true,
    true,
    NOW(),
    NOW()
) ON DUPLICATE KEY UPDATE username=username;

INSERT INTO user_roles (user_id, role)
SELECT id, 'CUSTOMER' FROM users WHERE email = 'customer@aiagent.com'
ON DUPLICATE KEY UPDATE role=role;

-- Customer 1
INSERT INTO users (username, email, password, full_name, phone_number, active, email_verified, created_at, updated_at)
VALUES (
    'customer1',
    'customer1@gmail.com',
    '$2a$10$YOUR_BCRYPT_HASH_HERE', -- customer123
    'Nguyễn Văn A',
    '0903333333',
    true,
    true,
    NOW(),
    NOW()
) ON DUPLICATE KEY UPDATE username=username;

INSERT INTO user_roles (user_id, role)
SELECT id, 'CUSTOMER' FROM users WHERE email = 'customer1@gmail.com'
ON DUPLICATE KEY UPDATE role=role;

-- Customer 2
INSERT INTO users (username, email, password, full_name, phone_number, active, email_verified, created_at, updated_at)
VALUES (
    'customer2',
    'customer2@gmail.com',
    '$2a$10$YOUR_BCRYPT_HASH_HERE', -- customer123
    'Trần Thị B',
    '0904444444',
    true,
    true,
    NOW(),
    NOW()
) ON DUPLICATE KEY UPDATE username=username;

INSERT INTO user_roles (user_id, role)
SELECT id, 'CUSTOMER' FROM users WHERE email = 'customer2@gmail.com'
ON DUPLICATE KEY UPDATE role=role;

-- Customer 3
INSERT INTO users (username, email, password, full_name, phone_number, active, email_verified, created_at, updated_at)
VALUES (
    'customer3',
    'customer3@gmail.com',
    '$2a$10$YOUR_BCRYPT_HASH_HERE', -- customer123
    'Lê Văn C',
    '0905555555',
    true,
    true,
    NOW(),
    NOW()
) ON DUPLICATE KEY UPDATE username=username;

INSERT INTO user_roles (user_id, role)
SELECT id, 'CUSTOMER' FROM users WHERE email = 'customer3@gmail.com'
ON DUPLICATE KEY UPDATE role=role;

-- ========================================
-- Verify Data
-- ========================================

SELECT 
    u.id,
    u.username,
    u.email,
    u.full_name,
    GROUP_CONCAT(ur.role) as roles,
    u.active,
    u.email_verified
FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
GROUP BY u.id
ORDER BY u.id;

-- ========================================
-- Summary
-- ========================================
-- Total users:
SELECT COUNT(*) as total_users FROM users;

-- Users by role:
SELECT role, COUNT(*) as count
FROM user_roles
GROUP BY role;

-- Note: Password hashes cần được generate bằng BCrypt
-- Sử dụng DataSeeder.java để tự động tạo accounts thay vì SQL script này

