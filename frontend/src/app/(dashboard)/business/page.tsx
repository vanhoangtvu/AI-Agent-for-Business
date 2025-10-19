"use client"

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Users, Package, ShoppingCart, MessageSquare, TrendingUp, DollarSign } from "lucide-react"
import { useAnalytics } from "@/hooks/useAnalytics"
import { Skeleton } from "@/components/ui/skeleton"

export default function BusinessDashboard() {
  const { data: analytics, isLoading } = useAnalytics()

  const stats = [
    {
      title: "Total Customers",
      value: analytics?.data?.totalCustomers || "0",
      change: "+12.5%",
      icon: Users,
      color: "text-blue-600",
    },
    {
      title: "Total Products",
      value: analytics?.data?.totalProducts || "0",
      change: "+5.2%",
      icon: Package,
      color: "text-green-600",
    },
    {
      title: "Total Orders",
      value: analytics?.data?.totalOrders || "0",
      change: "+18.3%",
      icon: ShoppingCart,
      color: "text-purple-600",
    },
    {
      title: "Conversations",
      value: analytics?.data?.activeConversations || "0",
      change: "+3.1%",
      icon: MessageSquare,
      color: "text-orange-600",
    },
    {
      title: "Revenue",
      value: `$${analytics?.data?.totalRevenue || "0"}`,
      change: "+23.1%",
      icon: DollarSign,
      color: "text-emerald-600",
    },
    {
      title: "Avg Order Value",
      value: `$${analytics?.data?.averageOrderValue || "0"}`,
      change: "+8.7%",
      icon: TrendingUp,
      color: "text-pink-600",
    },
  ]

  if (isLoading) {
    return (
      <div className="space-y-6">
        <Skeleton className="h-12 w-64" />
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
          {[1, 2, 3, 4, 5, 6].map((i) => (
            <Skeleton key={i} className="h-32" />
          ))}
        </div>
      </div>
    )
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold">Business Dashboard</h1>
        <p className="text-muted-foreground mt-2">
          Welcome back! Here's your business overview.
        </p>
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
        {stats.map((stat) => {
          const Icon = stat.icon
          return (
            <Card key={stat.title}>
              <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                <CardTitle className="text-sm font-medium">{stat.title}</CardTitle>
                <Icon className={`h-4 w-4 ${stat.color}`} />
              </CardHeader>
              <CardContent>
                <div className="text-2xl font-bold">{stat.value}</div>
                <p className="text-xs text-green-600">{stat.change} from last month</p>
              </CardContent>
            </Card>
          )
        })}
      </div>

      <div className="grid gap-4 md:grid-cols-2">
        <Card>
          <CardHeader>
            <CardTitle>Recent Orders</CardTitle>
            <CardDescription>Latest customer orders</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {[1, 2, 3, 4, 5].map((i) => (
                <div key={i} className="flex items-center justify-between border-b pb-3">
                  <div>
                    <p className="text-sm font-medium">Order #{1000 + i}</p>
                    <p className="text-xs text-muted-foreground">Customer {i}</p>
                  </div>
                  <div className="text-right">
                    <p className="text-sm font-semibold">${(Math.random() * 500 + 100).toFixed(2)}</p>
                    <p className="text-xs text-muted-foreground">{i}h ago</p>
                  </div>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Recent Conversations</CardTitle>
            <CardDescription>Latest customer messages</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {[1, 2, 3, 4, 5].map((i) => (
                <div key={i} className="flex items-center gap-3 border-b pb-3">
                  <div className="h-10 w-10 rounded-full bg-primary/10 flex items-center justify-center">
                    <span className="text-sm font-medium">C{i}</span>
                  </div>
                  <div className="flex-1">
                    <p className="text-sm font-medium">Customer {i}</p>
                    <p className="text-xs text-muted-foreground">Last message {i}m ago</p>
                  </div>
                  {i <= 2 && (
                    <span className="h-2 w-2 rounded-full bg-blue-600"></span>
                  )}
                </div>
              ))}
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}
