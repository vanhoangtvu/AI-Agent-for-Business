# 🔐 Hệ Thống Phân Quyền - AI Agent for Business

## 📋 Tổng Quan

Hệ thống phân quyền 3 roles với phân chia rõ ràng quyền hạn:

### 1. **ADMIN** - Quản Trị Viên Hệ Thống
- ✅ **Toàn quyền** quản lý hệ thống
- ✅ Quản lý tất cả users, products, orders, documents
- ✅ Xem analytics của tất cả doanh nghiệp
- ✅ Cấu hình hệ thống, quản lý roles

### 2. **BUSINESS** - Chủ Doanh Nghiệp
- ✅ Quản lý **sản phẩm** của doanh nghiệp mình
- ✅ Quản lý **đơn hàng** sản phẩm của mình
- ✅ Xem **phân tích doanh thu, số lượng bán** của mình
- ✅ Quản lý **tài liệu doanh nghiệp** của mình
- ✅ Sử dụng **AI Agent** để phân tích tài liệu
- ❌ **KHÔNG** được xem/sửa data của doanh nghiệp khác

### 3. **CUSTOMER** - Khách Hàng
- ✅ Xem danh sách sản phẩm, categories
- ✅ Mua hàng, tạo đơn hàng
- ✅ Xem lịch sử đơn hàng của mình
- ✅ Sử dụng chat AI
- ❌ **KHÔNG** được quản lý sản phẩm
- ❌ **KHÔNG** được xem data doanh nghiệp

---

## 🎯 Permissions Chi Tiết

### User Management
| Permission | ADMIN | BUSINESS | CUSTOMER |
|-----------|-------|----------|----------|
| USER_READ | ✅ | ❌ | ❌ |
| USER_CREATE | ✅ | ❌ | ❌ |
| USER_UPDATE | ✅ | ❌ | ❌ |
| USER_DELETE | ✅ | ❌ | ❌ |

### Product Management
| Permission | ADMIN | BUSINESS | CUSTOMER |
|-----------|-------|----------|----------|
| PRODUCT_READ | ✅ | ✅ | ✅ |
| PRODUCT_CREATE | ✅ | ✅ (own) | ❌ |
| PRODUCT_UPDATE | ✅ | ✅ (own) | ❌ |
| PRODUCT_DELETE | ✅ | ✅ (own) | ❌ |
| PRODUCT_MANAGE_ALL | ✅ | ❌ | ❌ |

### Category Management
| Permission | ADMIN | BUSINESS | CUSTOMER |
|-----------|-------|----------|----------|
| CATEGORY_READ | ✅ | ✅ | ✅ |
| CATEGORY_CREATE | ✅ | ❌ | ❌ |
| CATEGORY_UPDATE | ✅ | ❌ | ❌ |
| CATEGORY_DELETE | ✅ | ❌ | ❌ |

### Order Management
| Permission | ADMIN | BUSINESS | CUSTOMER |
|-----------|-------|----------|----------|
| ORDER_READ | ✅ | ✅ (own products) | ✅ (own orders) |
| ORDER_CREATE | ✅ | ❌ | ✅ |
| ORDER_UPDATE | ✅ | ✅ (own products) | ❌ |
| ORDER_DELETE | ✅ | ❌ | ❌ |
| ORDER_MANAGE_ALL | ✅ | ❌ | ❌ |

### Document Management
| Permission | ADMIN | BUSINESS | CUSTOMER |
|-----------|-------|----------|----------|
| DOCUMENT_READ | ✅ | ✅ (own) | ❌ |
| DOCUMENT_CREATE | ✅ | ✅ | ❌ |
| DOCUMENT_UPDATE | ✅ | ✅ (own) | ❌ |
| DOCUMENT_DELETE | ✅ | ✅ (own) | ❌ |
| DOCUMENT_MANAGE_ALL | ✅ | ❌ | ❌ |

### Analytics & Reports
| Permission | ADMIN | BUSINESS | CUSTOMER |
|-----------|-------|----------|----------|
| ANALYTICS_VIEW | ✅ | ✅ (own data) | ❌ |
| ANALYTICS_VIEW_ALL | ✅ | ❌ | ❌ |
| REPORT_GENERATE | ✅ | ✅ | ❌ |
| REPORT_EXPORT | ✅ | ✅ | ❌ |

