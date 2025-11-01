# 📚 Swagger/OpenAPI Documentation Guide

## 🌐 Truy Cập Swagger UI

### Local Development
- **Swagger UI**: http://localhost:8100/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8100/api-docs
- **OpenAPI YAML**: http://localhost:8100/api-docs.yaml

## 🎯 Tính Năng Swagger

### 1. **Interactive API Documentation**
- Xem tất cả endpoints
- Chi tiết request/response
- Example requests & responses
- Schema definitions

### 2. **Try It Out**
- Test API trực tiếp từ browser
- Không cần Postman hay cURL
- Real-time response display

### 3. **Authentication Support**
- JWT Bearer Token integration
- Click "Authorize" button
- Nhập token và test protected endpoints

## 🚀 Hướng Dẫn Sử Dụng

### Bước 1: Khởi động Backend
```bash
cd /home/hv/DuAn/AI-Agent-for-Business/backend/SpringService
mvn spring-boot:run
```

### Bước 2: Truy cập Swagger UI
Mở browser và vào: http://localhost:8100/swagger-ui.html

### Bước 3: Đăng ký/Đăng nhập

#### 3.1. Đăng ký tài khoản mới
1. Mở section **Authentication**
2. Click vào `POST /api/auth/register`
3. Click "Try it out"
4. Nhập thông tin:
```json
{
  "username": "hoangvan",
  "email": "110122078@st.tvu.edu.vn",
  "password": "123456",
  "fullName": "Nguyễn Văn Hoàng",
  "phoneNumber": "0123456789"
}
```
5. Click "Execute"
6. Copy `token` từ response

#### 3.2. Hoặc đăng nhập nếu đã có tài khoản
1. Click vào `POST /api/auth/login`
2. Click "Try it out"
3. Nhập:
```json
{
  "email": "110122078@st.tvu.edu.vn",
  "password": "123456"
}
```
4. Click "Execute"
5. Copy `token` từ response

### Bước 4: Authenticate trong Swagger

1. Click nút **"Authorize"** ở góc trên bên phải (icon 🔓)
2. Trong popup, nhập: `Bearer YOUR_TOKEN_HERE`
   
   Ví dụ:
   ```
   Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMTAxMjIwNzhAc3QudHZ1LmVkdS52biIsImlhdCI6MTcwMDAwMDAwMCwiZXhwIjoxNzAwMDg2NDAwfQ.abc123def456
   ```

3. Click "Authorize"
4. Click "Close"

### Bước 5: Test Protected Endpoints

Bây giờ bạn có thể test các endpoint cần authentication:

1. Click vào `POST /api/auth/logout`
2. Click "Try it out"
3. Click "Execute"
4. Xem response

## 📋 API Endpoints

### 🔓 Public Endpoints (Không cần token)

#### 1. Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "hoangvan",
  "email": "110122078@st.tvu.edu.vn",
  "password": "123456",
  "fullName": "Nguyễn Văn Hoàng"
}
```

**Response 201:**
```json
{
  "token": "eyJhbGc...",
  "refreshToken": "eyJhbGc...",
  "type": "Bearer",
  "userId": 1,
  "username": "hoangvan",
  "email": "110122078@st.tvu.edu.vn",
  "roles": ["USER"]
}
```

#### 2. Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "110122078@st.tvu.edu.vn",
  "password": "123456"
}
```

#### 3. Health Check
```http
GET /api/auth/health
```

**Response 200:**
```json
{
  "status": "OK",
  "service": "AI Agent Authentication Service",
  "author": "Nguyễn Văn Hoàng - Đại Học Trà Vinh"
}
```

### 🔒 Protected Endpoints (Cần Bearer token)

#### 1. Logout
```http
POST /api/auth/logout
Authorization: Bearer YOUR_TOKEN_HERE
```

#### 2. Refresh Token
```http
POST /api/auth/refresh
Content-Type: application/json

{
  "refreshToken": "YOUR_REFRESH_TOKEN_HERE"
}
```

## 🎨 Swagger UI Features

### Tags/Groups
API được tổ chức theo tags:
- **Authentication** - Đăng ký, đăng nhập, logout
- **Documents** (Coming soon)
- **Chat** (Coming soon)
- **Strategic Analysis** (Coming soon)
- **Users** (Coming soon)

### Schemas
Xem chi tiết data models:
- Click vào tab "Schemas" ở cuối trang
- Xem cấu trúc của:
  - AuthRequest
  - AuthResponse
  - RegisterRequest
  - ChatRequest/Response
  - DocumentRequest/Response
  - Etc.

### Examples
Mỗi endpoint có:
- ✅ Request example
- ✅ Response example (success)
- ✅ Error response examples
- ✅ Schema definitions

## 🔧 Customization

### Thêm Swagger cho Controller mới

1. **Add Tag to Controller:**
```java
@Tag(name = "Documents", description = "API quản lý tài liệu")
@RestController
@RequestMapping("/api/documents")
public class DocumentController {
```

2. **Add Operation to Endpoint:**
```java
@Operation(
    summary = "Upload tài liệu",
    description = "Upload file PDF, DOC, DOCX, TXT hoặc Excel"
)
@ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",
        description = "Upload thành công",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = DocumentResponse.class)
        )
    ),
    @ApiResponse(
        responseCode = "400",
        description = "File không hợp lệ"
    )
})
@PostMapping("/upload")
public ResponseEntity<?> uploadDocument(...) {
```

3. **Add Schema to DTO:**
```java
@Schema(description = "Document upload request")
public class DocumentRequest {
    
    @Schema(
        description = "Tiêu đề tài liệu",
        example = "Báo cáo tài chính Q4 2024",
        required = true
    )
    private String title;
    
    @Schema(
        description = "Mô tả tài liệu",
        example = "Báo cáo tài chính quý 4 năm 2024"
    )
    private String description;
}
```

## 📖 OpenAPI Specification

Download OpenAPI spec:

```bash
# JSON format
curl http://localhost:8100/api-docs > openapi.json

# YAML format
curl http://localhost:8100/api-docs.yaml > openapi.yaml
```

Sử dụng với các tools khác:
- **Postman**: Import OpenAPI spec
- **Insomnia**: Import OpenAPI spec
- **Code Generation**: Sử dụng openapi-generator

## 🐛 Troubleshooting

### Swagger UI không load
```bash
# Kiểm tra app đang chạy
curl http://localhost:8100/actuator/health

# Kiểm tra OpenAPI spec
curl http://localhost:8100/api-docs
```

### Cannot authorize với JWT token
- Đảm bảo thêm prefix "Bearer " trước token
- Kiểm tra token chưa expire (24 hours)
- Thử login lại để lấy token mới

### 401 Unauthorized khi test endpoint
- Click "Authorize" và nhập token
- Token phải bắt đầu với "Bearer "
- Token phải còn hạn

## 📚 Resources

- **Springdoc OpenAPI**: https://springdoc.org/
- **OpenAPI Specification**: https://swagger.io/specification/
- **Swagger Editor**: https://editor.swagger.io/

## 👨‍💻 Contact

**Sinh viên:** Nguyễn Văn Hoàng  
**Email:** 110122078@st.tvu.edu.vn  
**GitHub:** https://github.com/vanhoangtvu/AI-Agent-for-Business

---

Made with ❤️ for Đồ Án Chuyên Ngành - Đại Học Trà Vinh

