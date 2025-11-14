// Auth Types
export interface User {
  id: number;
  username: string;
  email: string;
  fullName?: string;
  phoneNumber?: string;
  roles: string[];
  createdAt?: string;
}

export interface UserResponse {
  id: number;
  username: string;
  email: string;
  fullName?: string;
  phoneNumber?: string;
  roles: string[];
  createdAt?: string;
}

export interface UpdateProfileRequest {
  email: string;
  fullName?: string;
  phoneNumber?: string;
}

export interface ChangePasswordRequest {
  oldPassword: string;
  newPassword: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  fullName?: string;
  phone?: string;
}

export interface AuthResponse {
  token: string;
  type: string;
  id: number;
  username: string;
  email: string;
  roles: string[];
}

// Document Types
export interface Document {
  id: number;
  fileName: string;
  fileType: string;
  fileSize: number;
  status: 'PENDING' | 'PROCESSING' | 'COMPLETED' | 'FAILED';
  category?: string;
  tags?: string;
  vectorized: boolean;
  chunkCount: number;
  createdAt: string;
  processedAt?: string;
}

export interface DocumentResponse {
  id: number;
  fileName: string;
  fileType: string;
  fileSize: number;
  status: string;
  category?: string;
  tags?: string;
  vectorized: boolean;
  chunkCount: number;
  createdAt: string;
  processedAt?: string;
}

// Chat Types
export interface Conversation {
  id: number;
  title: string;
  createdAt: string;
  updatedAt: string;
  messageCount: number;
}

export interface Message {
  id: number;
  content: string;
  role: 'USER' | 'AI';
  timestamp: string;
  sources?: DocumentSource[];
}

export interface MessageResponse {
  id: number;
  role: 'USER' | 'AI';
  sender: 'USER' | 'AI';
  content: string;
  timestamp: string;
  sources?: DocumentSource[];
}

export interface DocumentSource {
  documentId: number;
  documentName: string;
  fileName?: string;
  pageNumber?: number;
  score?: number;
  relevanceScore?: number;
}

export interface ChatRequest {
  conversationId: number;
  message: string;
}

export interface ChatResponse {
  id: number;
  role: 'USER' | 'AI';
  content: string;
  timestamp: string;
}

export interface ConversationResponse {
  id: number;
  title: string;
  messageCount: number;
  createdAt: string;
  updatedAt: string;
}

// Report Types
export interface StrategicReport {
  id: number;
  title: string;
  reportType: string;
  generatedAt: string;
  swotAnalysis: string;
  recommendations: string;
  marketInsights: string;
  riskAssessment: string;
}

export interface ReportRequest {
  title: string;
  reportType: string;
  documentIds: number[];
  metrics: Record<string, any>;
}

// Activity Log Types
export interface ActivityLog {
  id: number;
  activityType: string;
  description: string;
  ipAddress?: string;
  timestamp: string;
}

// Add alias for backward compatibility
export type Activity = ActivityLog & {
  action: string;
};

// Pagination Types
export interface PageRequest {
  page: number;
  size: number;
  sort?: string;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
  first: boolean;
  last: boolean;
}

// Product Types
export interface Product {
  id: number;
  name: string;
  description?: string;
  price: number;
  compareAtPrice?: number;
  stockQuantity: number;
  sku?: string;
  barcode?: string;
  category?: CategoryResponse;
  isActive: boolean;
  isFeatured: boolean;
  imageUrls: string[];
  tags: string[];
  weight?: number;
  manufacturer?: string;
  origin?: string;
  rating?: number;
  reviewCount?: number;
  viewCount?: number;
  soldCount?: number;
  createdAt: string;
  updatedAt: string;
  inStock?: boolean;
  onSale?: boolean;
  discountPercentage?: number;
}

export interface ProductRequest {
  name: string;
  description?: string;
  price: number;
  compareAtPrice?: number;
  stockQuantity: number;
  sku?: string;
  barcode?: string;
  categoryId?: number;
  isActive?: boolean;
  isFeatured?: boolean;
  imageUrls?: string[];
  tags?: string[];
  weight?: number;
  manufacturer?: string;
  origin?: string;
}

export interface CategoryResponse {
  id: number;
  name: string;
  slug: string;
  description?: string;
  imageUrl?: string;
  parentId?: number;
  parentName?: string;
  children?: CategoryResponse[];
  isActive: boolean;
  displayOrder?: number;
  productCount?: number;
  createdAt: string;
  updatedAt: string;
}

// Order Types
export interface Order {
  id: number;
  orderNumber: string;
  userId: number;
  userName: string;
  items: OrderItem[];
  status: OrderStatus;
  paymentStatus: PaymentStatus;
  paymentMethod: PaymentMethod;
  subtotal: number;
  shippingFee: number;
  discount: number;
  tax: number;
  total: number;
  shippingName: string;
  shippingPhone: string;
  shippingAddress: string;
  shippingCity?: string;
  shippingDistrict?: string;
  shippingWard?: string;
  shippingNote?: string;
  trackingNumber?: string;
  paidAt?: string;
  shippedAt?: string;
  deliveredAt?: string;
  cancelledAt?: string;
  cancellationReason?: string;
  note?: string;
  createdAt: string;
  updatedAt: string;
}

export interface OrderItem {
  id: number;
  productId: number;
  productName: string;
  productImage?: string;
  price: number;
  quantity: number;
  totalPrice: number;
  discount: number;
}

export interface OrderRequest {
  items: OrderItemRequest[];
  shippingName: string;
  shippingPhone: string;
  shippingAddress: string;
  shippingCity?: string;
  shippingDistrict?: string;
  shippingWard?: string;
  shippingNote?: string;
  paymentMethod: PaymentMethod;
  shippingFee?: number;
  discount?: number;
  tax?: number;
  note?: string;
}

export interface OrderItemRequest {
  productId: number;
  quantity: number;
}

export enum OrderStatus {
  PENDING = 'PENDING',
  CONFIRMED = 'CONFIRMED',
  PROCESSING = 'PROCESSING',
  SHIPPING = 'SHIPPING',
  DELIVERED = 'DELIVERED',
  COMPLETED = 'COMPLETED',
  CANCELLED = 'CANCELLED',
  REFUNDED = 'REFUNDED',
}

export enum PaymentStatus {
  UNPAID = 'UNPAID',
  PAID = 'PAID',
  REFUNDED = 'REFUNDED',
  FAILED = 'FAILED',
}

export enum PaymentMethod {
  COD = 'COD',
  BANK_TRANSFER = 'BANK_TRANSFER',
  CREDIT_CARD = 'CREDIT_CARD',
  E_WALLET = 'E_WALLET',
  PAYPAL = 'PAYPAL',
}

export interface OrderStatistics {
  totalOrders: number;
  totalRevenue: number;
  averageOrderValue: number;
  ordersByStatus: Record<OrderStatus, number>;
}

