# 🤖 AI Agent for Business

<div align="center">

**Hệ thống Trợ lý AI thông minh cho doanh nghiệp - Kết hợp RAG và MCP Framework**

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![Next.js](https://img.shields.io/badge/Next.js-14.x-black.svg)](https://nextjs.org/)
[![AI Powered](https://img.shields.io/badge/AI-Gemini%20API-purple.svg)](https://ai.google.dev/)

</div>

---

## 📋 Mục lục

- [Giới thiệu](#-giới-thiệu)
- [Tính năng chính](#-tính-năng-chính)
- [Kiến trúc hệ thống](#-kiến-trúc-hệ-thống)
- [Công nghệ sử dụng](#-công-nghệ-sử-dụng)
- [RAG & MCP Framework](#-rag--mcp-framework)
- [Cài đặt](#-cài-đặt)
- [Sử dụng](#-sử-dụng)
- [API Documentation](#-api-documentation)
- [Roadmap](#-roadmap)
- [Đóng góp](#-đóng-góp)
- [License](#-license)
- [Liên hệ](#-liên-hệ)

---

## 🎯 Giới thiệu

**AI Agent for Business** là một hệ thống trợ lý AI toàn diện được thiết kế dành riêng cho doanh nghiệp vừa và nhỏ tại Việt Nam. Dự án sử dụng công nghệ **RAG (Retrieval-Augmented Generation)** kết hợp với **LLM (Large Language Model)**, được tổ chức theo framework **MCP (Model – Context – Process)** để phân tích và thiết kế hệ thống một cách rõ ràng, mang đến giải pháp tự động hóa chăm sóc khách hàng, quản lý dữ liệu bán hàng và phân tích chiến lược kinh doanh thông minh.

### 🎯 Mục tiêu

- **Tự động hóa CSKH**: Chatbot AI trả lời khách hàng 24/7 trên nhiều kênh
- **Đa kênh linh hoạt**: Website Widget + Zalo OA + Zalo Personal
- **Quản lý hiệu quả**: Hệ thống CRM mini quản lý khách hàng, sản phẩm, đơn hàng
- **Phân tích thông minh**: AI phân tích dữ liệu và đề xuất chiến lược kinh doanh
- **Độ chính xác cao**: RAG giúp AI trả lời dựa trên tài liệu nội bộ thực tế
- **Linh hoạt kiểm soát**: 3 chế độ AI (Tự động / Gợi ý / Thông báo)

### 💡 Giá trị cốt lõi

- ✅ **Tiết kiệm chi phí**: Giảm 70% chi phí nhân sự CSKH
- ✅ **Tăng hiệu suất**: Xử lý hàng trăm yêu cầu đồng thời
- ✅ **Chính xác**: Trả lời dựa trên dữ liệu thật từ doanh nghiệp
- ✅ **Thông minh**: AI học hỏi và cải thiện liên tục
- ✅ **Linh hoạt**: Dùng Zalo cá nhân hoặc OA, không bắt buộc
- ✅ **Kiểm soát**: 3 chế độ AI phù hợp mọi tình huống

---

## 🚀 Tính năng chính

### 1. 💬 Chatbot tư vấn khách hàng đa kênh

<table>
<tr>
<td width="50%">

**Tích hợp đa kênh**
- 🌐 Website (Chatbot Widget)
- 💬 Zalo OA (Official Account)
- 👤 Zalo Personal (Tài khoản cá nhân)
- 📱 Tương tác trực tiếp với khách hàng
- 🔔 Gửi thông báo tự động
- 📊 Quản lý hội thoại tập trung

</td>
<td width="50%">

**Chức năng thông minh**
- ❓ Trả lời FAQ tự động
- 🛒 Ghi nhận đơn hàng từ chat
- 🔍 Tìm kiếm sản phẩm
- 📦 Tra cứu đơn hàng
- 🤖 AI trả lời 24/7

</td>
</tr>
</table>

**Ví dụ hội thoại (Website hoặc Zalo OA):**
```
📱 Kênh: Website Widget / Zalo OA

Khách: "Em muốn mua iPhone 15 Pro Max màu xanh"

🤖 AI Bot: "Dạ, em có thông tin sản phẩm như sau:
     
     📱 iPhone 15 Pro Max 256GB - Xanh Titan
     💰 Giá: 29.990.000đ
     ✅ Tình trạng: Còn hàng (15 sản phẩm)
     🎁 Khuyến mãi: Tặng ốp lưng + cường lực
     
     Anh/chị có muốn đặt hàng không ạ?"

Khách: "Đặt 1 chiếc cho em"

🤖 AI Bot: "Dạ em ghi nhận đơn hàng:
     - Sản phẩm: iPhone 15 Pro Max 256GB
     - Số lượng: 1
     - Tổng tiền: 29.990.000đ
     
     Anh/chị vui lòng cung cấp:
     📍 Địa chỉ nhận hàng
     📞 Số điện thoại liên hệ"
```

**Tính năng đặc biệt:**
- 🔄 Đồng bộ hội thoại giữa Website và Zalo
- 📊 Theo dõi tất cả kênh trong 1 Dashboard
- 🎯 AI nhận diện khách hàng qua cả 3 kênh
- 🤖 3 chế độ AI: Tự động / Gợi ý / Chỉ thông báo

---

## 🤔 So sánh: Zalo OA vs Zalo Personal

| Tiêu chí | 💬 Zalo OA | 👤 Zalo Personal |
|----------|------------|------------------|
| **Chi phí** | Mất phí đăng ký & xác minh | Miễn phí (dùng tài khoản có sẵn) |
| **Độ tin cậy** | Cao (có dấu tích xanh) | Trung bình (tài khoản cá nhân) |
| **Giới hạn tin nhắn** | Không giới hạn | Có thể bị giới hạn nếu spam |
| **Template Message** | ✅ Có | ❌ Không |
| **Broadcast** | ✅ Có | ⚠️ Hạn chế |
| **API chính thức** | ✅ Có | ❌ Không (unofficial) |
| **Rủi ro** | ✅ An toàn | ⚠️ Có thể bị khóa nếu spam |
| **Phù hợp cho** | Doanh nghiệp lớn, thương hiệu | Doanh nghiệp nhỏ, test, cửa hàng online |
| **Setup** | Phức tạp (cần xác minh) | Đơn giản (quét QR) |
| **AI Mode** | Chỉ tự động | Tự động / Gợi ý / Thông báo |

### 💡 Khuyến nghị:

**Dùng Zalo Personal khi:**
- ✅ Mới bắt đầu kinh doanh online
- ✅ Chưa có Zalo OA
- ✅ Muốn test trước khi đầu tư
- ✅ Muốn kiểm soát câu trả lời AI

**Dùng Zalo OA khi:**
- ✅ Đã có thương hiệu rõ ràng
- ✅ Cần gửi tin nhắn hàng loạt
- ✅ Muốn tích hợp Zalo Pay
- ✅ Cần độ tin cậy cao

**Dùng cả hai khi:**
- ✅ Muốn tối ưu hiệu quả
- ✅ OA cho thông báo, Personal cho tư vấn chi tiết
- ✅ Tách biệt kênh chăm sóc khách hàng

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
   → Đề xuất: Tăng tồn kho và chạy chiến dịch quảng cáo qua Zalo Ads

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
- ✅ Độ chính xác cao, trả lời dựa trên tài liệu thực tế của doanh nghiệp
- ✅ Giảm thiểu tối đa việc bịa đặt thông tin (hallucination)
- ✅ Dễ dàng cập nhật khi doanh nghiệp thay đổi chính sách

---

## 🏗️ Kiến trúc hệ thống

```
┌─────────────────────────────────────────────────────────────┐
│                    USER INTERFACES                           │
│ [Dashboard] [Widget] [Zalo OA] [Zalo Personal] [Admin]      │
└─────────────┬───────────────────────────────────────────────┘
              │
┌─────────────▼───────────────────────────────────────────────┐
│                   FRONTEND LAYER                             │
│  Next.js 14 (App Router) - SSR/SSG & State Management        │
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
    │       • Zalo OA API  • Payment Gateway           │
    └──────────────────────────────────────────────────┘
```

### 📐 Luồng xử lý chính

#### **1. Luồng Chat với RAG (Đa kênh)**
```
Khách hàng gửi tin nhắn
  ├─ Website Widget → WebSocket → API Gateway
  ├─ Zalo OA → Webhook → API Gateway
  └─ Zalo Personal → WebSocket/Polling → API Gateway
                           ↓
                    Chatbot Service
                                              ↓
                                    RAG Engine truy vấn
                                    Vector Database
                                              ↓
                                    Context + Query → Gemini API
                                              ↓
                                    AI Response ← Chatbot Service
                                              ↓
         ┌─────────┴─────────────────┐
         ↓                            ↓
  Lưu lịch sử → MySQL      Check AI Mode:
                           • Auto: Gửi ngay
                           • Suggestion: Chờ xác nhận
                           • Notification: Chỉ thông báo
                           ↓
                    Response →
  ├─ Website Widget (real-time)
  ├─ Zalo OA (qua API)
  └─ Zalo Personal (qua API/WebSocket)
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
| ⚡ **Next.js** | React Framework, SSR, SSG, App Router | 14.x |
| 🎨 **TailwindCSS** | Styling & UI Components | 3.x |
| 🎭 **Shadcn/ui** | Component Library | Latest |
| 📊 **Recharts** | Biểu đồ và visualization | Latest |
| 🔌 **Socket.io Client** | Real-time chat | 4.x |
| 📦 **Zustand** | State management | Latest |
| 🔐 **NextAuth.js** | Authentication | 5.x |
| 🎯 **TypeScript** | Type safety | 5.x |
| 🔄 **React Query** | Data fetching & caching | Latest |

#### **Tính năng Next.js được sử dụng:**

- ⚡ **App Router**: Routing mới với Server Components
- 🚀 **Server-Side Rendering (SSR)**: Tối ưu SEO và performance
- 📦 **Static Site Generation (SSG)**: Pre-render pages tĩnh
- 🎨 **Server Actions**: Xử lý form và mutations
- 🖼️ **Image Optimization**: Tự động tối ưu hình ảnh
- 📱 **Progressive Web App (PWA)**: Hỗ trợ offline mode
- 🔄 **Incremental Static Regeneration**: Cập nhật nội dung động

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

### **External APIs & Integration**

- 💬 **Zalo Official Account API**: Tích hợp Zalo OA
  - Webhook xử lý tin nhắn
  - Gửi tin nhắn tự động
  - Template messages
  - User profile API
- 👤 **Zalo Personal Account**: Tích hợp tài khoản cá nhân (unofficial)
  - QR Code authentication
  - Real-time message sync
  - WebSocket connection
  - Session management
  - ⚠️ **Lưu ý**: Không phải API chính thức, có rủi ro bị khóa tài khoản nếu vi phạm điều khoản Zalo
- 💳 **VNPay / MoMo API**: Payment gateway

---

## 🧩 RAG & MCP Framework

### 🔍 RAG (Retrieval-Augmented Generation) - Công nghệ AI

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
│  │ - Chuyển text → vector embeddings         │         │
│  │ - Model: text-embedding-004 (768 dim)     │         │
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
- ✅ Giảm thiểu hallucination: Ít bịa đặt thông tin hơn
- ✅ Có thể trích dẫn nguồn tài liệu
- ✅ Dễ cập nhật: Upload tài liệu mới là xong

---

### 🔄 MCP (Model – Context – Process) - Framework Phân Tích Hệ Thống

> **📝 Định nghĩa:** MCP là **framework phân tích hệ thống**, giúp mô tả rõ ràng phạm vi và hoạt động của dự án. Đây KHÔNG phải là công nghệ AI như RAG, mà là phương pháp tổ chức và phân tích hệ thống.

**Sự khác biệt:**
- **RAG** = Công nghệ AI thực tế (Retrieval-Augmented Generation)
- **MCP** = Framework phân tích và tổ chức hệ thống

**Áp dụng MCP trong dự án:**

```
┌─────────────────────────────────────────────────────────┐
│  1️⃣ MODEL (M – Mô hình hệ thống)                       │
│                                                          │
│  Hệ thống AI Agent for Business gồm các thành phần:    │
│                                                          │
│  🤖 Chatbot AI                                          │
│     • Tư vấn khách hàng 24/7                           │
│     • Ghi nhận đơn hàng từ chat                        │
│     • Tích hợp đa kênh (Website, Zalo OA, Zalo Personal)│
│                                                          │
│  📊 CRM Mini                                            │
│     • Quản lý khách hàng                               │
│     • Quản lý sản phẩm                                 │
│     • Quản lý đơn hàng                                 │
│     • Báo cáo doanh thu                                │
│                                                          │
│  🧠 AI Phân tích & Đề xuất                              │
│     • Phân tích RFM khách hàng                         │
│     • Đề xuất chiến lược marketing                     │
│     • Dự đoán xu hướng nhập hàng                       │
│     • Gợi ý chương trình khuyến mãi                    │
│                                                          │
│  📚 Quản lý tài liệu (RAG)                              │
│     • Upload tài liệu PDF/DOCX/TXT                     │
│     • Vector hóa và lưu trữ                            │
│     • Truy xuất thông tin chính xác                    │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│  2️⃣ CONTEXT (C – Ngữ cảnh)                             │
│                                                          │
│  📌 Bối cảnh thực tế:                                   │
│     Doanh nghiệp nhỏ tại Việt Nam không có công cụ     │
│     quản lý khách hàng và CSKH hiệu quả                │
│                                                          │
│  ⚠️ Vấn đề cần giải quyết:                              │
│     • Chi phí CSKH cao (thuê nhân viên)                │
│     • Thiếu nhân sự chuyên môn                         │
│     • Thất thoát dữ liệu khách hàng                    │
│     • Khó phân tích & đưa ra chiến lược kinh doanh     │
│     • Không có insight về hành vi khách hàng           │
│                                                          │
│  ✅ Giải pháp:                                          │
│     AI Agent hỗ trợ tự động:                           │
│     → CSKH: Chatbot trả lời 24/7                       │
│     → Quản lý: CRM tập trung tất cả dữ liệu            │
│     → Phân tích: AI đưa ra insights & đề xuất          │
│                                                          │
│  🎯 Yêu cầu:                                            │
│     • Giá thành hợp lý cho DN nhỏ                      │
│     • Dễ sử dụng, không cần kỹ năng kỹ thuật          │
│     • Tích hợp đa kênh (Website, Zalo)                 │
│     • Hỗ trợ tiếng Việt hoàn toàn                      │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│  3️⃣ PROCESS (P – Quy trình hoạt động)                  │
│                                                          │
│  📱 Quy trình End-to-End:                               │
│                                                          │
│  BƯỚC 1: Tương tác khách hàng                          │
│  ┌──────────────────────────────────────────────┐     │
│  │ Khách hàng chat qua:                          │     │
│  │ • Website Widget                              │     │
│  │ • Zalo Official Account (OA)                  │     │
│  │ • Zalo Personal Account                       │     │
│  └──────────────────────────────────────────────┘     │
│                    ↓                                     │
│  BƯỚC 2: AI xử lý & trả lời                            │
│  ┌──────────────────────────────────────────────┐     │
│  │ • Chatbot nhận tin nhắn                       │     │
│  │ • Nếu cần, truy xuất tài liệu (RAG)          │     │
│  │ • AI trả lời dựa trên dữ liệu shop thực tế    │     │
│  └──────────────────────────────────────────────┘     │
│                    ↓                                     │
│  BƯỚC 3: Ghi nhận dữ liệu                              │
│  ┌──────────────────────────────────────────────┐     │
│  │ • Lưu đơn hàng vào CRM                        │     │
│  │ • Lưu thông tin khách hàng                    │     │
│  │ • Ghi nhận lịch sử tương tác                  │     │
│  └──────────────────────────────────────────────┘     │
│                    ↓                                     │
│  BƯỚC 4: Dashboard & Quản lý                           │
│  ┌──────────────────────────────────────────────┐     │
│  │ • Doanh nghiệp đăng nhập Dashboard           │     │
│  │ • Xem báo cáo doanh thu, đơn hàng            │     │
│  │ • Quản lý khách hàng, sản phẩm               │     │
│  └──────────────────────────────────────────────┘     │
│                    ↓                                     │
│  BƯỚC 5: AI Phân tích                                  │
│  ┌──────────────────────────────────────────────┐     │
│  │ • AI phân tích dữ liệu doanh thu              │     │
│  │ • Phân tích hành vi khách hàng (RFM)          │     │
│  │ • Dự đoán xu hướng                            │     │
│  └──────────────────────────────────────────────┘     │
│                    ↓                                     │
│  BƯỚC 6: Đề xuất chiến lược                            │
│  ┌──────────────────────────────────────────────┐     │
│  │ AI đưa ra insights & recommendations:         │     │
│  │ • Chiến lược marketing                        │     │
│  │ • Kế hoạch nhập hàng                          │     │
│  │ • Chương trình khuyến mãi                     │     │
│  │ • Phân khúc khách hàng                        │     │
│  └──────────────────────────────────────────────┘     │
│                    ↓                                     │
│  🔄 Vòng lặp cải tiến liên tục                         │
│     Doanh nghiệp áp dụng → Thu thập data mới           │
│     → AI học & cải thiện → Đề xuất tốt hơn            │
└─────────────────────────────────────────────────────────┘
```

**Tóm tắt MCP:**
- **MODEL**: Mô tả CÁI GÌ hệ thống làm (Chatbot, CRM, AI Analytics, RAG)
- **CONTEXT**: Giải thích TẠI SAO cần hệ thống này (vấn đề, giải pháp, yêu cầu)
- **PROCESS**: Trình bày NHƯ THẾ NÀO hệ thống hoạt động (workflow end-to-end)

---

## ⚙️ Cài đặt

### 📋 Yêu cầu hệ thống

- **Java**: 17 hoặc cao hơn
- **Node.js**: 18.x hoặc cao hơn (cho Next.js)
- **Next.js**: 14.x
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

Backend sẽ chạy tại `http://localhost:8088`

#### **3. Setup Frontend (Next.js 14)**

```bash
cd frontend

# Cài đặt dependencies
npm install

# Cấu hình environment
cp .env.example .env.local

# Chỉnh sửa .env.local:
# NEXT_PUBLIC_API_URL=http://localhost:8088/api/v1
# NEXT_PUBLIC_SOCKET_URL=ws://localhost:8088
# NEXTAUTH_SECRET=your_nextauth_secret_key
# NEXTAUTH_URL=http://localhost:3008

# Chạy development server
npm run dev
```

Frontend (Next.js) sẽ chạy tại `http://localhost:3008`

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
      - "8088:8088"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/ai_agent_db
      GEMINI_API_KEY: ${GEMINI_API_KEY}
    depends_on:
      - mysql

  frontend:
    build: 
      context: ./frontend
      dockerfile: Dockerfile
    ports:
      - "3008:3008"
    environment:
      NEXT_PUBLIC_API_URL: http://backend:8088/api/v1
      NEXT_PUBLIC_SOCKET_URL: ws://backend:8088
      NEXTAUTH_SECRET: ${NEXTAUTH_SECRET}
      NEXTAUTH_URL: http://localhost:3008
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

## 👥 Phân quyền và vai trò người dùng

Hệ thống sử dụng **Role-Based Access Control (RBAC)** với 3 vai trò chính:

### 🔐 1. ADMIN (Quản trị viên hệ thống)

**Quyền truy cập:**
- ✅ **Full access** toàn bộ hệ thống
- ✅ Quản lý tất cả doanh nghiệp (businesses)
- ✅ Xem và sửa tất cả dữ liệu
- ✅ Quản lý người dùng hệ thống
- ✅ Cấu hình hệ thống global
- ✅ Xem logs và monitoring
- ✅ Quản lý subscription và billing

**Giao diện:**
```
┌─────────────────────────────────────────────────────────┐
│  🎛️ ADMIN DASHBOARD                                     │
├─────────────────────────────────────────────────────────┤
│  📊 Tổng quan hệ thống                                  │
│     • Tổng số doanh nghiệp: 150                         │
│     • Tổng số khách hàng: 15,000                        │
│     • Messages hôm nay: 25,000                          │
│                                                          │
│  🏢 Quản lý doanh nghiệp                                │
│     • Danh sách businesses                              │
│     • Duyệt đăng ký mới                                 │
│     • Suspend/Active account                            │
│                                                          │
│  💳 Billing & Subscription                              │
│     • Quản lý gói dịch vụ                               │
│     • Doanh thu                                         │
│     • Gia hạn/Nâng cấp                                  │
│                                                          │
│  ⚙️ Cấu hình hệ thống                                   │
│     • AI Model settings                                 │
│     • Rate limiting                                     │
│     • Email/SMS gateway                                 │
│                                                          │
│  📈 Analytics & Reports                                 │
│     • System performance                                │
│     • Error logs                                        │
│     • Usage statistics                                  │
└─────────────────────────────────────────────────────────┘
```

**API Endpoints (Admin only):**
```
GET    /admin/businesses           # Danh sách doanh nghiệp
GET    /admin/businesses/{id}      # Chi tiết doanh nghiệp
PUT    /admin/businesses/{id}      # Cập nhật doanh nghiệp
DELETE /admin/businesses/{id}      # Xóa doanh nghiệp
GET    /admin/stats                # Thống kê hệ thống
GET    /admin/logs                 # System logs
POST   /admin/broadcast            # Gửi thông báo toàn hệ thống
```

---

### 🏢 2. BUSINESS (Doanh nghiệp)

**Quyền truy cập:**
- ✅ Quản lý **dữ liệu riêng** của doanh nghiệp mình
- ✅ Quản lý khách hàng của mình
- ✅ Quản lý sản phẩm, đơn hàng
- ✅ Cấu hình chatbot và AI
- ✅ Tích hợp Zalo (OA + Personal)
- ✅ Upload tài liệu RAG
- ✅ Xem analytics của doanh nghiệp mình
- ❌ **KHÔNG** thấy dữ liệu doanh nghiệp khác
- ❌ **KHÔNG** có quyền admin

**Giao diện:**
```
┌─────────────────────────────────────────────────────────┐
│  🏢 BUSINESS DASHBOARD - Cửa hàng ABC                   │
├─────────────────────────────────────────────────────────┤
│  📊 Tổng quan kinh doanh                                │
│     • Doanh thu tháng này: 150,000,000đ                 │
│     • Đơn hàng mới: 45                                  │
│     • Khách hàng mới: 23                                │
│     • Tin nhắn chưa đọc: 12                             │
│                                                          │
│  💬 Quản lý hội thoại                                   │
│     • Website Widget: 15 cuộc trò chuyện               │
│     • Zalo OA: 8 cuộc trò chuyện                       │
│     • Zalo Personal: 20 cuộc trò chuyện                │
│                                                          │
│  👥 CRM - Quản lý khách hàng                            │
│     • Danh sách khách hàng                              │
│     • Phân loại (VIP, Regular, Potential)               │
│     • Lịch sử mua hàng                                  │
│                                                          │
│  📦 Quản lý sản phẩm & đơn hàng                         │
│     • Danh mục sản phẩm                                 │
│     • Tồn kho                                           │
│     • Đơn hàng (pending, processing, completed)         │
│                                                          │
│  🤖 Cấu hình AI Chatbot                                 │
│     • Tích hợp kênh (Website, Zalo OA, Zalo Personal)  │
│     • Upload tài liệu RAG                               │
│     • Chế độ AI (Auto/Suggestion/Notification)          │
│     • Training & Fine-tuning                            │
│                                                          │
│  📊 AI Insights & Analytics                             │
│     • Phân tích khách hàng (RFM)                        │
│     • Sản phẩm bán chạy                                 │
│     • Dự đoán doanh thu                                 │
│     • Đề xuất chiến lược                                │
│                                                          │
│  ⚙️ Cài đặt                                             │
│     • Thông tin doanh nghiệp                            │
│     • Quản lý nhân viên                                 │
│     • Thanh toán & gói dịch vụ                          │
└─────────────────────────────────────────────────────────┘
```

**API Endpoints (Business):**
```
# Khách hàng
GET    /business/customers         # Khách hàng của mình
POST   /business/customers         # Thêm khách hàng
PUT    /business/customers/{id}    # Cập nhật khách hàng
DELETE /business/customers/{id}    # Xóa khách hàng

# Sản phẩm
GET    /business/products          # Sản phẩm của mình
POST   /business/products          # Thêm sản phẩm
PUT    /business/products/{id}     # Cập nhật sản phẩm
DELETE /business/products/{id}     # Xóa sản phẩm

# Đơn hàng
GET    /business/orders            # Đơn hàng của mình
POST   /business/orders            # Tạo đơn hàng
PUT    /business/orders/{id}       # Cập nhật đơn hàng

# Chatbot & AI
GET    /business/conversations     # Hội thoại của mình
POST   /business/chatbot/config    # Cấu hình chatbot
POST   /business/documents/upload  # Upload tài liệu RAG

# Zalo Integration
POST   /business/zalo/oa/config    # Config Zalo OA
POST   /business/zalo/personal/qr  # Generate QR Zalo Personal
GET    /business/zalo/conversations # Zalo conversations

# Analytics
GET    /business/analytics/overview    # Tổng quan
GET    /business/analytics/insights    # AI Insights
```

---

### 👤 3. CUSTOMER (Khách hàng)

**Quyền truy cập:**
- ✅ Chat với chatbot (Website, Zalo)
- ✅ Xem lịch sử chat của mình
- ✅ Xem đơn hàng của mình
- ✅ Cập nhật thông tin cá nhân
- ❌ **KHÔNG** thấy thông tin khách hàng khác
- ❌ **KHÔNG** vào Dashboard doanh nghiệp
- ❌ **KHÔNG** có quyền quản lý

**Giao diện:**
```
┌─────────────────────────────────────────────────────────┐
│  👤 CUSTOMER PORTAL - Nguyễn Văn A                      │
├─────────────────────────────────────────────────────────┤
│  💬 Trò chuyện với shop                                 │
│     [Chat widget hiển thị ở đây]                        │
│                                                          │
│  📦 Đơn hàng của tôi                                    │
│     • Đơn hàng #12345 - Đang giao                       │
│     • Đơn hàng #12344 - Hoàn thành                      │
│     • Đơn hàng #12343 - Đã hủy                          │
│                                                          │
│  👤 Thông tin cá nhân                                   │
│     • Họ tên: Nguyễn Văn A                              │
│     • Email: nguyenvana@email.com                       │
│     • SĐT: 0901234567                                   │
│     • Địa chỉ: ...                                      │
│                                                          │
│  🔔 Thông báo                                            │
│     • Đơn hàng #12345 đang được giao                    │
│     • Khuyến mãi mới: Giảm 20%                          │
└─────────────────────────────────────────────────────────┘
```

**API Endpoints (Customer):**
```
# Chat
POST   /customer/chat/send         # Gửi tin nhắn
GET    /customer/chat/history      # Lịch sử chat của mình

# Đơn hàng
GET    /customer/orders            # Đơn hàng của mình
GET    /customer/orders/{id}       # Chi tiết đơn hàng
POST   /customer/orders/{id}/cancel # Hủy đơn hàng

# Profile
GET    /customer/profile           # Thông tin của mình
PUT    /customer/profile           # Cập nhật thông tin
```

---

## 🔐 Bảng phân quyền chi tiết

| Chức năng | 🎛️ Admin | 🏢 Business | 👤 Customer |
|-----------|----------|-------------|-------------|
| **Quản lý hệ thống** | ✅ Full | ❌ | ❌ |
| **Quản lý doanh nghiệp khác** | ✅ | ❌ | ❌ |
| **Quản lý khách hàng (của mình)** | ✅ | ✅ | ❌ |
| **Quản lý sản phẩm** | ✅ | ✅ | ❌ |
| **Quản lý đơn hàng (của mình)** | ✅ | ✅ | ✅ (chỉ xem) |
| **Cấu hình AI Chatbot** | ✅ | ✅ | ❌ |
| **Upload tài liệu RAG** | ✅ | ✅ | ❌ |
| **Tích hợp Zalo** | ✅ | ✅ | ❌ |
| **Xem Analytics (của mình)** | ✅ | ✅ | ❌ |
| **Xem Analytics (toàn hệ thống)** | ✅ | ❌ | ❌ |
| **Chat với bot** | ✅ | ✅ | ✅ |
| **Xem hội thoại khách hàng** | ✅ | ✅ | ❌ |
| **Billing & Subscription** | ✅ | ✅ (của mình) | ❌ |
| **System logs** | ✅ | ❌ | ❌ |

---

## 🔑 Authentication Flow

```
┌──────────────────────────────────────────────────────┐
│  1. User truy cập hệ thống                            │
│     → http://localhost:3008                           │
└──────────────────┬───────────────────────────────────┘
                   │
┌──────────────────▼───────────────────────────────────┐
│  2. Login/Register                                    │
│     • Email + Password                                │
│     • Chọn vai trò: Business / Customer              │
│     • (Admin tạo bởi system)                          │
└──────────────────┬───────────────────────────────────┘
                   │
┌──────────────────▼───────────────────────────────────┐
│  3. Backend xác thực                                  │
│     • Spring Security + JWT                           │
│     • Kiểm tra credentials                            │
│     • Tạo JWT token với role                          │
└──────────────────┬───────────────────────────────────┘
                   │
┌──────────────────▼───────────────────────────────────┐
│  4. Response JWT Token                                │
│     {                                                 │
│       "token": "eyJhbGc...",                          │
│       "user": {                                       │
│         "id": 123,                                    │
│         "email": "admin@business.com",                │
│         "role": "BUSINESS",  // ADMIN/BUSINESS/CUSTOMER│
│         "businessId": 456                             │
│       }                                               │
│     }                                                 │
└──────────────────┬───────────────────────────────────┘
                   │
┌──────────────────▼───────────────────────────────────┐
│  5. Frontend route theo role                          │
│     • ADMIN    → /admin/dashboard                     │
│     • BUSINESS → /business/dashboard                  │
│     • CUSTOMER → /customer/portal                     │
└───────────────────────────────────────────────────────┘
```

---

## 🛡️ Bảo mật

**1. JWT Token:**
```json
{
  "sub": "user_email@domain.com",
  "role": "BUSINESS",
  "businessId": 123,
  "permissions": ["read:customers", "write:products"],
  "iat": 1735171200,
  "exp": 1735257600
}
```

**2. Route Protection (Next.js):**
```typescript
// middleware.ts
export function middleware(request: NextRequest) {
  const token = request.cookies.get('token');
  const userRole = decodeToken(token).role;
  
  // Admin routes
  if (request.url.includes('/admin') && userRole !== 'ADMIN') {
    return NextResponse.redirect('/403');
  }
  
  // Business routes
  if (request.url.includes('/business') && userRole !== 'BUSINESS') {
    return NextResponse.redirect('/403');
  }
  
  // Customer routes
  if (request.url.includes('/customer') && userRole !== 'CUSTOMER') {
    return NextResponse.redirect('/403');
  }
}
```

**3. Backend Security (Spring Boot):**
```java
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> getAllBusinesses() { ... }

@PreAuthorize("hasRole('BUSINESS')")
public ResponseEntity<?> getMyCustomers(@AuthenticationPrincipal User user) {
    // Chỉ trả về khách hàng của business này
    return customerService.findByBusinessId(user.getBusinessId());
}

@PreAuthorize("hasRole('CUSTOMER')")
public ResponseEntity<?> getMyOrders(@AuthenticationPrincipal User user) {
    // Chỉ trả về đơn hàng của customer này
    return orderService.findByCustomerId(user.getId());
}
```

---

## 📖 Sử dụng

### 🔐 Đăng nhập hệ thống

1. Truy cập `http://localhost:3008`
2. Chọn vai trò và đăng ký tài khoản
3. Đăng nhập với email/password

**Demo accounts:**

**🎛️ Admin:**
- Email: `admin@aiagent.system`
- Password: `Admin@123456`
- Dashboard: `/admin/dashboard`

**🏢 Business (Doanh nghiệp):**
- Email: `demo@business.com`
- Password: `Business@123`
- Dashboard: `/business/dashboard`

**👤 Customer (Khách hàng):**
- Email: `customer@example.com`
- Password: `Customer@123`
- Portal: `/customer/portal`

### 🌐 Tích hợp Chatbot Widget vào Website

```html
<!-- Thêm đoạn code sau vào website của bạn (trước thẻ </body>) -->
<script>
  (function(w,d,s,o,f,js,fjs){
    w['ChatbotWidget']=o;w[o]=w[o]||function(){(w[o].q=w[o].q||[]).push(arguments)};
    js=d.createElement(s),fjs=d.getElementsByTagName(s)[0];
    js.id=o;js.src=f;js.async=1;fjs.parentNode.insertBefore(js,fjs);
  }(window,document,'script','chatbot','http://localhost:3008/widget.js'));
  
  chatbot('init', {
    businessId: 'YOUR_BUSINESS_ID',
    position: 'bottom-right',  // bottom-left, top-right, top-left
    color: '#0084ff',           // Màu chủ đạo
    title: 'Hỗ trợ khách hàng',
    subtitle: 'Chúng tôi luôn sẵn sàng hỗ trợ bạn',
    avatar: 'https://yourdomain.com/bot-avatar.png'
  });
</script>
```

**Tùy chỉnh nâng cao:**
```javascript
chatbot('init', {
  businessId: 'YOUR_BUSINESS_ID',
  position: 'bottom-right',
  color: '#0084ff',
  
  // Tùy chỉnh hiển thị
  title: 'AI Chatbot',
  subtitle: 'Online',
  welcomeMessage: 'Xin chào! Tôi có thể giúp gì cho bạn?',
  
  // Tùy chỉnh hành vi
  autoOpen: false,           // Tự động mở chat
  autoOpenDelay: 5000,       // Delay trước khi tự động mở (ms)
  showNotification: true,    // Hiển thị thông báo
  
  // Custom styles
  width: '400px',
  height: '600px',
  zIndex: 9999
});
```

### 💬 Tích hợp Zalo OA

**Bước 1: Đăng ký Zalo Official Account**
1. Truy cập [https://oa.zalo.me](https://oa.zalo.me)
2. Đăng ký Official Account cho doanh nghiệp
3. Hoàn thành xác minh doanh nghiệp

**Bước 2: Lấy thông tin API**
1. Vào phần **Cài đặt** → **Quản lý ứng dụng**
2. Tạo App mới hoặc sử dụng App có sẵn
3. Lấy thông tin:
   - OA ID
   - App ID
   - App Secret
   - Access Token

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

**Bước 3: Cấu hình Webhook**
1. Vào Dashboard hệ thống → **Tích hợp Zalo OA**
2. Nhập thông tin:
   - OA ID
   - App ID  
   - App Secret
   - Access Token
3. Cấu hình Webhook URL tại Zalo:
   - Callback URL: `https://yourdomain.com/api/v1/webhook/zalo`
   - Secret Key: Tạo tự động trong Dashboard
4. Test kết nối và lưu cấu hình

**Bước 4: Bắt đầu sử dụng**
- Khách hàng nhắn tin vào Zalo OA
- Bot tự động trả lời dựa trên RAG
- Theo dõi hội thoại trong Dashboard

### 👤 Tích hợp Zalo Personal Account (Tài khoản cá nhân)

> **⚠️ CẢNH BÁO QUAN TRỌNG:**  
> - Tính năng này sử dụng **unofficial API** (không phải API chính thức từ Zalo)
> - Có rủi ro **bị khóa tài khoản** nếu vi phạm điều khoản sử dụng Zalo
> - **Không nên spam** hoặc gửi tin nhắn hàng loạt
> - Chỉ nên dùng cho **test** hoặc **doanh nghiệp nhỏ** với lượng tin nhắn vừa phải
> - Với doanh nghiệp chuyên nghiệp, khuyến nghị dùng **Zalo OA** để đảm bảo an toàn

> **💡 Phù hợp cho:** Doanh nghiệp nhỏ, cửa hàng online chưa có Zalo OA, hoặc test tính năng trước khi đầu tư vào Zalo OA.

**Bước 1: Đăng nhập qua QR Code**

1. Vào Dashboard → **Tích hợp Zalo Personal**
2. Click **"Đăng nhập bằng Zalo"**
3. Hệ thống hiển thị **mã QR Code**
4. Mở app Zalo trên điện thoại → Quét mã QR
5. Xác nhận đăng nhập

**Bước 2: Cấu hình AI Chatbot**

```
┌─────────────────────────────────────────────────────┐
│  Cấu hình AI cho tài khoản Zalo                     │
├─────────────────────────────────────────────────────┤
│                                                      │
│  ✅ Bật AI tự động trả lời                          │
│  ⏰ Thời gian hoạt động: 24/7                       │
│  🎯 Chế độ:                                         │
│     ○ Tự động hoàn toàn                             │
│     ● Gợi ý câu trả lời (cần xác nhận)             │
│     ○ Chỉ thông báo (không tự động)                │
│                                                      │
│  📚 Sử dụng tài liệu RAG: ✅                        │
│  🛒 Tự động ghi nhận đơn hàng: ✅                   │
│  📊 Tự động phân loại khách hàng: ✅                │
│                                                      │
│  [Lưu cấu hình]                                     │
└─────────────────────────────────────────────────────┘
```

**Bước 3: Quản lý và giám sát**

- 📱 **Dashboard thống nhất**: Xem tất cả hội thoại từ tài khoản Zalo
- 🔄 **Live preview**: Xem tin nhắn real-time
- ✋ **Takeover**: Chuyển sang chế độ thủ công khi cần
- 📊 **Thống kê**: Số tin nhắn, tỷ lệ trả lời tự động, độ hài lòng

**Bước 4: Chế độ hoạt động**

```
┌──────────────────────────────────────────────────────┐
│  Chế độ 1: TỰ ĐỘNG HOÀN TOÀN                        │
│  • AI tự động trả lời TẤT CẢ tin nhắn               │
│  • Không cần can thiệp                               │
│  • Phù hợp: Ngoài giờ làm việc, lúc bận             │
├──────────────────────────────────────────────────────┤
│  Chế độ 2: GỢI Ý + XÁC NHẬN                         │
│  • AI gợi ý câu trả lời                              │
│  • Bạn xem và click "Gửi" hoặc chỉnh sửa           │
│  • Phù hợp: Trong giờ làm việc                      │
├──────────────────────────────────────────────────────┤
│  Chế độ 3: CHỈ THÔNG BÁO                            │
│  • AI KHÔNG tự động trả lời                          │
│  • Chỉ phân tích và thông báo                       │
│  • Phù hợp: Khi muốn tự trả lời                     │
└──────────────────────────────────────────────────────┘
```

**Ví dụ luồng hoạt động:**

```
1. Khách hàng: "Shop ơi, áo thun size M còn không?"
   
2. AI phân tích → Tìm trong database
   
3. Dashboard thông báo: 🔔 Tin nhắn mới từ Nguyễn Văn A
   
4. AI gợi ý: 
   "Dạ shop còn ạ. Áo thun size M có 3 màu:
   - Trắng: 150k
   - Đen: 150k  
   - Xanh navy: 150k
   Anh/chị muốn đặt màu nào ạ?"
   
5. [Chế độ tự động] → Gửi ngay
   [Chế độ gợi ý] → Hiển thị nút "Gửi" hoặc "Chỉnh sửa"
```

**Tính năng bảo mật:**

- 🔒 **Mã hóa end-to-end**: Session được mã hóa
- 🔐 **Token refresh**: Tự động làm mới phiên đăng nhập
- 🚪 **Logout từ xa**: Đăng xuất từ Dashboard bất cứ lúc nào
- 📱 **Thông báo**: Nhận thông báo khi có người đăng nhập
- ⏰ **Session timeout**: Tự động đăng xuất sau 7 ngày không dùng

**Lợi ích:**

- ✅ Không cần đăng ký Zalo OA (tiết kiệm chi phí)
- ✅ Dùng ngay tài khoản Zalo hiện có
- ✅ Khách hàng quen thuộc với tài khoản
- ✅ Linh hoạt chuyển đổi chế độ tự động/thủ công
- ✅ AI học từ cách bạn trả lời

---

## 📚 API Documentation

### Base URL
```
http://localhost:8088/api/v1
```

### Authentication

Tất cả API (trừ login/register) yêu cầu JWT token trong header:

```
Authorization: Bearer <your_jwt_token>
```

**JWT Token chứa thông tin vai trò:**
- `role`: ADMIN / BUSINESS / CUSTOMER
- `businessId`: ID của doanh nghiệp (với BUSINESS role)
- `permissions`: Danh sách quyền cụ thể

**Prefix routes theo role:**
- `/admin/*` - Chỉ ADMIN
- `/business/*` - Chỉ BUSINESS  
- `/customer/*` - Chỉ CUSTOMER
- `/public/*` - Không cần đăng nhập

### Endpoints

#### **Authentication**

**Register (Business):**
```http
POST /auth/register/business
Content-Type: application/json

{
  "businessName": "Cửa hàng ABC",
  "email": "admin@abc.com",
  "password": "Password123",
  "phone": "0901234567",
  "address": "123 Đường ABC, Quận 1, TP.HCM"
}

Response: 201 Created
{
  "id": 1,
  "businessName": "Cửa hàng ABC",
  "email": "admin@abc.com",
  "role": "BUSINESS",
  "businessId": 1,
  "token": "eyJhbGciOiJIUzI1NiIs..."
}
```

**Register (Customer):**
```http
POST /auth/register/customer
Content-Type: application/json

{
  "name": "Nguyễn Văn A",
  "email": "customer@example.com",
  "password": "Password123",
  "phone": "0912345678"
}

Response: 201 Created
{
  "id": 101,
  "name": "Nguyễn Văn A",
  "email": "customer@example.com",
  "role": "CUSTOMER",
  "token": "eyJhbGciOiJIUzI1NiIs..."
}
```

**Login:**
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
  "expiresIn": 86400,
  "user": {
    "id": 1,
    "email": "admin@abc.com",
    "role": "BUSINESS",
    "businessId": 1,
    "businessName": "Cửa hàng ABC"
  }
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
  "channel": "website"  // hoặc "zalo"
}

Response: 200 OK
{
  "reply": "Dạ, em có thông tin sản phẩm iPhone 15 như sau...",
  "intent": "product_inquiry",
  "entities": ["iPhone 15"],
  "actions": ["show_product"],
  "products": [
    {
      "id": 1,
      "name": "iPhone 15 Pro Max 256GB",
      "price": 29990000,
      "stock": 50
    }
  ]
}
```

#### **Zalo OA Webhook**

```http
POST /webhook/zalo/oa
Content-Type: application/json

{
  "event_name": "user_send_text",
  "timestamp": 1735171200000,
  "message": {
    "text": "Tôi muốn mua iPhone 15",
    "msg_id": "abc123"
  },
  "sender": {
    "id": "1234567890"
  },
  "recipient": {
    "id": "YOUR_OA_ID"
  }
}

Response: 200 OK
{
  "status": "success"
}
```

#### **Zalo Personal Account**

**Login via QR Code:**
```http
POST /zalo/personal/generate-qr
Authorization: Bearer <token>

Response: 200 OK
{
  "qrCode": "data:image/png;base64,iVBORw0KGgo...",
  "qrCodeUrl": "http://localhost:3008/qr/abc123",
  "sessionId": "session_abc123",
  "expiresIn": 300  // 5 phút
}
```

**Check Login Status:**
```http
GET /zalo/personal/login-status/{sessionId}
Authorization: Bearer <token>

Response: 200 OK
{
  "status": "logged_in",  // hoặc "waiting", "expired"
  "accountInfo": {
    "name": "Cửa hàng ABC",
    "phone": "0901234567",
    "avatar": "https://...",
    "userId": "zalo_user_123"
  }
}
```

**Configure AI Settings:**
```http
PUT /zalo/personal/config
Authorization: Bearer <token>
Content-Type: application/json

{
  "mode": "suggestion",  // "auto", "suggestion", "notification"
  "autoReply": true,
  "useRAG": true,
  "autoCreateOrder": true,
  "workingHours": {
    "enabled": true,
    "start": "08:00",
    "end": "22:00"
  }
}

Response: 200 OK
{
  "status": "success",
  "config": { ... }
}
```

**Get Conversations:**
```http
GET /zalo/personal/conversations
Authorization: Bearer <token>
Query: ?page=1&limit=20&status=unread

Response: 200 OK
{
  "conversations": [
    {
      "id": "conv_123",
      "contact": {
        "name": "Nguyễn Văn A",
        "phone": "0912345678",
        "avatar": "https://..."
      },
      "lastMessage": {
        "text": "Cảm ơn shop",
        "timestamp": 1735171200000,
        "fromMe": false
      },
      "unreadCount": 2,
      "aiHandled": true
    }
  ],
  "total": 45,
  "page": 1
}
```

**Send Message (Manual or AI Suggested):**
```http
POST /zalo/personal/send-message
Authorization: Bearer <token>
Content-Type: application/json

{
  "conversationId": "conv_123",
  "message": "Dạ shop đang xử lý đơn hàng cho anh ạ",
  "isAISuggestion": false
}

Response: 200 OK
{
  "messageId": "msg_456",
  "status": "sent",
  "timestamp": 1735171300000
}
```

#### **Customers (Business only)**

```http
GET /business/customers
Authorization: Bearer <token>  # BUSINESS role required

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
      "segment": "VIP",
      "businessId": 1  # Chỉ khách hàng của business này
    }
  ],
  "total": 150,
  "page": 1,
  "pageSize": 20
}
```

#### **Products (Business only)**

```http
GET /business/products
Authorization: Bearer <token>  # BUSINESS role required

Response: 200 OK
{
  "data": [
    {
      "id": 1,
      "name": "iPhone 15 Pro Max 256GB",
      "price": 29990000,
      "stock": 50,
      "sold": 120,
      "category": "Điện thoại",
      "businessId": 1  # Chỉ sản phẩm của business này
    }
  ]
}
```

#### **Orders**

**Business - Tạo đơn hàng:**
```http
POST /business/orders
Authorization: Bearer <token>  # BUSINESS role required
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
  "totalAmount": 29990000,
  "businessId": 1
}
```

**Customer - Xem đơn hàng của mình:**
```http
GET /customer/orders
Authorization: Bearer <token>  # CUSTOMER role required

Response: 200 OK
{
  "data": [
    {
      "orderId": 123,
      "orderCode": "ORD20250101001",
      "status": "pending",
      "totalAmount": 29990000,
      "createdAt": "2025-01-01T10:00:00Z",
      "items": [...]
    }
  ]
}
```

#### **Analytics & AI Insights (Business only)**

```http
GET /business/analytics/insights
Authorization: Bearer <token>  # BUSINESS role required
Query: ?startDate=2025-01-01&endDate=2025-01-31

Response: 200 OK
{
  "businessId": 1,
  "period": {
    "start": "2025-01-01",
    "end": "2025-01-31"
  },
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

#### **Admin - System Stats**

```http
GET /admin/stats
Authorization: Bearer <token>  # ADMIN role required

Response: 200 OK
{
  "totalBusinesses": 150,
  "totalCustomers": 15000,
  "totalOrders": 5000,
  "totalRevenue": 2500000000,
  "messagesPerDay": 25000,
  "activeUsers": 8500,
  "systemHealth": "healthy"
}
```

#### **RAG Document Management (Business only)**

```http
POST /business/documents/upload
Authorization: Bearer <token>  # BUSINESS role required
Content-Type: multipart/form-data

file: [binary file data]
category: "policy"
name: "Chính sách bảo hành 2025"

Response: 201 Created
{
  "documentId": 45,
  "name": "Chính sách bảo hành 2025",
  "businessId": 1,
  "status": "processing",
  "chunks": 0,
  "message": "Đang xử lý tài liệu, AI sẽ học từ tài liệu này"
}
```

```http
GET /business/documents
Authorization: Bearer <token>  # BUSINESS role required

Response: 200 OK
{
  "documents": [
    {
      "id": 45,
      "name": "Chính sách bảo hành 2025",
      "category": "policy",
      "uploadedAt": "2025-01-15T10:30:00Z",
      "chunks": 12,
      "status": "ready",
      "businessId": 1  # Chỉ tài liệu của business này
    }
  ]
}
```

**Xem full API documentation tại:** `http://localhost:8088/swagger-ui.html`

---

## 🗺️ Roadmap

### ✅ Phase 1: MVP (Hoàn thành - Q1 2025)
- [x] Chatbot cơ bản với Gemini API
- [x] CRM mini (Khách hàng, Sản phẩm, Đơn hàng)
- [x] Dashboard thống kê
- [x] Tích hợp RAG với vector database
- [x] Authentication & Authorization
- [x] **Role-based Access Control (RBAC)**: Admin / Business / Customer

### 🚧 Phase 2: Nâng cao đa kênh (Đang phát triển - Q2 2025)
- [x] Tích hợp Zalo OA cơ bản
- [x] Website Chatbot Widget
- [x] **Zalo Personal Account** (QR Code login)
- [ ] **AI Suggestion Mode** (Gợi ý + xác nhận)
- [ ] **Takeover Mode** (Chuyển sang nhân viên thật)
- [ ] Zalo OA Template Messages
- [ ] Zalo Pay integration
- [ ] Zalo Mini App
- [ ] Push notifications (Website + Zalo)
- [ ] Email marketing automation

### 🔮 Phase 3: AI nâng cao (Q3 2025)
- [ ] Phân tích tâm lý khách hàng (Sentiment Analysis)
- [ ] Dự đoán churn rate
- [ ] Recommendation engine cá nhân hóa
- [ ] AI voice assistant (Text-to-Speech & Speech-to-Text)
- [ ] Multi-language support
- [ ] Auto-reply thông minh theo thời gian
- [ ] Chatbot learning từ feedback khách hàng

### 🚀 Phase 4: Scale & Commercialize (Q4 2025)
- [ ] SaaS multi-tenant architecture
- [ ] Subscription billing system
- [ ] White-label solution (Website Widget + Zalo OA)
- [ ] Advanced analytics with ML models
- [ ] Zalo Mini App nâng cao
- [ ] API marketplace & webhooks
- [ ] Widget customization builder (no-code)

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
- [Next.js](https://nextjs.org/) - React Framework with SSR/SSG
- [LangChain](https://www.langchain.com/) - RAG Framework
- [Pinecone](https://www.pinecone.io/) - Vector Database
- [TailwindCSS](https://tailwindcss.com/) - CSS Framework
- [Shadcn/ui](https://ui.shadcn.com/) - Component Library
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
