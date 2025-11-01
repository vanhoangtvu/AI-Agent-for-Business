# 📊 Dashboard API Documentation

## 🎯 Tổng Quan

Hệ thống cung cấp **2 Dashboard riêng biệt**:
1. **Admin Dashboard** - Tổng quan toàn hệ thống
2. **Business Dashboard** - Thống kê riêng cho từng doanh nghiệp

---

## 1. 👑 Admin Dashboard

### Endpoint
```
GET /api/dashboard/admin
```

### Authorization
- **Role Required:** `ADMIN` only
- **Header:** `Authorization: Bearer {admin_token}`

### Response Data

```json
{
  "totalUsers": 150,
  "newUsersThisMonth": 25,
  "usersByRole": {
    "ADMIN": 2,
    "BUSINESS": 48,
    "CUSTOMER": 100
  },
  "totalDocuments": 450,
  "documentsByStatus": {
    "PENDING": 10,
    "PROCESSING": 5,
    "COMPLETED": 430,
    "FAILED": 5
  },
  "totalConversations": 1200,
  "messagesToday": 150,
  "totalStorageUsed": 5368709120,
  "activeUsersToday": 45,
  "systemHealth": "HEALTHY",
  "topBusinesses": {...},
  "recentActivities": {...}
}
```

### Data Explanation

| Field | Description | Example |
|-------|-------------|---------|
| `totalUsers` | Tổng số users trong hệ thống | 150 |
| `newUsersThisMonth` | Users đăng ký trong tháng này | 25 |
| `usersByRole` | Phân bố users theo role | ADMIN: 2, BUSINESS: 48, CUSTOMER: 100 |
| `totalDocuments` | Tổng số documents đã upload | 450 |
| `documentsByStatus` | Documents theo trạng thái | PENDING: 10, COMPLETED: 430 |
| `totalConversations` | Tổng số cuộc hội thoại | 1200 |
| `messagesToday` | Số messages hôm nay | 150 |
| `totalStorageUsed` | Dung lượng đã dùng (bytes) | 5.36 GB |
| `activeUsersToday` | Users active hôm nay | 45 |
| `systemHealth` | Trạng thái hệ thống | HEALTHY |

### Use Cases

**1. Monitoring System Health**
```bash
# Check system status
curl -X GET http://localhost:8100/api/dashboard/admin \
  -H "Authorization: Bearer {admin_token}"
```

**2. View User Growth**
- Track `newUsersThisMonth`
- Compare với tháng trước
- Phân tích theo `usersByRole`

**3. Storage Management**
- Monitor `totalStorageUsed`
- Alert khi đạt ngưỡng
- Plan storage upgrade

**4. Document Processing**
- Track `documentsByStatus`
- Identify bottlenecks
- Monitor FAILED documents

---

## 2. 🏢 Business Dashboard

### Endpoint
```
GET /api/dashboard/business
```

### Authorization
- **Roles Required:** `ADMIN` hoặc `BUSINESS`
- **Header:** `Authorization: Bearer {business_token}`

### Response Data

```json
{
  "businessName": "Công ty TNHH ABC",
  "totalDocuments": 50,
  "processedDocuments": 45,
  "pendingDocuments": 5,
  "totalCustomers": 150,
  "newCustomersThisMonth": 20,
  "totalConversations": 300,
  "messagesToday": 45,
  "messagesThisWeek": 280,
  "avgMessagesPerDay": 40.0,
  "topCategories": {
    "Products": 20,
    "Services": 15,
    "Support": 10,
    "Finance": 5
  },
  "sentimentAnalysis": {
    "POSITIVE": 60,
    "NEUTRAL": 30,
    "NEGATIVE": 10
  },
  "satisfactionScore": 75.5,
  "responseRate": 95.5,
  "activeConversations": 85
}
```

### Data Explanation

