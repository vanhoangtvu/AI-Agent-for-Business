# Spring Boot Service

Dự án Spring Boot với Java 17 và MySQL.

## Yêu cầu

- Java 17
- Maven 3.6+
- MySQL Server
- MySQL root password: 1111

## Cấu trúc dự án

```
src/
├── main/
│   ├── java/com/business/springservice/
│   │   ├── controller/     # REST API Controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── entity/         # JPA Entities
│   │   ├── repository/     # Spring Data JPA Repositories
│   │   ├── service/        # Business Logic Services
│   │   └── SpringServiceApplication.java
│   └── resources/
│       └── application.properties
```

## Cấu hình Database

Database sẽ tự động được tạo với tên `springservice_db` khi ứng dụng chạy lần đầu.

**MySQL Configuration:**
- Host: localhost:3306
- Database: springservice_db
- Username: root
- Password: 1111

## Chạy ứng dụng

### 1. Sử dụng Maven Wrapper (khuyến nghị)

```bash
./mvnw spring-boot:run
```

### 2. Sử dụng Maven đã cài đặt

```bash
mvn spring-boot:run
```

### 3. Build và chạy JAR file

```bash
./mvnw clean package
java -jar target/springservice-0.0.1-SNAPSHOT.jar
```

## API Endpoints

### User Management

- **GET** `/api/users` - Lấy danh sách tất cả users
- **GET** `/api/users/{id}` - Lấy thông tin user theo ID
- **POST** `/api/users` - Tạo user mới
- **PUT** `/api/users/{id}` - Cập nhật thông tin user
- **DELETE** `/api/users/{id}` - Xóa user

### Ví dụ Request

**Tạo user mới:**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "password123"
  }'
```

**Lấy danh sách users:**
```bash
curl http://localhost:8080/api/users
```

## Dependencies

- Spring Boot Web
- Spring Data JPA
- MySQL Connector
- Lombok
- Spring Boot DevTools

## Cổng mặc định

Ứng dụng chạy trên cổng **8080**

Access: http://localhost:8080
