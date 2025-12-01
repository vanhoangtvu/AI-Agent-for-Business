# Python API Service with Swagger

API service được xây dựng bằng Flask và tích hợp Swagger documentation.

## Cài đặt

1. Tạo virtual environment:
```bash
python -m venv venv
source venv/bin/activate  # Linux/Mac
```

2. Cài đặt dependencies:
```bash
pip install -r requirements.txt
```

## Chạy ứng dụng

```bash
python app.py
```

API sẽ chạy tại: `http://localhost:5000`

## Swagger Documentation

Truy cập Swagger UI tại: `http://localhost:5000/docs`

## API Endpoints

- `GET /api/health` - Health check
- `GET /api/users` - Lấy danh sách users
- `POST /api/users` - Tạo user mới
- `GET /api/users/{user_id}` - Lấy thông tin user theo ID
- `PUT /api/users/{user_id}` - Cập nhật user
- `DELETE /api/users/{user_id}` - Xóa user

## Ví dụ sử dụng

### Tạo user mới
```bash
curl -X POST http://localhost:5000/api/users \
  -H "Content-Type: application/json" \
  -d '{"name": "Test User", "email": "test@example.com"}'
```

### Lấy danh sách users
```bash
curl http://localhost:5000/api/users
```