| Field | Description | Business Value |
|-------|-------------|----------------|
| `businessName` | Tên doanh nghiệp | "Công ty TNHH ABC" |
| `totalDocuments` | Tổng documents đã upload | Track knowledge base size |
| `processedDocuments` | Documents đã xử lý xong | Monitor processing completion |
| `pendingDocuments` | Documents đang chờ | Action required |
| `totalCustomers` | Tổng số khách hàng | Customer base size |
| `newCustomersThisMonth` | Khách hàng mới tháng này | Growth metric |
| `totalConversations` | Tổng cuộc hội thoại | Engagement level |
| `messagesToday` | Messages hôm nay | Daily activity |
| `messagesThisWeek` | Messages trong tuần | Weekly trend |
| `avgMessagesPerDay` | Trung bình messages/ngày | Activity pattern |
| `topCategories` | Top 5 categories documents | Content distribution |
| `sentimentAnalysis` | Phân tích cảm xúc | Customer sentiment |
| `satisfactionScore` | Điểm hài lòng (0-100) | Service quality |
| `responseRate` | Tỷ lệ phản hồi (%) | Engagement rate |
| `activeConversations` | Conversations đang active | Current workload |

### Use Cases

**1. Monitor Business Performance**
```bash
# Get dashboard data
curl -X GET http://localhost:8100/api/dashboard/business \
  -H "Authorization: Bearer {business_token}"
```

**2. Track Customer Growth**
- Monitor `totalCustomers` trend
- Compare `newCustomersThisMonth` với target
- Analyze customer acquisition cost

**3. Content Strategy**
- Review `topCategories`
- Identify content gaps
- Plan content creation

**4. Customer Satisfaction**
- Track `satisfactionScore`
- Monitor `sentimentAnalysis`
- Improve service quality

**5. Response Management**
- Track `responseRate`
- Monitor `activeConversations`
- Manage team workload

---

## 📊 Dashboard Metrics Visualization

### Admin Dashboard Layout

```
┌─────────────────────────────────────────────────────┐
│                  ADMIN DASHBOARD                     │
├─────────────────────────────────────────────────────┤
│  📊 Users Statistics                                │
│  ├─ Total Users: 150                                │
│  ├─ New This Month: 25                              │
│  └─ By Role: ADMIN(2), BUSINESS(48), CUSTOMER(100) │
│                                                      │
│  📁 Documents Statistics                             │
│  ├─ Total: 450                                      │
│  └─ By Status: Completed(430), Pending(10)         │
│                                                      │
│  💬 Conversations                                    │
│  ├─ Total: 1200                                     │
│  └─ Messages Today: 150                             │
│                                                      │
│  💾 Storage: 5.36 GB                                │
│  👥 Active Today: 45 users                          │
│  🟢 System: HEALTHY                                 │
└─────────────────────────────────────────────────────┘
```

### Business Dashboard Layout

```
┌─────────────────────────────────────────────────────┐
│              BUSINESS DASHBOARD                      │
│              Công ty TNHH ABC                        │
├─────────────────────────────────────────────────────┤
│  📁 Documents                                        │
│  ├─ Total: 50                                       │
│  ├─ Processed: 45 (90%)                             │
│  └─ Pending: 5                                      │
│                                                      │
│  👥 Customers                                        │
│  ├─ Total: 150                                      │
│  └─ New This Month: 20                              │
│                                                      │
│  💬 Messages                                         │
│  ├─ Today: 45                                       │
│  ├─ This Week: 280                                  │
│  └─ Avg/Day: 40.0                                   │
│                                                      │
│  📊 Top Categories                                   │
│  └─ Products(20), Services(15), Support(10)        │
│                                                      │
│  😊 Customer Satisfaction                           │
│  ├─ Score: 75.5/100                                │
│  ├─ Positive: 60%, Neutral: 30%, Negative: 10%    │
│  └─ Response Rate: 95.5%                            │
└─────────────────────────────────────────────────────┘
```

---

## 🔄 Real-time Updates

### Polling Strategy

```javascript
// Frontend: Poll dashboard every 30 seconds
setInterval(async () => {
  const dashboard = await fetch('/api/dashboard/business', {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
  updateDashboard(dashboard);
}, 30000);
```

