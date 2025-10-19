"use client"

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { useAnalytics, useRFMAnalysis } from "@/hooks/useAnalytics"
import { Skeleton } from "@/components/ui/skeleton"
import { TrendingUp, TrendingDown, DollarSign, ShoppingCart, Users, Package } from "lucide-react"

export default function AnalyticsPage() {
  const { data: analytics, isLoading } = useAnalytics()
  const { data: rfmData } = useRFMAnalysis()

  if (isLoading) {
    return (
      <div className="space-y-6">
        <Skeleton className="h-12 w-64" />
        <div className="grid gap-4 md:grid-cols-4">
          {[1, 2, 3, 4].map((i) => (
            <Skeleton key={i} className="h-32" />
          ))}
        </div>
      </div>
    )
  }

  const stats = [
    {
      title: "Total Revenue",
      value: `$${analytics?.data?.totalRevenue || 0}`,
      change: "+23.1%",
      trend: "up",
      icon: DollarSign,
    },
    {
      title: "Total Orders",
      value: analytics?.data?.totalOrders || 0,
      change: "+12.5%",
      trend: "up",
      icon: ShoppingCart,
    },
    {
      title: "Total Customers",
      value: analytics?.data?.totalCustomers || 0,
      change: "+8.2%",
      trend: "up",
      icon: Users,
    },
    {
      title: "Products Sold",
      value: analytics?.data?.productsSold || 0,
      change: "-2.4%",
      trend: "down",
      icon: Package,
    },
  ]

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold">Analytics</h1>
        <p className="text-muted-foreground mt-2">
          Business insights and AI-powered analytics
        </p>
      </div>

      <div className="grid gap-4 md:grid-cols-4">
        {stats.map((stat) => {
          const Icon = stat.icon
          return (
            <Card key={stat.title}>
              <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
                <CardTitle className="text-sm font-medium">{stat.title}</CardTitle>
                <Icon className="h-4 w-4 text-muted-foreground" />
              </CardHeader>
              <CardContent>
                <div className="text-2xl font-bold">{stat.value}</div>
                <p
                  className={`text-xs flex items-center gap-1 mt-1 ${
                    stat.trend === "up" ? "text-green-600" : "text-red-600"
                  }`}
                >
                  {stat.trend === "up" ? (
                    <TrendingUp className="h-3 w-3" />
                  ) : (
                    <TrendingDown className="h-3 w-3" />
                  )}
                  {stat.change} from last month
                </p>
              </CardContent>
            </Card>
          )
        })}
      </div>

      <Tabs defaultValue="overview" className="space-y-4">
        <TabsList>
          <TabsTrigger value="overview">Overview</TabsTrigger>
          <TabsTrigger value="customers">Customers</TabsTrigger>
          <TabsTrigger value="products">Products</TabsTrigger>
          <TabsTrigger value="ai-insights">AI Insights</TabsTrigger>
        </TabsList>

        <TabsContent value="overview" className="space-y-4">
          <div className="grid gap-4 md:grid-cols-2">
            <Card>
              <CardHeader>
                <CardTitle>Revenue Overview</CardTitle>
                <CardDescription>Monthly revenue trend</CardDescription>
              </CardHeader>
              <CardContent>
                <div className="h-[300px] flex items-center justify-center text-muted-foreground">
                  Chart placeholder (use Recharts/Chart.js)
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
                    { status: "Pending", count: 23, color: "bg-yellow-500" },
                    { status: "Processing", count: 45, color: "bg-blue-500" },
                    { status: "Shipped", count: 67, color: "bg-purple-500" },
                    { status: "Delivered", count: 234, color: "bg-green-500" },
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
        </TabsContent>

        <TabsContent value="customers" className="space-y-4">
          <div className="grid gap-4 md:grid-cols-2">
            <Card>
              <CardHeader>
                <CardTitle>Customer Segments</CardTitle>
                <CardDescription>RFM Analysis</CardDescription>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  {rfmData?.map((segment: any, i: number) => (
                    <div key={i} className="flex items-center justify-between border-b pb-3">
                      <div>
                        <p className="font-medium">{segment.segment}</p>
                        <p className="text-sm text-muted-foreground">{segment.description}</p>
                      </div>
                      <span className="font-semibold">{segment.count} customers</span>
                    </div>
                  )) || (
                    <>
                      {[
                        { segment: "VIP", count: 45 },
                        { segment: "Regular", count: 123 },
                        { segment: "New", count: 67 },
                        { segment: "At Risk", count: 23 },
                      ].map((item) => (
                        <div key={item.segment} className="flex items-center justify-between border-b pb-3">
                          <span className="font-medium">{item.segment}</span>
                          <span className="font-semibold">{item.count} customers</span>
                        </div>
                      ))}
                    </>
                  )}
                </div>
              </CardContent>
            </Card>

            <Card>
              <CardHeader>
                <CardTitle>Customer Growth</CardTitle>
                <CardDescription>New customers over time</CardDescription>
              </CardHeader>
              <CardContent>
                <div className="h-[300px] flex items-center justify-center text-muted-foreground">
                  Chart placeholder
                </div>
              </CardContent>
            </Card>
          </div>
        </TabsContent>

        <TabsContent value="products" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>Top Performing Products</CardTitle>
              <CardDescription>Best sellers this month</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {[1, 2, 3, 4, 5].map((i) => (
                  <div key={i} className="flex items-center justify-between border-b pb-3">
                    <div>
                      <p className="font-medium">Product {i}</p>
                      <p className="text-sm text-muted-foreground">{50 + i * 10} units sold</p>
                    </div>
                    <span className="font-semibold">${(1000 + i * 500).toLocaleString()}</span>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="ai-insights" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>AI-Powered Insights</CardTitle>
              <CardDescription>Recommendations from AI analysis</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {[
                  {
                    title: "Optimize Inventory",
                    description: "Product A is running low. Consider reordering 50 units.",
                    priority: "high",
                  },
                  {
                    title: "Customer Retention",
                    description: "23 customers at risk of churning. Consider sending offers.",
                    priority: "medium",
                  },
                  {
                    title: "Pricing Strategy",
                    description: "Product B could increase price by 15% based on demand.",
                    priority: "low",
                  },
                ].map((insight, i) => (
                  <div key={i} className="border-l-4 border-primary pl-4 py-2">
                    <div className="flex items-start justify-between mb-1">
                      <p className="font-medium">{insight.title}</p>
                      <span
                        className={`text-xs px-2 py-1 rounded ${
                          insight.priority === "high"
                            ? "bg-red-100 text-red-700"
                            : insight.priority === "medium"
                            ? "bg-yellow-100 text-yellow-700"
                            : "bg-green-100 text-green-700"
                        }`}
                      >
                        {insight.priority}
                      </span>
                    </div>
                    <p className="text-sm text-muted-foreground">{insight.description}</p>
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

