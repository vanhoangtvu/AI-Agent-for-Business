# AI Agent for Business - Frontend

Frontend Next.js 14 với TypeScript, TailwindCSS, React Query, Zustand.

## 📦 Tech Stack

- **Framework**: Next.js 14 (App Router)
- **Language**: TypeScript
- **Styling**: TailwindCSS
- **State Management**: Zustand + React Query
- **Auth**: NextAuth.js + JWT
- **Forms**: React Hook Form + Zod
- **Icons**: Lucide React
- **Charts**: Recharts
- **HTTP Client**: Axios

## 🚀 Quick Start

```bash
# Install dependencies
npm install

# Run development server
npm run dev

# Build for production
npm run build

# Start production server
npm start
```

## 📁 Cấu trúc Project

```
src/
├── app/                   # Next.js App Router
│   ├── (auth)/           # Auth pages (login, register)
│   ├── (dashboard)/      # Dashboard pages (admin, business, customer)
│   ├── layout.tsx        # Root layout
│   └── page.tsx          # Home page
├── components/           # React components
│   ├── ui/              # Base UI components
│   ├── layout/          # Layout components
│   ├── auth/            # Auth components
│   ├── chatbot/         # Chatbot features
│   ├── customers/       # Customer management
│   ├── products/        # Product management
│   ├── orders/          # Order management
│   └── analytics/       # Analytics dashboards
├── lib/                 # Utilities
│   ├── api.ts          # API client
│   ├── utils.ts        # Helper functions
│   └── queryClient.ts  # React Query config
├── hooks/              # Custom React hooks
├── store/              # Zustand stores
└── types/              # TypeScript types
```

## ✅ Đã hoàn thành

### Core Files
- ✅ `package.json` - Dependencies configuration
- ✅ `next.config.js` - Next.js configuration
- ✅ `tailwind.config.ts` - TailwindCSS setup
- ✅ `tsconfig.json` - TypeScript configuration
- ✅ `.env.local` - Environment variables
- ✅ `src/types/index.ts` - TypeScript type definitions (đầy đủ)
- ✅ `src/lib/api.ts` - API client với tất cả endpoints
- ✅ `src/lib/utils.ts` - Utility functions
- ✅ `src/lib/queryClient.ts` - React Query config
- ✅ `src/store/authStore.ts` - Auth state management
- ✅ `src/store/uiStore.ts` - UI state management
- ✅ `src/app/layout.tsx` - Root layout
- ✅ `src/app/page.tsx` - Home/Landing page
- ✅ `src/app/globals.css` - Global styles
- ✅ `src/app/providers.tsx` - Provider wrapper
- ✅ `src/components/ui/button.tsx` - Button component
- ✅ `src/components/ui/input.tsx` - Input component
- ✅ `src/components/ui/card.tsx` - Card component

### Cấu trúc thư mục
- ✅ Tất cả thư mục cho App Router
- ✅ Tất cả thư mục cho Components

## 🎯 Cần tạo thêm

### Authentication Pages
```bash
# Cần tạo:
src/app/(auth)/login/page.tsx
src/app/(auth)/register/page.tsx
src/components/auth/LoginForm.tsx
src/components/auth/RegisterForm.tsx
src/middleware.ts  # Route protection
```

### Dashboard Pages
```bash
# Admin Dashboard
src/app/(dashboard)/admin/page.tsx
src/app/(dashboard)/admin/businesses/page.tsx
src/app/(dashboard)/admin/users/page.tsx
src/app/(dashboard)/admin/stats/page.tsx

# Business Dashboard
src/app/(dashboard)/business/page.tsx
src/app/(dashboard)/business/customers/page.tsx
src/app/(dashboard)/business/products/page.tsx
src/app/(dashboard)/business/orders/page.tsx
src/app/(dashboard)/business/conversations/page.tsx
src/app/(dashboard)/business/analytics/page.tsx
src/app/(dashboard)/business/chatbot/page.tsx
src/app/(dashboard)/business/zalo/page.tsx

# Customer Portal
src/app/(dashboard)/customer/page.tsx
src/app/(dashboard)/customer/orders/page.tsx
src/app/(dashboard)/customer/profile/page.tsx
```

