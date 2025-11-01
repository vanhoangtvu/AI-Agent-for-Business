# Spring Boot Backend - Setup Summary

## ✅ Đã Hoàn Thành

### 1. Project Configuration
- ✅ `pom.xml` - Maven configuration với đầy đủ dependencies
- ✅ `application.yml` - Application configuration
- ✅ `Dockerfile` - Docker configuration
- ✅ `.gitignore` - Git ignore rules

### 2. Database Configuration
- ✅ Database: `AI_Agent_db`
- ✅ MySQL Password: `1111`
- ✅ JPA Auto DDL: `update`
- ✅ Connection pool: HikariCP

### 3. Entities (Model Layer)
- ✅ `User.java` - User entity với roles, authentication
- ✅ `Document.java` - Document entity với file management
- ✅ `Conversation.java` - Conversation và Message entities

### 4. DTOs (Data Transfer Objects)
- ✅ Auth DTOs: `AuthRequest`, `AuthResponse`, `RegisterRequest`
- ✅ Chat DTOs: `ChatRequest`, `ChatResponse`  
- ✅ Document DTOs: `DocumentRequest`, `DocumentResponse`
- ✅ Strategic DTOs: `StrategicAnalysisRequest`, `StrategicAnalysisResponse`

### 5. Repositories (Data Access Layer)
- ✅ `UserRepository` - User data access
- ✅ `DocumentRepository` - Document data access  
- ✅ `ConversationRepository` - Conversation data access
- ✅ `MessageRepository` - Message data access

### 6. Security & Authentication
- ✅ `SecurityConfig` - Spring Security configuration
- ✅ `JwtService` - JWT token generation & validation
- ✅ `JwtAuthenticationFilter` - JWT filter
- ✅ `JwtAuthenticationEntryPoint` - Unauthorized handler
- ✅ `CustomUserDetailsService` - User details service

### 7. Configuration Classes
- ✅ `DatabaseConfig` - JPA & Database configuration
- ✅ `SecurityConfig` - Security & CORS configuration
- ✅ `WebSocketConfig` - WebSocket configuration  
- ✅ `AIServiceConfig` - AI Service client configuration
- ✅ `RedisConfig` - Redis & Caching configuration

### 8. Services
- ✅ `AuthService` - Authentication service interface
- ✅ `AuthServiceImpl` - Authentication implementation

### 9. Controllers
- ✅ `AuthController` - Authentication REST API

### 10. Main Application
- ✅ `Application.java` - Spring Boot main class

## 📦 Dependencies Installed

```xml
- Spring Boot Web
- Spring Data JPA  
- Spring Security + JWT
- Spring WebSocket
- Spring Data Redis
- MySQL Connector
- Lombok
- Springdoc OpenAPI (Swagger)
- Spring Boot Actuator
- Validation API
- WebFlux (for AI Service client)
```

## 🚀 Cách Chạy

### 1. Kiểm tra MySQL đang chạy
```bash
mysql -u root -p1111
CREATE DATABASE IF NOT EXISTS AI_Agent_db;
```

### 2. Build và Run
```bash
cd /home/hv/DuAn/AI-Agent-for-Business/backend/SpringService

# Build project
mvn clean install

# Run application
mvn spring-boot:run
```

### 3. Test API
```bash
# Health check
curl http://localhost:8100/api/auth/health

# Register user
curl -X POST http://localhost:8100/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "hoangvan",
    "email": "110122078@st.tvu.edu.vn",
    "password": "123456",
    "fullName": "Nguyễn Văn Hoàng"
  }'

# Login
curl -X POST http://localhost:8100/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "110122078@st.tvu.edu.vn",
    "password": "123456"
  }'
```

## 🔗 API Endpoints

### Public Endpoints (No Auth Required)
- `POST /api/auth/register` - Đăng ký
- `POST /api/auth/login` - Đăng nhập
- `POST /api/auth/refresh` - Refresh token
- `GET /api/auth/health` - Health check
- `GET /swagger-ui.html` - API Documentation
- `GET /actuator/health` - Actuator health

### Protected Endpoints (Auth Required)
- `POST /api/auth/logout` - Đăng xuất
- All other `/api/**` endpoints

## 📊 Database Tables

Sau khi chạy app lần đầu, Hibernate sẽ tự động tạo các tables:

- `users` - User accounts
- `user_roles` - User roles mapping
- `documents` - Documents & files
- `document_tags` - Document tags
- `conversations` - Chat conversations
- `messages` - Chat messages

## 🔧 Configuration trong application.yml

```yaml
Database: 
  - URL: jdbc:mysql://localhost:3306/AI_Agent_db
  - Username: root
  - Password: 1111

JWT:
  - Expiration: 24 hours
  - Refresh: 7 days

AI Service:
  - URL: http://localhost:8000

Server:
  - Port: 8100
```

## 📝 Các Bước Tiếp Theo

### Để Hoàn Thiện Backend:
1. ⏳ Tạo các Service còn lại:
   - ChatService & ChatServiceImpl
   - DocumentService & DocumentServiceImpl
   - AIClientService (gọi Python service)
   - VectorSearchService
   - StrategicService & StrategicServiceImpl

2. ⏳ Tạo các Controller còn lại:
   - ChatController
   - DocumentController
   - StrategicController
   - UserController
   - ChatWebSocketHandler

3. ⏳ Exception Handling:
   - GlobalExceptionHandler
   - Custom Exception classes

4. ⏳ Testing:
   - Unit tests
   - Integration tests

5. ⏳ Documentation:
   - Swagger annotations
   - API documentation

## 🎯 Tính Năng Hiện Tại

✅ User registration & authentication
✅ JWT token generation & validation
✅ Password encoding (BCrypt)
✅ Role-based access control (RBAC)
✅ Database auto-schema generation
✅ Redis caching support
✅ CORS configuration
✅ Swagger UI integration
✅ Health monitoring (Actuator)

## 🐛 Troubleshooting

### Lỗi kết nối database:
```bash
# Kiểm tra MySQL
sudo systemctl status mysql
mysql -u root -p1111
```

### Lỗi port 8100 bị chiếm:
```bash
# Đổi port trong application.yml
server:
  port: 8101
```

### Lỗi build Maven:
```bash
# Clean và build lại
mvn clean
mvn install -DskipTests
```

---

**Sinh viên:** Nguyễn Văn Hoàng - MSSV: 110122078  
**Trường:** Đại Học Trà Vinh  
**Ngày:** $(date +"%d/%m/%Y")