### Chat & AI
| Permission | ADMIN | BUSINESS | CUSTOMER |
|-----------|-------|----------|----------|
| CHAT_USE | ✅ | ✅ | ✅ |
| CHAT_VIEW_HISTORY | ✅ | ✅ | ✅ |

### System
| Permission | ADMIN | BUSINESS | CUSTOMER |
|-----------|-------|----------|----------|
| SYSTEM_CONFIG | ✅ | ❌ | ❌ |
| SYSTEM_LOGS | ✅ | ❌ | ❌ |
| ROLE_READ | ✅ | ❌ | ❌ |
| ROLE_MANAGE | ✅ | ❌ | ❌ |

---

## 💼 Business Owner Features

### Thông tin doanh nghiệp (User entity)
```java
- businessName         // Tên doanh nghiệp
- businessType         // Loại hình: Retail, Manufacturing, Service
- businessDescription  // Mô tả doanh nghiệp
- businessAddress      // Địa chỉ
- businessPhone        // SĐT doanh nghiệp
- businessEmail        // Email doanh nghiệp
- taxCode              // Mã số thuế
- businessLogo         // URL logo
- isBusinessVerified   // Đã xác thực doanh nghiệp chưa
```

### Quản lý sản phẩm
- Mỗi Product có `businessOwner` (FK → User)
- Business owner chỉ thấy/sửa sản phẩm của mình
- ADMIN thấy tất cả sản phẩm

### Quản lý tài liệu
- Mỗi Document có `businessOwner` (FK → User)
- Document types:
  - PRODUCT_MANUAL (Hướng dẫn sản phẩm)
  - BUSINESS_PLAN (Kế hoạch kinh doanh)
  - FINANCIAL_REPORT (Báo cáo tài chính)
  - MARKETING_MATERIAL (Tài liệu marketing)
  - POLICY, CONTRACT, INVOICE
  - RESEARCH (Nghiên cứu thị trường)
  - TRAINING, INTERNAL_DOC, OTHER

### Analytics Dashboard
Business owner xem:
- ✅ Doanh thu (hôm nay, tuần này, tháng này, năm nay)
- ✅ Tỷ lệ tăng trưởng
- ✅ Top sản phẩm bán chạy
- ✅ Sản phẩm sắp hết hàng
- ✅ Phân tích đơn hàng theo trạng thái
- ✅ Phân tích khách hàng

---

## 🔒 Cách Sử Dụng Permissions

### 1. Annotation trên Controller

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    // Chỉ ADMIN và BUSINESS được tạo sản phẩm
    @PostMapping
    @RequirePermission(Role.Permission.PRODUCT_CREATE)
    public ResponseEntity<ProductResponse> createProduct(...) {
        // ...
    }
    
    // Chỉ ADMIN được xóa bất kỳ sản phẩm nào
    @DeleteMapping("/{id}")
    @RequirePermission(Role.Permission.PRODUCT_MANAGE_ALL)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        // ...
    }
    
    // BUSINESS hoặc ADMIN
    @GetMapping("/analytics")
    @RequireRole({Role.RoleName.BUSINESS, Role.RoleName.ADMIN})
    @RequirePermission(Role.Permission.ANALYTICS_VIEW)
    public ResponseEntity<BusinessAnalyticsResponse> getAnalytics(...) {
        // ...
    }
}
```

### 2. Programmatic Check trong Service

```java
@Service
public class ProductService {
    
    public void updateProduct(Long productId, User currentUser) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException("Product not found"));
        
        // Business owner chỉ được sửa sản phẩm của mình
        if (currentUser.isBusiness()) {
            if (!product.getBusinessOwner().getId().equals(currentUser.getId())) {
                throw new AccessDeniedException("Bạn không có quyền sửa sản phẩm này");
            }
        }
        
        // Admin được sửa tất cả
        // Continue with update...
    }
}
```

### 3. Helper Methods trong User Entity

```java
User currentUser = getCurrentUser();

// Check role
if (currentUser.isAdmin()) {
    // Allow all
}

if (currentUser.isBusiness()) {
    // Check ownership
}

if (currentUser.isCustomer()) {
    // Read only
}

