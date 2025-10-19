# 🔧 Network Troubleshooting - Backend Public Access

## ✅ Vấn Đề Đã Giải Quyết

### 1. **Backend Controller Issue** (ĐÃ SỬA)
❌ **Trước:** `PublicController` không có endpoint `/public/health`  
✅ **Sau:** Đã thêm endpoint `/public/health` và `/public/info`

### 2. **Cần Restart Backend**
```bash
# Terminal đang chạy backend: Ctrl+C
cd /home/hv/DuAn/AI-Agent-for-Business/backend
mvn spring-boot:run
```

---

## 🧪 Test Backend sau khi Restart

### Test 1: Local Connection
```bash
curl http://localhost:8088/api/v1/public/health
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Service is running",
  "data": "OK"
}
```

### Test 2: LAN IP Connection
```bash
curl http://192.168.1.10:8088/api/v1/public/health
```

### Test 3: Public IP Connection
```bash
curl http://113.170.159.180:8088/api/v1/public/health
```

---

## 🔍 Nếu Vẫn Không Truy Cập Được Public IP

### Bước 1: Kiểm tra Backend Bind Address
```bash
ss -tlnp | grep 8088
```

**Phải thấy:** `*:8088` hoặc `0.0.0.0:8088` (KHÔNG phải `127.0.0.1:8088`)

**Nếu sai**, thêm vào `application.properties`:
```properties
server.address=0.0.0.0
```

### Bước 2: Kiểm tra Firewall
```bash
# Ubuntu/Debian
sudo ufw status
sudo ufw allow 8088/tcp
sudo ufw reload

# CentOS/RHEL
sudo firewall-cmd --list-all
sudo firewall-cmd --add-port=8088/tcp --permanent
sudo firewall-cmd --reload
```

### Bước 3: Kiểm tra Router Port Forwarding

Truy cập router admin (thường `192.168.1.1`):
1. Tìm **Port Forwarding** hoặc **Virtual Server**
2. Thêm rule:
   - **External Port:** 8088
   - **Internal IP:** 192.168.1.10 (IP máy của bạn)
   - **Internal Port:** 8088
   - **Protocol:** TCP
3. Save và restart router

### Bước 4: Kiểm tra ISP Blocking

Một số ISP chặn certain ports. Test với port khác:

**Sửa `application.properties`:**
```properties
server.port=8080
```

Hoặc dùng cổng cao hơn: `8888`, `9090`

### Bước 5: Test với Ngrok (Workaround)

```bash
# Cài ngrok nếu chưa có
snap install ngrok

# Expose port 8088
ngrok http 8088
```

Nhận URL dạng: `https://abcd-1234.ngrok-free.app`

**Update frontend `.env.local`:**
```env
NEXT_PUBLIC_BACKEND_URL=https://abcd-1234.ngrok-free.app/api/v1
```

---

## 📋 Checklist Hoàn Chỉnh

### Backend Configuration
- [x] ✅ `PublicController` có endpoint `/public/health`
- [x] ✅ `server.port=8088` trong `application.properties`
- [ ] ⏳ `server.address=0.0.0.0` (mặc định là OK)
- [ ] ⏳ Backend đã restart
- [ ] ⏳ Test localhost thành công

### Network Configuration
- [ ] ⏳ Firewall allow port 8088
- [ ] ⏳ Router port forwarding configured
- [ ] ⏳ Public IP confirmed: `curl ifconfig.me`
- [ ] ⏳ ISP không block port 8088

### Frontend Configuration
- [ ] ⏳ `.env.local` có `NEXT_PUBLIC_BACKEND_URL`
- [ ] ⏳ CORS configured trong `SecurityConfig.java`

---

## 🚀 Quick Fix Script

```bash
#!/bin/bash

echo "=== Backend Network Diagnostic ==="
echo ""

echo "1. Public IP:"
curl -s ifconfig.me
echo ""

echo "2. Local IP:"
hostname -I | awk '{print $1}'
echo ""

echo "3. Backend Status:"
ps aux | grep spring-boot | grep -v grep | wc -l
echo ""

echo "4. Port 8088 Listen:"
ss -tlnp | grep 8088 || netstat -tlnp | grep 8088
echo ""

echo "5. Test localhost:"
curl -s http://localhost:8088/api/v1/public/health
echo ""

echo "6. Firewall Status:"
sudo ufw status 2>/dev/null || echo "UFW not installed"
echo ""

echo "7. Test Public IP:"
PUBLIC_IP=$(curl -s ifconfig.me)
curl -s http://$PUBLIC_IP:8088/api/v1/public/health
echo ""
```

Save as `check_backend.sh`, chmod +x, và chạy:
```bash
bash check_backend.sh
```

---

## 💡 Common Issues & Solutions

### Issue 1: "Connection Refused"
**Nguyên nhân:** Backend không chạy hoặc port sai  
**Giải pháp:**
```bash
ps aux | grep spring-boot
cd backend && mvn spring-boot:run
```

### Issue 2: "Connection Timeout"
**Nguyên nhân:** Firewall hoặc ISP chặn  
**Giải pháp:**
```bash
sudo ufw allow 8088
# Hoặc dùng ngrok
```

### Issue 3: "404 Not Found"
**Nguyên nhân:** Endpoint không tồn tại  
**Giải pháp:** Check Swagger UI:
```
http://localhost:8088/api/v1/swagger-ui/index.html
```

### Issue 4: "CORS Error"
**Nguyên nhân:** Frontend origin không được allow  
**Giải pháp:** Thêm vào `SecurityConfig.java`:
```java
configuration.setAllowedOrigins(Arrays.asList(
    "http://localhost:3008",
    "http://113.170.159.180:3008"
));
```

### Issue 5: "502 Bad Gateway" (với Nginx/Reverse Proxy)
**Nguyên nhân:** Proxy configuration sai  
**Giải pháp:**
```nginx
location /api/ {
    proxy_pass http://localhost:8088;
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
}
```

---

## 🎯 Khuyến Nghị

### For Development:
✅ **Dùng localhost** - Nhanh, đơn giản, không cần config network

### For Testing với Client/Team:
✅ **Dùng Ngrok** - Public URL ngay lập tức, HTTPS miễn phí

### For Production:
✅ **Deploy lên Cloud** - Railway, Render, AWS, DigitalOcean

---

## 📞 Still Not Working?

1. **Share terminal output của:**
   ```bash
   ss -tlnp | grep 8088
   curl http://localhost:8088/api/v1/public/health
   curl ifconfig.me
   ```

2. **Check backend logs:**
   ```bash
   tail -f backend/logs/spring.log
   # Hoặc trong terminal đang chạy mvn spring-boot:run
   ```

3. **Try alternative solution:**
   - Dùng Ngrok (100% success rate)
   - Đổi port: 8080, 8888, 9090
   - Deploy lên cloud platform

---

**🎉 Sau khi giải quyết, backend sẽ accessible từ bất kỳ đâu!**

