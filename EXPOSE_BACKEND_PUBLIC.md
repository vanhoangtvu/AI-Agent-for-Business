# 🌐 Hướng Dẫn Expose Backend Ra Public

Backend hiện đang chạy trên `http://localhost:8088` (chỉ truy cập được từ máy local). Để expose ra public (internet), có 3 cách:

---

## ✅ Cách 1: Dùng Ngrok (Khuyến nghị - Nhanh nhất)

### Bước 1: Cài đặt Ngrok

```bash
# Download ngrok
curl -s https://ngrok-agent.s3.amazonaws.com/ngrok.asc | sudo tee /etc/apt/trusted.gpg.d/ngrok.asc >/dev/null
echo "deb https://ngrok-agent.s3.amazonaws.com buster main" | sudo tee /etc/apt/sources.list.d/ngrok.list
sudo apt update
sudo apt install ngrok

# Hoặc dùng snap
sudo snap install ngrok
```

### Bước 2: Đăng ký tài khoản Ngrok (Free)

1. Truy cập: https://dashboard.ngrok.com/signup
2. Lấy authtoken từ dashboard
3. Cấu hình:

```bash
ngrok config add-authtoken YOUR_AUTHTOKEN_HERE
```

### Bước 3: Expose Backend

```bash
# Expose port 8088 (backend)
ngrok http 8088
```

Bạn sẽ nhận được URL dạng: `https://abcd-1234-5678.ngrok-free.app`

### Bước 4: Cập nhật Frontend Config

Mở `/home/hv/DuAn/AI-Agent-for-Business/frontend/.env.local`:

```env
NEXT_PUBLIC_BACKEND_URL=https://abcd-1234-5678.ngrok-free.app/api/v1
```

### Bước 5: Expose Frontend (Optional)

Mở terminal mới:

```bash
# Expose port 3008 (frontend)
ngrok http 3008
```

Bạn sẽ nhận được URL dạng: `https://efgh-9876-5432.ngrok-free.app`

Cập nhật:
```env
NEXTAUTH_URL=https://efgh-9876-5432.ngrok-free.app
```

---

## ✅ Cách 2: Dùng Public IP + Port Forwarding

### Bước 1: Lấy Public IP

```bash
curl ifconfig.me
# Output: 123.456.789.012
```

### Bước 2: Cấu hình Router

Vào router settings (thường là `192.168.1.1`):
- Forward port **8088** → IP máy của bạn (port 8088)
- Forward port **3008** → IP máy của bạn (port 3008)

### Bước 3: Cấu hình Backend CORS

Mở `/home/hv/DuAn/AI-Agent-for-Business/backend/src/main/resources/application.properties`:

```properties
# Thêm public IP vào CORS
cors.allowed-origins=http://localhost:3008,http://123.456.789.012:3008
```

### Bước 4: Cập nhật Frontend Config

```env
NEXT_PUBLIC_BACKEND_URL=http://123.456.789.012:8088/api/v1
NEXTAUTH_URL=http://123.456.789.012:3008
```

### Bước 5: Restart Backend

```bash
cd /home/hv/DuAn/AI-Agent-for-Business/backend
mvn spring-boot:run
```

---

## ✅ Cách 3: Deploy lên Cloud (Production)

### Option A: Deploy Backend lên Heroku/Railway/Render

#### Railway (Khuyến nghị)

1. Tạo tài khoản: https://railway.app
2. Connect GitHub repo
3. Deploy backend (auto-detect Spring Boot)
4. Nhận URL: `https://your-app.railway.app`

#### Render (Free tier)

1. Tạo tài khoản: https://render.com
2. New → Web Service
3. Connect repo → Build: `mvn clean install`, Start: `java -jar target/*.jar`
4. Nhận URL: `https://your-app.onrender.com`

### Option B: Deploy Frontend lên Vercel

```bash
cd /home/hv/DuAn/AI-Agent-for-Business/frontend

# Install Vercel CLI
npm i -g vercel

# Deploy
vercel

# Production deploy
vercel --prod
```

Nhận URL: `https://your-app.vercel.app`

### Cập nhật Environment Variables

**Vercel Dashboard:**
- Settings → Environment Variables
- Add:
  - `NEXT_PUBLIC_BACKEND_URL`: `https://your-backend.railway.app/api/v1`
  - `NEXTAUTH_SECRET`: (generate với `openssl rand -base64 32`)
  - `NEXTAUTH_URL`: `https://your-app.vercel.app`

**Backend Railway/Render:**
- Environment Variables
- Add các biến từ `.env.example`

---

## 🔧 Quick Setup với Ngrok (5 phút)

### Terminal 1: Backend (đang chạy)
```bash
# Giữ backend running trên port 8088
cd /home/hv/DuAn/AI-Agent-for-Business/backend
mvn spring-boot:run
```

