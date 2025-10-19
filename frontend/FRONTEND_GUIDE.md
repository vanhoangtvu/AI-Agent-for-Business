# Frontend Development Guide - AI Agent for Business

## 📦 Cấu trúc Project

```
frontend/
├── src/
│   ├── app/                          # Next.js 14 App Router
│   │   ├── (auth)/                   # Auth routes group
│   │   │   ├── login/
│   │   │   │   └── page.tsx
│   │   │   └── register/
│   │   │       └── page.tsx
│   │   ├── (dashboard)/              # Dashboard routes group  
│   │   │   ├── admin/                # Admin dashboard
│   │   │   │   ├── businesses/
│   │   │   │   ├── users/
│   │   │   │   ├── stats/
│   │   │   │   └── page.tsx
│   │   │   ├── business/             # Business dashboard
│   │   │   │   ├── customers/
│   │   │   │   ├── products/
│   │   │   │   ├── orders/
│   │   │   │   ├── conversations/
│   │   │   │   ├── analytics/
│   │   │   │   ├── chatbot/
│   │   │   │   ├── zalo/
│   │   │   │   └── page.tsx
│   │   │   └── customer/             # Customer portal
│   │   │       ├── orders/
│   │   │       ├── conversations/
│   │   │       ├── profile/
│   │   │       └── page.tsx
│   │   ├── api/                      # API routes (NextAuth)
│   │   │   └── auth/
│   │   │       └── [...nextauth]/
│   │   │           └── route.ts
│   │   ├── layout.tsx                # Root layout
│   │   ├── page.tsx                  # Home page
│   │   └── globals.css               # Global styles
│   │
│   ├── components/                   # React components
│   │   ├── ui/                       # Base UI components (shadcn/ui style)
│   │   │   ├── button.tsx
│   │   │   ├── card.tsx
│   │   │   ├── input.tsx
│   │   │   ├── select.tsx
│   │   │   ├── dialog.tsx
│   │   │   ├── table.tsx
│   │   │   ├── badge.tsx
│   │   │   ├── avatar.tsx
│   │   │   └── ...
│   │   ├── layout/                   # Layout components
│   │   │   ├── Header.tsx
│   │   │   ├── Sidebar.tsx
│   │   │   ├── Footer.tsx
│   │   │   └── DashboardLayout.tsx
│   │   ├── auth/                     # Auth components
│   │   │   ├── LoginForm.tsx
│   │   │   ├── RegisterForm.tsx
│   │   │   └── ProtectedRoute.tsx
│   │   ├── chatbot/                  # Chatbot components
│   │   │   ├── ChatWidget.tsx
│   │   │   ├── ChatMessage.tsx
│   │   │   ├── ChatInput.tsx
│   │   │   └── ChatbotConfig.tsx
│   │   ├── dashboard/                # Dashboard components
│   │   │   ├── StatsCard.tsx
│   │   │   ├── RevenueChart.tsx
│   │   │   ├── RecentOrders.tsx
│   │   │   └── TopProducts.tsx
│   │   ├── customers/                # Customer management
│   │   │   ├── CustomerList.tsx
│   │   │   ├── CustomerDetail.tsx
│   │   │   └── CustomerForm.tsx
│   │   ├── products/                 # Product management
│   │   │   ├── ProductList.tsx
│   │   │   ├── ProductDetail.tsx
│   │   │   └── ProductForm.tsx
│   │   ├── orders/                   # Order management
│   │   │   ├── OrderList.tsx
│   │   │   ├── OrderDetail.tsx
│   │   │   └── OrderForm.tsx
│   │   ├── conversations/            # Conversation management
│   │   │   ├── ConversationList.tsx
│   │   │   ├── MessageThread.tsx
│   │   │   └── MessageInput.tsx
│   │   ├── analytics/                # Analytics components
│   │   │   ├── OverviewDashboard.tsx
│   │   │   ├── RFMAnalysis.tsx
│   │   │   ├── RevenueChart.tsx
│   │   │   └── InsightsPanel.tsx
│   │   └── zalo/                     # Zalo integration
│   │       ├── ZaloQRLogin.tsx
│   │       ├── ZaloConfig.tsx
│   │       └── ZaloConversations.tsx
│   │
│   ├── lib/                          # Utility libraries
│   │   ├── api.ts                    # API client (axios)
│   │   ├── utils.ts                  # Utility functions
│   │   ├── auth.ts                   # NextAuth config
│   │   └── queryClient.ts            # React Query config
│   │
│   ├── hooks/                        # Custom React hooks
│   │   ├── useAuth.ts
│   │   ├── useCustomers.ts
│   │   ├── useProducts.ts
│   │   ├── useOrders.ts
│   │   ├── useConversations.ts
│   │   ├── useAnalytics.ts
│   │   └── useChatbot.ts
│   │
│   ├── store/                        # Zustand stores
│   │   ├── authStore.ts
│   │   ├── chatbotStore.ts
│   │   └── uiStore.ts
│   │
│   ├── types/                        # TypeScript types
│   │   ├── index.ts
│   │   ├── auth.ts
│   │   ├── customer.ts
│   │   ├── product.ts
│   │   ├── order.ts
│   │   ├── conversation.ts
│   │   └── analytics.ts
│   │
│   └── middleware.ts                 # Next.js middleware (auth)
│
├── public/                           # Static assets
│   ├── images/
│   ├── icons/
│   └── favicon.ico
│
├── package.json
├── next.config.js
├── tailwind.config.ts
├── tsconfig.json
├── postcss.config.js
└── .env.local

```

