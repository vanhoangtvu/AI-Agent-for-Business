"use client"

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Badge } from "@/components/ui/badge"

export default function StatsPage() {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold">System Statistics</h1>
        <p className="text-muted-foreground mt-2">
          Comprehensive system analytics and metrics
        </p>
      </div>

      <Tabs defaultValue="overview" className="space-y-4">
        <TabsList>
          <TabsTrigger value="overview">Overview</TabsTrigger>
          <TabsTrigger value="revenue">Revenue</TabsTrigger>
          <TabsTrigger value="usage">Usage</TabsTrigger>
          <TabsTrigger value="performance">Performance</TabsTrigger>
        </TabsList>

        <TabsContent value="overview" className="space-y-4">
          <div className="grid gap-4 md:grid-cols-3">
            <Card>
              <CardHeader>
                <CardTitle>Total Businesses</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="text-3xl font-bold">234</div>
                <p className="text-sm text-muted-foreground mt-1">+12 this month</p>
              </CardContent>
            </Card>

            <Card>
              <CardHeader>
                <CardTitle>Total Users</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="text-3xl font-bold">1,432</div>
                <p className="text-sm text-muted-foreground mt-1">+89 this month</p>
              </CardContent>
            </Card>

            <Card>
              <CardHeader>
                <CardTitle>Active Subscriptions</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="text-3xl font-bold">189</div>
                <p className="text-sm text-muted-foreground mt-1">80.8% of businesses</p>
              </CardContent>
            </Card>
          </div>

          <Card>
            <CardHeader>
              <CardTitle>Subscription Distribution</CardTitle>
              <CardDescription>Current plan breakdown</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {[
                  { plan: "FREE", count: 45, percentage: 19.2, color: "bg-gray-500" },
                  { plan: "BASIC", count: 98, percentage: 41.9, color: "bg-blue-500" },
                  { plan: "PREMIUM", count: 91, percentage: 38.9, color: "bg-purple-500" },
                ].map((item) => (
                  <div key={item.plan} className="space-y-2">
                    <div className="flex items-center justify-between">
                      <div className="flex items-center gap-2">
                        <div className={`h-3 w-3 rounded-full ${item.color}`}></div>
                        <span className="font-medium">{item.plan}</span>
                      </div>
                      <span className="text-sm text-muted-foreground">
                        {item.count} ({item.percentage}%)
                      </span>
                    </div>
                    <div className="h-2 rounded-full bg-gray-100">
                      <div
                        className={`h-2 rounded-full ${item.color}`}
                        style={{ width: `${item.percentage}%` }}
                      ></div>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="revenue" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>Revenue Overview</CardTitle>
              <CardDescription>Monthly recurring revenue</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="text-4xl font-bold">$45,231</div>
              <p className="text-sm text-green-600 mt-2">+23.1% from last month</p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader>
              <CardTitle>Revenue by Plan</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {[
                  { plan: "BASIC", revenue: "$9,800", mrr: "$100/mo" },
                  { plan: "PREMIUM", revenue: "$35,431", mrr: "$389/mo" },
                ].map((item) => (
                  <div key={item.plan} className="flex items-center justify-between border-b pb-3">
                    <div>
                      <Badge>{item.plan}</Badge>
                      <p className="text-sm text-muted-foreground mt-1">{item.mrr}</p>
                    </div>
                    <span className="text-lg font-semibold">{item.revenue}</span>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="usage" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>API Usage</CardTitle>
              <CardDescription>Last 30 days</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {[
                  { endpoint: "/chatbot/chat", calls: "125,430", avgResponse: "245ms" },
                  { endpoint: "/customers/*", calls: "89,234", avgResponse: "120ms" },
                  { endpoint: "/products/*", calls: "67,891", avgResponse: "98ms" },
                  { endpoint: "/orders/*", calls: "45,123", avgResponse: "156ms" },
                ].map((item) => (
                  <div key={item.endpoint} className="flex items-center justify-between border-b pb-3">
                    <div>
                      <p className="font-mono text-sm">{item.endpoint}</p>
                      <p className="text-xs text-muted-foreground mt-1">
                        Avg: {item.avgResponse}
                      </p>
                    </div>
                    <span className="font-semibold">{item.calls} calls</span>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="performance" className="space-y-4">
          <div className="grid gap-4 md:grid-cols-2">
            <Card>
              <CardHeader>
                <CardTitle>System Uptime</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="text-3xl font-bold">99.98%</div>
                <p className="text-sm text-muted-foreground mt-1">Last 30 days</p>
              </CardContent>
            </Card>

            <Card>
              <CardHeader>
                <CardTitle>Avg Response Time</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="text-3xl font-bold">142ms</div>
                <p className="text-sm text-green-600 mt-1">-12ms from last month</p>
              </CardContent>
            </Card>
          </div>

          <Card>
            <CardHeader>
              <CardTitle>Service Health</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-3">
                {[
                  { service: "API Gateway", status: "Healthy", uptime: "100%" },
                  { service: "Database", status: "Healthy", uptime: "99.99%" },
                  { service: "Redis Cache", status: "Healthy", uptime: "100%" },
                  { service: "AI Service", status: "Healthy", uptime: "99.95%" },
                  { service: "WebSocket Server", status: "Healthy", uptime: "99.98%" },
                ].map((item) => (
                  <div key={item.service} className="flex items-center justify-between">
                    <span>{item.service}</span>
                    <div className="flex items-center gap-4">
                      <span className="text-sm text-muted-foreground">{item.uptime}</span>
                      <Badge variant="success">{item.status}</Badge>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  )
}

