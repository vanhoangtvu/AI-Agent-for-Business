# 🚀 Hướng dẫn Cấu hình Backend - AI Agent for Business

## 📋 Mục lục

1. [Yêu cầu hệ thống](#-yêu-cầu-hệ-thống)
2. [Cài đặt Dependencies](#-cài-đặt-dependencies)
3. [Cấu hình Database](#-cấu-hình-database)
4. [Cấu hình Redis](#-cấu-hình-redis)
5. [Cấu hình Environment Variables](#-cấu-hình-environment-variables)
6. [Cấu hình JWT](#-cấu-hình-jwt)
7. [Cấu hình Google Gemini AI](#-cấu-hình-google-gemini-ai)
8. [Cấu hình Vector Database](#-cấu-hình-vector-database)
9. [Cấu hình Zalo Integration](#-cấu-hình-zalo-integration)
10. [Chạy ứng dụng](#-chạy-ứng-dụng)
11. [Troubleshooting](#-troubleshooting)

---

## 🔧 Yêu cầu hệ thống

### Phần mềm bắt buộc:

- **Java**: 17 hoặc cao hơn
  ```bash
  java -version
  # Nên hiện: openjdk version "17.x.x" hoặc cao hơn
  ```

- **Maven**: 3.8+
  ```bash
  mvn -version
  # Nên hiện: Apache Maven 3.8.x
  ```

- **MySQL**: 8.0+
  ```bash
  mysql --version
  # Nên hiện: mysql Ver 8.0.x
  ```

- **Redis**: 7.0+
  ```bash
  redis-server --version
  # Nên hiện: Redis server v=7.0.x
  ```

### Phần mềm tùy chọn:

- **PostgreSQL**: 14+ (nếu dùng pgvector)
- **Docker & Docker Compose**: Nếu muốn chạy với container

---

## 📦 Cài đặt Dependencies

### 1. Clone & Navigate

```bash
cd backend
```

### 2. Build Maven Project

```bash
mvn clean install
```

Nếu thành công, bạn sẽ thấy:
```
[INFO] BUILD SUCCESS
[INFO] Total time: xx.xxx s
```

---

## 🗄️ Cấu hình Database

### Bước 1: Cài đặt MySQL 8.0+

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install mysql-server
sudo systemctl start mysql
sudo systemctl enable mysql
```

**MacOS:**
```bash
brew install mysql
brew services start mysql
```

**Windows:**
- Download từ: https://dev.mysql.com/downloads/mysql/
- Cài đặt MySQL Installer

### Bước 2: Tạo Database

```bash
# Đăng nhập MySQL
mysql -u root -p

# Trong MySQL shell:
CREATE DATABASE AI_Agent_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# Tạo user riêng (khuyến nghị cho production)
CREATE USER 'aiagent_user'@'localhost' IDENTIFIED BY 'your_strong_password';
GRANT ALL PRIVILEGES ON AI_Agent_db.* TO 'aiagent_user'@'localhost';
FLUSH PRIVILEGES;

# Kiểm tra
SHOW DATABASES;
USE AI_Agent_db;

# Thoát
EXIT;
```

### Bước 3: Cấu hình Connection

File: `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/AI_Agent_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=aiagent_user
spring.datasource.password=your_strong_password
```

**Hoặc dùng Environment Variables:**

```bash
export DB_USERNAME=aiagent_user
export DB_PASSWORD=your_strong_password
```

### Bước 4: Test Connection

```bash
mysql -u aiagent_user -p AI_Agent_db
# Nhập password và test
```

---

## 🔴 Cấu hình Redis

### Bước 1: Cài đặt Redis

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install redis-server
sudo systemctl start redis-server
sudo systemctl enable redis-server
```

**MacOS:**
```bash
brew install redis
brew services start redis
```

**Windows:**
- Download từ: https://github.com/microsoftarchive/redis/releases
- Hoặc dùng WSL

### Bước 2: Test Redis

```bash
redis-cli ping
# Nên trả về: PONG
```

### Bước 3: Cấu hình Password (Khuyến nghị)

```bash
# Sửa file redis.conf
sudo nano /etc/redis/redis.conf

# Tìm dòng:
# requirepass foobared

# Bỏ comment và đổi password:
requirepass your_redis_password

# Restart Redis
sudo systemctl restart redis-server

# Test với password
redis-cli
AUTH your_redis_password
PING
# Nên trả về: PONG
```

### Bước 4: Cấu hình trong Application

```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.data.redis.password=your_redis_password
```

**Hoặc:**

```bash
export REDIS_HOST=localhost
export REDIS_PORT=6379
export REDIS_PASSWORD=your_redis_password
```

---

## 🔐 Cấu hình Environment Variables

### Bước 1: Copy file .env.example

```bash
cp .env.example .env
```

### Bước 2: Chỉnh sửa .env

```bash
nano .env
```

### Bước 3: Điền thông tin:

```env
# Database
DB_USERNAME=aiagent_user
DB_PASSWORD=your_strong_password

# Redis
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=your_redis_password

# JWT (Tạo secret key mạnh)
JWT_SECRET=ThayDoiBangSecretKeyManh123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefg

# Gemini AI
GEMINI_API_KEY=your-actual-gemini-api-key

# Pinecone
PINECONE_API_KEY=your-actual-pinecone-api-key
PINECONE_ENV=us-east1-gcp
```

### Bước 4: Load Environment Variables

**Linux/MacOS:**
```bash
export $(cat .env | xargs)
```

**Windows PowerShell:**
```powershell
Get-Content .env | ForEach-Object {
    $name, $value = $_.split('=')
    Set-Content env:\$name $value
}
```

---

## 🔑 Cấu hình JWT

### Tạo JWT Secret Key mạnh

```bash
# Dùng openssl (Linux/MacOS)
openssl rand -base64 64

# Hoặc dùng online: https://www.grc.com/passwords.htm
```

### Cấu hình:

```properties
jwt.secret=YourGeneratedSecretKeyHere
jwt.expiration=86400000
jwt.refresh-expiration=604800000
```

**Giải thích:**
- `jwt.expiration=86400000` → 24 giờ (24 * 60 * 60 * 1000)
- `jwt.refresh-expiration=604800000` → 7 ngày

---

## 🤖 Cấu hình Google Gemini AI

### Bước 1: Lấy API Key

1. Truy cập: https://ai.google.dev/
2. Đăng nhập với Google Account
3. Click "Get API Key"
4. Create API Key trong project của bạn
5. Copy API Key

### Bước 2: Cấu hình

```properties
gemini.api-key=AIzaSyXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
gemini.model=gemini-pro
gemini.embedding-model=text-embedding-004
```

**Hoặc:**

```bash
export GEMINI_API_KEY=AIzaSyXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

### Bước 3: Test API Key

```bash
curl "https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent?key=YOUR_API_KEY" \
  -H 'Content-Type: application/json' \
  -d '{"contents":[{"parts":[{"text":"Hello"}]}]}'
```

---

## 🗂️ Cấu hình Vector Database

### Option 1: Pinecone (Khuyến nghị - Dễ dùng)

#### Bước 1: Tạo tài khoản Pinecone

1. Truy cập: https://www.pinecone.io/
2. Sign up (Free tier: 1 index, 100k vectors)
3. Tạo API Key

#### Bước 2: Tạo Index

```bash
# Truy cập Pinecone Console
# Create Index:
# - Name: ai-agent-business
# - Dimensions: 768 (cho Gemini text-embedding-004)
# - Metric: cosine
# - Pod Type: Starter (free)
```

#### Bước 3: Cấu hình

```properties
vector-db.type=PINECONE
vector-db.pinecone.api-key=your-pinecone-api-key
vector-db.pinecone.environment=us-east1-gcp
vector-db.pinecone.index-name=ai-agent-business
```

### Option 2: pgvector (Self-hosted)

#### Bước 1: Cài PostgreSQL + pgvector

**Ubuntu:**
```bash
sudo apt install postgresql postgresql-contrib
sudo -u postgres psql

# Trong psql:
CREATE DATABASE vector_db;
\c vector_db
CREATE EXTENSION vector;
```

#### Bước 2: Cấu hình

```properties
vector-db.type=PGVECTOR
vector-db.pgvector.url=jdbc:postgresql://localhost:5432/vector_db
vector-db.pgvector.username=postgres
vector-db.pgvector.password=your_postgres_password
```

---

## 📱 Cấu hình Zalo Integration

### Zalo Official Account (OA)

#### Bước 1: Đăng ký Zalo OA

1. Truy cập: https://oa.zalo.me/
2. Đăng ký tài khoản doanh nghiệp
3. Tạo Official Account

#### Bước 2: Lấy App ID & Secret

1. Vào OA Settings → Developers
2. Create App
3. Copy App ID và App Secret

#### Bước 3: Cấu hình Webhook

```properties
zalo.oa.app-id=your-zalo-app-id
zalo.oa.app-secret=your-zalo-app-secret
zalo.oa.webhook-url=https://your-domain.com/api/v1/webhook/zalo
```

### Zalo Personal Account

```properties
zalo.personal.enabled=true
zalo.personal.qr-timeout=300000
```

**Lưu ý:** Zalo Personal sử dụng unofficial API, có rủi ro bị khóa tài khoản.

---

## ▶️ Chạy ứng dụng

### Development Mode

```bash
# Option 1: Maven
mvn spring-boot:run

# Option 2: IDE (IntelliJ IDEA)
# Right-click AiAgentBusinessApplication.java → Run
```

### Production Mode

```bash
# Build
mvn clean package -DskipTests

# Run
java -jar target/ai-agent-business-1.0.0.jar
```

### Với Docker

```bash
# Build image
docker build -t ai-agent-backend:1.0.0 .

# Run
docker run -p 8088:8088 \
  -e DB_USERNAME=root \
  -e DB_PASSWORD=password \
  -e REDIS_HOST=redis \
  ai-agent-backend:1.0.0
```

---

## ✅ Kiểm tra sau khi chạy

### 1. Server Running

```bash
# Check logs
# Nên thấy:
# 🚀 AI Agent for Business API is running on http://localhost:8088/api/v1
# 📚 Swagger UI: http://localhost:8088/swagger-ui.html
```

### 2. Health Check

```bash
curl http://localhost:8088/api/v1/actuator/health

# Nên trả về:
# {"status":"UP"}
```

### 3. Swagger UI

Mở trình duyệt: http://localhost:8088/swagger-ui.html

### 4. Test API

```bash
# Register
curl -X POST http://localhost:8088/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123",
    "fullName": "Test User",
    "role": "BUSINESS",
    "businessName": "Test Business"
  }'

# Login
curl -X POST http://localhost:8088/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

---

## 🐛 Troubleshooting

### Lỗi: "Access denied for user"

**Nguyên nhân:** MySQL username/password sai

**Giải pháp:**
```bash
# Reset MySQL password
sudo mysql
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'new_password';
FLUSH PRIVILEGES;
```

### Lỗi: "Communications link failure"

**Nguyên nhân:** MySQL không chạy

**Giải pháp:**
```bash
sudo systemctl start mysql
sudo systemctl status mysql
```

### Lỗi: "Could not connect to Redis"

**Nguyên nhân:** Redis không chạy hoặc password sai

**Giải pháp:**
```bash
# Start Redis
sudo systemctl start redis-server

# Test connection
redis-cli
AUTH your_password
PING
```

### Lỗi: "Table doesn't exist"

**Nguyên nhân:** Hibernate chưa tạo bảng

**Giải pháp:**
```properties
# Trong application.properties, đảm bảo:
spring.jpa.hibernate.ddl-auto=update
```

### Lỗi: "Port 8088 already in use"

**Giải pháp:**
```bash
# Tìm process đang dùng port
lsof -i :8088

# Kill process
kill -9 <PID>

# Hoặc đổi port
server.port=8089
```

### Lỗi: JWT Token Invalid

**Giải pháp:**
- Check JWT secret có đủ dài (>= 256 bits)
- Check token expiration
- Check Authorization header format: `Bearer <token>`

---

## 📊 Monitoring & Logs

### View Logs

```bash
# Real-time logs
tail -f logs/ai-agent-business.log

# Search logs
grep "ERROR" logs/ai-agent-business.log
```

### Actuator Endpoints

- Health: http://localhost:8088/api/v1/actuator/health
- Info: http://localhost:8088/api/v1/actuator/info
- Metrics: http://localhost:8088/api/v1/actuator/metrics

---

## 🔗 Tài liệu tham khảo

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [MySQL 8.0 Reference Manual](https://dev.mysql.com/doc/refman/8.0/en/)
- [Redis Documentation](https://redis.io/documentation)
- [Google Gemini AI](https://ai.google.dev/docs)
- [Pinecone Documentation](https://docs.pinecone.io/)
- [Zalo Official Account](https://developers.zalo.me/docs/official-account)

---

## 📞 Hỗ trợ

Nếu gặp vấn đề, vui lòng:
1. Check logs: `logs/ai-agent-business.log`
2. Check Troubleshooting section
3. Create issue trên GitHub

---

**Happy Coding! 🚀**

