"use client"

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Building2, Users, TrendingUp, Activity } from "lucide-react"

export default function AdminDashboard() {
  const stats = [
    {
      title: "Total Businesses",
      value: "234",
      change: "+12.5%",
      icon: Building2,
      color: "text-blue-600",
    },
    {
      title: "Total Users",
      value: "1,432",
      change: "+8.2%",
      icon: Users,
      color: "text-green-600",
    },
    {
      title: "System Revenue",
      value: "$45,231",
      change: "+23.1%",
      icon: TrendingUp,
      color: "text-purple-600",
    },
    {
      title: "Active Sessions",
      value: "89",
      change: "+5.4%",
      icon: Activity,
      color: "text-orange-600",
    },
  ]

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold">Admin Dashboard</h1>
        <p className="text-muted-foreground mt-2">
          System overview and management
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
                <p className="text-xs text-green-600">{stat.change} from last month</p>
              </CardContent>
            </Card>
          )
        })}
      </div>

      <div className="grid gap-4 md:grid-cols-2">
        <Card>
          <CardHeader>
            <CardTitle>Recent Activities</CardTitle>
            <CardDescription>Latest system events</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {[1, 2, 3, 4, 5].map((i) => (
                <div key={i} className="flex items-center gap-4">
                  <div className="h-2 w-2 rounded-full bg-blue-600"></div>
                  <div className="flex-1">
                    <p className="text-sm font-medium">New business registered</p>
                    <p className="text-xs text-muted-foreground">{i} hour ago</p>
                  </div>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>System Health</CardTitle>
            <CardDescription>Service status monitoring</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {["API Server", "Database", "Redis Cache", "AI Service", "WebSocket"].map((service) => (
                <div key={service} className="flex items-center justify-between">
                  <span className="text-sm">{service}</span>
                  <span className="inline-flex items-center rounded-full px-2 py-0.5 text-xs font-medium bg-green-100 text-green-700">
                    Healthy
                  </span>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}

