// User & Auth Types
export type UserRole = 'ADMIN' | 'BUSINESS' | 'CUSTOMER'

export interface User {
  id: number
  email: string
  fullName: string
  phone?: string
  role: UserRole
  businessId?: number
  isActive: boolean
  isVerified: boolean
  avatarUrl?: string
  createdAt: string
  updatedAt: string
}

export interface AuthResponse {
  token: string
  type: string
  id: number
  email: string
  fullName: string
  role: UserRole
  businessId?: number
}

export interface LoginRequest {
  email: string
  password: string
}

export interface RegisterRequest {
  email: string
  password: string
  fullName: string
  phone?: string
  role: UserRole
}

// Business Types
export type BusinessStatus = 'ACTIVE' | 'SUSPENDED' | 'INACTIVE'
export type SubscriptionPlan = 'FREE' | 'BASIC' | 'PRO' | 'ENTERPRISE'

export interface Business {
  id: number
  businessName: string
  email: string
  phone: string
  address: string
  website?: string
  logo?: string
  description?: string
  status: BusinessStatus
  subscriptionPlan: SubscriptionPlan
  totalCustomers: number
  totalOrders: number
  totalRevenue: number
  createdAt: string
  updatedAt: string
}

// Customer Types
export type CustomerSegment = 'VIP' | 'REGULAR' | 'POTENTIAL' | 'AT_RISK'
export type CustomerSource = 'WEBSITE' | 'ZALO_OA' | 'ZALO_PERSONAL' | 'MANUAL'

export interface Customer {
  id: number
  businessId: number
  name: string
  email?: string
  phone: string
  address?: string
  avatar?: string
  source: CustomerSource
  segment: CustomerSegment
  totalOrders: number
  totalSpent: number
  averageOrderValue: number
  lastOrderAt?: string
  notes?: string
  tags?: string
  createdAt: string
  updatedAt: string
}

// Product Types
export interface Product {
  id: number
  businessId: number
  sku: string
  name: string
  description?: string
  category?: string
  imageUrl?: string
  images?: string
  price: number
  salePrice?: number
  costPrice?: number
  stockQuantity: number
  lowStockThreshold?: number
  totalSold: number
  totalRevenue: number
  rating?: number
  reviewCount: number
  isActive: boolean
  createdAt: string
  updatedAt: string
}

// Order Types
export type OrderStatus = 'PENDING' | 'PROCESSING' | 'COMPLETED' | 'CANCELLED'
export type PaymentMethod = 'COD' | 'BANK_TRANSFER' | 'CREDIT_CARD' | 'VNPAY' | 'MOMO'
export type PaymentStatus = 'PENDING' | 'PAID' | 'FAILED' | 'REFUNDED'

export interface Order {
  id: number
  businessId: number
  customerId: number
  orderCode: string
  status: OrderStatus
  subtotal: number
  discount: number
  shippingFee: number
  tax: number
  totalAmount: number
  paymentMethod?: PaymentMethod
  paymentStatus: PaymentStatus
  shippingAddress?: string
  notes?: string
  createdAt: string
  updatedAt: string
  customer?: Customer
  items?: OrderItem[]
}

export interface OrderItem {
  id: number
  orderId: number
  productId: number
  quantity: number
  price: number
  subtotal: number
  product?: Product
}

// Conversation Types
export type ConversationChannel = 'WEBSITE' | 'ZALO_OA' | 'ZALO_PERSONAL'
export type ConversationStatus = 'ACTIVE' | 'RESOLVED' | 'ARCHIVED'
export type AiMode = 'AUTO' | 'SUGGESTION' | 'NOTIFICATION' | 'MANUAL'

export interface Conversation {
  id: number
  businessId: number
  customerId?: number
  channel: ConversationChannel
  status: ConversationStatus
  aiMode: AiMode
  lastMessageAt: string
  unreadCount: number
  createdAt: string
  updatedAt: string
  customer?: Customer
  lastMessage?: Message
}

// Message Types
export type MessageType = 'TEXT' | 'IMAGE' | 'FILE' | 'PRODUCT' | 'ORDER'
export type SenderType = 'CUSTOMER' | 'BUSINESS' | 'AI'

export interface Message {
  id: number
  conversationId: number
  content: string
  messageType: MessageType
  senderType: SenderType
  senderId?: number
  isAiGenerated: boolean
  aiConfidence?: number
  usedRag: boolean
  createdAt: string
}

// Analytics Types
export interface OverviewStats {
  totalRevenue: number
  revenueGrowth: number
  totalOrders: number
  ordersGrowth: number
  totalCustomers: number
  customersGrowth: number
  unreadMessages: number
}

export interface RevenueData {
  date: string
  revenue: number
  orders: number
}

export interface TopProduct {
  id: number
  name: string
  sold: number
  revenue: number
}

export interface RFMSegment {
  segment: CustomerSegment
  count: number
  percentage: number
  totalSpent: number
}

// API Response Types
export interface ApiResponse<T = any> {
  success: boolean
  message: string
  data?: T
}

export interface PaginatedResponse<T> {
  data: T[]
  total: number
  page: number
  pageSize: number
  totalPages: number
}

// Chatbot Types
export interface ChatbotConfig {
  businessId: number
  autoReply: boolean
  useRAG: boolean
  tone: 'PROFESSIONAL' | 'FRIENDLY' | 'CASUAL'
  language: 'VI' | 'EN'
  greetingMessage: string
  workingHours: {
    enabled: boolean
    start: string
    end: string
  }
}

export interface Document {
  id: number
  businessId: number
  fileName: string
  fileType: string
  fileSize: number
  documentType: string
  description?: string
  uploadedAt: string
}

// Zalo Types
export interface ZaloQRSession {
  sessionId: string
  qrCode: string
  expiresIn: number
  status: 'WAITING' | 'LOGGED_IN' | 'EXPIRED'
}

export interface ZaloConfig {
  mode: 'AUTO' | 'SUGGESTION' | 'NOTIFICATION'
  autoReply: boolean
  useRAG: boolean
  autoCreateOrder: boolean
  workingHours: {
    enabled: boolean
    start: string
    end: string
  }
}

