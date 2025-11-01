# 📚 API Summary - Spring Boot Backend

## 🎯 Tổng Quan API

Backend cung cấp **5 nhóm API chính** cho hệ thống AI Agent:

1. **Authentication** - Xác thực & quản lý token
2. **Users** - Quản lý người dùng
3. **Documents** - Quản lý tài liệu
4. **Chat** - Chatbot & conversations
5. **Strategic Analysis** - Phân tích chiến lược

---

## 1. 🔐 Authentication API

**Base URL:** `/api/auth`

### Endpoints

| Method | Endpoint | Description | Auth Required | Roles |
|--------|----------|-------------|---------------|-------|
| POST | `/register` | Đăng ký tài khoản mới | ❌ | Public |
| POST | `/login` | Đăng nhập | ❌ | Public |
| POST | `/refresh` | Làm mới token | ❌ | Public |
| POST | `/logout` | Đăng xuất | ✅ | All |
| GET | `/health` | Health check | ❌ | Public |

### Examples

**Register:**
```bash
POST /api/auth/register
{
  "username": "hoangvan",
  "email": "110122078@st.tvu.edu.vn",
  "password": "123456",
  "fullName": "Nguyễn Văn Hoàng",
  "role": "CUSTOMER"
}
```

**Login:**
```bash
POST /api/auth/login
{
  "email": "admin@aiagent.com",
  "password": "admin123"
}
```

---

## 2. 👥 Users API

**Base URL:** `/api/users`

### Endpoints

| Method | Endpoint | Description | Roles |
|--------|----------|-------------|-------|
| GET | `/me` | Lấy thông tin user hiện tại | All |
| GET | `/` | Lấy tất cả users | ADMIN |
| GET | `/{id}` | Lấy user theo ID | ADMIN |
| PUT | `/{id}` | Cập nhật user | ADMIN |
| DELETE | `/{id}` | Xóa user | ADMIN |
| GET | `/role/{role}` | Lấy users theo role | ADMIN |

### Examples

**Get Current User:**
```bash
GET /api/users/me
Authorization: Bearer {token}
```

**Get All Users (Admin only):**
```bash
GET /api/users
Authorization: Bearer {admin_token}
```

---

## 3. 📁 Documents API

**Base URL:** `/api/documents`

### Endpoints

| Method | Endpoint | Description | Roles |
|--------|----------|-------------|-------|
| POST | `/upload` | Upload tài liệu | ADMIN, BUSINESS |
| GET | `/` | Lấy tất cả documents | All |
| GET | `/{id}` | Lấy document theo ID | All |
| GET | `/user/{userId}` | Lấy documents của user | All |
| GET | `/category/{category}` | Lấy documents theo category | All |
| PUT | `/{id}` | Cập nhật document | ADMIN, BUSINESS |
| DELETE | `/{id}` | Xóa document | ADMIN, BUSINESS |
| GET | `/search?keyword=` | Tìm kiếm documents | All |
| GET | `/categories` | Lấy danh sách categories | All |

### Examples

**Upload Document:**
```bash
POST /api/documents/upload
Content-Type: multipart/form-data
Authorization: Bearer {token}

file: [binary]
title: "Báo cáo Q4 2024"
description: "Báo cáo tài chính quý 4"
category: "Finance"
```

**Search Documents:**
```bash
GET /api/documents/search?keyword=báo cáo&page=0&size=10
Authorization: Bearer {token}
```

---

## 4. 💬 Chat API

**Base URL:** `/api/chat`

### Endpoints

| Method | Endpoint | Description | Roles |
|--------|----------|-------------|-------|
| POST | `/conversations` | Tạo conversation mới | All |
| GET | `/conversations` | Lấy danh sách conversations | All |
| GET | `/conversations/{id}` | Lấy chi tiết conversation | All |
| POST | `/message` | Gửi message | All |
| GET | `/conversations/{id}/messages` | Lấy messages | All |
| DELETE | `/conversations/{id}` | Xóa conversation | All |

### Examples

**Create Conversation:**
```bash
POST /api/chat/conversations?title=Hỏi về sản phẩm
Authorization: Bearer {token}
```

**Send Message:**
```bash
POST /api/chat/message
Authorization: Bearer {token}
{
  "conversationId": 1,
  "message": "Giá sản phẩm X là bao nhiêu?",
  "useContext": true,
  "maxResults": 5
}
```

**Response:**
```json
{
  "messageId": 123,
  "response": "Sản phẩm X có giá...",
  "relevantDocuments": [...],
  "sentiment": "POSITIVE",
  "confidence": 0.85,
  "timestamp": "2024-11-01T20:30:00"
}
```