// Check permission
if (currentUser.hasPermission(Role.Permission.PRODUCT_CREATE)) {
    // Allow create
}
```

---

## 📊 Business Analytics API

### GET `/api/analytics/business/overview`
**Role:** BUSINESS, ADMIN  
**Permission:** ANALYTICS_VIEW

Response:
```json
{
  "overview": {
    "totalProducts": 50,
    "activeProducts": 45,
    "lowStockProducts": 5,
    "totalOrders": 120,
    "pendingOrders": 10,
    "totalCustomers": 80,
    "totalRevenue": 100000000,
    "monthlyRevenue": 15000000
  },
  "revenue": {
    "today": 500000,
    "thisWeek": 3000000,
    "thisMonth": 15000000,
    "lastMonth": 12000000,
    "thisYear": 85000000,
    "growthRate": 25.5
  },
  "products": {
    "topSelling": [...],
    "topRevenue": [...],
    "lowStock": [...]
  },
  "orders": {
    "totalOrders": 120,
    "pendingOrders": 10,
    "completedOrders": 90,
    "orderStatusDistribution": {...},
    "recentOrders": [...]
  },
  "customers": {
    "totalCustomers": 80,
    "newCustomersThisMonth": 15,
    "activeCustomers": 50,
    "topCustomers": [...]
  }
}
```

### GET `/api/analytics/business/{businessId}/overview`
**Role:** ADMIN only  
**Permission:** ANALYTICS_VIEW_ALL

ADMIN xem analytics của business cụ thể.

---

## 🗄️ Database Migration

### Chạy Migration Script

```bash
cd backend/SpringService/src/main/resources
mysql -u root -p aiagent_db < migration-role-system.sql
```

### Demo Users được tạo

| Username | Password | Role | Description |
|----------|----------|------|-------------|
| admin | admin123 | ADMIN | Quản trị viên |
| business_demo | admin123 | BUSINESS | Chủ doanh nghiệp demo |
| customer_demo | admin123 | CUSTOMER | Khách hàng demo |

---

## 🚀 Workflow Tiêu Biểu

### Workflow 1: Business Owner đăng ký
1. Register với thông tin doanh nghiệp
2. Admin xác thực doanh nghiệp (set `isBusinessVerified = true`)
3. Business owner login, được gán role BUSINESS
4. Tạo sản phẩm → `product.businessOwner = currentUser`
5. Upload tài liệu → `document.businessOwner = currentUser`
6. Xem analytics dashboard của mình

### Workflow 2: Customer mua hàng
1. Register/Login với role CUSTOMER
2. Browse products (chỉ đọc)
3. Thêm vào giỏ hàng
4. Checkout → tạo Order
5. Xem lịch sử đơn hàng của mình
6. Sử dụng chat AI để hỏi về sản phẩm

### Workflow 3: Admin quản lý
1. Login với role ADMIN
2. Xem tất cả products, orders, users
3. Xác thực business owners
4. Xem analytics tổng thể
5. Quản lý categories, roles, system config

---

## ✅ Checklist Triển Khai

- [x] Cập nhật Role entity với 3 roles
- [x] Thêm Permissions enum
- [x] Cập nhật User entity với business info
- [x] Cập nhật Product entity với businessOwner
- [x] Cập nhật Document entity với businessOwner và DocumentType
- [x] Tạo @RequirePermission, @RequireRole annotations
- [x] Tạo PermissionAspect
- [x] Tạo BusinessAnalyticsService
- [x] Tạo BusinessAnalyticsController
- [x] Tạo migration script
- [x] Thêm spring-boot-starter-aop vào pom.xml
- [ ] Update ProductController với permission checks
- [ ] Update OrderController với permission checks
- [ ] Update DocumentController với permission checks
- [ ] Test permissions
- [ ] Test analytics APIs
- [ ] Frontend integration

---

## 📝 Notes

- **Data Isolation:** BUSINESS chỉ thấy data của mình (products, orders, documents, analytics)
- **AI Agent Integration:** Tài liệu doanh nghiệp sẽ được xử lý riêng, AI Agent trả lời dựa trên tài liệu của business owner đó
- **Scalability:** Mỗi business có namespace riêng trong vector DB để tách biệt dữ liệu
- **Security:** AspectJ đảm bảo permission được check trước khi method thực thi

---

**Developed by:** Nguyễn Văn Hoàng - 110122078  
**Project:** AI Agent for Business  
**Date:** 15/11/2025
