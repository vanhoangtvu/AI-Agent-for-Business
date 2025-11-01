# 🔐 Roles & Permissions

## 👥 Các Vai Trò Trong Hệ Thống

### 1. 👑 ADMIN - Quản Trị Viên
**Mô tả:** Quản trị viên hệ thống, có quyền cao nhất

**Quyền hạn:**
- ✅ Quản lý toàn bộ hệ thống
- ✅ Quản lý tất cả users (CRUD)
- ✅ Quản lý tất cả businesses
- ✅ Xem tất cả dữ liệu trong hệ thống
- ✅ Cấu hình hệ thống
- ✅ Xem logs và audit trails
- ✅ Quản lý tài liệu của mọi doanh nghiệp
- ✅ Xem báo cáo tổng hợp
- ✅ Phân tích chiến lược
- ✅ Chat với AI

**API Endpoints:**
- ✅ `/api/admin/**` - Tất cả admin endpoints
- ✅ `/api/users/**` - Quản lý users
- ✅ `/api/business/**` - Quản lý businesses
- ✅ `/api/documents/**` - Tất cả operations
- ✅ `/api/chat/**` - Chat
- ✅ `/api/strategic/**` - Strategic analysis

---

### 2. 🏢 BUSINESS - Doanh Nghiệp
**Mô tả:** Chủ doanh nghiệp hoặc nhân viên quản lý

**Quyền hạn:**
- ✅ Quản lý tài liệu của doanh nghiệp mình
  - Upload documents
  - Edit/Delete own documents
  - Organize into categories
  - Manage tags
- ✅ Quản lý khách hàng của mình
  - Xem danh sách customers
  - Xem lịch sử chat của customers
  - Assign customers
- ✅ Xem báo cáo và phân tích
  - Business metrics
  - Customer insights
  - Strategic analysis
- ✅ Cấu hình chatbot
  - Customize responses
  - Set up knowledge base
  - Configure AI settings
- ✅ Chat với AI
- ✅ Quản lý nhân viên (nếu có sub-roles)

**API Endpoints:**
- ✅ `/api/business/**` - Business management
- ✅ `/api/documents/**` - Document management (own business)
- ✅ `/api/chat/**` - Chat
- ✅ `/api/strategic/**` - Strategic analysis
- ❌ `/api/admin/**` - NO ACCESS
- ❌ `/api/users/**` - NO ACCESS (except own profile)

**Use Cases:**
- 📊 CEO/Manager muốn xem báo cáo kinh doanh
- 📁 Nhân viên upload tài liệu sản phẩm
- 💬 Quản lý trả lời câu hỏi của khách hàng
- 🤖 Cấu hình chatbot cho business

---

### 3. 👤 CUSTOMER - Khách Hàng
**Mô tả:** Khách hàng cuối sử dụng dịch vụ của doanh nghiệp

**Quyền hạn:**
- ✅ Chat với AI chatbot
  - Hỏi về sản phẩm/dịch vụ
  - Được hỗ trợ tự động
  - Lịch sử chat của mình
- ✅ Xem tài liệu được chia sẻ
  - Documents shared by business
  - Public knowledge base
  - Read-only access
- ✅ Xem và cập nhật thông tin cá nhân
  - Profile
  - Settings
  - Chat history
- ❌ KHÔNG thể upload documents
- ❌ KHÔNG thể xem báo cáo
- ❌ KHÔNG thể quản lý users

**API Endpoints:**
- ✅ `/api/chat/**` - Chat with AI
- ✅ `/api/documents` (GET only) - View shared documents
- ✅ `/api/users/me` - Own profile
- ❌ `/api/admin/**` - NO ACCESS
- ❌ `/api/business/**` - NO ACCESS
- ❌ `/api/strategic/**` - NO ACCESS
- ❌ `/api/documents/upload` - NO ACCESS
- ❌ `/api/users/**` - NO ACCESS

**Use Cases:**
- 💬 Khách hàng hỏi về giá sản phẩm
- 📖 Xem catalog sản phẩm
- 🔍 Tìm kiếm thông tin
- 📱 Chat support 24/7

---

## 📊 Bảng So Sánh Quyền

