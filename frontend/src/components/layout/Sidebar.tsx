"use client"

import Link from "next/link"
import { usePathname } from "next/navigation"
import { cn } from "@/lib/utils"
import { useAuthStore } from "@/store/authStore"
import { useUIStore } from "@/store/uiStore"
import {
  LayoutDashboard,
  Users,
  Package,
  ShoppingCart,
  MessageSquare,
  BarChart3,
  Bot,
  Settings,
  FileText,
  Building2,
  UserCircle,
  Zap,
} from "lucide-react"
import { Button } from "@/components/ui/button"
import { ScrollArea } from "@/components/ui/scroll-area"

interface NavItem {
  title: string
  href: string
  icon: React.ComponentType<{ className?: string }>
  role?: string[]
}

const navItems: NavItem[] = [
  {
    title: "Dashboard",
    href: "/admin",
    icon: LayoutDashboard,
  },
  {
    title: "Businesses",
    href: "/admin/businesses",
    icon: Building2,
    role: ["ADMIN"],
  },
  {
    title: "Users",
    href: "/admin/users",
    icon: UserCircle,
    role: ["ADMIN"],
  },
  {
    title: "System Stats",
    href: "/admin/stats",
    icon: BarChart3,
    role: ["ADMIN"],
  },
  {
    title: "Customers",
    href: "/business/customers",
    icon: Users,
    role: ["BUSINESS"],
  },
  {
    title: "Products",
    href: "/business/products",
    icon: Package,
    role: ["BUSINESS"],
  },
  {
    title: "Orders",
    href: "/business/orders",
    icon: ShoppingCart,
    role: ["BUSINESS"],
  },
  {
    title: "Conversations",
    href: "/business/conversations",
    icon: MessageSquare,
    role: ["BUSINESS"],
  },
  {
    title: "Analytics",
    href: "/business/analytics",
    icon: BarChart3,
    role: ["BUSINESS"],
  },
  {
    title: "Chatbot",
    href: "/business/chatbot",
    icon: Bot,
    role: ["BUSINESS"],
  },
  {
    title: "Zalo Integration",
    href: "/business/zalo",
    icon: Zap,
    role: ["BUSINESS"],
  },
  {
    title: "Documents",
    href: "/business/documents",
    icon: FileText,
    role: ["BUSINESS"],
  },
  {
    title: "My Orders",
    href: "/customer/orders",
    icon: ShoppingCart,
    role: ["CUSTOMER"],
  },
  {
    title: "Profile",
    href: "/customer/profile",
    icon: UserCircle,
    role: ["CUSTOMER"],
  },
  {
    title: "Settings",
    href: "/customer/profile",
    icon: Settings,
    role: ["CUSTOMER"],
  },
]

export function Sidebar() {
  const pathname = usePathname()
  const user = useAuthStore((state) => state.user)
  const sidebarOpen = useUIStore((state) => state.sidebarOpen)

  const filteredNavItems = navItems.filter((item) => {
    if (!item.role) return true
    return user?.role && item.role.includes(user.role)
  })

  return (
    <>
      {/* Mobile overlay */}
      {sidebarOpen && (
        <div
          className="fixed inset-0 z-40 bg-black/50 md:hidden"
          onClick={() => useUIStore.getState().toggleSidebar()}
        />
      )}

      {/* Sidebar */}
      <aside
        className={cn(
          "fixed left-0 top-16 z-40 h-[calc(100vh-4rem)] w-64 border-r bg-background transition-transform duration-300 md:translate-x-0",
          sidebarOpen ? "translate-x-0" : "-translate-x-full"
        )}
      >
        <ScrollArea className="h-full py-6">
          <nav className="flex flex-col gap-2 px-4">
            {filteredNavItems.map((item) => {
              const Icon = item.icon
              const isActive = pathname === item.href || pathname?.startsWith(item.href + "/")
              
              return (
                <Link key={item.href} href={item.href}>
                  <Button
                    variant={isActive ? "secondary" : "ghost"}
                    className={cn(
                      "w-full justify-start gap-3",
                      isActive && "bg-secondary font-medium"
                    )}
                  >
                    <Icon className="h-4 w-4" />
                    {item.title}
                  </Button>
                </Link>
              )
            })}
          </nav>
        </ScrollArea>
      </aside>
    </>
  )
}

