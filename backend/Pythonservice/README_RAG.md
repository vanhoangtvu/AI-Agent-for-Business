# Hệ thống RAG Prompts - Quản lý Prompts cho AI

## Tổng quan
Hệ thống cho phép quản lý các RAG prompts được lưu trong ChromaDB. Khi frontend gửi câu hỏi đến AI, hệ thống sẽ tự động lấy các prompts này và sử dụng làm hướng dẫn cho AI trả lời.

## API Endpoints

### 1. Push RAG Prompt (Thêm Prompt)
```bash
POST /rag/prompts
Content-Type: application/json

{
  "prompt": "When greeting users, always be friendly and professional. Start with 'Hello' and ask how you can help.",
  "category": "greeting",
  "tags": ["customer-service", "friendly"],
  "metadata": {
    "author": "Admin",
    "version": "1.0"
  }
}
```

**Response:**
```json
{
  "id": "uuid-generated-id",
  "prompt": "When greeting users...",
  "category": "greeting",
  "tags": ["customer-service", "friendly"],
  "metadata": {...},
  "message": "Prompt added successfully"
}
```

### 2. Get RAG Prompts (Xem Prompts)
```bash
# Lấy tất cả prompts
GET /rag/prompts

# Lọc theo category
GET /rag/prompts?category=greeting

# Lọc theo tags
GET /rag/prompts?tags=customer-service,friendly

# Giới hạn số lượng
GET /rag/prompts?limit=10

# Kết hợp filters
GET /rag/prompts?category=greeting&tags=friendly&limit=5
```

**Response:**
```json
[
  {
    "id": "uuid-1",
    "prompt": "When greeting users...",
    "category": "greeting",
    "tags": ["customer-service", "friendly"],
    "metadata": {...}
  },
  {
    "id": "uuid-2",
    "prompt": "For technical questions...",
    "category": "technical",
    "tags": ["support"],
    "metadata": {...}
  }
]
```

### 3. Get Prompt by ID (Xem 1 Prompt)
```bash
GET /rag/prompts/{prompt_id}
```

**Response:**
```json
{
  "id": "uuid-1",
  "prompt": "When greeting users...",
  "category": "greeting",
  "tags": ["customer-service"],
  "metadata": {...}
}
```

### 4. Update RAG Prompt (Cập nhật Prompt)
```bash
PUT /rag/prompts/{prompt_id}
Content-Type: application/json

{
  "prompt": "Updated prompt text",
  "category": "new-category",
  "tags": ["new-tag"],
  "metadata": {"version": "2.0"}
}
```

### 5. Delete RAG Prompt (Xóa 1 Prompt)
```bash
DELETE /rag/prompts/{prompt_id}
```

**Response:**
```json
{
  "id": "uuid-1",
  "message": "Prompt deleted successfully"
}
```

### 6. Delete All Prompts (Xóa tất cả hoặc theo category)
```bash
# Xóa tất cả
DELETE /rag/prompts

# Xóa theo category
DELETE /rag/prompts?category=greeting
```

### 7. Get Stats (Thống kê)
```bash
GET /rag/stats
```

**Response:**
```json
{
  "total_prompts": 10,
  "categories": {
    "greeting": 3,
    "technical": 5,
    "sales": 2
  },
  "collection_name": "rag_prompts"
}
```

### 8. Chat với RAG (AI sử dụng prompts)
```bash
POST /gemini/chat/rag
Content-Type: application/json

{
  "message": "Hello, can you help me?",
  "model": "gemini-2.5-flash"
}
```

**Response:**
```json
{
  "message": "Hello, can you help me?",
  "response": "Hello! I'd be happy to help you. What can I assist you with today?",
  "model": "gemini-2.5-flash"
}
```

### 9. Chat với RAG Streaming
```bash
POST /gemini/chat/rag/stream
Content-Type: application/json

{
  "message": "Tell me about your services",
  "model": "gemini-2.5-flash"
}
```

**Response (Server-Sent Events):**
```
data: {"type": "start", "model": "gemini-2.5-flash", "rag_enabled": true}

data: {"type": "chunk", "text": "Hello!"}

data: {"type": "chunk", "text": " I'd be happy to..."}

data: {"type": "done"}
```

## Workflow sử dụng