| Chức Năng | ADMIN | BUSINESS | CUSTOMER |
|-----------|-------|----------|----------|
| **User Management** |
| Xem tất cả users | ✅ | ❌ | ❌ |
| Tạo/Sửa/Xóa users | ✅ | ❌ | ❌ |
| Xem profile của mình | ✅ | ✅ | ✅ |
| Sửa profile của mình | ✅ | ✅ | ✅ |
| **Document Management** |
| Upload documents | ✅ | ✅ | ❌ |
| Edit/Delete own docs | ✅ | ✅ | ❌ |
| View all documents | ✅ | ✅ (own) | ✅ (shared) |
| Manage categories/tags | ✅ | ✅ | ❌ |
| **Chat & AI** |
| Chat with AI | ✅ | ✅ | ✅ |
| View own chat history | ✅ | ✅ | ✅ |
| View others' chat | ✅ | ✅ (customers) | ❌ |
| Configure chatbot | ✅ | ✅ | ❌ |
| **Analytics & Reports** |
| Strategic analysis | ✅ | ✅ | ❌ |
| Business metrics | ✅ | ✅ (own) | ❌ |
| Customer insights | ✅ | ✅ (own) | ❌ |
| System-wide reports | ✅ | ❌ | ❌ |
| **Administration** |
| System configuration | ✅ | ❌ | ❌ |
| Manage businesses | ✅ | ❌ | ❌ |
| View audit logs | ✅ | ❌ | ❌ |

---

## 🔐 Security Rules

### Authentication
- Tất cả endpoints (trừ `/api/auth/**` và public endpoints) cần JWT token
- Token expires sau 24 giờ
- Refresh token expires sau 7 ngày

### Authorization
```java
// ADMIN only
@PreAuthorize("hasRole('ADMIN')")

// ADMIN or BUSINESS
@PreAuthorize("hasAnyRole('ADMIN', 'BUSINESS')")

// All authenticated users
@PreAuthorize("isAuthenticated()")

// Custom: Owner or ADMIN
@PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(#userId)")
```

---

## 📝 Đăng Ký Theo Role

### Customer (Default)
```bash
curl -X POST http://localhost:8100/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "customer1",
    "email": "customer@example.com",
    "password": "123456",
    "fullName": "Khách Hàng A",
    "role": "CUSTOMER"
  }'
```

### Business
```bash
curl -X POST http://localhost:8100/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "business1",
    "email": "business@example.com",
    "password": "123456",
    "fullName": "Công ty ABC",
    "companyName": "Công ty ABC",
    "role": "BUSINESS"
  }'
```

### Admin (Chỉ nên tạo manually hoặc qua seed data)
```bash
curl -X POST http://localhost:8100/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "email": "admin@aiagent.com",
    "password": "admin123",
    "fullName": "Administrator",
    "role": "ADMIN"
  }'
```

---

## 🎯 Use Case Examples

### 1. Doanh Nghiệp Setup Bot
```
1. BUSINESS đăng ký account
2. Upload tài liệu về sản phẩm/dịch vụ
3. Cấu hình chatbot
4. Chia sẻ link cho customers
```

### 2. Customer Tương Tác
```
1. CUSTOMER truy cập website
2. Chat với AI chatbot
3. Nhận thông tin về sản phẩm
4. Xem tài liệu liên quan
```

### 3. Admin Giám Sát
```
1. ADMIN login
2. Xem tất cả businesses
3. Xem metrics tổng hợp
4. Quản lý system configuration
```

---

## 🔄 Role Upgrade Flow

### Customer → Business
```
1. Customer request upgrade
2. Admin review & approve
3. Update user role to BUSINESS
4. Grant business permissions
```

### Migration Script (Future)
```sql
UPDATE users 
SET roles = 'BUSINESS' 
WHERE id = ? AND roles = 'CUSTOMER';
```

---

## 🛡️ Best Practices

1. **Principle of Least Privilege**
   - Mặc định: CUSTOMER role
   - Upgrade khi cần thiết

2. **ADMIN Account Security**
   - Strong password required
   - 2FA enabled (future)
   - Limit số lượng ADMIN accounts

3. **Audit Logging**
   - Log tất cả ADMIN actions
   - Log BUSINESS document changes
   - Track role changes

4. **Data Isolation**
   - BUSINESS chỉ xem data của mình
   - CUSTOMER chỉ xem shared data
   - ADMIN có full access

---

**Sinh viên:** Nguyễn Văn Hoàng - MSSV: 110122078  
**Trường:** Đại Học Trà Vinh

