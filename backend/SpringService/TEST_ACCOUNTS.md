# 🧪 Test Accounts - Tài Khoản Test

## 📋 Danh Sách Tài Khoản

### 👑 ADMIN - Quản Trị Viên

#### 1. Admin Principal
- **Email:** `admin@aiagent.com`
- **Password:** `admin123`
- **Username:** `admin`
- **Full Name:** Administrator
- **Phone:** 0900000001
- **Quyền:** Full system access

#### 2. Sinh Viên (Admin)
- **Email:** `110122078@st.tvu.edu.vn`
- **Password:** `hoang123`
- **Username:** `hoangvan`
- **Full Name:** Nguyễn Văn Hoàng
- **Phone:** 0123456789
- **Quyền:** Full system access

---

### 🏢 BUSINESS - Doanh Nghiệp

#### 1. Business Principal
- **Email:** `business@aiagent.com`
- **Password:** `business123`
- **Username:** `business`
- **Full Name:** Công ty TNHH ABC
- **Phone:** 0900000002
- **Quyền:** Manage own documents, customers, analytics

#### 2. Business 1
- **Email:** `business1@company.com`
- **Password:** `business123`
- **Username:** `business1`
- **Full Name:** Công ty XYZ
- **Phone:** 0901111111

#### 3. Business 2
- **Email:** `business2@company.com`
- **Password:** `business123`
- **Username:** `business2`
- **Full Name:** Công ty 123
- **Phone:** 0902222222

---

### 👤 CUSTOMER - Khách Hàng

#### 1. Customer Principal
- **Email:** `customer@aiagent.com`
- **Password:** `customer123`
- **Username:** `customer`
- **Full Name:** Khách Hàng Test
- **Phone:** 0900000003
- **Quyền:** Chat, view shared documents

#### 2. Customer 1
- **Email:** `customer1@gmail.com`
- **Password:** `customer123`
- **Username:** `customer1`
- **Full Name:** Nguyễn Văn A
- **Phone:** 0903333333

#### 3. Customer 2
- **Email:** `customer2@gmail.com`
- **Password:** `customer123`
- **Username:** `customer2`
- **Full Name:** Trần Thị B
- **Phone:** 0904444444

#### 4. Customer 3
- **Email:** `customer3@gmail.com`
- **Password:** `customer123`
- **Username:** `customer3`
- **Full Name:** Lê Văn C
- **Phone:** 0905555555

---

## 🚀 Cách Sử Dụng

### 1. Khởi động Backend
```bash
cd /home/hv/DuAn/AI-Agent-for-Business/backend/SpringService
mvn spring-boot:run
```

**Accounts sẽ được tự động tạo khi app khởi động!**

### 2. Login Test với cURL

#### Login ADMIN
```bash
curl -X POST http://localhost:8100/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@aiagent.com",
    "password": "admin123"
  }'
```

#### Login BUSINESS
```bash
curl -X POST http://localhost:8100/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "business@aiagent.com",
    "password": "business123"
  }'
```

#### Login CUSTOMER
```bash
curl -X POST http://localhost:8100/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "customer@aiagent.com",
    "password": "customer123"
  }'
```

### 3. Test với Swagger UI

1. Truy cập: http://localhost:8100/swagger-ui.html
2. Click vào **POST /api/auth/login**
3. Click **"Try it out"**
4. Nhập credentials từ danh sách trên
5. Click **"Execute"**
6. Copy **token** từ response
7. Click **"Authorize"** button (🔓 icon)
8. Nhập: `Bearer YOUR_TOKEN_HERE`
9. Test các endpoints theo role

---

## 🧪 Test Scenarios

### Scenario 1: Test ADMIN Access
```bash
# 1. Login as ADMIN
TOKEN=$(curl -X POST http://localhost:8100/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@aiagent.com","password":"admin123"}' \
  | jq -r '.token')

# 2. Test admin endpoint (should work)
curl -X GET http://localhost:8100/api/admin/users \
  -H "Authorization: Bearer $TOKEN"

# 3. Test business endpoint (should work - admin has all access)
curl -X GET http://localhost:8100/api/business/dashboard \
  -H "Authorization: Bearer $TOKEN"
```

