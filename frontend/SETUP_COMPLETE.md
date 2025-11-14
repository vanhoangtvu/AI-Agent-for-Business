# AI Agent for Business - Frontend Setup Complete ✅

## 🎉 Cài đặt thành công!

Frontend Next.js đã được khởi tạo và đang chạy song song với Spring Boot backend.

---

## 🌐 Thông tin truy cập

### Frontend (Next.js)
- **URL:** http://113.187.152.149:3009
- **Port:** 3009
- **Host:** 0.0.0.0 (Public access)
- **Framework:** Next.js 16.0.3 with Turbopack
- **Node.js:** v20.19.5
- **npm:** v10.8.2

### Backend (Spring Boot)
- **API URL:** http://113.187.152.149:8089
- **Swagger UI:** http://113.187.152.149:8089/swagger-ui/index.html
- **Port:** 8089
- **Framework:** Spring Boot 3.2.0
- **Java:** 17

---

## 📦 Công nghệ đã cài đặt

### Frontend Stack
```json
{
  "next": "^16.0.3",
  "react": "^18.3.1", 
  "react-dom": "^18.3.1",
  "typescript": "^5",
  "tailwindcss": "^4",
  "@tanstack/react-query": "^5.90.9",
  "zustand": "^5.0.8",
  "axios": "^1.13.2",
  "react-hook-form": "^7.66.0",
  "zod": "^4.1.12",
  "lucide-react": "^0.553.0"
}
```

---

## 🗂️ Cấu trúc dự án

```
frontend/
├── app/                          # Next.js App Router
│   ├── login/                   # ✅ Trang đăng nhập
│   │   └── page.tsx
│   ├── register/                # ✅ Trang đăng ký
│   │   └── page.tsx
│   ├── dashboard/               # ✅ Dashboard chính
│   │   └── page.tsx
│   ├── layout.tsx               # ✅ Root layout với Providers
│   ├── page.tsx                 # ✅ Home (redirect logic)
│   └── globals.css              # ✅ Tailwind CSS
│
├── components/                   # React components
│   └── Providers.tsx            # ✅ React Query Provider
│
├── lib/                          # Utilities & configs
│   ├── api.ts                   # ✅ Axios instance với JWT interceptor
│   └── config.ts                # ✅ API endpoints configuration
│
├── store/                        # State management
│   └── auth.ts                  # ✅ Zustand auth store (login/register/logout)
│
├── types/                        # TypeScript definitions
│   └── index.ts                 # ✅ All type interfaces
│
├── .env.local                   # ✅ Environment variables
├── next.config.mjs              # ✅ Next.js configuration
├── package.json                 # ✅ Dependencies
├── tsconfig.json                # ✅ TypeScript config
└── tailwind.config.ts           # ✅ Tailwind config
```

---

## ✅ Đã hoàn thành

### 1. Authentication System
- [x] Login page (`/login`)
- [x] Register page (`/register`)
- [x] JWT token management
- [x] Protected routes
- [x] Auto redirect logic
- [x] Zustand auth store
- [x] Axios interceptors

### 2. Dashboard
- [x] Main dashboard page
- [x] Navigation cards (Documents, Chat, Reports, Profile, Activities)
- [x] User info display
- [x] Logout functionality