### Terminal 2: Ngrok cho Backend
```bash
ngrok http 8088
# Copy URL: https://xxxx.ngrok-free.app
```

### Terminal 3: Update Frontend Config
```bash
cd /home/hv/DuAn/AI-Agent-for-Business/frontend

# Sửa .env.local
nano .env.local
# Thay NEXT_PUBLIC_BACKEND_URL=https://xxxx.ngrok-free.app/api/v1

# Run frontend
npm run dev
```

### Terminal 4: Ngrok cho Frontend (Optional)
```bash
ngrok http 3008
# Copy URL: https://yyyy.ngrok-free.app
```

---

## 🌐 Cập nhật Backend CORS cho Public Access

Mở `/home/hv/DuAn/AI-Agent-for-Business/backend/src/main/java/com/aiagent/business/config/SecurityConfig.java`:

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    
    // Thêm ngrok URLs hoặc public URLs
    configuration.setAllowedOrigins(Arrays.asList(
        "http://localhost:3008", 
        "http://localhost:3000",
        "https://*.ngrok-free.app",  // Cho phép tất cả ngrok subdomains
        "https://your-frontend.vercel.app"  // Production URL
    ));
    
    // ... rest of config
}
```

Hoặc đơn giản hơn (CHỈ cho development):

```java
configuration.setAllowedOriginPatterns(Arrays.asList("*"));
```

**⚠️ Warning**: `*` chỉ dùng cho development, production phải specify exact origins!

---

## 📝 Checklist

### Local Development
- [ ] Backend running trên `localhost:8088`
- [ ] Frontend running trên `localhost:3008`
- [ ] `.env.local` có `NEXT_PUBLIC_BACKEND_URL=http://localhost:8088/api/v1`

### Public Access với Ngrok
- [ ] Ngrok installed và authenticated
- [ ] Backend exposed: `ngrok http 8088`
- [ ] Copy ngrok URL
- [ ] Update `.env.local` với ngrok URL
- [ ] Restart frontend
- [ ] Test API calls

### Production Deployment
- [ ] Backend deployed (Railway/Render/Heroku)
- [ ] Frontend deployed (Vercel/Netlify)
- [ ] Environment variables configured
- [ ] CORS configured correctly
- [ ] Database configured (không dùng localhost)
- [ ] SSL/HTTPS enabled

---

## 🔍 Kiểm Tra Backend Public Access

### Test Backend Endpoint

```bash
# Local
curl http://localhost:8088/api/v1/public/health

# Ngrok
curl https://your-ngrok-url.ngrok-free.app/api/v1/public/health

# Production
curl https://your-backend.railway.app/api/v1/public/health
```

Expected response:
```json
{
  "success": true,
  "message": "Public service is healthy",
  "data": "OK"
}
```

---

## 🚀 Ngrok Commands Cheat Sheet

```bash
# Start ngrok for backend
ngrok http 8088

# Start ngrok for frontend
ngrok http 3008

# Start with custom subdomain (paid plan)
ngrok http 8088 --subdomain=my-backend

# Start with basic auth
ngrok http 8088 --basic-auth="user:password"

# View all active tunnels
ngrok http list

# Dashboard
# http://127.0.0.1:4040
```

---

## 💡 Tips

### 1. **Ngrok Free Limitations**
- URL thay đổi mỗi lần restart
- Có banner warning (có thể bỏ qua)
- Rate limit: 40 requests/minute

### 2. **Production Ready**
- Dùng Railway/Render cho backend (có free tier)
- Dùng Vercel cho frontend (free unlimited)
- Configure proper CORS
- Use environment variables
- Enable HTTPS

### 3. **Security**
- Không expose database port
- Luôn dùng HTTPS cho production
- Configure firewall rules
- Use strong NEXTAUTH_SECRET
- Enable rate limiting

---

## 📞 Troubleshooting

### Backend không accessible

```bash
# Check backend running
curl http://localhost:8088/api/v1/public/health

# Check firewall
sudo ufw status

# Allow port if needed
sudo ufw allow 8088
```

### CORS Error

Update `SecurityConfig.java`:
```java
configuration.setAllowedOriginPatterns(Arrays.asList("*"));
```

### Ngrok không kết nối

```bash
# Reinstall ngrok
sudo snap remove ngrok
sudo snap install ngrok

# Re-authenticate
ngrok config add-authtoken YOUR_TOKEN
```

---

## 🎯 Khuyến Nghị

**Development (Local):**
- ✅ Localhost URLs
- ✅ No ngrok needed
- ✅ Fast & simple

**Testing với bạn bè/client:**
- ✅ Dùng Ngrok (nhanh nhất)
- ✅ Free, không cần config
- ✅ HTTPS tự động

**Production:**
- ✅ Deploy lên Railway + Vercel
- ✅ Custom domain
- ✅ Stable URLs
- ✅ Better performance

---

**🌐 Backend sẽ public sau khi setup theo hướng dẫn trên!**