### Scenario 2: Test BUSINESS Access
```bash
# 1. Login as BUSINESS
TOKEN=$(curl -X POST http://localhost:8100/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"business@aiagent.com","password":"business123"}' \
  | jq -r '.token')

# 2. Test business endpoint (should work)
curl -X GET http://localhost:8100/api/business/dashboard \
  -H "Authorization: Bearer $TOKEN"

# 3. Test admin endpoint (should fail - 403 Forbidden)
curl -X GET http://localhost:8100/api/admin/users \
  -H "Authorization: Bearer $TOKEN"
```

### Scenario 3: Test CUSTOMER Access
```bash
# 1. Login as CUSTOMER
TOKEN=$(curl -X POST http://localhost:8100/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"customer@aiagent.com","password":"customer123"}' \
  | jq -r '.token')

# 2. Test chat endpoint (should work)
curl -X GET http://localhost:8100/api/chat/conversations \
  -H "Authorization: Bearer $TOKEN"

# 3. Test business endpoint (should fail - 403 Forbidden)
curl -X GET http://localhost:8100/api/business/dashboard \
  -H "Authorization: Bearer $TOKEN"

# 4. Test document upload (should fail - 403 Forbidden)
curl -X POST http://localhost:8100/api/documents/upload \
  -H "Authorization: Bearer $TOKEN"
```

---

## 📊 Expected Results

| Endpoint | ADMIN | BUSINESS | CUSTOMER |
|----------|-------|----------|----------|
| `/api/admin/**` | ✅ 200 | ❌ 403 | ❌ 403 |
| `/api/business/**` | ✅ 200 | ✅ 200 | ❌ 403 |
| `/api/documents/upload` | ✅ 200 | ✅ 200 | ❌ 403 |
| `/api/documents` (GET) | ✅ 200 | ✅ 200 | ✅ 200 |
| `/api/chat/**` | ✅ 200 | ✅ 200 | ✅ 200 |
| `/api/strategic/**` | ✅ 200 | ✅ 200 | ❌ 403 |

---

## 🔄 Reset Data

Nếu muốn reset lại database và tạo lại test accounts:

```bash
# Option 1: Xóa database và chạy lại
mysql -u root -p1111 -e "DROP DATABASE AI_Agent_db; CREATE DATABASE AI_Agent_db;"
mvn spring-boot:run

# Option 2: Chỉ xóa users table
mysql -u root -p1111 AI_Agent_db -e "DELETE FROM users;"
mvn spring-boot:run
```

---

## 📝 Notes

- ✅ Tất cả accounts có `emailVerified = true` để test ngay
- ✅ Tất cả accounts có `active = true`
- ✅ Password đều đơn giản để dễ nhớ (chỉ dùng cho testing!)
- ✅ DataSeeder chỉ chạy nếu account chưa tồn tại (idempotent)
- ⚠️  **KHÔNG dùng passwords này cho production!**

---

## 🎯 Quick Reference

### Login Commands (One-liner)

**ADMIN:**
```bash
curl -X POST http://localhost:8100/api/auth/login -H "Content-Type: application/json" -d '{"email":"admin@aiagent.com","password":"admin123"}'
```

**BUSINESS:**
```bash
curl -X POST http://localhost:8100/api/auth/login -H "Content-Type: application/json" -d '{"email":"business@aiagent.com","password":"business123"}'
```

**CUSTOMER:**
```bash
curl -X POST http://localhost:8100/api/auth/login -H "Content-Type: application/json" -d '{"email":"customer@aiagent.com","password":"customer123"}'
```

**STUDENT (ADMIN):**
```bash
curl -X POST http://localhost:8100/api/auth/login -H "Content-Type: application/json" -d '{"email":"110122078@st.tvu.edu.vn","password":"hoang123"}'
```

---

**Created by:** Nguyễn Văn Hoàng - MSSV: 110122078  
**Trường:** Đại Học Trà Vinh  
**Email:** 110122078@st.tvu.edu.vn

