# 🚀 AI Agent for Business - Backend

Backend API cho hệ thống AI Agent for Business, xây dựng với **Spring Boot 3.x**.

## 📋 Mục lục

- [Công nghệ](#-công-nghệ)
- [Cấu trúc thư mục](#-cấu-trúc-thư-mục)
- [Cài đặt](#-cài-đặt)
- [Chạy ứng dụng](#-chạy-ứng-dụng)
- [API Documentation](#-api-documentation)
- [Database](#-database)
- [Security](#-security)

---

## 🛠️ Công nghệ

- **Spring Boot 3.2.0** - Framework chính
- **Spring Data JPA** - ORM với Hibernate
- **Spring Security** - Bảo mật và JWT
- **Spring WebSocket** - Real-time communication
- **MySQL 8.0+** - Database chính
- **Redis** - Caching & Session
- **JWT** - Token-based authentication
- **Lombok** - Giảm boilerplate code
- **MapStruct** - DTO mapping
- **Swagger/OpenAPI** - API documentation
- **Maven** - Build tool

---

## 📁 Cấu trúc thư mục

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/aiagent/business/
│   │   │   ├── config/              # Configuration classes
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── WebSocketConfig.java
│   │   │   ├── controller/          # REST Controllers
│   │   │   │   └── AuthController.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── request/
│   │   │   │   │   ├── LoginRequest.java
│   │   │   │   │   └── RegisterRequest.java
│   │   │   │   └── response/
│   │   │   │       ├── ApiResponse.java
│   │   │   │       └── AuthResponse.java
│   │   │   ├── exception/           # Exception handlers
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── model/               # JPA Entities
│   │   │   │   ├── User.java
│   │   │   │   ├── Business.java
│   │   │   │   ├── Customer.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── Conversation.java
│   │   │   │   └── Message.java
│   │   │   ├── repository/          # JPA Repositories
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── BusinessRepository.java
│   │   │   │   ├── CustomerRepository.java
│   │   │   │   ├── ProductRepository.java
│   │   │   │   ├── OrderRepository.java
│   │   │   │   ├── ConversationRepository.java
│   │   │   │   └── MessageRepository.java
│   │   │   ├── security/            # Security & JWT
│   │   │   │   ├── JwtUtil.java
│   │   │   │   └── JwtAuthenticationFilter.java
│   │   │   ├── service/             # Business logic
│   │   │   │   └── AuthService.java
│   │   │   ├── utils/               # Utility classes
│   │   │   └── AiAgentBusinessApplication.java
│   │   └── resources/
│   │       └── application.yml      # Application config
│   └── test/                        # Unit tests
├── pom.xml                          # Maven dependencies
└── README.md                        # This file
```

---

## 🔧 Cài đặt

### Yêu cầu hệ thống

- **Java**: 17 hoặc cao hơn
- **Maven**: 3.8+
- **MySQL**: 8.0+
- **Redis**: 7.0+

### Bước 1: Clone repository

```bash
cd backend
```

### Bước 2: Cấu hình database

Tạo database MySQL:

```sql
CREATE DATABASE ai_agent_business CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Bước 3: Cấu hình môi trường

Chỉnh sửa `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ai_agent_business
    username: your_db_username
    password: your_db_password
  
  data:
    redis:
      host: localhost
      port: 6379
      password: your_redis_password

jwt:
  secret: your-jwt-secret-key-here
  expiration: 86400000

gemini:
  api-key: your-gemini-api-key
```

Hoặc sử dụng biến môi trường:

```bash
export DB_USERNAME=root
export DB_PASSWORD=password
export JWT_SECRET=your-jwt-secret
export GEMINI_API_KEY=your-api-key
export PINECONE_API_KEY=your-pinecone-key
```

### Bước 4: Build project

```bash
mvn clean install
```

---

## ▶️ Chạy ứng dụng

### Development mode

```bash
mvn spring-boot:run
```

### Production mode

```bash
mvn clean package
java -jar target/ai-agent-business-1.0.0.jar
```

Server sẽ chạy tại: **http://localhost:8088/api/v1**

---

## 📚 API Documentation

### Swagger UI

Truy cập API documentation tại:

**http://localhost:8088/swagger-ui.html**

### API Endpoints

#### Authentication

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/auth/register` | Đăng ký tài khoản | ❌ |
| POST | `/auth/login` | Đăng nhập | ❌ |
| GET | `/auth/me` | Lấy thông tin user | ✅ |

#### Business (Role: BUSINESS)

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/business/customers` | Danh sách khách hàng | ✅ |
| GET | `/business/products` | Danh sách sản phẩm | ✅ |
| GET | `/business/orders` | Danh sách đơn hàng | ✅ |
| GET | `/business/conversations` | Danh sách hội thoại | ✅ |

#### Customer (Role: CUSTOMER)

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/customer/orders` | Đơn hàng của tôi | ✅ |
| GET | `/customer/profile` | Thông tin cá nhân | ✅ |

#### Admin (Role: ADMIN)

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/admin/users` | Danh sách người dùng | ✅ |
| GET | `/admin/businesses` | Danh sách doanh nghiệp | ✅ |
| GET | `/admin/stats` | Thống kê hệ thống | ✅ |

---

## 🗄️ Database

### Schema

Database gồm **22 bảng** (đã tối ưu từ 26):

**Core Tables:**
- `users`, `businesses`, `employees`
- `customers`, `customer_tags`, `products`, `product_categories`, `orders`, `order_items`
- `conversations`, `messages`
- `zalo_oa_configs`, `zalo_personal_sessions`
- `documents`, `document_chunks`
- `ai_insights`, `analytics_logs`
- `notifications`
- `audit_logs`, `system_configs`, `api_keys`

### Migrations

JPA sẽ tự động tạo bảng khi chạy ứng dụng lần đầu (với `ddl-auto: update`).

Để tạo bảng thủ công, xem file `DATABASE.md` ở root project.

---

## 🔐 Security

### JWT Authentication

#### Flow:

1. User đăng ký/đăng nhập → Nhận JWT token
2. Client gửi token trong header: `Authorization: Bearer <token>`
3. Server validate token và authenticate user

#### Token Structure:

```json
{
  "sub": "user@example.com",
  "userId": 1,
  "role": "BUSINESS",
  "businessId": 5,
  "iat": 1634567890,
  "exp": 1634654290
}
```

### RBAC (Role-Based Access Control)

#### Roles:

- **ADMIN**: Quản trị hệ thống
- **BUSINESS**: Chủ doanh nghiệp
- **CUSTOMER**: Khách hàng

#### Example:

```java
@PreAuthorize("hasRole('BUSINESS')")
@GetMapping("/business/customers")
public ResponseEntity<List<Customer>> getCustomers() {
    // Only BUSINESS role can access
}
```

### CORS

CORS được cấu hình cho:
- `http://localhost:3008` (Frontend Next.js)
- `http://localhost:3000` (Alternative)

---

## 🧪 Testing

### Run tests

```bash
mvn test
```

### Test coverage

```bash
mvn jacoco:report
```

Report sẽ được tạo tại: `target/site/jacoco/index.html`

---

## 📦 Build & Deploy

### Build Docker image

```bash
docker build -t ai-agent-backend:1.0.0 .
```

### Run with Docker Compose

```bash
docker-compose up -d
```

---

## 🔗 Liên kết

- [README chính](../README.md)
- [DATABASE.md](../DATABASE.md)
- [USE_CASE_DIAGRAM.md](../USE_CASE_DIAGRAM.md)

---

## 📝 License

MIT License

---

## 👥 Contributors

AI Agent for Business Team

---

**Happy Coding! 🚀**

