# 🗄️ Thiết kế Cơ sở dữ liệu - AI Agent for Business

## 📋 Mục lục

- [Tổng quan](#-tổng-quan)
- [ERD Diagram](#-erd-diagram)
- [Chi tiết các bảng](#-chi-tiết-các-bảng)
- [Relationships](#-relationships)
- [Indexes & Performance](#-indexes--performance)
- [Sample Data](#-sample-data)
- [Migration Scripts](#-migration-scripts)

---

## 🎯 Tổng quan

### Database Engine
- **Primary Database**: MySQL 8.0+
- **Vector Database**: PostgreSQL with pgvector / Pinecone
- **Cache**: Redis 7.0+

### Số lượng bảng
- **Core Tables**: 15 bảng (đã tối ưu từ 18)
- **Junction Tables**: 4 bảng (đã tối ưu từ 5)
- **Audit Tables**: 3 bảng
- **Tổng**: 22 bảng (đã giảm 4 bảng, -15%)

> **Đã tối ưu hóa:**
> - ❌ Bỏ `message_intents` (AI tự phân tích)
> - ❌ Gộp `chat_attachments` → `messages.metadata`
> - ❌ Gộp `vector_db_refs` → `document_chunks`
> - ❌ Gộp `zalo_conversations` → `conversations.channel_metadata`

---

## 📊 ERD Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                        USER MANAGEMENT                           │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────┐         ┌──────────────┐        ┌──────────────┐ │
│  │  users   │────1:1──│  businesses  │───1:N──│  employees   │ │
│  └──────────┘         └──────────────┘        └──────────────┘ │
│       │                      │                                   │
│       │                      │                                   │
│       │                      └──────┬──────────────────────┐    │
│       │                             │                       │    │
└───────┼─────────────────────────────┼───────────────────────┼───┘
        │                             │                       │
┌───────┼─────────────────────────────┼───────────────────────┼───┐
│       │                CRM & SALES  │                       │    │
├───────┼─────────────────────────────┼───────────────────────┼───┤
│       │                             │                       │    │
│  ┌────▼────────┐        ┌───────────▼────┐    ┌────────────▼─┐ │
│  │  customers  │───┐    │    products     │    │   orders     │ │
│  └─────────────┘   │    └─────────────────┘    └──────────────┘ │
│       │            │             │                      │         │
│       │            │             │                      │         │
│       │            └─────────────┼──────────────────────┤         │
│       │                          │                      │         │
│  ┌────▼──────────┐  ┌───────────▼────────┐  ┌─────────▼──────┐ │
│  │customer_tags  │  │ product_categories  │  │  order_items   │ │
│  └───────────────┘  └─────────────────────┘  └────────────────┘ │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                    CHAT & MESSAGING                              │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────┐       ┌───────────────────────────────┐      │
│  │conversations │───1:N─│   messages                    │      │
│  └──────────────┘       │   + metadata.attachments JSON │      │
│         │               └───────────────────────────────┘      │
│         │                                                       │
│         └── channel_metadata JSON                              │
│              (gộp zalo_conversations)                           │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                    ZALO INTEGRATION                              │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────────┐       ┌────────────────────┐             │
│  │  zalo_oa_configs │       │  zalo_personal     │             │
│  │                  │       │  _sessions         │             │
│  └──────────────────┘       └────────────────────┘             │
│                                                                  │
│  (zalo_conversations đã gộp vào conversations.channel_metadata) │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                    RAG & DOCUMENTS                               │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────┐       ┌─────────────────────────────────┐    │
│  │  documents   │───1:N─│  document_chunks                │    │
│  └──────────────┘       │  + vector_db_type, vector_id    │    │
│                         │  (gộp vector_db_refs)           │    │
│                         └─────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                    ANALYTICS & REPORTS                           │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────┐       ┌───────────────┐    ┌──────────────┐  │
│  │ai_insights  │       │ analytics_logs │    │ notifications│  │
│  └──────────────┘       └───────────────┘    └──────────────┘  │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                    AUDIT & SYSTEM                                │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────┐       ┌───────────────┐    ┌──────────────┐  │
│  │ audit_logs   │       │ system_configs │   │ api_keys     │  │
│  └──────────────┘       └───────────────┘    └──────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

---

## 📝 Chi tiết các bảng

### 1. 👥 USER MANAGEMENT

#### **1.1. users** - Bảng người dùng chính
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'BUSINESS', 'CUSTOMER') NOT NULL,
    
    -- Profile info
    full_name VARCHAR(255),
    phone VARCHAR(20),
    avatar_url VARCHAR(500),
    
    -- Status
    is_active BOOLEAN DEFAULT TRUE,
    is_email_verified BOOLEAN DEFAULT FALSE,
    email_verified_at TIMESTAMP NULL,
    
    -- Security
    last_login_at TIMESTAMP NULL,
    last_login_ip VARCHAR(45),
    failed_login_attempts INT DEFAULT 0,
    locked_until TIMESTAMP NULL,
    
    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    
    -- Indexes
    INDEX idx_email (email),
    INDEX idx_role (role),
    INDEX idx_created_at (created_at),
    INDEX idx_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### **1.2. businesses** - Bảng doanh nghiệp
```sql
CREATE TABLE businesses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL UNIQUE,
    
    -- Business info
    business_name VARCHAR(255) NOT NULL,
    business_type ENUM('RETAIL', 'ECOMMERCE', 'SERVICE', 'FOOD', 'OTHER') DEFAULT 'OTHER',
    tax_code VARCHAR(50),
    
    -- Contact info
    email VARCHAR(255),
    phone VARCHAR(20),
    website VARCHAR(255),
    address TEXT,
    city VARCHAR(100),
    district VARCHAR(100),
    ward VARCHAR(100),
    
    -- Business settings
    timezone VARCHAR(50) DEFAULT 'Asia/Ho_Chi_Minh',
    currency VARCHAR(10) DEFAULT 'VND',
    language VARCHAR(10) DEFAULT 'vi',
    
    -- Subscription
    plan ENUM('FREE', 'BASIC', 'PRO', 'ENTERPRISE') DEFAULT 'FREE',
    plan_started_at TIMESTAMP NULL,
    plan_expires_at TIMESTAMP NULL,
    
    -- Limits (based on plan)
    max_customers INT DEFAULT 100,
    max_products INT DEFAULT 50,
    max_messages_per_month INT DEFAULT 1000,
    max_documents INT DEFAULT 10,
    
    -- Status
    is_active BOOLEAN DEFAULT TRUE,
    is_verified BOOLEAN DEFAULT FALSE,
    verified_at TIMESTAMP NULL,
    
    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    
    INDEX idx_business_name (business_name),
    INDEX idx_plan (plan),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### **1.3. employees** - Bảng nhân viên
```sql
CREATE TABLE employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    business_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    
    -- Employee info
    position VARCHAR(100),
    department VARCHAR(100),
    
    -- Permissions
    permissions JSON, -- ['manage_customers', 'manage_products', 'view_analytics']
    
    -- Status
    is_active BOOLEAN DEFAULT TRUE,
    hired_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    
    UNIQUE KEY unique_business_user (business_id, user_id),
    INDEX idx_business (business_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

---

### 2. 🛒 CRM & SALES

#### **2.1. customers** - Bảng khách hàng
```sql
CREATE TABLE customers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    business_id BIGINT NOT NULL,
    user_id BIGINT NULL, -- Nếu khách hàng đăng ký tài khoản
    
    -- Customer info
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20),
    gender ENUM('MALE', 'FEMALE', 'OTHER') NULL,
    date_of_birth DATE NULL,
    
    -- Address
    address TEXT,
    city VARCHAR(100),
    district VARCHAR(100),
    ward VARCHAR(100),
    
    -- Segmentation (RFM Analysis)
    rfm_score INT DEFAULT 0, -- 0-100
    segment ENUM('VIP', 'LOYAL', 'POTENTIAL', 'AT_RISK', 'LOST', 'NEW') DEFAULT 'NEW',
    
    -- Purchase behavior
    total_orders INT DEFAULT 0,
    total_spent DECIMAL(15,2) DEFAULT 0,
    average_order_value DECIMAL(15,2) DEFAULT 0,
    last_order_at TIMESTAMP NULL,
    last_contact_at TIMESTAMP NULL,
    
    -- Engagement
    lifetime_value DECIMAL(15,2) DEFAULT 0,
    churn_probability DECIMAL(5,2) DEFAULT 0, -- 0-100%
    
    -- Notes
    notes TEXT,
    tags VARCHAR(255), -- 'vip,frequent-buyer,discount-hunter'
    
    -- Source
    source ENUM('WEBSITE', 'ZALO_OA', 'ZALO_PERSONAL', 'MANUAL', 'IMPORT') DEFAULT 'MANUAL',
    source_id VARCHAR(255), -- ID from external system
    
    -- Status
    is_active BOOLEAN DEFAULT TRUE,
    
    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    
    FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    
    INDEX idx_business (business_id),
    INDEX idx_email (email),
    INDEX idx_phone (phone),
    INDEX idx_segment (segment),
    INDEX idx_source (source, source_id),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### **2.2. customer_tags** - Bảng tag khách hàng
```sql
CREATE TABLE customer_tags (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    business_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    color VARCHAR(7), -- Hex color #FF5733
    description TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE,
    UNIQUE KEY unique_business_tag (business_id, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### **2.3. product_categories** - Danh mục sản phẩm
```sql
CREATE TABLE product_categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    business_id BIGINT NOT NULL,
    parent_id BIGINT NULL,
    
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    description TEXT,
    image_url VARCHAR(500),
    
    display_order INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE,
    FOREIGN KEY (parent_id) REFERENCES product_categories(id) ON DELETE SET NULL,
    
    INDEX idx_business (business_id),
    INDEX idx_parent (parent_id),
    INDEX idx_slug (slug)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### **2.4. products** - Bảng sản phẩm
```sql
CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    business_id BIGINT NOT NULL,
    category_id BIGINT NULL,
    
    -- Product info
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    sku VARCHAR(100),
    barcode VARCHAR(100),
    
    description TEXT,
    short_description TEXT,
    
    -- Pricing
    price DECIMAL(15,2) NOT NULL,
    cost_price DECIMAL(15,2),
    compare_at_price DECIMAL(15,2), -- Original price for showing discount
    
    -- Inventory
    stock_quantity INT DEFAULT 0,
    low_stock_threshold INT DEFAULT 10,
    track_inventory BOOLEAN DEFAULT TRUE,
    
    -- Product variants
    has_variants BOOLEAN DEFAULT FALSE,
    
    -- Media
    image_url VARCHAR(500),
    images JSON, -- ['url1', 'url2', 'url3']
    
    -- SEO
    meta_title VARCHAR(255),
    meta_description TEXT,
    meta_keywords TEXT,
    
    -- Statistics
    total_sold INT DEFAULT 0,
    view_count INT DEFAULT 0,
    
    -- Status
    is_active BOOLEAN DEFAULT TRUE,
    is_featured BOOLEAN DEFAULT FALSE,
    
    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    
    FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES product_categories(id) ON DELETE SET NULL,
    
    INDEX idx_business (business_id),
    INDEX idx_category (category_id),
    INDEX idx_sku (sku),
    INDEX idx_slug (slug),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### **2.5. orders** - Bảng đơn hàng
```sql
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    business_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    
    -- Order info
    order_code VARCHAR(50) NOT NULL UNIQUE,
    status ENUM(
        'PENDING',
        'CONFIRMED', 
        'PROCESSING',
        'SHIPPING',
        'DELIVERED',
        'COMPLETED',
        'CANCELLED',
        'REFUNDED'
    ) DEFAULT 'PENDING',
    
    -- Financial
    subtotal DECIMAL(15,2) NOT NULL,
    discount_amount DECIMAL(15,2) DEFAULT 0,
    shipping_fee DECIMAL(15,2) DEFAULT 0,
    tax_amount DECIMAL(15,2) DEFAULT 0,
    total_amount DECIMAL(15,2) NOT NULL,
    
    -- Payment
    payment_method ENUM('COD', 'BANK_TRANSFER', 'VNPAY', 'MOMO', 'ZALO_PAY') NOT NULL,
    payment_status ENUM('UNPAID', 'PARTIAL', 'PAID', 'REFUNDED') DEFAULT 'UNPAID',
    paid_at TIMESTAMP NULL,
    
    -- Shipping
    shipping_address TEXT,
    shipping_city VARCHAR(100),
    shipping_district VARCHAR(100),
    shipping_ward VARCHAR(100),
    shipping_phone VARCHAR(20),
    shipping_name VARCHAR(255),
    
    shipping_carrier VARCHAR(100),
    tracking_number VARCHAR(100),
    
    -- Notes
    customer_note TEXT,
    admin_note TEXT,
    
    -- Source
    source ENUM('WEBSITE', 'ZALO_OA', 'ZALO_PERSONAL', 'MANUAL') DEFAULT 'MANUAL',
    
    -- Timestamps
    confirmed_at TIMESTAMP NULL,
    shipped_at TIMESTAMP NULL,
    delivered_at TIMESTAMP NULL,
    completed_at TIMESTAMP NULL,
    cancelled_at TIMESTAMP NULL,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE RESTRICT,
    
    INDEX idx_business (business_id),
    INDEX idx_customer (customer_id),
    INDEX idx_order_code (order_code),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### **2.6. order_items** - Chi tiết đơn hàng
```sql
CREATE TABLE order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    
    -- Product info snapshot (at time of order)
    product_name VARCHAR(255) NOT NULL,
    product_sku VARCHAR(100),
    product_image VARCHAR(500),
    
    -- Pricing
    price DECIMAL(15,2) NOT NULL,
    quantity INT NOT NULL,
    discount_amount DECIMAL(15,2) DEFAULT 0,
    subtotal DECIMAL(15,2) NOT NULL,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT,
    
    INDEX idx_order (order_id),
    INDEX idx_product (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

---

### 3. 💬 CHAT & MESSAGING

#### **3.1. conversations** - Cuộc hội thoại
```sql
CREATE TABLE conversations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    business_id BIGINT NOT NULL,
    customer_id BIGINT NULL,
    
    -- Conversation info
    channel ENUM('WEBSITE', 'ZALO_OA', 'ZALO_PERSONAL') NOT NULL,
    channel_conversation_id VARCHAR(255), -- ID from external system
    
    -- Channel metadata (đã gộp zalo_conversations vào đây)
    channel_metadata JSON, -- {zalo_user_id, zalo_user_name, zalo_avatar_url, source, ip_address, user_agent, etc}
    
    -- Status
    status ENUM('ACTIVE', 'RESOLVED', 'ARCHIVED') DEFAULT 'ACTIVE',
    
    -- Assignment
    assigned_to BIGINT NULL, -- user_id of employee
    assigned_at TIMESTAMP NULL,
    
    -- AI Mode
    ai_mode ENUM('AUTO', 'SUGGESTION', 'NOTIFICATION', 'DISABLED') DEFAULT 'AUTO',
    ai_handled_count INT DEFAULT 0,
    human_handled_count INT DEFAULT 0,
    
    -- Metadata
    last_message_at TIMESTAMP NULL,
    last_message_preview TEXT,
    unread_count INT DEFAULT 0,
    
    -- Tags
    tags VARCHAR(255),
    priority ENUM('LOW', 'NORMAL', 'HIGH', 'URGENT') DEFAULT 'NORMAL',
    
    -- Timestamps
    started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP NULL,
    archived_at TIMESTAMP NULL,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE SET NULL,
    FOREIGN KEY (assigned_to) REFERENCES users(id) ON DELETE SET NULL,
    
    INDEX idx_business (business_id),
    INDEX idx_customer (customer_id),
    INDEX idx_channel (channel, channel_conversation_id),
    INDEX idx_status (status),
    INDEX idx_last_message_at (last_message_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### **3.2. messages** - Tin nhắn
```sql
CREATE TABLE messages (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    conversation_id BIGINT NOT NULL,
    
    -- Sender
    sender_type ENUM('CUSTOMER', 'BUSINESS', 'AI') NOT NULL,
    sender_id BIGINT NULL, -- user_id or customer_id
    
    -- Message content
    message_type ENUM('TEXT', 'IMAGE', 'FILE', 'AUDIO', 'VIDEO', 'LOCATION', 'STICKER') DEFAULT 'TEXT',
    content TEXT NOT NULL,
    
    -- Metadata (đã gộp chat_attachments vào đây)
    metadata JSON, -- {attachments: [{file_name, file_type, file_size, file_url, thumbnail_url}], mentions: [], etc}
    
    -- AI related
    is_ai_generated BOOLEAN DEFAULT FALSE,
    ai_confidence DECIMAL(5,2), -- 0-100%
    ai_intent VARCHAR(100),
    
    -- RAG related
    used_rag BOOLEAN DEFAULT FALSE,
    rag_sources JSON, -- [{document_id: 1, chunk_id: 5, similarity: 0.89}]
    
    -- Status
    is_read BOOLEAN DEFAULT FALSE,
    read_at TIMESTAMP NULL,
    
    -- External reference
    external_message_id VARCHAR(255), -- ID from Zalo, etc
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (conversation_id) REFERENCES conversations(id) ON DELETE CASCADE,
    
    INDEX idx_conversation (conversation_id),
    INDEX idx_created_at (created_at),
    INDEX idx_sender (sender_type, sender_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### **3.3. ~~message_intents~~ - ĐÃ BỎ**
> ❌ **Bảng đã bị loại bỏ để tối ưu hóa**
> - Lý do: AI (Gemini) tự động phân tích intent, không cần pre-defined intents
> - Intent được lưu trong `messages.ai_intent` field

#### **3.4. ~~chat_attachments~~ - ĐÃ GỘP**
> ❌ **Bảng đã được gộp vào `messages.metadata`**
> - Lý do: Giảm complexity, 1 message có ít attachments
> - Attachments được lưu trong JSON:
> ```json
> {
>   "attachments": [
>     {
>       "file_name": "image.jpg",
>       "file_type": "image/jpeg",
>       "file_size": 1024000,
>       "file_url": "https://storage.example.com/files/image.jpg",
>       "thumbnail_url": "https://storage.example.com/thumbnails/image_thumb.jpg"
>     }
>   ]
> }
> ```

---

### 4. 📱 ZALO INTEGRATION

#### **4.1. zalo_oa_configs** - Cấu hình Zalo OA
```sql
CREATE TABLE zalo_oa_configs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    business_id BIGINT NOT NULL UNIQUE,
    
    -- Zalo OA credentials
    oa_id VARCHAR(100) NOT NULL,
    app_id VARCHAR(100) NOT NULL,
    app_secret VARCHAR(255) NOT NULL,
    access_token TEXT,
    refresh_token TEXT,
    
    -- Token expiry
    access_token_expires_at TIMESTAMP NULL,
    refresh_token_expires_at TIMESTAMP NULL,
    
    -- Webhook
    webhook_url VARCHAR(500),
    webhook_secret VARCHAR(255),
    
    -- Settings
    auto_reply_enabled BOOLEAN DEFAULT TRUE,
    welcome_message TEXT,
    
    -- Status
    is_active BOOLEAN DEFAULT TRUE,
    is_verified BOOLEAN DEFAULT FALSE,
    last_sync_at TIMESTAMP NULL,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### **4.2. zalo_personal_sessions** - Phiên Zalo cá nhân
```sql
CREATE TABLE zalo_personal_sessions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    business_id BIGINT NOT NULL,
    
    -- Session info
    session_id VARCHAR(255) NOT NULL UNIQUE,
    qr_code TEXT, -- Base64 QR code
    qr_code_expires_at TIMESTAMP,
    
    -- Account info (after login)
    zalo_user_id VARCHAR(100),
    zalo_display_name VARCHAR(255),
    zalo_avatar_url VARCHAR(500),
    zalo_phone VARCHAR(20),
    
    -- Session status
    status ENUM('WAITING', 'LOGGED_IN', 'EXPIRED', 'LOGGED_OUT') DEFAULT 'WAITING',
    
    -- Token/Credentials (encrypted)
    credentials TEXT, -- Encrypted JSON
    
    -- AI Settings
    ai_mode ENUM('AUTO', 'SUGGESTION', 'NOTIFICATION') DEFAULT 'SUGGESTION',
    auto_reply_enabled BOOLEAN DEFAULT FALSE,
    
    -- Working hours
    working_hours JSON, -- {start: '08:00', end: '22:00', days: [1,2,3,4,5]}
    
    -- Security
    login_ip VARCHAR(45),
    last_activity_at TIMESTAMP NULL,
    
    -- Session timeout (7 days default)
    expires_at TIMESTAMP,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE,
    INDEX idx_business (business_id),
    INDEX idx_session (session_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### **4.3. ~~zalo_conversations~~ - ĐÃ GỘP**
> ❌ **Bảng đã được gộp vào `conversations.channel_metadata`**
> - Lý do: 1:1 relationship với conversations, tránh join không cần thiết
> - Dữ liệu Zalo được lưu trong JSON:
> ```json
> {
>   "zalo_user_id": "1234567890",
>   "zalo_user_name": "Nguyễn Văn A",
>   "zalo_avatar_url": "https://zalo.me/avatar/...",
>   "source": "ZALO_PERSONAL",
>   "source_config_id": 5,
>   "external_conversation_id": "conv_12345",
>   "last_sync_at": "2025-10-18T10:30:00Z"
> }
> ```
> - Query example:
> ```sql
> SELECT * FROM conversations 
> WHERE channel = 'ZALO_PERSONAL'
> AND JSON_EXTRACT(channel_metadata, '$.zalo_user_id') = '1234567890';
> ```

---

### 5. 📚 RAG & DOCUMENTS

#### **5.1. documents** - Tài liệu
```sql
CREATE TABLE documents (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    business_id BIGINT NOT NULL,
    
    -- Document info
    name VARCHAR(255) NOT NULL,
    original_filename VARCHAR(255),
    file_type VARCHAR(50), -- 'PDF', 'DOCX', 'TXT', 'CSV'
    file_size BIGINT,
    file_url VARCHAR(500),
    
    -- Category
    category ENUM('POLICY', 'FAQ', 'GUIDE', 'PRODUCT', 'OTHER') DEFAULT 'OTHER',
    
    -- Processing status
    status ENUM('UPLOADING', 'PROCESSING', 'COMPLETED', 'FAILED') DEFAULT 'UPLOADING',
    
    -- Processing info
    total_chunks INT DEFAULT 0,
    total_tokens INT DEFAULT 0,
    processing_started_at TIMESTAMP NULL,
    processing_completed_at TIMESTAMP NULL,
    processing_error TEXT,
    
    -- Metadata
    metadata JSON, -- {author, created_date, etc}
    
    -- Usage statistics
    query_count INT DEFAULT 0,
    last_queried_at TIMESTAMP NULL,
    
    is_active BOOLEAN DEFAULT TRUE,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    
    FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE,
    
    INDEX idx_business (business_id),
    INDEX idx_status (status),
    INDEX idx_category (category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### **5.2. document_chunks** - Phân đoạn tài liệu
```sql
CREATE TABLE document_chunks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    document_id BIGINT NOT NULL,
    business_id BIGINT NOT NULL,
    
    -- Chunk info
    chunk_index INT NOT NULL,
    content TEXT NOT NULL,
    
    -- Token info
    token_count INT,
    
    -- Embedding & Vector DB (đã gộp vector_db_refs vào đây)
    vector_db_type ENUM('PINECONE', 'PGVECTOR', 'WEAVIATE') NOT NULL DEFAULT 'PINECONE',
    vector_id VARCHAR(255), -- ID in Pinecone/pgvector
    vector_namespace VARCHAR(255), -- For Pinecone
    embedding_dimension INT DEFAULT 768,
    embedding_model VARCHAR(100) DEFAULT 'text-embedding-004',
    
    -- Metadata
    page_number INT,
    section_title VARCHAR(255),
    metadata JSON,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (document_id) REFERENCES documents(id) ON DELETE CASCADE,
    FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE,
    
    INDEX idx_document (document_id),
    INDEX idx_business (business_id),
    INDEX idx_vector (vector_db_type, vector_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### **5.3. ~~vector_db_refs~~ - ĐÃ GỘP**
> ❌ **Bảng đã được gộp vào `document_chunks`**
> - Lý do: 1:1 relationship, mỗi chunk có 1 vector
> - Thông tin vector DB được lưu trực tiếp trong document_chunks:
>   - `vector_db_type`: PINECONE, PGVECTOR, WEAVIATE
>   - `vector_id`: ID trong vector database
>   - `vector_namespace`: Namespace (cho Pinecone)
>   - `embedding_dimension`: 768 (Gemini text-embedding-004)
> - Đơn giản hóa queries, không cần join thêm bảng

---

### 6. 📊 ANALYTICS & AI

#### **6.1. ai_insights** - Kết quả phân tích AI
```sql
CREATE TABLE ai_insights (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    business_id BIGINT NOT NULL,
    
    -- Insight info
    insight_type ENUM(
        'CUSTOMER_SEGMENTATION',
        'SALES_FORECAST',
        'PRODUCT_RECOMMENDATION',
        'MARKETING_STRATEGY',
        'INVENTORY_PLANNING',
        'CHURN_PREDICTION'
    ) NOT NULL,
    
    title VARCHAR(255) NOT NULL,
    description TEXT,
    
    -- Analysis result
    result JSON NOT NULL, -- Complete analysis data
    
    -- Recommendations
    recommendations JSON, -- [{ priority, action, reason }]
    
    -- Metrics
    confidence_score DECIMAL(5,2), -- 0-100%
    impact_score DECIMAL(5,2), -- 0-100%
    
    -- Period
    period_start DATE,
    period_end DATE,
    
    -- Status
    status ENUM('PENDING', 'COMPLETED', 'FAILED') DEFAULT 'PENDING',
    
    -- AI Model info
    model_version VARCHAR(50),
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE,
    
    INDEX idx_business (business_id),
    INDEX idx_type (insight_type),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### **6.2. analytics_logs** - Log phân tích
```sql
CREATE TABLE analytics_logs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    business_id BIGINT NOT NULL,
    
    -- Metric type
    metric_type VARCHAR(100) NOT NULL, -- 'daily_revenue', 'customer_count', etc
    
    -- Date
    date DATE NOT NULL,
    
    -- Values
    value DECIMAL(15,2),
    value_json JSON, -- For complex metrics
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE,
    
    UNIQUE KEY unique_metric_date (business_id, metric_type, date),
    INDEX idx_business_date (business_id, date),
    INDEX idx_metric (metric_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

---

### 7. 🔔 NOTIFICATIONS

#### **7.1. notifications** - Thông báo
```sql
CREATE TABLE notifications (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    business_id BIGINT NULL,
    
    -- Notification info
    type VARCHAR(100) NOT NULL, -- 'new_order', 'new_message', 'low_stock'
    title VARCHAR(255) NOT NULL,
    message TEXT,
    
    -- Data
    data JSON, -- Additional data
    
    -- Action
    action_url VARCHAR(500),
    action_label VARCHAR(100),
    
    -- Status
    is_read BOOLEAN DEFAULT FALSE,
    read_at TIMESTAMP NULL,
    
    -- Priority
    priority ENUM('LOW', 'NORMAL', 'HIGH', 'URGENT') DEFAULT 'NORMAL',
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE,
    
    INDEX idx_user (user_id),
    INDEX idx_business (business_id),
    INDEX idx_is_read (is_read),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

---

### 8. 🔐 SYSTEM & AUDIT

#### **8.1. audit_logs** - Log hệ thống
```sql
CREATE TABLE audit_logs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    
    -- Actor
    user_id BIGINT NULL,
    business_id BIGINT NULL,
    
    -- Action
    action VARCHAR(100) NOT NULL, -- 'CREATE', 'UPDATE', 'DELETE', 'LOGIN'
    entity_type VARCHAR(100), -- 'product', 'order', 'customer'
    entity_id BIGINT,
    
    -- Changes
    old_values JSON,
    new_values JSON,
    
    -- Request info
    ip_address VARCHAR(45),
    user_agent TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE SET NULL,
    
    INDEX idx_user (user_id),
    INDEX idx_business (business_id),
    INDEX idx_entity (entity_type, entity_id),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### **8.2. system_configs** - Cấu hình hệ thống
```sql
CREATE TABLE system_configs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    config_key VARCHAR(100) NOT NULL UNIQUE,
    config_value TEXT,
    config_type ENUM('STRING', 'NUMBER', 'BOOLEAN', 'JSON') DEFAULT 'STRING',
    
    description TEXT,
    is_public BOOLEAN DEFAULT FALSE,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### **8.3. api_keys** - API Keys
```sql
CREATE TABLE api_keys (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    business_id BIGINT NOT NULL,
    
    -- Key info
    key_name VARCHAR(255) NOT NULL,
    api_key VARCHAR(255) NOT NULL UNIQUE,
    api_secret VARCHAR(255),
    
    -- Permissions
    permissions JSON, -- ['read:customers', 'write:orders']
    
    -- Rate limiting
    rate_limit INT DEFAULT 1000, -- requests per hour
    
    -- Status
    is_active BOOLEAN DEFAULT TRUE,
    expires_at TIMESTAMP NULL,
    
    -- Usage
    last_used_at TIMESTAMP NULL,
    usage_count BIGINT DEFAULT 0,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE,
    
    INDEX idx_business (business_id),
    INDEX idx_api_key (api_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

---

## 🔗 Relationships Summary

```
users (1) -------- (1) businesses
businesses (1) ---- (*) employees
businesses (1) ---- (*) customers
businesses (1) ---- (*) products
businesses (1) ---- (*) orders
customers (1) ----- (*) orders
orders (1) -------- (*) order_items
products (1) ------- (*) order_items
businesses (1) ---- (*) conversations
conversations (1) - (*) messages
businesses (1) ---- (*) documents
documents (1) ----- (*) document_chunks
businesses (1) ---- (*) zalo_oa_configs
businesses (1) ---- (*) zalo_personal_sessions
```

---

## ⚡ Indexes & Performance

### **Critical Indexes:**

```sql
-- Users
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role_active ON users(role, is_active);

-- Customers
CREATE INDEX idx_customers_business_segment ON customers(business_id, segment);
CREATE INDEX idx_customers_phone_email ON customers(phone, email);

-- Products
CREATE INDEX idx_products_business_active ON products(business_id, is_active);
CREATE INDEX idx_products_sku ON products(sku);

-- Orders
CREATE INDEX idx_orders_business_status_date ON orders(business_id, status, created_at DESC);
CREATE INDEX idx_orders_customer_date ON orders(customer_id, created_at DESC);

-- Messages
CREATE INDEX idx_messages_conversation_date ON messages(conversation_id, created_at DESC);
CREATE INDEX idx_messages_ai_generated ON messages(is_ai_generated, created_at DESC);

-- Documents
CREATE INDEX idx_documents_business_status ON documents(business_id, status);
```

### **Performance Tips:**

1. **Partitioning cho bảng lớn:**
```sql
-- Partition messages by month
ALTER TABLE messages 
PARTITION BY RANGE (YEAR(created_at)*100 + MONTH(created_at)) (
    PARTITION p202501 VALUES LESS THAN (202502),
    PARTITION p202502 VALUES LESS THAN (202503),
    ...
);
```

2. **Archiving old data:**
```sql
-- Archive messages older than 1 year
CREATE TABLE messages_archive LIKE messages;
-- Move old data periodically
```

3. **Read replicas for analytics:**
- Use separate read replica for heavy analytics queries
- Keep write operations on primary

---

## 📦 Sample Data

### **Insert Admin User:**
```sql
INSERT INTO users (email, password_hash, role, full_name, is_active, is_email_verified)
VALUES ('admin@aiagent.system', '$2a$10$...', 'ADMIN', 'System Admin', TRUE, TRUE);
```

### **Insert Demo Business:**
```sql
INSERT INTO businesses (user_id, business_name, business_type, phone, plan)
VALUES (1, 'Cửa hàng ABC', 'RETAIL', '0901234567', 'PRO');
```

### **Insert Demo Customer:**
```sql
INSERT INTO customers (business_id, full_name, email, phone, segment)
VALUES (1, 'Nguyễn Văn A', 'nguyenvana@example.com', '0912345678', 'VIP');
```

### **Insert Demo Product:**
```sql
INSERT INTO products (business_id, category_id, name, slug, price, stock_quantity)
VALUES (1, 1, 'iPhone 15 Pro Max 256GB', 'iphone-15-pro-max-256gb', 29990000, 50);
```

---

## 🚀 Migration Scripts

### **Initial Setup:**

```bash
# 1. Create database
mysql -u root -p -e "CREATE DATABASE ai_agent_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 2. Run migrations
mysql -u root -p ai_agent_db < migrations/001_create_users_table.sql
mysql -u root -p ai_agent_db < migrations/002_create_businesses_table.sql
# ... etc
```

### **Migration File Structure:**
```
migrations/
├── 001_create_users_table.sql
├── 002_create_businesses_table.sql
├── 003_create_customers_table.sql
├── 004_create_products_table.sql
├── 005_create_orders_table.sql
├── 006_create_conversations_table.sql
├── 007_create_documents_table.sql
├── 008_create_zalo_tables.sql
├── 009_create_analytics_tables.sql
└── 010_create_indexes.sql
```

---

## 🔄 Backup Strategy

### **Daily Backup:**
```bash
#!/bin/bash
# Backup script
DATE=$(date +%Y%m%d_%H%M%S)
mysqldump -u root -p ai_agent_db > backup_$DATE.sql
gzip backup_$DATE.sql
```

### **Point-in-Time Recovery:**
- Enable binary logging
- Keep binlogs for 7 days
- Test restore procedures monthly

---

## 📈 Monitoring Queries

### **Database Size:**
```sql
SELECT 
    table_schema AS 'Database',
    SUM(data_length + index_length) / 1024 / 1024 AS 'Size (MB)'
FROM information_schema.TABLES
WHERE table_schema = 'ai_agent_db'
GROUP BY table_schema;
```

### **Table Row Counts:**
```sql
SELECT 
    table_name,
    table_rows
FROM information_schema.TABLES
WHERE table_schema = 'ai_agent_db'
ORDER BY table_rows DESC;
```

### **Slow Queries:**
```sql
-- Enable slow query log
SET GLOBAL slow_query_log = 'ON';
SET GLOBAL long_query_time = 2;
```

---

## ✅ Checklist

- [x] User Management (RBAC)
- [x] Business Management
- [x] Customer CRM
- [x] Product Management
- [x] Order Processing
- [x] Chat & Messaging
- [x] Zalo Integration (OA + Personal)
- [x] RAG & Documents
- [x] AI Analytics
- [x] Notifications
- [x] Audit Logs
- [x] API Keys
- [x] Indexes & Performance
- [x] Sample Data
- [x] Migration Scripts
- [x] Backup Strategy

---

**Tổng kết:**
- ✅ **22 bảng** (đã tối ưu từ 26 bảng, giảm 15%)
- ✅ Hỗ trợ 3 roles (Admin, Business, Customer)
- ✅ Đầy đủ indexes và constraints
- ✅ Tích hợp Zalo OA + Personal
- ✅ RAG với vector database
- ✅ Analytics và AI insights
- ✅ Audit logs đầy đủ
- ✅ Performance optimization

---

## 🚀 Tối ưu hóa Database

### **Đã áp dụng:**

#### ❌ **Đã bỏ/gộp 4 bảng:**

1. **`message_intents`** → BỎ
   - AI (Gemini) tự động phân tích intent
   - Intent lưu trong `messages.ai_intent`

2. **`chat_attachments`** → GỘP vào `messages.metadata`
   - Giảm complexity, ít join
   - Attachments dạng JSON array

3. **`vector_db_refs`** → GỘP vào `document_chunks`
   - 1:1 relationship
   - Thông tin vector lưu trực tiếp trong chunks

4. **`zalo_conversations`** → GỘP vào `conversations.channel_metadata`
   - Tránh join không cần thiết
   - Linh hoạt cho nhiều channels

### **Lợi ích:**
- ✅ Giảm 15% số lượng bảng (26 → 22)
- ✅ Giảm số lượng JOIN queries
- ✅ Tăng performance
- ✅ Dễ maintain và mở rộng
- ✅ MySQL 8.0+ JSON indexes hỗ trợ tốt

### **Trade-offs:**
- ⚠️ JSON fields phức tạp hơn SQL thuần
- ⚠️ Cần MySQL 8.0+ cho JSON indexes
- ⚠️ Query JSON cần làm quen

---

**Kết luận:** Database đã được tối ưu hóa hợp lý, giảm complexity nhưng vẫn đầy đủ tính năng! 🎯

