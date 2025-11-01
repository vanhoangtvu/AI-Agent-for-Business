# 👤 Customer Features - Tính Năng Dành Cho Khách Hàng

## 🎯 Tổng Quan

Khách hàng (CUSTOMER) có giao diện riêng với các tính năng phù hợp để:
- 💬 Chat với AI chatbot
- 📚 Xem tài liệu được chia sẻ
- 👤 Quản lý profile cá nhân
- 📞 Liên hệ support

---

## 📊 Customer Dashboard

### Endpoint
```
GET /api/customer/dashboard
```

### Response Data

```json
{
  "customerInfo": {
    "fullName": "Nguyễn Văn A",
    "email": "customer@aiagent.com",
    "phoneNumber": "0903333333",
    "joinedDate": "2024-01-15T10:00:00",
    "status": "Active"
  },
  "totalConversations": 15,
  "totalMessages": 120,
  "recentConversations": [
    {
      "id": 1,
      "title": "Hỏi về sản phẩm X",
      "lastMessageAt": "2024-11-01T14:30:00",
      "messageCount": 8,
      "active": true
    }
  ],
  "sharedDocuments": [
    {
      "id": 1,
      "title": "Catalog sản phẩm 2024",
      "category": "Products",
      "fileType": "application/pdf",
      "sharedAt": "2024-10-01T09:00:00"
    }
  ],
  "availableActions": [
    "Start New Conversation",
    "View Chat History",
    "Browse Documents",
    "Update Profile",
    "Contact Support"
  ],
  "supportInfo": {
    "supportEmail": "support@aiagent.com",
    "supportPhone": "1900-xxxx",
    "businessHours": "Mon-Fri: 8AM - 6PM",
    "chatAvailable": true
  }
}
```

---

## 🔧 API Endpoints

### 1. Dashboard & Profile

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/customer/dashboard` | Customer dashboard - Trang chủ |
| GET | `/api/customer/profile` | Lấy profile cá nhân |
| PUT | `/api/customer/profile` | Cập nhật profile |

### 2. Conversations

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/customer/conversations` | Lấy tất cả conversations |
| POST | `/api/chat/conversations` | Tạo conversation mới |
| POST | `/api/chat/message` | Gửi message |

### 3. Documents

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/customer/documents` | Xem documents được chia sẻ |
| GET | `/api/customer/documents/{id}` | Xem chi tiết document |

### 4. Support

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/customer/support` | Lấy thông tin support |
| GET | `/api/customer/actions` | Lấy quick actions |

---

## 💬 Use Cases

### 1. Customer Landing (Dashboard)

**Flow:**
```
1. Customer login
2. Redirect to /customer/dashboard
3. Hiển thị:
   - Welcome message với tên
   - Recent conversations
   - Shared documents
   - Quick actions buttons
   - Support contact info
```

**Example:**
```bash
# Login
TOKEN=$(curl -s -X POST http://localhost:8100/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"customer@aiagent.com","password":"customer123"}' \
  | jq -r '.token')

# Get Dashboard
curl -X GET http://localhost:8100/api/customer/dashboard \
  -H "Authorization: Bearer $TOKEN" \
  | jq
```

---

### 2. Start New Chat

**Flow:**
```
1. Click "Start New Conversation"
2. POST /api/chat/conversations?title="Hỏi về sản phẩm"
3. Nhận conversationId
4. Gửi message đầu tiên
5. Nhận AI response
```

**Example:**
```bash
# Create conversation
CONV_ID=$(curl -s -X POST "http://localhost:8100/api/chat/conversations?title=Hỏi về sản phẩm" \
  -H "Authorization: Bearer $TOKEN" \
  | jq -r '.id')

# Send message
curl -X POST http://localhost:8100/api/chat/message \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d "{
    \"conversationId\": $CONV_ID,
    \"message\": \"Giá sản phẩm X là bao nhiêu?\",
    \"useContext\": true
  }" | jq
```

---

### 3. Browse Documents

**Flow:**
```
1. Click "Browse Documents"
2. GET /api/customer/documents
3. Hiển thị danh sách documents
4. Click vào document để xem chi tiết
5. Có thể download/view (read-only)
```

**Example:**
```bash
# List documents
curl -X GET http://localhost:8100/api/customer/documents \
  -H "Authorization: Bearer $TOKEN" \
  | jq

# View document detail
curl -X GET http://localhost:8100/api/customer/documents/1 \
  -H "Authorization: Bearer $TOKEN" \
  | jq
```

---

### 4. Update Profile

**Flow:**
```
1. Click "Update Profile"
2. GET /api/customer/profile (load current data)
3. User sửa thông tin (fullName, phoneNumber)
4. PUT /api/customer/profile
5. Show success message
```

**Example:**
```bash
# Get current profile
curl -X GET http://localhost:8100/api/customer/profile \
  -H "Authorization: Bearer $TOKEN" \
  | jq

# Update profile
curl -X PUT http://localhost:8100/api/customer/profile \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Nguyễn Văn A Updated",
    "phoneNumber": "0901234567"
  }' | jq
```

---

### 5. Contact Support

**Flow:**
```
1. Click "Contact Support"
2. GET /api/customer/support
3. Hiển thị support info:
   - Email
   - Phone
   - Business hours
   - Chat availability
```

**Example:**
```bash
curl -X GET http://localhost:8100/api/customer/support \
  -H "Authorization: Bearer $TOKEN" \
  | jq
```

---

## 🎨 UI Layout Suggestions

### Customer Dashboard Layout

