# AI Agent Backend - Spring Boot Service

## 📋 Tổng Quan

Backend service được xây dựng bằng Spring Boot 3.2, cung cấp API RESTful và WebSocket cho hệ thống AI Agent.

## 🚀 Chạy Ứng Dụng

### Yêu Cầu
- Java 17+
- Maven 3.8+
- MySQL 8.0 (Database: `AI_Agent_db`, Password: `1111`)
- Redis (optional - nếu dùng caching)

### Build và Run

```bash
# Build project
mvn clean install

# Run application
mvn spring-boot:run

# Hoặc run JAR file
java -jar target/ai-agent-backend-1.0.0.jar
```

### Truy Cập

- **API Base URL**: http://localhost:8100/api
- **Swagger UI**: http://localhost:8100/swagger-ui.html
- **H2 Console** (dev): http://localhost:8100/h2-console
- **Actuator**: http://localhost:8100/actuator

## 📁 Cấu Trúc

```
src/main/java/com/aiagent/
├── config/              # Configuration classes
│   ├── DatabaseConfig.java
│   ├── SecurityConfig.java
│   ├── WebSocketConfig.java
│   ├── AIServiceConfig.java
│   └── RedisConfig.java
├── controller/          # REST Controllers
│   ├── api/
│   │   ├── AuthController.java
│   │   ├── ChatController.java
│   │   ├── DocumentController.java
│   │   ├── StrategicController.java
│   │   └── UserController.java
│   └── websocket/
│       └── ChatWebSocketHandler.java
├── service/             # Business Logic
│   ├── impl/
│   │   ├── AuthServiceImpl.java
│   │   ├── ChatServiceImpl.java
│   │   ├── DocumentServiceImpl.java
│   │   └── StrategicServiceImpl.java
│   ├── AuthService.java
│   ├── ChatService.java
│   ├── DocumentService.java
│   ├── AIClientService.java
│   └── VectorSearchService.java
├── repository/          # Data Access Layer
│   ├── UserRepository.java
│   ├── DocumentRepository.java
│   ├── ConversationRepository.java
│   └── MessageRepository.java
├── model/               # Entities & DTOs
│   ├── entity/
│   │   ├── User.java
│   │   ├── Document.java
│   │   └── Conversation.java
│   └── dto/
│       ├── AuthRequest.java
│       ├── AuthResponse.java
│       ├── ChatRequest.java
│       ├── ChatResponse.java
│       ├── DocumentRequest.java
│       └── DocumentResponse.java
├── security/            # Security & JWT
│   ├── JwtService.java
│   ├── JwtAuthenticationFilter.java
│   ├── JwtAuthenticationEntryPoint.java
│   └── CustomUserDetailsService.java
├── exception/           # Exception Handling
└── Application.java     # Main Application
```

## 🔐 Authentication

API sử dụng JWT (JSON Web Token) để xác thực.

### Endpoints

```http
POST /api/auth/register    # Đăng ký
POST /api/auth/login       # Đăng nhập
POST /api/auth/refresh     # Refresh token
POST /api/auth/logout      # Đăng xuất
```

### Example Request

```bash
# Login
curl -X POST http://localhost:8100/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'

# Sử dụng token
curl -X GET http://localhost:8100/api/documents \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## 📚 API Endpoints

### Authentication
- `POST /api/auth/register` - Đăng ký tài khoản
- `POST /api/auth/login` - Đăng nhập
- `POST /api/auth/refresh` - Refresh token
- `POST /api/auth/logout` - Đăng xuất

### Documents
- `GET /api/documents` - Lấy danh sách tài liệu
- `GET /api/documents/{id}` - Lấy chi tiết tài liệu
- `POST /api/documents/upload` - Upload tài liệu
- `PUT /api/documents/{id}` - Cập nhật tài liệu
- `DELETE /api/documents/{id}` - Xóa tài liệu
- `POST /api/documents/search` - Tìm kiếm tài liệu

### Chat
- `POST /api/chat/message` - Gửi message
- `GET /api/chat/conversations` - Lấy danh sách conversations
- `GET /api/chat/conversations/{id}` - Lấy chi tiết conversation
- `DELETE /api/chat/conversations/{id}` - Xóa conversation
- `WS /ws/chat` - WebSocket endpoint

### Strategic Analysis
- `POST /api/strategic/analyze` - Phân tích chiến lược
- `GET /api/strategic/reports` - Lấy danh sách báo cáo
- `GET /api/strategic/reports/{id}` - Lấy chi tiết báo cáo

### Users (Admin)
- `GET /api/users` - Lấy danh sách users
- `GET /api/users/{id}` - Lấy thông tin user
- `PUT /api/users/{id}` - Cập nhật user
- `DELETE /api/users/{id}` - Xóa user

## ⚙️ Configuration

File `application.yml` chứa cấu hình:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/AI_Agent_db
    username: root
    password: 1111

app:
  jwt:
    secret: your_jwt_secret
    expiration: 86400000  # 24 hours
  
  ai-service:
    url: http://localhost:8000
```

## 🔧 Dependencies Chính

- Spring Boot 3.2.0
- Spring Security + JWT
- Spring Data JPA + MySQL
- Spring WebSocket
- Spring Data Redis
- Lombok
- Springdoc OpenAPI (Swagger)

## 🧪 Testing

```bash
# Run tests
mvn test

# Run tests với coverage
mvn clean verify
```

## 📝 Database Schema

### Users
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    phone_number VARCHAR(20),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

### Documents
```sql
CREATE TABLE documents (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_type VARCHAR(100),
    file_size BIGINT,
    title VARCHAR(500),
    description TEXT,
    category VARCHAR(100),
    status VARCHAR(50),
    created_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

### Conversations & Messages
```sql
CREATE TABLE conversations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    last_message_at TIMESTAMP,
    created_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE messages (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    conversation_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    content TEXT NOT NULL,
    context TEXT,
    sentiment VARCHAR(50),
    confidence DOUBLE,
    created_at TIMESTAMP,
    FOREIGN KEY (conversation_id) REFERENCES conversations(id)
);
```

## 🐛 Troubleshooting

### Database Connection Error
```bash
# Kiểm tra MySQL đang chạy
mysql -u root -p

# Tạo database nếu chưa có
CREATE DATABASE AI_Agent_db;
```

### Port đã được sử dụng
```bash
# Port hiện tại: 8100
# Thay đổi port trong application.yml nếu cần
server:
  port: 8100
```

## 👨‍💻 Development

```bash
# Enable development mode
spring:
  profiles:
    active: dev

# Hot reload
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
```

## 📦 Build Production

```bash
# Build JAR
mvn clean package -DskipTests

# Build Docker image
docker build -t ai-agent-backend .

# Run Docker container
docker run -p 8080:8080 ai-agent-backend
```

## 📄 License

MIT License - Nguyễn Văn Hoàng - Đại Học Trà Vinh

---

Made with ❤️ by Nguyễn Văn Hoàng