---

## 5. 📊 Strategic Analysis API

**Base URL:** `/api/strategic`

### Endpoints

| Method | Endpoint | Description | Roles |
|--------|----------|-------------|-------|
| POST | `/analyze` | Phân tích chiến lược | ADMIN, BUSINESS |
| GET | `/reports/{id}` | Lấy báo cáo | ADMIN, BUSINESS |

### Analysis Types

- `SWOT` - Phân tích điểm mạnh, yếu, cơ hội, thách thức
- `MARKET_OPPORTUNITY` - Phân tích cơ hội thị trường
- `RISK_ASSESSMENT` - Đánh giá rủi ro
- `COMPETITIVE_ANALYSIS` - Phân tích cạnh tranh
- `GROWTH_STRATEGY` - Chiến lược tăng trưởng
- `FULL_STRATEGIC` - Phân tích toàn diện

### Examples

**Analyze Strategy:**
```bash
POST /api/strategic/analyze
Authorization: Bearer {token}
{
  "analysisType": "SWOT",
  "startDate": "2024-01-01",
  "endDate": "2024-12-31",
  "businessMetrics": {
    "revenue": 1000000,
    "customers": 500,
    "growthRate": 15
  },
  "industry": "Technology",
  "additionalContext": "E-commerce startup"
}
```

**Response:**
```json
{
  "reportId": 456,
  "analysisType": "SWOT",
  "insights": {
    "strengths": "...",
    "weaknesses": "...",
    "opportunities": "...",
    "threats": "..."
  },
  "recommendations": {
    "shortTerm": "...",
    "mediumTerm": "...",
    "longTerm": "..."
  },
  "summary": "...",
  "confidenceScore": 0.85,
  "generatedAt": "2024-11-01T20:30:00"
}
```

---

## 🔐 Authentication & Authorization

### JWT Token

Tất cả protected endpoints cần JWT token trong header:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Role-Based Access

| Endpoint Pattern | ADMIN | BUSINESS | CUSTOMER |
|-----------------|-------|----------|----------|
| `/api/auth/**` | ✅ | ✅ | ✅ |
| `/api/users/me` | ✅ | ✅ | ✅ |
| `/api/users/**` | ✅ | ❌ | ❌ |
| `/api/documents` (GET) | ✅ | ✅ | ✅ |
| `/api/documents/upload` | ✅ | ✅ | ❌ |
| `/api/documents/{id}` (PUT/DELETE) | ✅ | ✅ | ❌ |
| `/api/chat/**` | ✅ | ✅ | ✅ |
| `/api/strategic/**` | ✅ | ✅ | ❌ |

---

## 📝 Response Format

### Success Response

```json
{
  "data": {...},
  "status": 200,
  "message": "Success"
}
```

### Error Response

```json
{
  "timestamp": "2024-11-01T20:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid input",
  "path": "/api/documents/upload",
  "validationErrors": {
    "title": "Title is required"
  }
}
```

---

## 🔄 Pagination

Các endpoints trả về danh sách hỗ trợ pagination:

**Request:**
```
GET /api/documents?page=0&size=10&sort=createdAt,desc
```

**Response:**
```json
{
  "content": [...],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 100,
  "totalPages": 10,
  "last": false,
  "first": true
}
```

---

## 🧪 Testing với cURL

### 1. Login và lấy token

```bash
TOKEN=$(curl -s -X POST http://localhost:8100/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@aiagent.com","password":"admin123"}' \
  | jq -r '.token')
```

### 2. Sử dụng token

```bash
curl -X GET http://localhost:8100/api/users/me \
  -H "Authorization: Bearer $TOKEN"
```

### 3. Upload document

```bash
curl -X POST http://localhost:8100/api/documents/upload \
  -H "Authorization: Bearer $TOKEN" \
  -F "file=@document.pdf" \
  -F "title=My Document" \
  -F "category=Finance"
```

---

## 📖 Swagger UI

Xem full API documentation tại:

**http://localhost:8100/swagger-ui.html**

---

## ⚠️ TODO - Future Implementation

- [ ] AI Service integration cho chat responses
- [ ] Vector search cho documents  
- [ ] Real-time WebSocket cho chat
- [ ] File download endpoint
- [ ] Batch document upload
- [ ] Advanced filtering & sorting
- [ ] Rate limiting
- [ ] API versioning

---

**Sinh viên:** Nguyễn Văn Hoàng - MSSV: 110122078  
**Trường:** Đại Học Trà Vinh  
**Email:** 110122078@st.tvu.edu.vn