```
┌─────────────────────────────────────────────────────┐
│  ☰  AI Agent for Business              👤 Nguyễn V.A│
├─────────────────────────────────────────────────────┤
│                                                      │
│  👋 Welcome back, Nguyễn Văn A!                     │
│                                                      │
│  ┌──────────────────────────────────────────────┐  │
│  │  Quick Actions                                │  │
│  │  [💬 Start Chat] [📚 Browse Docs] [👤 Profile]│  │
│  └──────────────────────────────────────────────┘  │
│                                                      │
│  📊 Your Activity                                   │
│  ├─ Total Conversations: 15                         │
│  └─ Total Messages: 120                             │
│                                                      │
│  💬 Recent Conversations                            │
│  ┌──────────────────────────────────────────────┐  │
│  │ Hỏi về sản phẩm X        8 msgs  │  2 hours  │  │
│  │ Tư vấn dịch vụ          12 msgs  │  1 day    │  │
│  │ Thông tin giá cả         5 msgs  │  3 days   │  │
│  └──────────────────────────────────────────────┘  │
│                                                      │
│  📁 Shared Documents                                │
│  ┌──────────────────────────────────────────────┐  │
│  │ 📄 Catalog sản phẩm 2024                     │  │
│  │ 📄 Bảng giá dịch vụ                          │  │
│  │ 📄 Hướng dẫn sử dụng                         │  │
│  └──────────────────────────────────────────────┘  │
│                                                      │
│  📞 Need Help?                                      │
│  ├─ 📧 support@aiagent.com                         │
│  ├─ ☎️  1900-xxxx                                  │
│  └─ 🕐 Mon-Fri: 8AM - 6PM                          │
└─────────────────────────────────────────────────────┘
```

---

### Chat Page Layout

```
┌─────────────────────────────────────────────────────┐
│  ← Back to Dashboard      Hỏi về sản phẩm X         │
├─────────────────────────────────────────────────────┤
│                                                      │
│  💬 You: Giá sản phẩm X là bao nhiêu?               │
│  🕐 14:25                                           │
│                                                      │
│  🤖 AI Agent:                                       │
│  Sản phẩm X có giá 500,000 VNĐ.                    │
│  Hiện đang có chương trình giảm giá 10%.            │
│  🕐 14:25                                           │
│                                                      │
│  💬 You: Còn hàng không?                            │
│  🕐 14:26                                           │
│                                                      │
│  🤖 AI Agent: Đang typing...                        │
│                                                      │
├─────────────────────────────────────────────────────┤
│  💬 Type your message...              [Send]        │
└─────────────────────────────────────────────────────┘
```

---

### Documents Browser Layout

```
┌─────────────────────────────────────────────────────┐
│  ← Back        Shared Documents        🔍 Search    │
├─────────────────────────────────────────────────────┤
│                                                      │
│  Filter: [All] [Products] [Services] [Support]     │
│                                                      │
│  ┌──────────────────────────────────────────────┐  │
│  │ 📄 Catalog sản phẩm 2024                     │  │
│  │    PDF • 2.5 MB • Products                   │  │
│  │    Shared 1 month ago            [View] [📥]│  │
│  └──────────────────────────────────────────────┘  │
│                                                      │
│  ┌──────────────────────────────────────────────┐  │
│  │ 📄 Bảng giá dịch vụ 2024                     │  │
│  │    Excel • 1.2 MB • Services                 │  │
│  │    Shared 2 weeks ago            [View] [📥]│  │
│  └──────────────────────────────────────────────┘  │
│                                                      │
│  ┌──────────────────────────────────────────────┐  │
│  │ 📄 Hướng dẫn sử dụng sản phẩm                │  │
│  │    PDF • 5.8 MB • Support                    │  │
│  │    Shared 1 week ago             [View] [📥]│  │
│  └──────────────────────────────────────────────┘  │
│                                                      │
└─────────────────────────────────────────────────────┘
```

---

## 🔒 Permissions Summary

| Feature | CUSTOMER | BUSINESS | ADMIN |
|---------|----------|----------|-------|
| View Dashboard | ✅ Customer | ✅ Business | ✅ Admin |
| Chat with AI | ✅ | ✅ | ✅ |
| View Documents | ✅ (read-only) | ✅ (full) | ✅ (full) |
| Upload Documents | ❌ | ✅ | ✅ |
| Update Own Profile | ✅ | ✅ | ✅ |
| View Strategic Reports | ❌ | ✅ | ✅ |
| Manage Users | ❌ | ❌ | ✅ |

---

## 📱 Frontend Components Needed

### Pages
```
/customer/dashboard       - Customer homepage
/customer/profile         - Profile management
/customer/chat           - Chat interface
/customer/chat/:id       - Specific conversation
/customer/documents      - Documents browser
/customer/documents/:id  - Document viewer
/customer/support        - Support contact page
```

### Components
```typescript
// CustomerDashboard.tsx
// CustomerProfile.tsx
// ChatInterface.tsx
// DocumentBrowser.tsx
// DocumentViewer.tsx
// SupportContact.tsx
// QuickActions.tsx
// RecentConversations.tsx
// SharedDocumentsWidget.tsx
```

---

## 🎯 Key Features

### 1. **Easy Chat Access**
   - Quick start new conversation
   - View chat history
   - Resume previous conversations

### 2. **Document Access**
   - Browse shared documents
   - View/Download documents
   - Search documents

### 3. **Profile Management**
   - Update personal info
   - Change password (future)
   - Notification settings (future)

### 4. **Support Access**
   - Contact information always visible
   - Quick support button
   - Business hours displayed

---

## 🚀 Next Steps

1. Implement frontend với Next.js 14
2. Tích hợp real-time chat với WebSocket
3. Add document viewer component
4. Implement notification system
5. Add customer feedback/rating feature

---

**Sinh viên:** Nguyễn Văn Hoàng - MSSV: 110122078  
**Trường:** Đại Học Trà Vinh  
**Email:** 110122078@st.tvu.edu.vn

