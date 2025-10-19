"use client"

import { Header } from "./Header"
import { Sidebar } from "./Sidebar"
import { useAuthStore } from "@/store/authStore"
import { useRouter } from "next/navigation"
import { useEffect } from "react"

export function DashboardLayout({ children }: { children: React.ReactNode }) {
  const isAuthenticated = useAuthStore((state) => state.isAuthenticated)
  const router = useRouter()

  useEffect(() => {
    if (!isAuthenticated) {
      router.push("/login")
    }
  }, [isAuthenticated, router])

  if (!isAuthenticated) {
    return null
  }

  return (
    <div className="relative min-h-screen">
      <Header />
      <div className="flex">
        <Sidebar />
        <main className="flex-1 md:ml-64 p-6 bg-gray-50 dark:bg-gray-900 min-h-[calc(100vh-4rem)]">
          {children}
        </main>
      </div>
    </div>
  )
}