### 3. API Integration
- [x] API client setup (Axios)
- [x] Base URL configuration (http://113.187.152.149:8089)
- [x] JWT authentication flow
- [x] 401 error handling
- [x] Request/Response interceptors

### 4. TypeScript Types
- [x] User, AuthResponse
- [x] Document, DocumentUploadResponse
- [x] Conversation, Message, ChatRequest/Response
- [x] StrategicReport, ReportRequest
- [x] ActivityLog
- [x] PageRequest, PageResponse

---

## 🚀 Lệnh chạy dự án

### Development
```bash
cd /home/hv/DuAn/AI-Agent-for-Business/frontend
npm run dev
```
Server sẽ chạy tại: http://113.187.152.149:3009

### Build Production
```bash
npm run build
```

### Start Production
```bash
npm run start
```

---

## 🔐 Tài khoản Demo

### Admin Account
- **Username:** `admin`
- **Password:** `admin123`
- **Roles:** ROLE_ADMIN

### Test User (đã tạo khi test API)
- **Username:** `testuser`
- **Password:** `password123`
- **Roles:** ROLE_USER

---

## 📋 TODO List - Phase tiếp theo

### ⏳ Chưa hoàn thành

#### 1. Document Management UI (Phase 2.1)
- [ ] Tạo `/dashboard/documents` page
- [ ] Component upload file (drag & drop)
- [ ] Danh sách documents với table
- [ ] Pagination & filters
- [ ] Delete document functionality
- [ ] View document details
- [ ] Integration với API:
  - `POST /api/documents/upload`
  - `GET /api/documents`
  - `GET /api/documents/{id}`
  - `DELETE /api/documents/{id}`

#### 2. Chat Interface (Phase 2.2)
- [ ] Tạo `/dashboard/chat` page
- [ ] Conversation list sidebar
- [ ] Chat message area
- [ ] Message input component
- [ ] New conversation button
- [ ] Display AI responses
- [ ] Show source documents
- [ ] Integration với API:
  - `POST /api/chat/conversations`
  - `GET /api/chat/conversations`
  - `POST /api/chat/send`
  - `GET /api/chat/conversations/{id}/messages`

#### 3. Reports & Analytics (Phase 2.3)
- [ ] Tạo `/dashboard/reports` page
- [ ] Generate report form
- [ ] Reports list table
- [ ] Report detail view
- [ ] SWOT analysis display
- [ ] Export report functionality
- [ ] Integration với API:
  - `POST /api/reports/generate`
  - `GET /api/reports`
  - `GET /api/reports/{id}`

#### 4. User Profile (Phase 2.4)
- [ ] Tạo `/dashboard/profile` page
- [ ] Edit profile form
- [ ] Change password form
- [ ] User info display
- [ ] Integration với API:
  - `GET /api/users/profile`
  - `PUT /api/users/profile`
  - `POST /api/users/change-password`

#### 5. Activity Logs (Phase 2.5)
- [ ] Tạo `/dashboard/activities` page
- [ ] Activity logs table
- [ ] Filters (date, type)
- [ ] Integration với API:
  - `GET /api/activities`

---

## 🔧 Cấu hình đã thiết lập

### Environment Variables (.env.local)
```env
NEXT_PUBLIC_API_BASE_URL=http://113.187.152.149:8089
```

### API Endpoints (lib/config.ts)
```typescript
export const API_CONFIG = {
  BASE_URL: 'http://113.187.152.149:8089',
  ENDPOINTS: {
    LOGIN: '/api/auth/login',
    REGISTER: '/api/auth/register',
    DOCUMENTS: '/api/documents',
    CONVERSATIONS: '/api/chat/conversations',
    SEND_MESSAGE: '/api/chat/send',
    REPORTS: '/api/reports',
    USER_PROFILE: '/api/users/profile',
    ACTIVITIES: '/api/activities',
  }
};
```

### Package.json Scripts
```json
{
  "scripts": {
    "dev": "next dev -p 3009 -H 0.0.0.0",
    "build": "next build",
    "start": "next start -p 3009 -H 0.0.0.0"
  }
}
```

---

## 🐛 Troubleshooting

### Port đã được sử dụng
```bash
# Kill process đang dùng port 3009
lsof -ti:3009 | xargs kill -9

# Hoặc dừng tất cả Next.js processes
pkill -f "next dev"
```

### Node version issues
```bash
# Kiểm tra version (phải >= 20.9.0)
node --version  # v20.19.5 ✅

# Nếu cần cập nhật
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
sudo apt install nodejs -y
```

### Dependencies issues
```bash
# Xóa node_modules và reinstall
rm -rf node_modules package-lock.json
npm install
```

---

## 📊 Tiến độ dự án

### Phase 1: Backend Core ✅ 100%
- Spring Boot REST API
- MySQL database
- JWT Authentication
- All endpoints tested

### Phase 2: Frontend Development ⏳ 30%
- ✅ Setup & Configuration (100%)
- ✅ Authentication Pages (100%)
- ✅ Dashboard Layout (100%)
- ⏳ Document Management (0%)
- ⏳ Chat Interface (0%)
- ⏳ Reports & Analytics (0%)
- ⏳ User Profile (0%)

### Phase 3: Python AI Service ⏳ 0%
- FastAPI setup
- ChromaDB integration
- RAG implementation
- Gemini API integration

---

## 🎯 Bước tiếp theo

### Ưu tiên cao (Next Sprint)
1. **Document Management UI** - Upload và quản lý tài liệu
2. **Chat Interface** - Tương tác với AI
3. **Reports Generation** - Tạo báo cáo chiến lược

### Ưu tiên trung bình
4. User Profile & Settings
5. Activity Logs

### Ưu tiên thấp (Sau khi hoàn thành frontend)
6. Python AI Service development
7. RAG implementation
8. ChromaDB setup

---

## 📞 Liên hệ

**Sinh viên:** Nguyễn Văn Hoàng  
**MSSV:** 110122078  
**Dự án:** AI Agent for Business

---

## 📝 Notes

### Server Information
- **IP:** 113.187.152.149
- **OS:** Ubuntu 24.04
- **Node.js:** v20.19.5
- **Java:** OpenJDK 17
- **MySQL:** 8.0

### Security
- JWT tokens expire sau 24 giờ
- Passwords được hash bằng BCrypt
- CORS enabled cho tất cả origins (development)
- Production cần restrict CORS origins

### Performance
- Next.js Turbopack enabled
- React Query caching (1 minute stale time)
- Image optimization tự động
- Code splitting automatic

---

**Cập nhật lần cuối:** 15/11/2025  
**Status:** ✅ Frontend running on port 3009 with public access
