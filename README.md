# 🤖 AI Agent for Business

<div align="center">

**Hệ thống Trợ lý AI thông minh cho doanh nghiệp - Kết hợp RAG và MCP**

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18.x-blue.svg)](https://reactjs.org/)
[![AI Powered](https://img.shields.io/badge/AI-Gemini%20API-purple.svg)](https://ai.google.dev/)

</div>

---

## 📋 Mục lục

- [Giới thiệu](#-giới-thiệu)
- [Tính năng chính](#-tính-năng-chính)
- [Kiến trúc hệ thống](#-kiến-trúc-hệ-thống)
- [Công nghệ sử dụng](#-công-nghệ-sử-dụng)
- [RAG & MCP](#-rag--mcp)
- [Cài đặt](#-cài-đặt)
- [Sử dụng](#-sử-dụng)
- [API Documentation](#-api-documentation)
- [Roadmap](#-roadmap)
- [Đóng góp](#-đóng-góp)
- [License](#-license)
- [Liên hệ](#-liên-hệ)

---

## 🎯 Giới thiệu

**AI Agent for Business** là một hệ thống trợ lý AI toàn diện được thiết kế dành riêng cho doanh nghiệp vừa và nhỏ tại Việt Nam. Dự án kết hợp công nghệ **RAG (Retrieval-Augmented Generation)** và mô hình **MCP (Model – Context – Process)** để mang đến giải pháp tự động hóa chăm sóc khách hàng, quản lý dữ liệu bán hàng và phân tích chiến lược kinh doanh thông minh.

### 🎯 Mục tiêu

- **Tự động hóa CSKH**: Chatbot AI trả lời khách hàng 24/7 trên nhiều kênh
- **Quản lý hiệu quả**: Hệ thống CRM mini quản lý khách hàng, sản phẩm, đơn hàng
- **Phân tích thông minh**: AI phân tích dữ liệu và đề xuất chiến lược kinh doanh
- **Độ chính xác cao**: RAG giúp AI trả lời dựa trên tài liệu nội bộ thực tế

### 💡 Giá trị cốt lõi

- ✅ **Tiết kiệm chi phí**: Giảm 70% chi phí nhân sự CSKH
- ✅ **Tăng hiệu suất**: Xử lý hàng trăm yêu cầu đồng thời
- ✅ **Chính xác**: Trả lời dựa trên dữ liệu thật từ doanh nghiệp
- ✅ **Thông minh**: AI học hỏi và cải thiện liên tục

---

## 🚀 Tính năng chính

### 1. 💬 Chatbot tư vấn khách hàng

<table>
<tr>
<td width="50%">

**Đa kênh tích hợp**
- 🌐 Website (Widget)
- 📘 Facebook Messenger
- 💬 Zalo OA
- 📱 Mobile App

</td>
<td width="50%">

**Chức năng thông minh**
- ❓ Trả lời FAQ tự động
- 🛒 Ghi nhận đơn hàng từ chat
- 🔍 Tìm kiếm sản phẩm
- 📦 Tra cứu đơn hàng

</td>
</tr>
</table>

**Ví dụ hội thoại:**
```
Khách: "Em muốn mua iPhone 15 Pro Max màu xanh"
AI: "Dạ, em có thông tin sản phẩm như sau:
     - iPhone 15 Pro Max 256GB - Xanh Titan
     - Giá: 29.990.000đ
     - Tình trạng: Còn hàng
     Anh/chị có muốn đặt hàng không ạ?"
```

### 2. 📊 Quản lý doanh nghiệp (CRM Mini)

#### **Quản lý khách hàng**
- 👥 Danh sách khách hàng với phân loại (VIP, thường, tiềm năng)
- 📝 Lịch sử mua hàng và tương tác
- 🎂 Nhắc nhở sinh nhật, chăm sóc định kỳ
- 📞 Thông tin liên hệ đầy đủ

#### **Quản lý sản phẩm**
- 📦 Danh mục sản phẩm đa cấp
- 💰 Quản lý giá, khuyến mãi
- 📊 Theo dõi tồn kho
- 🖼️ Hình ảnh và mô tả chi tiết

#### **Quản lý đơn hàng**
- 🛍️ Trạng thái đơn hàng (chờ xác nhận, đang giao, hoàn thành)
- 💳 Phương thức thanh toán
- 🚚 Theo dõi vận chuyển
- 📧 Thông báo tự động

#### **Thống kê & Báo cáo**
- 📈 Doanh thu theo ngày/tháng/quý/năm
- 📊 Biểu đồ sản phẩm bán chạy
- 👑 Top khách hàng VIP
- 💹 Tỷ lệ chuyển đổi đơn hàng
- 📉 Phân tích xu hướng

### 3. 🧠 AI Phân tích & Đề xuất chiến lược

#### **Phân tích hành vi khách hàng**
- 🔍 Phân tích RFM (Recency, Frequency, Monetary)
- 🎯 Phân khúc khách hàng tự động
- 📊 Dự đoán xu hướng mua sắm
- 💡 Gợi ý sản phẩm cá nhân hóa

#### **Đề xuất Marketing**
```
📌 Gợi ý từ AI:
✅ Khách hàng trong nhóm "Đã lâu không mua" đang tăng 15%
   → Đề xuất: Chạy chiến dịch email marketing với mã giảm giá 20%

✅ Sản phẩm "Tai nghe Bluetooth X" có xu hướng tăng doanh số
   → Đề xuất: Tăng tồn kho và chạy quảng cáo Facebook

✅ Thời điểm tốt nhất đăng bài: 20:00 - 22:00 (tỷ lệ tương tác cao nhất)
```

#### **Chiến lược kinh doanh**
- 📅 Lập kế hoạch nhập hàng dựa trên dự đoán
- 🎁 Gợi ý chương trình khuyến mãi
- 💰 Tối ưu hóa giá bán
- 📣 Đề xuất kênh quảng cáo hiệu quả

### 4. 📚 Quản lý tài liệu (RAG - Retrieval-Augmented Generation)

#### **Upload & Xử lý tài liệu**
- 📄 Hỗ trợ: PDF, DOCX, TXT, CSV, Excel
- 🔄 Tự động trích xuất và vector hóa nội dung
- 🗂️ Phân loại tài liệu (chính sách, hướng dẫn, quy trình)
- 🔍 Tìm kiếm ngữ nghĩa thông minh

#### **Cách hoạt động**
```
1. Doanh nghiệp upload: "Chính sách bảo hành.pdf"
2. Hệ thống xử lý: Tạo embeddings và lưu vào vector database
3. Khách hỏi: "Sản phẩm được bảo hành bao lâu?"
4. AI trả lời: "Theo chính sách của công ty, sản phẩm được bảo hành 12 tháng kể từ ngày mua..."
   (Trích xuất từ tài liệu thực tế)
```

#### **Lợi ích**
- ✅ Trả lời chính xác 100% theo tài liệu doanh nghiệp
- ✅ Không bịa đặt thông tin (hallucination)
- ✅ Luôn cập nhật khi doanh nghiệp thay đổi chính sách

---

## 🏗️ Kiến trúc hệ thống

```
┌─────────────────────────────────────────────────────────────┐
│                    USER INTERFACES                           │
│  [Web Dashboard] [Chatbot Widget] [Mobile App] [Admin Panel] │
└─────────────┬───────────────────────────────────────────────┘
              │
┌─────────────▼───────────────────────────────────────────────┐
│                   FRONTEND LAYER                             │
│  ReactJS / Next.js - UI Components & State Management        │
│  • Dashboard UI  • Chat Interface  • Analytics Charts        │
└─────────────┬───────────────────────────────────────────────┘
              │
              │ REST API / WebSocket
              │
┌─────────────▼───────────────────────────────────────────────┐
│                   API GATEWAY                                │
│  • Authentication (JWT)  • Rate Limiting  • Load Balancing   │
└─────────────┬───────────────────────────────────────────────┘
              │
┌─────────────▼───────────────────────────────────────────────┐
│                   BACKEND LAYER                              │
│  Spring Boot - Business Logic & Services                     │
│                                                               │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  🤖 AI Agent Core                                    │   │
│  │  ┌──────────────┐  ┌──────────────┐  ┌───────────┐ │   │
│  │  │  Chatbot     │  │  RAG Engine  │  │ Analytics │ │   │
│  │  │  Service     │  │  Service     │  │  Service  │ │   │
│  │  └──────────────┘  └──────────────┘  └───────────┘ │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                               │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  💼 Business Services                                │   │
│  │  • Customer Service  • Product Service               │   │
│  │  • Order Service     • Report Service                │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────┬─────────────┬─────────────┬───────────────────┘
              │             │             │
    ┌─────────▼─────┐ ┌────▼─────┐  ┌───▼──────────────┐
    │   MySQL DB    │ │ Vector   │  │  Google Gemini   │
    │               │ │ Database │  │      API         │
    │ • Customers   │ │ (pgvector│  │                  │
    │ • Products    │ │  or      │  │ • LLM Model      │
    │ • Orders      │ │ Pinecone)│  │ • Embeddings     │
    │ • Analytics   │ │          │  │ • AI Analysis    │
    └───────────────┘ └──────────┘  └──────────────────┘
              │             │             │
    ┌─────────▼─────────────▼─────────────▼───────────┐
    │           EXTERNAL INTEGRATIONS                  │
    │  • Facebook API  • Zalo API  • Payment Gateway   │
    └──────────────────────────────────────────────────┘
```

### 📐 Luồng xử lý chính

#### **1. Luồng Chat với RAG**
```
Khách hàng → Chat Message → API Gateway → Chatbot Service
                                              ↓
                                    RAG Engine truy vấn
                                    Vector Database
                                              ↓
                                    Context + Query → Gemini API
                                              ↓
                                    AI Response ← Chatbot Service
                                              ↓
                                    Lưu lịch sử → MySQL
                                              ↓
                                    Response → Khách hàng
```

#### **2. Luồng phân tích AI**
```
Dữ liệu bán hàng → Analytics Service → Xử lý & tổng hợp
                                              ↓
                                    Gửi lên Gemini API
                                    để phân tích
                                              ↓
                                    AI Insights & Recommendations
                                              ↓
                                    Lưu báo cáo → MySQL
                                              ↓
                                    Hiển thị Dashboard
```

---

## 🛠️ Công nghệ sử dụng

### **Frontend**

| Công nghệ | Mục đích | Version |
|-----------|----------|---------|
| ⚛️ **ReactJS** / **Next.js** | Framework UI, SSR, SEO | 18.x / 14.x |
| 🎨 **TailwindCSS** / **Material-UI** | Styling & Components | 3.x / 5.x |
| 📊 **Recharts** / **Chart.js** | Biểu đồ và visualization | Latest |
| 🔌 **Socket.io Client** | Real-time chat | 4.x |
| 🔐 **React Router** | Navigation | 6.x |
| 📦 **Redux** / **Zustand** | State management | Latest |

### **Backend**

| Công nghệ | Mục đích | Version |
|-----------|----------|---------|
| ☕ **Spring Boot** | Framework backend, REST API | 3.x |
| 🔒 **Spring Security** | Authentication & Authorization | 6.x |
| 🎫 **JWT** | Token-based authentication | - |
| 💾 **Spring Data JPA** | ORM, database access | 3.x |
| 🔄 **Spring WebSocket** | Real-time communication | 6.x |
| 📝 **Lombok** | Reduce boilerplate code | 1.18.x |
| ✅ **Validation API** | Input validation | - |

### **Database**

| Công nghệ | Mục đích |
|-----------|----------|
| 🐬 **MySQL** | Main database (customers, orders, products) |
| 🔢 **pgvector** / **Pinecone** / **Weaviate** | Vector database for RAG embeddings |
| 📦 **Redis** | Caching & session management |

### **AI & NLP**

| Công nghệ | Mục đích |
|-----------|----------|
| 🤖 **Google Gemini API** | LLM for chatbot, analysis, recommendations |
| 📊 **LangChain** | RAG orchestration framework |
| 🔤 **Sentence Transformers** | Text embeddings |

### **DevOps & Deployment**

| Công nghệ | Mục đích |
|-----------|----------|
| 🐳 **Docker** | Containerization |
| 📦 **Docker Compose** | Multi-container orchestration |
| ☁️ **VPS Ubuntu Server** | Production hosting |
| 🔄 **GitHub Actions** | CI/CD pipeline |
| 📊 **Prometheus + Grafana** | Monitoring & analytics |

### **External APIs**

- 📘 **Facebook Graph API**: Tích hợp Messenger
- 💬 **Zalo Official Account API**: Tích hợp Zalo OA
- 💳 **VNPay / MoMo API**: Payment gateway

---

## 🧩 RAG & MCP

### 🔍 RAG (Retrieval-Augmented Generation)

**RAG là gì?**

RAG là kỹ thuật kết hợp giữa tìm kiếm thông tin (Retrieval) và sinh text (Generation) để AI đưa ra câu trả lời chính xác dựa trên dữ liệu thực tế thay vì chỉ dựa vào kiến thức đã học.

**Cách hoạt động trong dự án:**

```
┌──────────────────────────────────────────────────────────┐
│  STEP 1: DOCUMENT INGESTION                              │
│  ┌────────────────┐                                      │
│  │ Doanh nghiệp   │ → Upload PDF/DOCX/TXT                │
│  │ upload tài liệu│                                      │
│  └────────┬───────┘                                      │
│           │                                               │
│           ▼                                               │
│  ┌────────────────────────────────────────────┐         │
│  │ Text Extraction & Chunking                 │         │
│  │ - Chia nhỏ văn bản thành chunks 500 tokens │         │
│  │ - Xử lý định dạng, loại bỏ noise          │         │
│  └────────┬───────────────────────────────────┘         │
│           │                                               │
│           ▼                                               │
│  ┌────────────────────────────────────────────┐         │
│  │ Generate Embeddings (Gemini API)           │         │
│  │ - Chuyển text → vector 768 chiều          │         │
│  └────────┬───────────────────────────────────┘         │
│           │                                               │
│           ▼                                               │
│  ┌────────────────────────────────────────────┐         │
│  │ Store in Vector Database                   │         │
│  │ - Lưu embeddings + metadata               │         │
│  └────────────────────────────────────────────┘         │
└──────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────┐
│  STEP 2: QUERY PROCESSING                                │
│  ┌────────────────┐                                      │
│  │ Khách hàng hỏi:│                                      │
│  │ "Chính sách    │                                      │
│  │ bảo hành?"     │                                      │
│  └────────┬───────┘                                      │
│           │                                               │
│           ▼                                               │
│  ┌────────────────────────────────────────────┐         │
│  │ Generate Query Embedding                   │         │
│  └────────┬───────────────────────────────────┘         │
│           │                                               │
│           ▼                                               │
│  ┌────────────────────────────────────────────┐         │
│  │ Vector Similarity Search                   │         │
│  │ - Tìm top 5 chunks liên quan nhất         │         │
│  │ - Cosine similarity > 0.7                  │         │
│  └────────┬───────────────────────────────────┘         │
│           │                                               │
│           ▼                                               │
│  ┌────────────────────────────────────────────┐         │
│  │ Context: "Sản phẩm được bảo hành 12       │         │
│  │ tháng kể từ ngày mua. Khách hàng cần...   │         │
│  └────────┬───────────────────────────────────┘         │
└───────────┼──────────────────────────────────────────────┘

┌───────────▼──────────────────────────────────────────────┐
│  STEP 3: GENERATION                                      │
│  ┌────────────────────────────────────────────┐         │
│  │ Prompt = Context + Query + Instructions    │         │
│  └────────┬───────────────────────────────────┘         │
│           │                                               │
│           ▼                                               │
│  ┌────────────────────────────────────────────┐         │
│  │ Gemini API generates answer                │         │
│  └────────┬───────────────────────────────────┘         │
│           │                                               │
│           ▼                                               │
│  ┌────────────────────────────────────────────┐         │
│  │ AI Response: "Dạ, sản phẩm của bên em     │         │
│  │ được bảo hành 12 tháng kể từ ngày mua.     │         │
│  │ Anh/chị cần giữ hóa đơn để được hỗ trợ..." │         │
│  └────────────────────────────────────────────┘         │
└──────────────────────────────────────────────────────────┘
```

**Lợi ích:**
- ✅ Độ chính xác cao: Trả lời dựa trên tài liệu thực
- ✅ Không hallucination: Không bịa đặt thông tin
- ✅ Có thể trích dẫn nguồn
- ✅ Dễ cập nhật: Upload tài liệu mới là xong

### 🔄 MCP (Model – Context – Process)

**MCP Framework trong dự án:**

```
┌─────────────────────────────────────────────────────────┐
│  MODEL (Mô hình)                                        │
│  ┌────────────────────────────────────────────────┐   │
│  │  AI Agent System Architecture                   │   │
│  │  • Chatbot Module: Xử lý hội thoại            │   │
│  │  • CRM Module: Quản lý dữ liệu                │   │
│  │  • Analytics Module: Phân tích & dự đoán      │   │
│  │  • RAG Module: Truy xuất kiến thức            │   │
│  └────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│  CONTEXT (Bối cảnh)                                     │
│  ┌────────────────────────────────────────────────┐   │
│  │  Target Users: Doanh nghiệp vừa & nhỏ VN      │   │
│  │  Pain Points:                                   │   │
│  │  • Chi phí CSKH cao                            │   │
│  │  • Thiếu nhân sự chuyên môn                   │   │
│  │  • Khó quản lý dữ liệu                         │   │
│  │  • Không có insight kinh doanh                 │   │
│  │                                                 │   │
│  │  Requirements:                                  │   │
│  │  • Giá thành hợp lý                            │   │
│  │  • Dễ sử dụng                                  │   │
│  │  • Tích hợp đa kênh                            │   │
│  │  • Hỗ trợ tiếng Việt                           │   │
│  └────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│  PROCESS (Quy trình)                                    │
│  ┌────────────────────────────────────────────────┐   │
│  │  End-to-End Workflow:                          │   │
│  │                                                 │   │
│  │  1. Khách hàng chat qua Website/Zalo/Facebook  │   │
│  │     ↓                                           │   │
│  │  2. AI Chatbot tư vấn (sử dụng RAG)           │   │
│  │     ↓                                           │   │
│  │  3. Ghi nhận đơn hàng / thông tin khách        │   │
│  │     ↓                                           │   │
│  │  4. Lưu vào CRM Database                       │   │
│  │     ↓                                           │   │
│  │  5. Doanh nghiệp xem Dashboard & Reports       │   │
│  │     ↓                                           │   │
│  │  6. AI phân tích dữ liệu định kỳ              │   │
│  │     ↓                                           │   │
│  │  7. Đưa ra insights & recommendations          │   │
│  │     ↓                                           │   │
│  │  8. Doanh nghiệp ra quyết định                 │   │
│  │     ↓                                           │   │
│  │  9. Triển khai chiến lược → Thu thập data mới │   │
│  │     ↓                                           │   │
│  │  10. Vòng lặp cải tiến liên tục               │   │
│  └────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────┘
```

---

## ⚙️ Cài đặt

### 📋 Yêu cầu hệ thống

- **Java**: 17 hoặc cao hơn
- **Node.js**: 18.x hoặc cao hơn
- **MySQL**: 8.0 hoặc cao hơn
- **Docker**: 24.x hoặc cao hơn (khuyến nghị)
- **RAM**: Tối thiểu 4GB (khuyến nghị 8GB)

### 🚀 Cài đặt Local Development

#### **1. Clone repository**

```bash
git clone https://github.com/yourusername/AI-Agent-for-Business.git
cd AI-Agent-for-Business
```

#### **2. Setup Backend (Spring Boot)**

```bash
cd backend

# Cấu hình database trong application.properties
cp src/main/resources/application.properties.example src/main/resources/application.properties

# Chỉnh sửa application.properties:
# spring.datasource.url=jdbc:mysql://localhost:3306/ai_agent_db
# spring.datasource.username=your_username
# spring.datasource.password=your_password
# gemini.api.key=your_gemini_api_key

# Build và chạy
./mvnw clean install
./mvnw spring-boot:run
```

Backend sẽ chạy tại `http://localhost:8080`

#### **3. Setup Frontend (React/Next.js)**

```bash
cd frontend

# Cài đặt dependencies
npm install

# Cấu hình environment
cp .env.example .env.local

# Chỉnh sửa .env.local:
# NEXT_PUBLIC_API_URL=http://localhost:8080
# NEXT_PUBLIC_SOCKET_URL=ws://localhost:8080

# Chạy development server
npm run dev
```

Frontend sẽ chạy tại `http://localhost:3000`

#### **4. Setup Database**

```bash
# Tạo database
mysql -u root -p

CREATE DATABASE ai_agent_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
exit;

# Import schema (nếu có)
mysql -u root -p ai_agent_db < database/schema.sql
```

#### **5. Setup Vector Database (Optional - cho RAG)**

**Sử dụng pgvector:**

```bash
# Cài đặt PostgreSQL với pgvector extension
docker run --name pgvector \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d ankane/pgvector

# Tạo database và enable extension
psql -h localhost -U postgres
CREATE DATABASE vector_db;
\c vector_db
CREATE EXTENSION vector;
```

**Hoặc sử dụng Pinecone (Cloud):**

```bash
# Đăng ký tài khoản tại https://www.pinecone.io/
# Lấy API key và cấu hình trong application.properties
pinecone.api.key=your_pinecone_key
pinecone.environment=us-west1-gcp
```

### 🐳 Triển khai với Docker

```bash
# Build và chạy tất cả services
docker-compose up -d

# Kiểm tra logs
docker-compose logs -f

# Dừng services
docker-compose down
```

**docker-compose.yml:**

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root123
      MYSQL_DATABASE: ai_agent_db
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  backend:
    build: ./backend
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/ai_agent_db
      GEMINI_API_KEY: ${GEMINI_API_KEY}
    depends_on:
      - mysql

  frontend:
    build: ./frontend
    ports:
      - "3000:3000"
    environment:
      NEXT_PUBLIC_API_URL: http://localhost:8080
    depends_on:
      - backend

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"

volumes:
  mysql_data:
```

---

## 📖 Sử dụng

### 🔐 Đăng nhập hệ thống

1. Truy cập `http://localhost:3000`
2. Đăng ký tài khoản doanh nghiệp
3. Đăng nhập với email/password

**Demo account:**
- Email: `admin@business.com`
- Password: `Admin@123`

### 💬 Tích hợp Chatbot vào Website

```html
<!-- Thêm đoạn code sau vào website của bạn -->
<script>
  (function(w,d,s,o,f,js,fjs){
    w['ChatbotWidget']=o;w[o]=w[o]||function(){(w[o].q=w[o].q||[]).push(arguments)};
    js=d.createElement(s),fjs=d.getElementsByTagName(s)[0];
    js.id=o;js.src=f;js.async=1;fjs.parentNode.insertBefore(js,fjs);
  }(window,document,'script','chatbot','http://localhost:3000/widget.js'));
  
  chatbot('init', {
    businessId: 'YOUR_BUSINESS_ID',
    position: 'bottom-right',
    color: '#0084ff'
  });
</script>
```

### 📤 Upload tài liệu cho RAG

1. Đăng nhập Dashboard
2. Vào mục **"Quản lý tài liệu"**
3. Click **"Upload tài liệu"**
4. Chọn file PDF/DOCX/TXT
5. Chọn loại tài liệu (Chính sách, Hướng dẫn, FAQ...)
6. Click **"Xử lý"** → Hệ thống tự động tạo embeddings

### 📊 Xem báo cáo AI

1. Vào mục **"AI Insights"**
2. Chọn khoảng thời gian
3. Xem các phân tích:
   - Phân khúc khách hàng
   - Sản phẩm bán chạy
   - Dự đoán doanh thu
   - Đề xuất chiến lược

### 🔌 Tích hợp Facebook Messenger

1. Tạo Facebook App tại [developers.facebook.com](https://developers.facebook.com)
2. Lấy Page Access Token
3. Cấu hình Webhook:
   - URL: `https://yourdomain.com/api/webhook/facebook`
   - Verify Token: `YOUR_VERIFY_TOKEN`
4. Lưu config vào Dashboard

### 💬 Tích hợp Zalo OA

1. Đăng ký Zalo Official Account
2. Lấy OA ID và Access Token
3. Cấu hình Webhook:
   - URL: `https://yourdomain.com/api/webhook/zalo`
4. Lưu config vào Dashboard

---

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/v1
```

### Authentication

Tất cả API (trừ login/register) yêu cầu JWT token trong header:

```
Authorization: Bearer <your_jwt_token>
```

### Endpoints

#### **Authentication**

```http
POST /auth/register
Content-Type: application/json

{
  "businessName": "Cửa hàng ABC",
  "email": "admin@abc.com",
  "password": "Password123",
  "phone": "0901234567"
}

Response: 201 Created
{
  "id": 1,
  "businessName": "Cửa hàng ABC",
  "email": "admin@abc.com",
  "token": "eyJhbGciOiJIUzI1NiIs..."
}
```

```http
POST /auth/login
Content-Type: application/json

{
  "email": "admin@abc.com",
  "password": "Password123"
}

Response: 200 OK
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "expiresIn": 86400
}
```

#### **Chatbot**

```http
POST /chatbot/message
Authorization: Bearer <token>
Content-Type: application/json

{
  "message": "Tôi muốn mua iPhone 15",
  "userId": "customer_123",
  "channel": "website"
}

Response: 200 OK
{
  "reply": "Dạ, em có thông tin sản phẩm iPhone 15 như sau...",
  "intent": "product_inquiry",
  "entities": ["iPhone 15"],
  "actions": ["show_product"]
}
```

#### **Customers**

```http
GET /customers
Authorization: Bearer <token>

Response: 200 OK
{
  "data": [
    {
      "id": 1,
      "name": "Nguyễn Văn A",
      "email": "nguyenvana@email.com",
      "phone": "0901234567",
      "totalOrders": 5,
      "totalSpent": 15000000,
      "segment": "VIP"
    }
  ],
  "total": 150,
  "page": 1,
  "pageSize": 20
}
```

#### **Products**

```http
GET /products
Authorization: Bearer <token>

Response: 200 OK
{
  "data": [
    {
      "id": 1,
      "name": "iPhone 15 Pro Max 256GB",
      "price": 29990000,
      "stock": 50,
      "sold": 120,
      "category": "Điện thoại"
    }
  ]
}
```

#### **Orders**

```http
POST /orders
Authorization: Bearer <token>
Content-Type: application/json

{
  "customerId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 1,
      "price": 29990000
    }
  ],
  "totalAmount": 29990000,
  "paymentMethod": "COD"
}

Response: 201 Created
{
  "orderId": 123,
  "orderCode": "ORD20250101001",
  "status": "pending",
  "totalAmount": 29990000
}
```

#### **Analytics & AI Insights**

```http
GET /analytics/insights
Authorization: Bearer <token>
Query: ?startDate=2025-01-01&endDate=2025-01-31

Response: 200 OK
{
  "revenue": {
    "total": 150000000,
    "trend": "+15%",
    "prediction": 180000000
  },
  "customerSegmentation": {
    "vip": 25,
    "regular": 100,
    "potential": 50
  },
  "recommendations": [
    {
      "type": "marketing",
      "title": "Chạy chiến dịch retargeting",
      "description": "25% khách hàng chưa quay lại sau 30 ngày",
      "priority": "high"
    }
  ]
}
```

#### **RAG Document Management**

```http
POST /documents/upload
Authorization: Bearer <token>
Content-Type: multipart/form-data

file: [binary file data]
category: "policy"
name: "Chính sách bảo hành 2025"

Response: 201 Created
{
  "documentId": 45,
  "name": "Chính sách bảo hành 2025",
  "status": "processing",
  "chunks": 0
}
```

```http
GET /documents
Authorization: Bearer <token>

Response: 200 OK
{
  "documents": [
    {
      "id": 45,
      "name": "Chính sách bảo hành 2025",
      "category": "policy",
      "uploadedAt": "2025-01-15T10:30:00Z",
      "chunks": 12,
      "status": "ready"
    }
  ]
}
```

**Xem full API documentation tại:** `http://localhost:8080/swagger-ui.html`

---

## 🗺️ Roadmap

### ✅ Phase 1: MVP (Hoàn thành - Q1 2025)
- [x] Chatbot cơ bản với Gemini API
- [x] CRM mini (Khách hàng, Sản phẩm, Đơn hàng)
- [x] Dashboard thống kê
- [x] Tích hợp RAG với vector database
- [x] Authentication & Authorization

### 🚧 Phase 2: Tích hợp đa kênh (Đang phát triển - Q2 2025)
- [ ] Tích hợp Facebook Messenger
- [ ] Tích hợp Zalo OA
- [ ] Mobile app (React Native)
- [ ] Push notifications
- [ ] Email marketing automation

### 🔮 Phase 3: AI nâng cao (Q3 2025)
- [ ] Phân tích tâm lý khách hàng (Sentiment Analysis)
- [ ] Dự đoán churn rate
- [ ] Recommendation engine cá nhân hóa
- [ ] AI voice assistant (Text-to-Speech)
- [ ] Multi-language support

### 🚀 Phase 4: Scale & Commercialize (Q4 2025)
- [ ] SaaS multi-tenant architecture
- [ ] Subscription billing
- [ ] White-label solution
- [ ] Advanced analytics with ML models
- [ ] Mobile app iOS/Android native
- [ ] Marketplace tích hợp bên thứ 3

---

## 🤝 Đóng góp

Chúng tôi rất hoan nghênh mọi đóng góp từ cộng đồng! 

### Cách đóng góp

1. **Fork repository**
2. **Tạo branch mới**: `git checkout -b feature/TenTinhNang`
3. **Commit changes**: `git commit -m 'Add: Thêm tính năng XYZ'`
4. **Push to branch**: `git push origin feature/TenTinhNang`
5. **Tạo Pull Request**

### Quy tắc đóng góp

- ✅ Code phải follow coding conventions
- ✅ Viết unit tests cho code mới
- ✅ Update documentation nếu cần
- ✅ Commit message rõ ràng và mô tả đầy đủ
- ✅ Pull request phải pass CI/CD checks

### Báo lỗi (Bug Report)

Nếu bạn phát hiện lỗi, vui lòng tạo issue với thông tin:
- Mô tả lỗi chi tiết
- Các bước tái hiện (steps to reproduce)
- Expected behavior vs Actual behavior
- Screenshots (nếu có)
- Environment (OS, Browser, versions...)

---

## 📄 License

Dự án này được phân phối dưới giấy phép **MIT License**.

```
MIT License

Copyright (c) 2025 AI Agent for Business

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

Xem chi tiết tại [LICENSE](./LICENSE)

---

## 📞 Liên hệ

### 👨‍💻 Tác giả

**Your Name**
- 📧 Email: your.email@example.com
- 🐙 GitHub: [@yourusername](https://github.com/yourusername)
- 💼 LinkedIn: [Your Name](https://linkedin.com/in/yourprofile)
- 🌐 Website: [yourwebsite.com](https://yourwebsite.com)

### 🏢 Tổ chức

**AI Agent for Business Team**
- 📧 Email: contact@aiagent.business
- 🌐 Website: https://aiagent.business
- 📱 Hotline: +84 901 234 567

### 💬 Cộng đồng

- 💬 **Telegram Group**: [Join our community](https://t.me/ai_agent_business)
- 💡 **Discord Server**: [Join Discord](https://discord.gg/aiagent)
- 📚 **Documentation**: [docs.aiagent.business](https://docs.aiagent.business)
- ❓ **Q&A Forum**: [GitHub Discussions](https://github.com/yourusername/AI-Agent-for-Business/discussions)

---

## 🙏 Acknowledgments

Dự án này được xây dựng với sự hỗ trợ của:

- [Google Gemini API](https://ai.google.dev/) - AI Language Model
- [Spring Boot](https://spring.io/projects/spring-boot) - Backend Framework
- [React](https://reactjs.org/) - Frontend Library
- [LangChain](https://www.langchain.com/) - RAG Framework
- [Pinecone](https://www.pinecone.io/) - Vector Database
- Và nhiều open-source libraries khác

Cảm ơn tất cả contributors đã đóng góp vào dự án! 🎉

---

## 📊 Project Stats

![GitHub stars](https://img.shields.io/github/stars/yourusername/AI-Agent-for-Business?style=social)
![GitHub forks](https://img.shields.io/github/forks/yourusername/AI-Agent-for-Business?style=social)
![GitHub issues](https://img.shields.io/github/issues/yourusername/AI-Agent-for-Business)
![GitHub pull requests](https://img.shields.io/github/issues-pr/yourusername/AI-Agent-for-Business)
![GitHub last commit](https://img.shields.io/github/last-commit/yourusername/AI-Agent-for-Business)
![GitHub contributors](https://img.shields.io/github/contributors/yourusername/AI-Agent-for-Business)

---

<div align="center">

**⭐ Nếu bạn thấy dự án hữu ích, đừng quên cho chúng tôi một ngôi sao nhé! ⭐**

Made with ❤️ by AI Agent for Business Team

</div>
