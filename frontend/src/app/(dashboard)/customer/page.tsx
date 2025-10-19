"use client"

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { ShoppingCart, Package, DollarSign, Clock } from "lucide-react"

export default function CustomerDashboard() {
  const stats = [
    {
      title: "Total Orders",
      value: "12",
      description: "All time orders",
      icon: ShoppingCart,
      color: "text-blue-600",
    },
    {
      title: "Delivered",
      value: "9",
      description: "Successfully delivered",
      icon: Package,
      color: "text-green-600",
    },
    {
      title: "Total Spent",
      value: "$1,234.56",
      description: "Lifetime value",
      icon: DollarSign,
      color: "text-purple-600",
    },
    {
      title: "Pending",
      value: "2",
      description: "Orders in progress",
      icon: Clock,
      color: "text-orange-600",
    },
  ]

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold">My Dashboard</h1>
        <p className="text-muted-foreground mt-2">
          Welcome back! Here's your order overview.
        </p>
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
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
                <p className="text-xs text-muted-foreground mt-1">{stat.description}</p>
              </CardContent>
            </Card>
          )
        })}
      </div>

      <div className="grid gap-4 md:grid-cols-2">
        <Card>
          <CardHeader>
            <CardTitle>Recent Orders</CardTitle>
            <CardDescription>Your latest purchases</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {[1, 2, 3].map((i) => (
                <div key={i} className="flex items-center justify-between border-b pb-3">
                  <div>
                    <p className="text-sm font-medium">Order #{1000 + i}</p>
                    <p className="text-xs text-muted-foreground">{i} day ago</p>
                  </div>
                  <div className="text-right">
                    <p className="text-sm font-semibold">${(Math.random() * 200 + 50).toFixed(2)}</p>
                    <p className="text-xs text-green-600">Delivered</p>
                  </div>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Order Status</CardTitle>
            <CardDescription>Current order distribution</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {[
                { status: "Pending", count: 1, color: "bg-yellow-500" },
                { status: "Processing", count: 1, color: "bg-blue-500" },
                { status: "Shipped", count: 1, color: "bg-purple-500" },
                { status: "Delivered", count: 9, color: "bg-green-500" },
              ].map((item) => (
                <div key={item.status} className="flex items-center justify-between">
                  <div className="flex items-center gap-3">
                    <div className={`h-3 w-3 rounded-full ${item.color}`}></div>
                    <span className="text-sm">{item.status}</span>
                  </div>
                  <span className="font-semibold">{item.count}</span>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}