### WebSocket (Future)

```javascript
// Real-time updates via WebSocket
const ws = new WebSocket('ws://localhost:8100/ws/dashboard');
ws.onmessage = (event) => {
  const update = JSON.parse(event.data);
  updateDashboardMetric(update.metric, update.value);
};
```

---

## 📈 KPI Tracking

### Admin KPIs

1. **User Growth Rate**
   ```
   Growth Rate = (New Users This Month / Total Users) * 100
   ```

2. **System Utilization**
   ```
   Utilization = (Active Users Today / Total Users) * 100
   ```

3. **Document Processing Rate**
   ```
   Processing Rate = (Completed / Total Documents) * 100
   ```

### Business KPIs

1. **Customer Acquisition**
   ```
   CAC = Marketing Cost / New Customers This Month
   ```

2. **Engagement Rate**
   ```
   Engagement = (Active Conversations / Total Customers) * 100
   ```

3. **Customer Satisfaction**
   ```
   CSAT = (Positive Sentiment / Total Sentiment) * 100
   ```

---

## 🧪 Testing Dashboards

### Test Admin Dashboard

```bash
# 1. Login as Admin
TOKEN=$(curl -s -X POST http://localhost:8100/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@aiagent.com","password":"admin123"}' \
  | jq -r '.token')

# 2. Get Admin Dashboard
curl -X GET http://localhost:8100/api/dashboard/admin \
  -H "Authorization: Bearer $TOKEN" \
  | jq
```

### Test Business Dashboard

```bash
# 1. Login as Business
TOKEN=$(curl -s -X POST http://localhost:8100/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"business@aiagent.com","password":"business123"}' \
  | jq -r '.token')

# 2. Get Business Dashboard
curl -X GET http://localhost:8100/api/dashboard/business \
  -H "Authorization: Bearer $TOKEN" \
  | jq
```

---

## 🎨 Frontend Integration

### React Example

```typescript
// hooks/useDashboard.ts
export const useAdminDashboard = () => {
  const [dashboard, setDashboard] = useState<AdminDashboard | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchDashboard = async () => {
      const response = await fetch('/api/dashboard/admin', {
        headers: {
          'Authorization': `Bearer ${getToken()}`
        }
      });
      const data = await response.json();
      setDashboard(data);
      setLoading(false);
    };

    fetchDashboard();
    const interval = setInterval(fetchDashboard, 30000);
    return () => clearInterval(interval);
  }, []);

  return { dashboard, loading };
};
```

### Next.js Page

```typescript
// app/dashboard/admin/page.tsx
'use client';

import { useAdminDashboard } from '@/hooks/useDashboard';

export default function AdminDashboard() {
  const { dashboard, loading } = useAdminDashboard();

  if (loading) return <LoadingSpinner />;

  return (
    <div className="dashboard">
      <h1>Admin Dashboard</h1>
      
      <StatsCard
        title="Total Users"
        value={dashboard.totalUsers}
        change={dashboard.newUsersThisMonth}
      />
      
      <ChartComponent
        title="Users by Role"
        data={dashboard.usersByRole}
      />
      
      {/* More widgets... */}
    </div>
  );
}
```

---

## 🔒 Security Notes

1. **Admin Dashboard** chỉ ADMIN mới access được
2. **Business Dashboard** ADMIN và BUSINESS đều access được
3. Business chỉ xem được data của mình
4. Tất cả endpoints đều require JWT authentication
5. Rate limiting nên được áp dụng (future)

---

## 📝 Future Enhancements

- [ ] Real-time updates via WebSocket
- [ ] Export dashboard to PDF/Excel
- [ ] Custom date range filtering
- [ ] Comparison with previous periods
- [ ] Advanced charts & visualizations
- [ ] Email reports automation
- [ ] Mobile-optimized dashboards
- [ ] Customizable widgets

---

**Sinh viên:** Nguyễn Văn Hoàng - MSSV: 110122078  
**Trường:** Đại Học Trà Vinh  
**Email:** 110122078@st.tvu.edu.vn

