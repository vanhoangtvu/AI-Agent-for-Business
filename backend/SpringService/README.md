# Spring Boot Backend Service

Backend service cho AI Agent for Business system.

## 📋 Yêu Cầu

- Java 17 hoặc cao hơn
- Maven 3.6+
- MySQL 8.0+

## 🚀 Cài Đặt & Chạy

### 1. Setup Database

```bash
# Đăng nhập MySQL
mysql -u root -p

# Nhập password: 1111

# Chạy script khởi tạo database
source src/main/resources/init-db.sql

# Hoặc
mysql -u root -p1111 < src/main/resources/init-db.sql
```

### 2. Cấu Hình

File `src/main/resources/application.yml` đã được cấu hình sẵn với:
- Database: `AI_Agent_db`
- Username: `root`
- Password: `1111`
- Port: `8089` (Configured for server access)

### 3. Build & Run

```bash
# Build project
mvn clean install

# Chạy application
mvn spring-boot:run

# Hoặc chạy file JAR
java -jar target/aiagent-1.0.0.jar
```

**Server Access:**
Application sẽ chạy trên port `8089` và cho phép truy cập từ tất cả network interfaces (`0.0.0.0`)

## 📚 API Endpoints

### Authentication APIs

#### Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "user1",
  "email": "user1@example.com",
  "password": "password123",
  "fullName": "User One",
  "phone": "0123456789"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "admin",
  "email": "admin@aiagent.com",
  "roles": ["ROLE_ADMIN"]
}
```

### Sử Dụng Token

Thêm header vào các request:
```http
Authorization: Bearer {token}
```

## 📖 API Documentation

Swagger UI có sẵn tại:
```
http://localhost:8089/swagger-ui.html
# Hoặc từ server: http://YOUR_SERVER_IP:8089/swagger-ui.html
```

OpenAPI JSON:
```
http://localhost:8089/v3/api-docs
```

## 🌐 Server Configuration

Application được cấu hình để chạy trên server:
- **Port:** 8089
- **Binding Address:** 0.0.0.0 (Allow all network interfaces)
- **CORS:** Cho phép tất cả origins (*)
- **Public endpoints:** `/api/auth/**`, `/swagger-ui/**`

## 🗂️ Cấu Trúc Project

```
src/main/java/com/business/aiagent/
├── config/              # Configuration classes
│   └── SecurityConfig.java
├── controller/          # REST Controllers
│   └── AuthController.java
├── dto/                 # Data Transfer Objects
│   ├── AuthResponse.java
│   ├── LoginRequest.java
│   └── RegisterRequest.java
├── entity/              # JPA Entities
│   ├── User.java
│   ├── Role.java
│   ├── Document.java
│   ├── Conversation.java
│   ├── Message.java
│   ├── StrategicReport.java
│   └── ActivityLog.java
├── repository/          # JPA Repositories
│   ├── UserRepository.java
│   ├── RoleRepository.java
│   ├── DocumentRepository.java
│   ├── ConversationRepository.java
│   ├── MessageRepository.java
│   ├── StrategicReportRepository.java
│   └── ActivityLogRepository.java
├── security/            # Security components
│   ├── JwtTokenProvider.java
│   ├── JwtAuthenticationFilter.java
│   └── CustomUserDetailsService.java
├── service/             # Business Logic
│   └── AuthService.java
└── AIAgentApplication.java  # Main class
```

## 🔧 Technologies

- **Spring Boot 3.2.0**
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - Database operations
- **JWT (jjwt 0.11.5)** - Token-based auth
- **MySQL 8.0** - Database
- **Lombok** - Reduce boilerplate code
- **SpringDoc OpenAPI** - API documentation

## 🧪 Testing

```bash
# Run tests
mvn test

# Run tests with coverage
mvn test jacoco:report
```

## 🔐 Default Accounts

### Admin Account
- Username: `admin`
- Password: `admin123`
- Role: `ROLE_ADMIN`

## 📝 Notes

- JWT token expires sau 24 giờ
- File upload max size: 10MB
- Database tự động tạo schema khi chạy lần đầu (ddl-auto: update)
- CORS được cấu hình cho phép tất cả origins (phù hợp cho server)
- Application chạy trên port 8089 với binding address 0.0.0.0

## 🐛 Troubleshooting

### Lỗi kết nối database
```
Error: Cannot create PoolableConnectionFactory
```
- Kiểm tra MySQL đang chạy
- Kiểm tra username/password trong `application.yml`
- Kiểm tra database `AI_Agent_db` đã được tạo

### Lỗi port đã được sử dụng
```
Error: Port 8089 is already in use
```
- Đổi port trong `application.yml`: `server.port: 8090`
- Hoặc kill process đang dùng port 8089: `sudo lsof -ti:8089 | xargs kill -9`

### Lỗi JWT
```
Error: JWT signature does not match
```
- Xóa token cũ và login lại
- Kiểm tra `jwt.secret` trong `application.yml`

## 📫 Contact

Nguyễn Văn Hoàng - 110122078