### UI Components (còn thiếu)
```bash
src/components/ui/badge.tsx
src/components/ui/select.tsx
src/components/ui/dialog.tsx
src/components/ui/table.tsx
src/components/ui/avatar.tsx
src/components/ui/tabs.tsx
src/components/ui/dropdown-menu.tsx
... (nhiều components khác)
```

### Layout Components
```bash
src/components/layout/Header.tsx
src/components/layout/Sidebar.tsx
src/components/layout/DashboardLayout.tsx
```

### Feature Components
```bash
# Chatbot
src/components/chatbot/ChatWidget.tsx
src/components/chatbot/ChatMessage.tsx
src/components/chatbot/ChatInput.tsx

# Dashboard
src/components/dashboard/StatsCard.tsx
src/components/dashboard/RevenueChart.tsx

# Customers
src/components/customers/CustomerList.tsx
src/components/customers/CustomerForm.tsx

# Products
src/components/products/ProductList.tsx
src/components/products/ProductForm.tsx

# Orders
src/components/orders/OrderList.tsx
src/components/orders/OrderDetail.tsx

# Analytics
src/components/analytics/OverviewDashboard.tsx
src/components/analytics/RFMAnalysis.tsx

# Zalo
src/components/zalo/ZaloQRLogin.tsx
src/components/zalo/ZaloConfig.tsx
```

### Custom Hooks
```bash
src/hooks/useAuth.ts
src/hooks/useCustomers.ts
src/hooks/useProducts.ts
src/hooks/useOrders.ts
src/hooks/useConversations.ts
src/hooks/useAnalytics.ts
```

## 🔑 API Integration

API client đã sẵn sàng ở `src/lib/api.ts` với tất cả endpoints:

```typescript
import { authApi, customersApi, productsApi, ... } from '@/lib/api'

// Sử dụng với React Query
const { data, isLoading } = useQuery({
  queryKey: ['customers'],
  queryFn: () => customersApi.getAll()
})
```

## 🎨 Styling

Sử dụng TailwindCSS với hệ thống design tokens (đã config trong `tailwind.config.ts` và `globals.css`).

```tsx
// Example
<div className="bg-primary text-primary-foreground rounded-lg p-4">
  <h1 className="text-2xl font-bold">Hello</h1>
</div>
```

## 🔐 Authentication Flow

1. User login → JWT token
2. Store token in localStorage & Zustand
3. API client auto-add token to headers
4. Middleware protects routes
5. Redirect to dashboard based on role

## 📝 Next Steps

### Priority 1: Authentication
1. Tạo Login/Register pages
2. Tạo LoginForm/RegisterForm components
3. Tạo middleware.ts cho route protection
4. Test authentication flow

### Priority 2: Dashboard Layouts
1. Tạo DashboardLayout component
2. Tạo Header/Sidebar components
3. Tạo các dashboard pages cơ bản

### Priority 3: Feature Implementation
1. Customer management (List, Create, Update, Delete)
2. Product management
3. Order management
4. Conversations/Chat
5. Analytics dashboards
6. Chatbot configuration
7. Zalo integration

## 🌐 Environment Variables

Đã tạo `.env.local` với:
```
NEXT_PUBLIC_API_URL=http://localhost:8088/api/v1
NEXTAUTH_URL=http://localhost:3008
NEXTAUTH_SECRET=your-secret-key
```

## 📚 Tài liệu tham khảo

- **Next.js 14 Docs**: https://nextjs.org/docs
- **TailwindCSS**: https://tailwindcss.com/docs
- **React Query**: https://tanstack.com/query/latest
- **Zustand**: https://docs.pmnd.rs/zustand
- **Shadcn/ui**: https://ui.shadcn.com (for more UI components)

---

## 🚀 Quick Deploy

```bash
# Vercel (recommended)
vercel

# Or manual
npm run build
npm start
```

## 📊 Current Status

- ✅ Project setup complete
- ✅ Core infrastructure ready (API, State, Types)
- ✅ Base UI components created
- ✅ Landing page done
- ⏳ Auth pages (need to create)
- ⏳ Dashboard pages (need to create)
- ⏳ Feature components (need to create)

**Ước tính:** Cần ~2-3 ngày để hoàn thiện tất cả features với 1 developer.

**Khuyến nghị:** Bắt đầu với Authentication → Business Dashboard → Customer Management → tiếp tục các features khác.