### Bước 1: Push RAG Prompts
```bash
# Push greeting prompt
curl -X POST http://localhost:5000/rag/prompts \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "Always greet users warmly and professionally",
    "category": "greeting",
    "tags": ["customer-service"]
  }'

# Push technical support prompt
curl -X POST http://localhost:5000/rag/prompts \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "For technical questions, provide clear step-by-step solutions",
    "category": "technical",
    "tags": ["support", "troubleshooting"]
  }'

# Push sales prompt
curl -X POST http://localhost:5000/rag/prompts \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "When discussing products, highlight key benefits and value",
    "category": "sales",
    "tags": ["marketing", "product"]
  }'
```

### Bước 2: Kiểm tra Prompts
```bash
# Xem tất cả prompts
curl http://localhost:5000/rag/prompts

# Xem stats
curl http://localhost:5000/rag/stats
```

### Bước 3: Chat với AI (AI tự động dùng RAG prompts)
```bash
# AI sẽ tự động áp dụng các prompts đã lưu
curl -X POST http://localhost:5000/gemini/chat/rag \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Hi there!"
  }'
```

AI sẽ trả lời theo hướng dẫn từ prompts (ví dụ: "Always greet users warmly...")

### Bước 4: Cập nhật hoặc Xóa Prompts
```bash
# Cập nhật prompt
curl -X PUT http://localhost:5000/rag/prompts/{prompt_id} \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "Updated greeting guidelines",
    "category": "greeting"
  }'

# Xóa prompt
curl -X DELETE http://localhost:5000/rag/prompts/{prompt_id}

# Xóa tất cả prompts trong category
curl -X DELETE "http://localhost:5000/rag/prompts?category=greeting"
```

## Cách hoạt động

1. **Push Prompts**: Admin/User push các prompts vào ChromaDB thông qua API
2. **Lưu trữ**: Prompts được lưu trong collection `rag_prompts` với metadata (category, tags, timestamps)
3. **Chat Request**: Khi frontend gửi message đến `/gemini/chat/rag`
4. **Load Prompts**: Hệ thống tự động lấy tất cả prompts từ ChromaDB
5. **System Instruction**: Các prompts được format thành system instruction cho Gemini
6. **AI Response**: Gemini trả lời dựa trên user message + RAG prompts guidelines

## Ví dụ thực tế

### Scenario: Customer Service Bot

```bash
# 1. Push prompts cho customer service
curl -X POST http://localhost:5000/rag/prompts \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "Always address customer by name if provided. Be empathetic to their concerns.",
    "category": "customer-service"
  }'

curl -X POST http://localhost:5000/rag/prompts \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "For complaints, acknowledge the issue first, apologize, then offer solution.",
    "category": "customer-service"
  }'

curl -X POST http://localhost:5000/rag/prompts \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "End conversations by asking if there is anything else you can help with.",
    "category": "customer-service"
  }'

# 2. Test chat - AI sẽ tự động follow các guidelines trên
curl -X POST http://localhost:5000/gemini/chat/rag \
  -H "Content-Type: application/json" \
  -d '{
    "message": "I am having issues with my order"
  }'
```

**AI Response sẽ theo prompts:**
- Acknowledge issue
- Be empathetic
- Offer solution
- Ask if there's anything else

## Testing với Swagger UI

1. Mở trình duyệt: `http://localhost:5000/docs`
2. Mở namespace **`/rag`**
3. Test các endpoints:
   - Push prompts
   - Get prompts
   - Update/Delete prompts
4. Mở namespace **`/gemini`**
5. Test `/gemini/chat/rag` để xem AI response với RAG prompts

## Lưu ý quan trọng

✅ **Không hardcode**: Tất cả prompts được quản lý qua API
✅ **Dynamic**: Thêm/sửa/xóa prompts bất cứ lúc nào
✅ **Category & Tags**: Tổ chức prompts theo category và tags
✅ **Auto-apply**: AI tự động áp dụng tất cả prompts khi chat
✅ **Persistent**: Prompts được lưu trong ChromaDB, không mất khi restart

## Cấu trúc dữ liệu

### Prompt Object
```json
{
  "id": "uuid",
  "prompt": "Prompt text",
  "category": "category-name",
  "tags": ["tag1", "tag2"],
  "metadata": {
    "created_at": "2024-12-02T10:00:00",
    "updated_at": "2024-12-02T10:00:00",
    "custom_field": "value"
  }
}
```

### Metadata Auto-generated
- `created_at`: ISO timestamp
- `updated_at`: ISO timestamp
- `category`: Default "general" if not provided
- `tags`: Comma-separated string in ChromaDB
