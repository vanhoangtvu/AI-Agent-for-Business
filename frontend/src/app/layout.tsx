import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import './globals.css'
import Providers from './providers'

const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'AI Agent for Business - Trợ lý AI thông minh cho doanh nghiệp',
  description: 'Hệ thống AI Chatbot kết hợp RAG và MCP Framework, tích hợp đa kênh (Website, Zalo OA, Zalo Personal)',
  keywords: 'AI Chatbot, RAG, MCP, Zalo, Business Assistant, CRM, Analytics',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="vi">
      <body className={inter.className}>
        <Providers>
          {children}
        </Providers>
      </body>
    </html>
  )
}

