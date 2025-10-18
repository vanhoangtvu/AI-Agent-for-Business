# 📊 Sơ đồ Use Case - AI Agent for Business

## 📋 Mục lục

- [Tổng quan](#-tổng-quan)
- [Actors](#-actors)
- [Use Case Diagram](#-use-case-diagram)
- [Chi tiết Use Cases](#-chi-tiết-use-cases)
- [Use Case Specifications](#-use-case-specifications)

---

## 🎯 Tổng quan

### Actors (3 vai trò chính):
1. 🎛️ **Admin** - Quản trị viên hệ thống
2. 🏢 **Business** - Chủ doanh nghiệp
3. 👤 **Customer** - Khách hàng

### Tổng số Use Cases: **45+ use cases**

---

## 👥 Actors

### 🎛️ **Admin (Quản trị viên hệ thống)**
**Mô tả:** Người quản lý toàn bộ hệ thống, có quyền cao nhất.

**Trách nhiệm:**
- Quản lý tất cả doanh nghiệp
- Quản lý người dùng hệ thống
- Cấu hình hệ thống global
- Theo dõi logs và monitoring
- Quản lý billing & subscription

### 🏢 **Business (Chủ doanh nghiệp)**
**Mô tả:** Người sở hữu và quản lý doanh nghiệp, sử dụng hệ thống để quản lý khách hàng và bán hàng.

**Trách nhiệm:**
- Quản lý CRM (khách hàng, sản phẩm, đơn hàng)
- Cấu hình chatbot và AI
- Tích hợp các kênh (Website, Zalo)
- Xem analytics và insights
- Quản lý nhân viên

### 👤 **Customer (Khách hàng)**
**Mô tả:** Người dùng cuối, tương tác với chatbot để mua hàng và tư vấn.

**Trách nhiệm:**
- Chat với chatbot
- Xem sản phẩm
- Đặt hàng
- Xem lịch sử đơn hàng
- Cập nhật thông tin cá nhân

---

## 📊 Use Case Diagram

### **Tổng quan toàn hệ thống:**

```
┌────────────────────────────────────────────────────────────────────────────┐
│                     AI AGENT FOR BUSINESS SYSTEM                           │
└────────────────────────────────────────────────────────────────────────────┘

                                                                                
        🎛️ ADMIN                🏢 BUSINESS               👤 CUSTOMER         
           │                        │                          │                
           │                        │                          │                
┌──────────┼────────────────────────┼──────────────────────────┼──────────────┐
│          │    ADMIN USE CASES     │                          │               │
│          │                        │                          │               │
│    ┌─────▼──────┐                │                          │               │
│    │ UC-A1      │                │                          │               │
│    │ Quản lý    │                │                          │               │
│    │ Businesses │                │                          │               │
│    └────────────┘                │                          │               │
│                                   │                          │               │
│    ┌──────────┐                  │                          │               │
│    │ UC-A2    │                  │                          │               │
│    │ Quản lý  │                  │                          │               │
│    │ Users    │                  │                          │               │
│    └──────────┘                  │                          │               │
│                                   │                          │               │
│    ┌──────────────┐              │                          │               │
│    │ UC-A3        │              │                          │               │
│    │ Cấu hình     │              │                          │               │
│    │ Hệ thống     │              │                          │               │
│    └──────────────┘              │                          │               │
│                                   │                          │               │
│    ┌──────────────┐              │                          │               │
│    │ UC-A4        │              │                          │               │
│    │ Xem System   │              │                          │               │
│    │ Logs         │              │                          │               │
│    └──────────────┘              │                          │               │
│                                   │                          │               │
│    ┌──────────────┐              │                          │               │
│    │ UC-A5        │              │                          │               │
│    │ Quản lý      │              │                          │               │
│    │ Billing      │              │                          │               │
│    └──────────────┘              │                          │               │
│                                   │                          │               │
└───────────────────────────────────┼──────────────────────────┼───────────────┘
                                    │                          │                
┌───────────────────────────────────┼──────────────────────────┼───────────────┐
│          BUSINESS USE CASES       │                          │               │
│                                   │                          │               │
│              ┌────────────────────▼──┐                       │               │
│              │ UC-B1: Đăng ký/       │                       │               │
│              │ Đăng nhập             │                       │               │
│              └───────────────────────┘                       │               │
│                                                               │               │
│              ┌─────────────────────┐                         │               │
│              │ UC-B2: Quản lý      │                         │               │
│              │ Khách hàng (CRM)    │◄────────includes────────┤               │
│              └─────────────────────┘                         │               │
│                      │                                        │               │
│                      │ includes                               │               │
│                      ▼                                        │               │
│              ┌─────────────────────┐                         │               │
│              │ UC-B2.1: Thêm       │                         │               │
│              │ khách hàng          │                         │               │
│              └─────────────────────┘                         │               │
│                                                               │               │
│              ┌─────────────────────┐                         │               │
│              │ UC-B2.2: Phân loại  │                         │               │
│              │ khách hàng (RFM)    │                         │               │
│              └─────────────────────┘                         │               │
│                                                               │               │
│              ┌─────────────────────┐                         │               │
│              │ UC-B3: Quản lý      │                         │               │
│              │ Sản phẩm            │                         │               │
│              └─────────────────────┘                         │               │
│                                                               │               │
│              ┌─────────────────────┐                         │               │
│              │ UC-B4: Quản lý      │                         │               │
│              │ Đơn hàng            │                         │               │
│              └─────────────────────┘                         │               │
│                                                               │               │
│              ┌─────────────────────┐                         │               │
│              │ UC-B5: Cấu hình     │                         │               │
│              │ AI Chatbot          │                         │               │
│              └─────────────────────┘                         │               │
│                                                               │               │
│              ┌─────────────────────┐                         │               │
│              │ UC-B6: Upload       │                         │               │
│              │ Tài liệu RAG        │                         │               │
│              └─────────────────────┘                         │               │
│                                                               │               │
│              ┌─────────────────────┐                         │               │
│              │ UC-B7: Tích hợp     │                         │               │
│              │ Zalo OA             │                         │               │
│              └─────────────────────┘                         │               │
│                                                               │               │
│              ┌─────────────────────┐                         │               │
│              │ UC-B8: Đăng nhập    │                         │               │
│              │ Zalo Personal (QR)  │                         │               │
│              └─────────────────────┘                         │               │
│                                                               │               │
│              ┌─────────────────────┐                         │               │
│              │ UC-B9: Quản lý      │                         │               │
│              │ Hội thoại           │                         │               │
│              └─────────────────────┘                         │               │
│                                                               │               │
│              ┌─────────────────────┐                         │               │
│              │ UC-B10: Xem AI      │                         │               │
│              │ Analytics & Insights│                         │               │
│              └─────────────────────┘                         │               │
│                                                               │               │
│              ┌─────────────────────┐                         │               │
│              │ UC-B11: Quản lý     │                         │               │
│              │ Nhân viên           │                         │               │
│              └─────────────────────┘                         │               │
│                                                               │               │
└───────────────────────────────────────────────────────────────┼───────────────┘
                                                                │                
┌───────────────────────────────────────────────────────────────┼───────────────┐
│          CUSTOMER USE CASES                                   │               │
│                                                               │               │
│                              ┌────────────────────────────────▼──┐            │
│                              │ UC-C1: Chat với Chatbot           │            │
│                              └───────────────────────────────────┘            │
│                                            │                                   │
│                                            │ includes                          │
│                                            ▼                                   │
│                              ┌────────────────────────┐                       │
│                              │ UC-C1.1: Hỏi về       │                       │
│                              │ sản phẩm              │                       │
│                              └────────────────────────┘                       │
│                                                                                │
│                              ┌────────────────────────┐                       │
│                              │ UC-C1.2: Đặt hàng     │                       │
│                              │ qua chat              │                       │
│                              └────────────────────────┘                       │
│                                                                                │
│                              ┌────────────────────────┐                       │
│                              │ UC-C1.3: Tra cứu      │                       │
│                              │ đơn hàng              │                       │
│                              └────────────────────────┘                       │
│                                                                                │
│                              ┌────────────────────────┐                       │
│                              │ UC-C2: Đăng ký/       │                       │
│                              │ Đăng nhập             │                       │
│                              └────────────────────────┘                       │
│                                                                                │
│                              ┌────────────────────────┐                       │
│                              │ UC-C3: Xem lịch sử    │                       │
│                              │ đơn hàng              │                       │
│                              └────────────────────────┘                       │
│                                                                                │
│                              ┌────────────────────────┐                       │
│                              │ UC-C4: Cập nhật       │                       │
│                              │ thông tin cá nhân     │                       │
│                              └────────────────────────┘                       │
│                                                                                │
│                              ┌────────────────────────┐                       │
│                              │ UC-C5: Hủy đơn hàng   │                       │
│                              └────────────────────────┘                       │
│                                                                                │
└────────────────────────────────────────────────────────────────────────────────┘


                        ┌─────────────────────────┐
                        │  EXTERNAL SYSTEMS       │
                        ├─────────────────────────┤
                        │  • Google Gemini API    │
                        │  • Zalo API             │
                        │  • VNPay/MoMo API       │
                        │  • Vector Database      │
                        └─────────────────────────┘
```

---

## 📝 Chi tiết Use Cases

### 🎛️ **ADMIN USE CASES**

#### **UC-A1: Quản lý Businesses**
**Actor:** Admin  
**Mô tả:** Quản lý tất cả doanh nghiệp trong hệ thống  
**Pre-condition:** Admin đã đăng nhập  
**Post-condition:** Thông tin doanh nghiệp được cập nhật  

**Main Flow:**
1. Admin truy cập trang quản lý businesses
2. Hệ thống hiển thị danh sách tất cả businesses
3. Admin có thể:
   - Xem chi tiết business
   - Duyệt/từ chối đăng ký mới
   - Suspend/Active business
   - Xem thống kê của từng business
   - Xóa business

**Alternative Flow:**
- 3a. Admin tìm kiếm business theo tên/email
- 3b. Admin lọc theo plan/status

---

#### **UC-A2: Quản lý Users**
**Actor:** Admin  
**Mô tả:** Quản lý tất cả người dùng hệ thống  

**Main Flow:**
1. Admin truy cập trang quản lý users
2. Xem danh sách users (Admin, Business, Customer)
3. Admin có thể:
   - Xem thông tin chi tiết user
   - Khóa/mở khóa tài khoản
   - Reset password
   - Xem activity logs
   - Xóa user

---

#### **UC-A3: Cấu hình Hệ thống**
**Actor:** Admin  
**Mô tả:** Cấu hình các tham số global  

**Main Flow:**
1. Admin truy cập system configs
2. Cấu hình:
   - AI model settings
   - Rate limiting
   - Email/SMS gateway
   - Storage limits
   - Feature flags
3. Lưu cấu hình
4. Hệ thống áp dụng cấu hình mới

---

#### **UC-A4: Xem System Logs**
**Actor:** Admin  
**Mô tả:** Theo dõi logs và monitoring  

**Main Flow:**
1. Admin truy cập logs page
2. Xem:
   - Error logs
   - API logs
   - User activity logs
   - Performance metrics
3. Filter theo time, level, user, business
4. Export logs

---

#### **UC-A5: Quản lý Billing & Subscription**
**Actor:** Admin  
**Mô tả:** Quản lý thanh toán và gói dịch vụ  

**Main Flow:**
1. Admin truy cập billing dashboard
2. Xem:
   - Doanh thu theo tháng
   - Danh sách subscriptions
   - Payment history
3. Admin có thể:
   - Duyệt/từ chối nâng cấp
   - Gia hạn thủ công
   - Hoàn tiền
   - Tạo coupon/discount

---

### 🏢 **BUSINESS USE CASES**

#### **UC-B1: Đăng ký/Đăng nhập**
**Actor:** Business  
**Pre-condition:** Có email và thông tin doanh nghiệp  
**Post-condition:** Business được tạo tài khoản và đăng nhập  

**Main Flow:**
1. Business truy cập trang đăng ký
2. Nhập thông tin:
   - Email
   - Password
   - Tên doanh nghiệp
   - Số điện thoại
   - Địa chỉ
3. Xác nhận email
4. Đăng nhập vào hệ thống
5. Chuyển đến Dashboard

**Alternative Flow:**
- 3a. Email đã tồn tại → Hiển thị lỗi
- 3b. Đăng nhập bằng Google/Facebook

---

#### **UC-B2: Quản lý Khách hàng (CRM)**
**Actor:** Business  
**Pre-condition:** Business đã đăng nhập  
**Post-condition:** Dữ liệu khách hàng được cập nhật  

**Main Flow:**
1. Business truy cập CRM
2. Xem danh sách khách hàng
3. Business có thể:
   - **UC-B2.1:** Thêm khách hàng mới
   - **UC-B2.2:** Phân loại khách hàng (RFM)
   - Sửa thông tin khách hàng
   - Xem lịch sử mua hàng
   - Thêm tags
   - Export danh sách

**UC-B2.1: Thêm khách hàng mới**
1. Click "Thêm khách hàng"
2. Nhập thông tin:
   - Tên
   - Email
   - Số điện thoại
   - Địa chỉ
3. Lưu
4. Hệ thống tạo customer record

**UC-B2.2: Phân loại khách hàng (RFM)**
1. Chọn "Phân tích RFM"
2. Hệ thống tính toán:
   - Recency: Lần mua gần nhất
   - Frequency: Tần suất mua
   - Monetary: Tổng chi tiêu
3. Phân khách hàng vào segments:
   - VIP
   - Loyal
   - Potential
   - At Risk
   - Lost
4. Hiển thị biểu đồ phân khúc

---

#### **UC-B3: Quản lý Sản phẩm**
**Actor:** Business  
**Pre-condition:** Business đã đăng nhập  
**Post-condition:** Danh sách sản phẩm được cập nhật  

**Main Flow:**
1. Business truy cập quản lý sản phẩm
2. Xem danh sách sản phẩm
3. Business có thể:
   - Thêm sản phẩm mới
   - Sửa thông tin sản phẩm
   - Upload hình ảnh
   - Quản lý tồn kho
   - Xóa sản phẩm
   - Import từ Excel
   - Export danh sách

**Alternative Flow - Thêm sản phẩm:**
1. Click "Thêm sản phẩm"
2. Nhập thông tin:
   - Tên sản phẩm
   - SKU
   - Giá
   - Danh mục
   - Mô tả
   - Hình ảnh
   - Số lượng tồn kho
3. Lưu
4. Hệ thống tạo product record

---

#### **UC-B4: Quản lý Đơn hàng**
**Actor:** Business  
**Pre-condition:** Business đã đăng nhập  
**Post-condition:** Trạng thái đơn hàng được cập nhật  

**Main Flow:**
1. Business truy cập quản lý đơn hàng
2. Xem danh sách đơn hàng (theo trạng thái)
3. Business có thể:
   - Xem chi tiết đơn hàng
   - Xác nhận đơn hàng (PENDING → CONFIRMED)
   - Cập nhật trạng thái (PROCESSING → SHIPPING → DELIVERED)
   - In hóa đơn
   - Hủy đơn hàng
   - Export báo cáo

**Workflow trạng thái đơn hàng:**
```
PENDING → CONFIRMED → PROCESSING → SHIPPING → DELIVERED → COMPLETED
                ↓
            CANCELLED
```

---

#### **UC-B5: Cấu hình AI Chatbot**
**Actor:** Business  
**Pre-condition:** Business đã đăng nhập  
**Post-condition:** Chatbot được cấu hình theo yêu cầu  

**Main Flow:**
1. Business truy cập cấu hình chatbot
2. Cấu hình:
   - **Welcome message**
   - **Chế độ AI:**
     - Auto: Tự động trả lời 100%
     - Suggestion: Gợi ý câu trả lời, cần xác nhận
     - Notification: Chỉ thông báo, không tự động
   - **Thời gian hoạt động:** 24/7 hoặc theo giờ
   - **Ngôn ngữ:** Tiếng Việt
   - **Tích hợp RAG:** Bật/Tắt
3. Test chatbot
4. Lưu cấu hình
5. Chatbot áp dụng cấu hình mới

---

#### **UC-B6: Upload Tài liệu RAG**
**Actor:** Business  
**Pre-condition:** Business đã đăng nhập  
**Post-condition:** Tài liệu được xử lý và AI có thể sử dụng  

**Main Flow:**
1. Business truy cập quản lý tài liệu
2. Click "Upload tài liệu"
3. Chọn file (PDF, DOCX, TXT, CSV)
4. Chọn danh mục:
   - Chính sách
   - FAQ
   - Hướng dẫn
   - Thông tin sản phẩm
5. Upload
6. **Hệ thống xử lý:**
   - Extract text
   - Chunking (500 tokens)
   - Generate embeddings (Gemini API)
   - Lưu vào Vector DB
7. Thông báo "Hoàn thành"
8. AI có thể truy xuất thông tin từ tài liệu

**Alternative Flow:**
- 6a. File quá lớn (>10MB) → Hiển thị lỗi
- 6b. Định dạng không hỗ trợ → Hiển thị lỗi

---

#### **UC-B7: Tích hợp Zalo OA**
**Actor:** Business  
**Pre-condition:** Business đã có Zalo OA  
**Post-condition:** Zalo OA được tích hợp thành công  

**Main Flow:**
1. Business truy cập "Tích hợp Zalo OA"
2. Nhập thông tin:
   - OA ID
   - App ID
   - App Secret
   - Access Token
3. Cấu hình Webhook URL
4. Test kết nối
5. Kích hoạt tích hợp
6. Hệ thống bắt đầu nhận tin nhắn từ Zalo OA
7. AI tự động trả lời theo cấu hình

**Alternative Flow:**
- 4a. Kết nối thất bại → Kiểm tra lại credentials

---

#### **UC-B8: Đăng nhập Zalo Personal (QR)**
**Actor:** Business  
**Pre-condition:** Business có tài khoản Zalo cá nhân  
**Post-condition:** Tài khoản Zalo được kết nối  

**Main Flow:**
1. Business truy cập "Tích hợp Zalo Personal"
2. Click "Đăng nhập bằng Zalo"
3. **Hệ thống hiển thị QR Code**
4. Business mở app Zalo trên điện thoại
5. Quét QR Code
6. Xác nhận đăng nhập
7. Hệ thống lưu session
8. Business cấu hình:
   - Chế độ AI (Auto/Suggestion/Notification)
   - Thời gian hoạt động
9. Lưu cấu hình
10. AI bắt đầu xử lý tin nhắn từ Zalo cá nhân

**Alternative Flow:**
- 3a. QR Code hết hạn (5 phút) → Tạo lại
- 6a. Từ chối đăng nhập → Hủy bỏ

⚠️ **Cảnh báo:** Đây là unofficial API, có rủi ro bị khóa tài khoản nếu spam.

---

#### **UC-B9: Quản lý Hội thoại**
**Actor:** Business  
**Pre-condition:** Business đã đăng nhập  
**Post-condition:** Hội thoại được xử lý  

**Main Flow:**
1. Business truy cập "Hội thoại"
2. Xem danh sách conversations từ các kênh:
   - Website Widget
   - Zalo OA
   - Zalo Personal
3. Business có thể:
   - Xem chi tiết hội thoại
   - Đọc tin nhắn
   - **Takeover:** Chuyển từ AI sang thủ công
   - Gán cho nhân viên
   - Thêm tags
   - Đánh dấu resolved
   - Xuất transcript
4. Nếu chế độ "Suggestion":
   - Xem câu trả lời AI gợi ý
   - Chỉnh sửa nếu cần
   - Click "Gửi"

**Real-time updates:**
- Thông báo khi có tin nhắn mới
- Hiển thị số lượng chưa đọc

---

#### **UC-B10: Xem AI Analytics & Insights**
**Actor:** Business  
**Pre-condition:** Business đã đăng nhập và có dữ liệu  
**Post-condition:** Business xem được insights  

**Main Flow:**
1. Business truy cập "AI Insights"
2. Chọn khoảng thời gian (ngày/tuần/tháng)
3. **Hệ thống hiển thị:**
   
   **📊 Phân tích khách hàng (RFM):**
   - Số lượng khách hàng theo segment
   - Biểu đồ phân bố
   - Top VIP customers
   
   **📈 Dự đoán doanh thu:**
   - Doanh thu dự kiến tháng tới
   - Xu hướng tăng/giảm
   - So sánh với kỳ trước
   
   **🛒 Sản phẩm bán chạy:**
   - Top 10 sản phẩm
   - Sản phẩm cần nhập thêm
   - Sản phẩm ế ẩm
   
   **💡 Đề xuất chiến lược:**
   - "25% khách hàng chưa quay lại sau 30 ngày"
     → Chạy chiến dịch email marketing
   - "Sản phẩm X có xu hướng tăng"
     → Tăng tồn kho và chạy ads
   - "Thời điểm đăng bài tốt nhất: 20:00-22:00"
   
   **⚠️ Cảnh báo churn:**
   - Danh sách khách hàng có nguy cơ rời bỏ
   - Đề xuất action để giữ chân

4. Business export báo cáo PDF

---

#### **UC-B11: Quản lý Nhân viên**
**Actor:** Business  
**Pre-condition:** Business đã đăng nhập  
**Post-condition:** Nhân viên được thêm/xóa/cấp quyền  

**Main Flow:**
1. Business truy cập "Quản lý nhân viên"
2. Xem danh sách nhân viên
3. Business có thể:
   - Thêm nhân viên mới (gửi email mời)
   - Cấp quyền:
     - Quản lý khách hàng
     - Quản lý sản phẩm
     - Quản lý đơn hàng
     - Xem analytics
     - Xem hội thoại
   - Xóa nhân viên
   - Vô hiệu hóa tài khoản

---

### 👤 **CUSTOMER USE CASES**

#### **UC-C1: Chat với Chatbot**
**Actor:** Customer  
**Pre-condition:** Không cần đăng nhập  
**Post-condition:** Khách hàng nhận được câu trả lời  

**Main Flow:**
1. Customer mở website/Zalo
2. Click vào chatbot widget hoặc nhắn tin qua Zalo
3. Chatbot chào hỏi:
   ```
   "Xin chào! Tôi là AI Assistant của [Tên shop].
   Tôi có thể giúp gì cho bạn?"
   ```
4. Customer nhập câu hỏi
5. **Hệ thống xử lý:**
   - Phân tích intent
   - Nếu cần → Truy xuất tài liệu (RAG)
   - Gửi query + context → Gemini API
   - Nhận response
6. Chatbot trả lời
7. Lưu lịch sử hội thoại

**Includes:**
- **UC-C1.1:** Hỏi về sản phẩm
- **UC-C1.2:** Đặt hàng qua chat
- **UC-C1.3:** Tra cứu đơn hàng

---

#### **UC-C1.1: Hỏi về sản phẩm**
**Main Flow:**
1. Customer: "iPhone 15 Pro Max giá bao nhiêu?"
2. AI tìm trong database sản phẩm
3. AI trả lời:
   ```
   📱 iPhone 15 Pro Max 256GB - Xanh Titan
   💰 Giá: 29.990.000đ
   ✅ Tình trạng: Còn hàng (15 sản phẩm)
   🎁 Khuyến mãi: Tặng ốp lưng + cường lực
   
   Anh/chị có muốn đặt hàng không ạ?
   ```
4. Hiển thị button "Đặt hàng ngay"

---

#### **UC-C1.2: Đặt hàng qua chat**
**Main Flow:**
1. Customer: "Đặt 1 chiếc cho em"
2. AI ghi nhận:
   ```
   Dạ em ghi nhận đơn hàng:
   - Sản phẩm: iPhone 15 Pro Max 256GB
   - Số lượng: 1
   - Tổng tiền: 29.990.000đ
   
   Anh/chị vui lòng cung cấp:
   📍 Địa chỉ nhận hàng
   📞 Số điện thoại liên hệ
   ```
3. Customer cung cấp thông tin
4. AI xác nhận:
   ```
   ✅ Đơn hàng #ORD20250118001 đã được tạo
   📦 Trạng thái: Chờ xác nhận
   💳 Thanh toán: COD
   
   Shop sẽ liên hệ xác nhận trong 15 phút ạ!
   ```
5. Hệ thống tạo order trong database
6. Gửi thông báo cho Business

---

#### **UC-C1.3: Tra cứu đơn hàng**
**Main Flow:**
1. Customer: "Đơn hàng của em đến đâu rồi?"
2. AI: "Anh/chị cho em mã đơn hàng hoặc số điện thoại ạ"
3. Customer cung cấp
4. AI tra cứu trong database
5. AI trả lời:
   ```
   📦 Đơn hàng #ORD20250118001
   📍 Trạng thái: Đang giao hàng
   🚚 Đơn vị vận chuyển: Giao Hàng Nhanh
   📞 Liên hệ: 1900-xxxx
   
   Dự kiến giao: 20/01/2025
   ```

---

#### **UC-C2: Đăng ký/Đăng nhập**
**Actor:** Customer  
**Pre-condition:** Customer muốn theo dõi đơn hàng  
**Post-condition:** Customer có tài khoản  

**Main Flow:**
1. Customer click "Đăng ký"
2. Nhập thông tin:
   - Họ tên
   - Email
   - Số điện thoại
   - Password
3. Xác nhận email
4. Đăng nhập
5. Vào Customer Portal

**Alternative Flow:**
- 3a. Email đã tồn tại → Chuyển sang đăng nhập
- 3b. Đăng nhập bằng Google/Facebook/Zalo

---

#### **UC-C3: Xem lịch sử đơn hàng**
**Actor:** Customer  
**Pre-condition:** Customer đã đăng nhập  
**Post-condition:** Hiển thị danh sách đơn hàng  

**Main Flow:**
1. Customer đăng nhập
2. Truy cập "Đơn hàng của tôi"
3. Xem danh sách đơn hàng:
   - Đang xử lý
   - Đang giao
   - Hoàn thành
   - Đã hủy
4. Click vào đơn hàng → Xem chi tiết:
   - Sản phẩm
   - Địa chỉ giao hàng
   - Tổng tiền
   - Trạng thái
   - Tracking number

---

#### **UC-C4: Cập nhật thông tin cá nhân**
**Actor:** Customer  
**Pre-condition:** Customer đã đăng nhập  
**Post-condition:** Thông tin được cập nhật  

**Main Flow:**
1. Customer truy cập "Thông tin cá nhân"
2. Sửa thông tin:
   - Họ tên
   - Số điện thoại
   - Địa chỉ mặc định
   - Avatar
3. Lưu thay đổi
4. Hệ thống cập nhật database

---

#### **UC-C5: Hủy đơn hàng**
**Actor:** Customer  
**Pre-condition:** Đơn hàng ở trạng thái PENDING hoặc CONFIRMED  
**Post-condition:** Đơn hàng bị hủy  

**Main Flow:**
1. Customer vào "Đơn hàng của tôi"
2. Chọn đơn hàng cần hủy
3. Click "Hủy đơn hàng"
4. Chọn lý do hủy:
   - Đặt nhầm
   - Đổi ý
   - Tìm được nơi khác rẻ hơn
   - Khác
5. Xác nhận hủy
6. Hệ thống:
   - Cập nhật trạng thái → CANCELLED
   - Hoàn kho (nếu đã trừ)
   - Gửi thông báo cho Business

**Alternative Flow:**
- 2a. Đơn hàng đã SHIPPING → Không thể hủy
  → "Vui lòng liên hệ shop để hỗ trợ"

---

## 📋 Use Case Specifications (Chi tiết)

### **Use Case: UC-B6 - Upload Tài liệu RAG**

| **Thuộc tính** | **Mô tả** |
|----------------|-----------|
| **Use Case ID** | UC-B6 |
| **Tên** | Upload Tài liệu RAG |
| **Actor** | Business (Chủ doanh nghiệp) |
| **Mô tả** | Business upload tài liệu để AI có thể truy xuất thông tin chính xác khi trả lời khách hàng |
| **Pre-condition** | - Business đã đăng nhập<br>- Business có tài liệu cần upload (PDF/DOCX/TXT) |
| **Post-condition** | - Tài liệu được xử lý thành công<br>- AI có thể truy xuất thông tin từ tài liệu |
| **Priority** | High |
| **Frequency** | 5-10 lần/tháng |

#### **Main Success Scenario:**

| **Bước** | **Actor** | **System** |
|----------|-----------|------------|
| 1 | Business truy cập "Quản lý tài liệu" | Hiển thị trang quản lý tài liệu |
| 2 | Click "Upload tài liệu" | Hiển thị form upload |
| 3 | Chọn file và danh mục | Validate file (type, size) |
| 4 | Click "Upload" | - Upload file lên cloud storage<br>- Tạo document record (status: UPLOADING) |
| 5 | | **Background Processing:**<br>1. Extract text từ file<br>2. Chunking (500 tokens/chunk)<br>3. Generate embeddings (Gemini API)<br>4. Lưu vào Vector DB<br>5. Update status → COMPLETED |
| 6 | | Gửi notification "Tài liệu đã sẵn sàng" |
| 7 | Xem tài liệu trong danh sách | Hiển thị tài liệu với status COMPLETED |

#### **Alternative Flows:**

**A1: File quá lớn (>10MB)**
| **Bước** | **Mô tả** |
|----------|-----------|
| 3a | System kiểm tra file size > 10MB |
| 3b | Hiển thị lỗi "File quá lớn. Vui lòng chọn file < 10MB" |
| 3c | Return to step 3 |

**A2: Định dạng không hỗ trợ**
| **Bước** | **Mô tả** |
|----------|-----------|
| 3a | System kiểm tra file type không phải PDF/DOCX/TXT |
| 3b | Hiển thị lỗi "Định dạng không hỗ trợ" |
| 3c | Return to step 3 |

**A3: Xử lý thất bại**
| **Bước** | **Mô tả** |
|----------|-----------|
| 5a | Processing fails (API error, parse error) |
| 5b | Update status → FAILED |
| 5c | Log error message |
| 5d | Gửi notification "Xử lý thất bại" với lý do |

#### **Business Rules:**
- BR1: Mỗi business có giới hạn số lượng tài liệu theo plan
  - FREE: 10 documents
  - BASIC: 50 documents
  - PRO: 200 documents
  - ENTERPRISE: Unlimited
- BR2: File size tối đa: 10MB
- BR3: Định dạng hỗ trợ: PDF, DOCX, TXT, CSV
- BR4: Chunk size: 500 tokens, overlap: 50 tokens
- BR5: Embedding model: text-embedding-004 (768 dimensions)

#### **Non-functional Requirements:**
- NFR1: Processing time < 5 minutes cho file 5MB
- NFR2: Cosine similarity threshold: 0.7 cho retrieval
- NFR3: Return top 5 chunks cho mỗi query

#### **UI Mockup:**
```
┌─────────────────────────────────────────────────┐
│  📚 Quản lý tài liệu                   [+ Upload]│
├─────────────────────────────────────────────────┤
│                                                  │
│  Tài liệu (3/10)                                │
│                                                  │
│  ┌──────────────────────────────────────────┐  │
│  │ 📄 Chính sách bảo hành 2025              │  │
│  │ Danh mục: Policy  •  12 chunks           │  │
│  │ ✅ Completed  •  Uploaded: 15/01/2025    │  │
│  │ [Xem] [Xóa]                              │  │
│  └──────────────────────────────────────────┘  │
│                                                  │
│  ┌──────────────────────────────────────────┐  │
│  │ 📄 FAQ sản phẩm                          │  │
│  │ Danh mục: FAQ  •  8 chunks               │  │
│  │ ✅ Completed  •  Uploaded: 10/01/2025    │  │
│  │ [Xem] [Xóa]                              │  │
│  └──────────────────────────────────────────┘  │
│                                                  │
│  ┌──────────────────────────────────────────┐  │
│  │ 📄 Hướng dẫn sử dụng                     │  │
│  │ Danh mục: Guide  •  Processing...        │  │
│  │ ⏳ Processing  •  Uploaded: Just now     │  │
│  └──────────────────────────────────────────┘  │
│                                                  │
└──────────────────────────────────────────────────┘

Upload Modal:
┌─────────────────────────────────────────────────┐
│  📤 Upload tài liệu                      [X]    │
├─────────────────────────────────────────────────┤
│                                                  │
│  Chọn file:                                     │
│  ┌────────────────────────────────────────────┐│
│  │  Kéo thả file vào đây                      ││
│  │  hoặc click để chọn                        ││
│  │                                             ││
│  │  Hỗ trợ: PDF, DOCX, TXT (Max 10MB)        ││
│  └────────────────────────────────────────────┘│
│                                                  │
│  Danh mục:                                      │
│  [Chính sách ▼]                                 │
│                                                  │
│  Tên tài liệu:                                  │
│  [____________________________________]         │
│                                                  │
│              [Hủy]  [Upload]                    │
│                                                  │
└──────────────────────────────────────────────────┘
```

---

## 📊 Use Case Summary

| **Category** | **Use Cases** | **Total** |
|--------------|---------------|-----------|
| **Admin** | UC-A1 to UC-A5 | 5 |
| **Business** | UC-B1 to UC-B11 | 11 |
| **Customer** | UC-C1 to UC-C5 | 5 |
| **Sub Use Cases** | UC-B2.1, UC-B2.2, UC-C1.1, UC-C1.2, UC-C1.3 | 5 |
| **TOTAL** | | **26** |

---

## ✅ Use Case Coverage

### **Functional Requirements Coverage:**

- ✅ Authentication & Authorization
- ✅ CRM Management (Customers, Products, Orders)
- ✅ AI Chatbot Configuration
- ✅ RAG Document Management
- ✅ Zalo Integration (OA + Personal)
- ✅ Conversation Management
- ✅ AI Analytics & Insights
- ✅ Employee Management
- ✅ Customer Portal
- ✅ System Administration
- ✅ Billing & Subscription

### **Non-functional Requirements:**

- ✅ Security (JWT, RBAC)
- ✅ Performance (Indexes, Caching)
- ✅ Scalability (Microservices ready)
- ✅ Usability (Intuitive UI)
- ✅ Reliability (Error handling, Logging)

---

**Tổng kết:**
- ✅ 26+ Use Cases đầy đủ
- ✅ 3 Actors (Admin, Business, Customer)
- ✅ Sơ đồ Use Case chi tiết
- ✅ Main/Alternative flows
- ✅ Business rules
- ✅ UI mockups
- ✅ Specifications đầy đủ