## 🚀 Installation & Setup

```bash
# Navigate to frontend directory
cd /home/hv/DuAn/AI-Agent-for-Business/frontend

# Install dependencies
npm install

# Run development server
npm run dev

# Build for production
npm run build

# Start production server
npm start
```

## 🔑 Key Files to Create

### 1. **src/app/layout.tsx** - Root Layout
### 2. **src/app/page.tsx** - Home Page
### 3. **src/app/globals.css** - Global Styles
### 4. **src/lib/api.ts** - API Client
### 5. **src/lib/auth.ts** - NextAuth Configuration
### 6. **src/types/index.ts** - TypeScript Types
### 7. **src/store/authStore.ts** - Auth State Management
### 8. **src/middleware.ts** - Route Protection
### 9. **src/components/ui/** - Base UI Components
### 10. **src/components/chatbot/ChatWidget.tsx** - Chat Widget

## 📝 Next Steps

1. **Install dependencies**: `npm install`
2. **Create base UI components** in `src/components/ui/`
3. **Setup authentication** with NextAuth.js
4. **Create dashboard layouts** for Admin, Business, Customer
5. **Implement API integration** with React Query
6. **Build chatbot widget**
7. **Add analytics dashboards**
8. **Integrate Zalo features**

## 🎨 Design System

- **Colors**: Primary (Blue), Secondary (Gray), Success (Green), Error (Red)
- **Typography**: Inter font family
- **Spacing**: 4px base unit (Tailwind)
- **Components**: Based on shadcn/ui patterns
- **Icons**: Lucide React

## 🔐 Authentication Flow

1. User navigates to `/login` or `/register`
2. Submit credentials to backend `/auth/login` or `/auth/register`
3. Receive JWT token
4. Store token in NextAuth session
5. Middleware protects dashboard routes
6. API client includes token in requests

## 📊 State Management

- **Zustand**: Global state (auth, UI)
- **React Query**: Server state (API data)
- **React Hook Form**: Form state
- **NextAuth**: Session state

## 🌐 API Integration

All API calls to: `http://localhost:8088/api/v1`

### Example API Hook:
```typescript
export function useCustomers() {
  return useQuery({
    queryKey: ['customers'],
    queryFn: () => api.get('/business/customers'),
  })
}
```

## 🚦 Routing Structure

- `/` - Landing page
- `/login` - Login page
- `/register` - Register page
- `/admin/*` - Admin dashboard (ADMIN role)
- `/business/*` - Business dashboard (BUSINESS role)
- `/customer/*` - Customer portal (CUSTOMER role)

## 📦 Key Dependencies

- **next**: 14.2.15 (App Router, SSR/SSG)
- **react-query**: API state management
- **zustand**: Global state
- **next-auth**: Authentication
- **axios**: HTTP client
- **react-hook-form**: Forms
- **zod**: Validation
- **tailwindcss**: Styling
- **lucide-react**: Icons
- **recharts**: Charts

## 🎯 Features to Implement

### Admin Dashboard
- [ ] Manage businesses
- [ ] View all users
- [ ] System statistics
- [ ] Logs & monitoring

### Business Dashboard  
- [ ] Overview & statistics
- [ ] Customer management (CRUD)
- [ ] Product management (CRUD)
- [ ] Order management
- [ ] Conversation management
- [ ] Chatbot configuration
- [ ] Zalo OA integration
- [ ] Zalo Personal integration
- [ ] Analytics & insights
- [ ] RAG document upload

### Customer Portal
- [ ] View profile
- [ ] View orders
- [ ] Chat with business
- [ ] Notifications

### Chatbot Widget (Public)
- [ ] Embeddable chat widget
- [ ] Real-time messaging
- [ ] AI responses
- [ ] Product recommendations

## 🔥 Priority Files to Create First

1. `src/app/layout.tsx` & `src/app/page.tsx`
2. `src/lib/api.ts` & `src/types/index.ts`
3. `src/components/ui/button.tsx` & other base components
4. `src/app/(auth)/login/page.tsx` & `src/app/(auth)/register/page.tsx`
5. `src/app/(dashboard)/business/page.tsx` - Business dashboard
6. `src/components/chatbot/ChatWidget.tsx`

---

**📌 Note**: Đây là cấu trúc đầy đủ. Bắt đầu với các files priority và mở rộng dần.

