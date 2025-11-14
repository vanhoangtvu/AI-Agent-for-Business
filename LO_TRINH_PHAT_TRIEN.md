# 🗺️ LỘ TRÌNH PHÁT TRIỂN DỰ ÁN

> **AI Agent for Business - Development Roadmap**
>
> Hướng dẫn chi tiết từng bước xây dựng hệ thống AI Agent for Business
>
> **Sinh viên thực hiện:** Nguyễn Văn Hoàng  
> **MSSV:** 110122078

---

## 📋 Mục Lục

- [Tổng Quan Lộ Trình](#-tổng-quan-lộ-trình)
- [Phase 1: Spring Boot Backend Core](#-phase-1-spring-boot-backend-core)
- [Phase 2: Python AI Service](#-phase-2-python-ai-service)
- [Phase 3: Frontend Development](#-phase-3-frontend-development)
- [Phase 4: Integration & Testing](#-phase-4-integration--testing)
- [Phase 5: Deployment & Optimization](#-phase-5-deployment--optimization)
- [Timeline & Milestones](#-timeline--milestones)

---

## 🎯 Tổng Quan Lộ Trình

### Nguyên Tắc Phát Triển

✅ **Bottom-up Approach**: Xây dựng từ tầng dữ liệu lên tầng ứng dụng  
✅ **Backend First**: Backend stable trước khi phát triển Frontend  
✅ **Incremental Development**: Phát triển từng module, test kỹ trước khi chuyển module tiếp theo  
✅ **Parallel Work**: Cho phép phát triển song song khi có thể

### Thứ Tự Ưu Tiên

```text
1️⃣ Spring Boot Backend (Core Business Logic)
   ↓
2️⃣ Python AI Service (AI/ML Features)
   ↓
3️⃣ Next.js Frontend (User Interface)
   ↓
4️⃣ Integration & Testing
   ↓
5️⃣ Deployment & Optimization
```

---

## 🏗️ PHASE 1: Spring Boot Backend Core

**Mục tiêu:** Xây dựng backend core với đầy đủ business logic

### Bước 1: Setup & Authentication Module

#### 1.1: Project Setup & Database Design

**Checklist:**
- [ ] Khởi tạo Spring Boot project (Spring Initializr)
  - Spring Boot 3.2.x
  - Java 17
  - Dependencies: Web, JPA, MySQL, Security, Validation, Lombok
- [ ] Setup cấu trúc thư mục chuẩn
- [ ] Cấu hình `application.properties` / `application.yml`
- [ ] Thiết kế database schema (MySQL)
- [ ] Tạo SQL migration scripts

**Deliverables:**
```
backend/SpringService/
├── src/main/java/com/business/aiagent/
│   ├── config/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── entity/
│   ├── dto/
│   ├── security/
│   ├── exception/
│   └── util/
├── src/main/resources/
│   ├── application.yml
│   └── db/migration/
└── pom.xml
```

**Database Schema:**
```sql
-- users, roles, user_roles
-- documents
-- conversations, messages
-- strategic_reports
-- activity_logs
```

#### 1.2: Authentication & User Management

**Tasks:**
- [ ] Tạo Entity: `User`, `Role`, `UserRole`
- [ ] Implement UserRepository với Spring Data JPA
- [ ] Tạo DTOs: `RegisterRequest`, `LoginRequest`, `UserResponse`
- [ ] Implement JWT Token Generation/Validation
- [ ] Tạo `JwtAuthenticationFilter`
- [ ] Implement `UserService` (register, login, get user info)
- [ ] Tạo `AuthController` với endpoints:
  - POST `/api/auth/register`
  - POST `/api/auth/login`
  - GET `/api/auth/me`
  - POST `/api/auth/logout`

**Security Configuration:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // JWT filter
    // Password encoder
    // Security filter chain
}
```

#### 1.3: Role-Based Access Control (RBAC)

**Tasks:**
- [ ] Implement Role management
- [ ] Custom annotations: `@RequireRole`, `@RequirePermission`
- [ ] Method-level security
- [ ] Tạo `RoleService` và `RoleController`
- [ ] API endpoints:
  - GET `/api/roles`
  - POST `/api/roles`
  - PUT `/api/roles/{id}`
  - DELETE `/api/roles/{id}`
  - POST `/api/users/{userId}/roles/{roleId}`

**Test Cases:**
- [ ] Unit tests cho UserService
- [ ] Integration tests cho Authentication
- [ ] Test JWT token validation
- [ ] Test role-based authorization

---

### Bước 2: Document & Conversation Management

#### 2.1: Document Management Module

**Tasks:**
- [ ] Tạo Entity: `Document`
- [ ] Implement `DocumentRepository`
- [ ] Tạo DTOs: `DocumentUploadRequest`, `DocumentResponse`
- [ ] Implement file upload service
  - File storage configuration
  - File validation (type, size)
  - Generate unique file paths
- [ ] Implement `DocumentService`:
  - Upload document (save file + metadata to MySQL)
  - List documents (with pagination, filters)
  - Get document by ID
  - Update document metadata
  - Delete document (soft delete)
  - Share document with users
- [ ] Tạo `DocumentController` với endpoints:
  - POST `/api/documents/upload`
  - GET `/api/documents`
  - GET `/api/documents/{id}`
  - PUT `/api/documents/{id}`
  - DELETE `/api/documents/{id}`
  - POST `/api/documents/{id}/share`
  - GET `/api/documents/shared`

**File Upload Configuration:**
```yaml
app:
  upload:
    directory: ./uploads
    max-file-size: 10MB
    allowed-types: [pdf, docx, doc, txt, xlsx]
```

**Document Permissions:**
- [ ] Owner permissions
- [ ] Shared permissions
- [ ] Role-based access

#### 2.2: Conversation Management Module

**Tasks:**
- [ ] Tạo Entities: `Conversation`, `Message`
- [ ] Implement Repositories
- [ ] Tạo DTOs: `ConversationRequest`, `MessageRequest`, `MessageResponse`
- [ ] Implement `ConversationService`:
  - Create conversation
  - Get user's conversations
  - Get conversation by ID
  - Delete conversation
- [ ] Implement `MessageService`:
  - Save user message
  - Save AI response
  - Get conversation messages
  - Search messages
- [ ] Tạo `ChatController`:
  - POST `/api/conversations`
  - GET `/api/conversations`
  - GET `/api/conversations/{id}`
  - DELETE `/api/conversations/{id}`
  - POST `/api/conversations/{id}/messages`
  - GET `/api/conversations/{id}/messages`

**Mock AI Response (Temporary):**
```java
@Service
public class MockAIService {
    public ChatResponse generateResponse(String question) {
        return ChatResponse.builder()
            .answer("Đây là câu trả lời mock. AI Service sẽ được tích hợp sau.")
            .sources(new ArrayList<>())
            .build();
    }
}
```

#### 2.3: Strategic Reports & Activity Logs

**Strategic Reports:**
- [ ] Entity: `StrategicReport`
- [ ] Repository & Service
- [ ] DTOs: `ReportRequest`, `ReportResponse`
- [ ] Controller endpoints:
  - POST `/api/reports/analyze`
  - GET `/api/reports`
  - GET `/api/reports/{id}`
  - DELETE `/api/reports/{id}`

**Activity Logs:**
- [ ] Entity: `ActivityLog`
- [ ] Aspect/Interceptor để log tự động
- [ ] Log types: LOGIN, LOGOUT, UPLOAD_DOCUMENT, SEND_MESSAGE, etc.
- [ ] Controller để xem logs:
  - GET `/api/logs`
  - GET `/api/logs/user/{userId}`

**Testing & Documentation:**
- [ ] Unit tests cho tất cả services
- [ ] Integration tests
- [ ] Swagger/OpenAPI documentation
- [ ] Postman collection

---

## 🤖 PHASE 2: Python AI Service

**Mục tiêu:** Xây dựng AI service với RAG capabilities

### Bước 1: Setup & Document Processing

#### 1.1: Project Setup & Vector DB

**Checklist:**
- [ ] Khởi tạo FastAPI project
- [ ] Setup virtual environment
- [ ] Install dependencies:
  ```bash
  pip install fastapi uvicorn chromadb langchain
  pip install python-multipart PyPDF2 python-docx openpyxl
  pip install google-generativeai sentence-transformers
  ```
- [ ] Cấu trúc thư mục:
  ```
  backend/PythonService/
  ├── app/
  │   ├── main.py
  │   ├── config/
  │   ├── routers/
  │   ├── services/
  │   │   ├── document_processor.py
  │   │   ├── embeddings_service.py
  │   │   ├── rag_service.py
  │   │   └── gemini_service.py
  │   ├── models/
  │   └── utils/
  ├── tests/
  ├── requirements.txt
  └── .env
  ```

**ChromaDB Setup:**
- [ ] Initialize ChromaDB client
- [ ] Create collection for documents
- [ ] Test basic CRUD operations

#### 1.2: Document Processing Pipeline

**Text Extraction:**
- [ ] PDF text extraction (PyPDF2)
- [ ] DOCX text extraction (python-docx)
- [ ] TXT file reading
- [ ] Excel parsing (openpyxl)

**Text Chunking:**
- [ ] Implement chunking strategies:
  - Fixed-size chunks with overlap
  - Sentence-based chunking
  - Paragraph-based chunking
- [ ] Metadata preservation

**API Endpoints:**
```python
@router.post("/documents/process")
async def process_document(
    document_id: int,
    file_path: str
):
    # Extract text
    # Chunk text
    # Generate embeddings
    # Store in Vector DB
    # Return results
```

#### 1.3: Embeddings & Vector Storage

**Embeddings Generation:**
- [ ] Setup sentence-transformers
- [ ] Choose model (e.g., `all-MiniLM-L6-v2`)
- [ ] Batch processing for performance
- [ ] Implement `EmbeddingsService`

**Vector DB Operations:**
- [ ] Store documents with embeddings
- [ ] Store metadata (document_id, chunk_index, etc.)
- [ ] Implement similarity search
- [ ] Test retrieval quality

**Service Implementation:**
```python
class DocumentProcessorService:
    def __init__(self):
        self.chroma_client = chromadb.Client()
        self.collection = self.chroma_client.get_or_create_collection("documents")
    
    async def process_and_store(
        self, 
        document_id: int,
        file_path: str
    ) -> ProcessResult:
        # Extract, chunk, embed, store
        pass
```

---

### Bước 2: RAG Implementation & Gemini Integration

#### 2.1: RAG Service Implementation

**Core RAG Pipeline:**
- [ ] Query embedding generation
- [ ] Vector similarity search
- [ ] Context retrieval & ranking
- [ ] Prompt engineering
- [ ] Response generation

**RAG Service:**
```python
class RAGService:
    async def answer_question(
        self,
        question: str,
        user_id: int,
        conversation_id: int,
        top_k: int = 5
    ) -> RAGResponse:
        # 1. Generate question embedding
        # 2. Search Vector DB
        # 3. Retrieve relevant chunks
        # 4. Build context
        # 5. Generate prompt
        # 6. Call LLM
        # 7. Return answer + sources
        pass
```

**Endpoints:**
```python
@router.post("/chat/answer")
async def generate_answer(request: ChatRequest):
    # RAG pipeline
    pass

@router.post("/search/semantic")
async def semantic_search(query: str, top_k: int = 5):
    # Vector search only
    pass
```

#### 2.2: Gemini API Integration

**Setup:**
- [ ] Get Gemini API key
- [ ] Configure `google-generativeai`
- [ ] Implement retry logic
- [ ] Handle rate limiting

**Gemini Service:**
```python
class GeminiService:
    def __init__(self):
        genai.configure(api_key=os.getenv("GEMINI_API_KEY"))
        self.model = genai.GenerativeModel('gemini-pro')
    
    async def generate_response(
        self,
        prompt: str,
        context: str
    ) -> str:
        # Call Gemini API
        pass
```

**Prompt Templates:**
- [ ] RAG prompt template
- [ ] Strategic analysis prompt
- [ ] SWOT analysis prompt

#### 2.3: Strategic Analysis Service

**Business Analysis:**
- [ ] Implement SWOT analysis generation
- [ ] Market insights generation
- [ ] Risk assessment
- [ ] Recommendations generation

**Endpoints:**
```python
@router.post("/strategic/analyze")
async def analyze_business(metrics: BusinessMetrics):
    # Generate strategic insights
    pass

@router.post("/strategic/swot")
async def generate_swot(data: SWOTInput):
    # SWOT analysis
    pass
```

**Testing & Integration:**
- [ ] Unit tests cho tất cả services
- [ ] Integration tests với Spring Boot
- [ ] Performance testing
- [ ] API documentation (auto-generated by FastAPI)

---

## 🎨 PHASE 3: Frontend Development

**Mục tiêu:** Xây dựng giao diện người dùng với Next.js

### Bước 1: Setup & Core Pages

#### 1.1: Project Setup & Authentication UI

**Setup:**
- [ ] Initialize Next.js 14 project with TypeScript
- [ ] Install dependencies:
  ```bash
  npm install axios @tanstack/react-query
  npm install @shadcn/ui tailwindcss
  npm install zustand react-hook-form zod
  ```
- [ ] Cấu trúc thư mục:
  ```
  frontend/
  ├── app/
  │   ├── (auth)/
  │   │   ├── login/
  │   │   └── register/
  │   ├── (dashboard)/
  │   │   ├── documents/
  │   │   ├── chat/
  │   │   └── reports/
  │   └── layout.tsx
  ├── components/
  ├── lib/
  ├── hooks/
  └── types/
  ```

**Authentication Pages:**
- [ ] Login page
- [ ] Register page
- [ ] Password reset page
- [ ] JWT token management
- [ ] Auth context/store

#### 1.2: Document Management UI

**Pages & Components:**
- [ ] Documents list page with:
  - File upload component
  - Document cards/table
  - Filters & search
  - Pagination
- [ ] Document detail page
- [ ] Share document modal
- [ ] Upload progress indicator

**Features:**
- [ ] Drag-and-drop upload
- [ ] File type validation
- [ ] Upload progress tracking
- [ ] Document preview (if possible)

#### 1.3: Chat Interface

**Chat UI:**
- [ ] Conversation list sidebar
- [ ] Chat message area
- [ ] Message input component
- [ ] Source documents display
- [ ] New conversation button
- [ ] Search conversations

**Features:**
- [ ] Real-time message display
- [ ] Markdown rendering
- [ ] Code syntax highlighting
- [ ] Copy message button
- [ ] Regenerate response

---

### Bước 2: Dashboard & Reports

#### 2.1: Dashboard Page

**Components:**
- [ ] Statistics cards
  - Total documents
  - Total conversations
  - Total reports
  - Recent activities
- [ ] Recent documents widget
- [ ] Recent conversations widget
- [ ] Quick actions

**Charts:**
- [ ] Document upload trends
- [ ] Chat activity chart
- [ ] Usage statistics

#### 2.2: Reports & Analytics

**Reports Page:**
- [ ] Reports list
- [ ] Generate new report form
- [ ] Report detail view
- [ ] Export report functionality

**Report Components:**
- [ ] SWOT analysis display
- [ ] Metrics visualization
- [ ] Recommendations list
- [ ] Risk assessment table

#### 2.3: Polish & Responsive Design

**UI/UX Improvements:**
- [ ] Responsive design (mobile, tablet, desktop)
- [ ] Dark mode support
- [ ] Loading states
- [ ] Error states
- [ ] Empty states
- [ ] Toast notifications
- [ ] Confirmation modals

**Accessibility:**
- [ ] Keyboard navigation
- [ ] ARIA labels
- [ ] Screen reader support
- [ ] Focus management

---

## 🔗 PHASE 4: Integration & Testing

**Mục tiêu:** Tích hợp đầy đủ và testing toàn diện

### Bước 1: Full Integration

**Spring Boot ↔ Python AI:**
- [ ] Implement RestTemplate/WebClient để gọi Python API
- [ ] Handle async processing
- [ ] Error handling & retry logic
- [ ] Update Document status flow
- [ ] Test RAG pipeline end-to-end

**Frontend ↔ Backend:**
- [ ] API integration testing
- [ ] Authentication flow
- [ ] File upload flow
- [ ] Chat flow
- [ ] Reports flow

### Bước 2: Testing

**Backend Testing:**
- [ ] Unit tests coverage > 80%
- [ ] Integration tests
- [ ] API contract testing
- [ ] Load testing (JMeter/Gatling)

**Frontend Testing:**
- [ ] Component tests (Jest/React Testing Library)
- [ ] E2E tests (Playwright/Cypress)
- [ ] Accessibility testing

**AI Service Testing:**
- [ ] Unit tests
- [ ] RAG quality testing
- [ ] Performance testing
- [ ] Embeddings quality validation

### Bước 3: Bug Fixes & Optimization

- [ ] Fix identified bugs
- [ ] Performance optimization
- [ ] Code refactoring
- [ ] Documentation updates

---

## 🚀 PHASE 5: Deployment & Optimization

**Mục tiêu:** Deploy và optimize hệ thống

### Bước 1: Deployment Setup

**Docker Setup:**
- [ ] Dockerfile cho Spring Boot
- [ ] Dockerfile cho Python AI Service
- [ ] Dockerfile cho Next.js
- [ ] docker-compose.yml
- [ ] Environment variables management

**Database:**
- [ ] MySQL production setup
- [ ] ChromaDB persistence
- [ ] Redis setup
- [ ] Database migrations

**Server Setup:**
- [ ] Choose hosting (AWS/GCP/Azure/VPS)
- [ ] Setup reverse proxy (Nginx)
- [ ] SSL certificates
- [ ] Domain configuration

### Bước 2: CI/CD Pipeline

**GitHub Actions:**
- [ ] Build pipeline
- [ ] Test pipeline
- [ ] Deploy pipeline
- [ ] Rollback strategy

### Bước 3: Monitoring & Documentation

**Monitoring:**
- [ ] Application logging
- [ ] Error tracking (Sentry)
- [ ] Performance monitoring
- [ ] Uptime monitoring

**Documentation:**
- [ ] Update README
- [ ] API documentation
- [ ] User guide
- [ ] Developer guide
- [ ] Deployment guide

---

## 📅 Key Milestones

### Các Mốc Quan Trọng

| Phase | Milestone | Deliverables |
|------|-----------|--------------|
| **Phase 1** | Backend Core Complete | ✅ Authentication, Document Management, Conversations, Basic APIs |
| **Phase 2** | AI Service Complete | ✅ Document Processing, RAG, Gemini Integration |
| **Phase 3** | Frontend Complete | ✅ All pages, Components, Integration |
| **Phase 4** | Testing Complete | ✅ All tests passing, Bugs fixed |
| **Phase 5** | Production Ready | ✅ Deployed, Monitored, Documented |

### Checkpoints

Mỗi bước hoàn thành:
- [ ] Code committed & pushed
- [ ] Tests written & passing
- [ ] Documentation updated
- [ ] Review & feedback

Mỗi phase hoàn thành:
- [ ] Demo/review
- [ ] Retrospective
- [ ] Planning cho phase tiếp theo
- [ ] Risk assessment

---

## 🎯 Success Criteria

### Phase 1 Success Criteria
- [ ] All REST APIs documented và tested
- [ ] Authentication working với JWT
- [ ] RBAC implemented
- [ ] Document metadata management hoàn chỉnh
- [ ] Database schema stable

### Phase 2 Success Criteria
- [ ] Document processing pipeline hoạt động
- [ ] Vector DB lưu trữ embeddings
- [ ] RAG trả lời chính xác dựa trên documents
- [ ] Gemini API integration stable
- [ ] Python ↔ Spring Boot communication working

### Phase 3 Success Criteria
- [ ] UI responsive trên mọi devices
- [ ] Authentication flow hoàn chỉnh
- [ ] File upload working
- [ ] Chat interface real-time
- [ ] Reports display correctly

### Phase 4 Success Criteria
- [ ] End-to-end flow working
- [ ] Test coverage > 80%
- [ ] No critical bugs
- [ ] Performance acceptable

### Phase 5 Success Criteria
- [ ] Application deployed
- [ ] Monitoring active
- [ ] Documentation complete
- [ ] CI/CD pipeline working

---

## 📝 Notes & Best Practices

### Development Principles

1. **Write Tests First (TDD)** - Test-driven development
2. **Commit Often** - Small, atomic commits
3. **Code Review** - Review trước khi merge
4. **Documentation** - Document as you code
5. **Refactor Regularly** - Keep code clean

### Git Workflow

```bash
# Feature branch workflow
git checkout -b feature/user-authentication
# ... work on feature ...
git add .
git commit -m "feat: implement user authentication"
git push origin feature/user-authentication
# Create Pull Request
```

### Commit Message Convention

```
feat: add user authentication
fix: resolve document upload issue
docs: update API documentation
test: add unit tests for UserService
refactor: improve RAG service performance
```

---

## 🆘 Risk Management

### Potential Risks & Mitigation

| Risk | Impact | Mitigation |
|------|--------|------------|
| API rate limiting (Gemini) | High | Implement caching, rate limiting, fallback responses |
| Vector DB performance | Medium | Optimize embeddings, use indexing, batch operations |
| Large file processing | Medium | Implement chunked upload, async processing |
| Security vulnerabilities | High | Regular security audits, dependency updates |
| Deployment issues | Medium | Thorough testing, staging environment, rollback plan |

---

## 📚 Resources & References

### Documentation
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [FastAPI Docs](https://fastapi.tiangolo.com/)
- [Next.js Docs](https://nextjs.org/docs)
- [ChromaDB Docs](https://docs.trychroma.com/)
- [Gemini API Docs](https://ai.google.dev/docs)

### Tutorials
- RAG Implementation Best Practices
- JWT Authentication in Spring Boot
- Vector Database Optimization
- Next.js Performance Optimization

---

## ✅ Final Checklist

Trước khi hoàn thành dự án:

- [ ] All features implemented
- [ ] All tests passing
- [ ] Code reviewed
- [ ] Documentation complete
- [ ] Performance optimized
- [ ] Security hardened
- [ ] Deployed to production
- [ ] Monitoring active
- [ ] User guide written
- [ ] Demo video recorded
- [ ] Presentation prepared

---

**Good luck with your development! 🚀**

*Liên hệ: Nguyễn Văn Hoàng - 110122078*
