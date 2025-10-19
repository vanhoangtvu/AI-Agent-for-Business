import Link from 'next/link'
import { Button } from '@/components/ui/button'

export default function HomePage() {
  return (
    <div className="min-h-screen bg-gradient-to-b from-blue-50 to-white">
      {/* Header */}
      <header className="border-b bg-white/80 backdrop-blur-sm sticky top-0 z-50">
        <div className="container mx-auto px-4 py-4 flex items-center justify-between">
          <div className="flex items-center space-x-2">
            <div className="w-10 h-10 bg-blue-600 rounded-lg flex items-center justify-center">
              <span className="text-white font-bold text-xl">AI</span>
            </div>
            <span className="font-bold text-xl">AI Agent</span>
          </div>
          <nav className="hidden md:flex space-x-8">
            <Link href="#features" className="text-gray-600 hover:text-blue-600">Tính năng</Link>
            <Link href="#pricing" className="text-gray-600 hover:text-blue-600">Bảng giá</Link>
            <Link href="#about" className="text-gray-600 hover:text-blue-600">Về chúng tôi</Link>
          </nav>
          <div className="flex items-center space-x-4">
            <Link href="/login">
              <Button variant="ghost">Đăng nhập</Button>
            </Link>
            <Link href="/register">
              <Button>Dùng thử miễn phí</Button>
            </Link>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <section className="container mx-auto px-4 py-20 text-center">
        <h1 className="text-5xl md:text-6xl font-bold mb-6 bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
          Trợ lý AI thông minh cho doanh nghiệp
        </h1>
        <p className="text-xl text-gray-600 mb-8 max-w-3xl mx-auto">
          Kết hợp RAG và MCP Framework, tích hợp đa kênh (Website, Zalo OA, Zalo Personal).
          Tự động hóa chăm sóc khách hàng, tăng doanh số với AI.
        </p>
        <div className="flex justify-center space-x-4">
          <Link href="/register">
            <Button size="lg" className="text-lg px-8">
              Bắt đầu miễn phí
            </Button>
          </Link>
          <Link href="#demo">
            <Button size="lg" variant="outline" className="text-lg px-8">
              Xem Demo
            </Button>
          </Link>
        </div>
      </section>

      {/* Features */}
      <section id="features" className="container mx-auto px-4 py-20">
        <h2 className="text-4xl font-bold text-center mb-12">Tính năng nổi bật</h2>
        <div className="grid md:grid-cols-3 gap-8">
          <FeatureCard
            icon="🤖"
            title="AI Chatbot thông minh"
            description="RAG (Retrieval-Augmented Generation) trả lời chính xác từ tài liệu doanh nghiệp"
          />
          <FeatureCard
            icon="💬"
            title="Tích hợp đa kênh"
            description="Website Widget, Zalo OA, Zalo Personal - Quản lý tập trung"
          />
          <FeatureCard
            icon="📊"
            title="Analytics & Insights"
            description="AI phân tích dữ liệu, dự đoán xu hướng, đề xuất chiến lược"
          />
          <FeatureCard
            icon="👥"
            title="CRM tích hợp"
            description="Quản lý khách hàng, sản phẩm, đơn hàng trong một hệ thống"
          />
          <FeatureCard
            icon="🔐"
            title="Bảo mật cao"
            description="JWT Authentication, mã hóa dữ liệu, phân quyền chi tiết"
          />
          <FeatureCard
            icon="⚡"
            title="Tự động hóa"
            description="AI tự động trả lời, tạo đơn hàng, phân loại khách hàng"
          />
        </div>
      </section>

      {/* CTA */}
      <section className="bg-blue-600 text-white py-20">
        <div className="container mx-auto px-4 text-center">
          <h2 className="text-4xl font-bold mb-6">Sẵn sàng bắt đầu?</h2>
          <p className="text-xl mb-8">Dùng thử miễn phí 14 ngày, không cần thẻ tín dụng</p>
          <Link href="/register">
            <Button size="lg" variant="secondary" className="text-lg px-8">
              Đăng ký ngay
            </Button>
          </Link>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-gray-900 text-gray-400 py-12">
        <div className="container mx-auto px-4 text-center">
          <p>&copy; 2025 AI Agent for Business. All rights reserved.</p>
        </div>
      </footer>
    </div>
  )
}

function FeatureCard({ icon, title, description }: { icon: string; title: string; description: string }) {
  return (
    <div className="bg-white p-6 rounded-lg shadow-lg hover:shadow-xl transition-shadow">
      <div className="text-4xl mb-4">{icon}</div>
      <h3 className="text-xl font-bold mb-2">{title}</h3>
      <p className="text-gray-600">{description}</p>
    </div>
  )
}

