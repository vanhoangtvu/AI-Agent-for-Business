"use client"

import { Card, CardContent, CardHeader } from "@/components/ui/card"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Search } from "lucide-react"

export default function CustomerOrdersPage() {
  const orders = [
    {
      id: 1,
      orderCode: "ORD-1001",
      date: "2024-10-15",
      status: "DELIVERED",
      paymentStatus: "PAID",
      items: 3,
      total: 234.56,
    },
    {
      id: 2,
      orderCode: "ORD-1002",
      date: "2024-10-16",
      status: "SHIPPED",
      paymentStatus: "PAID",
      items: 2,
      total: 156.78,
    },
    {
      id: 3,
      orderCode: "ORD-1003",
      date: "2024-10-17",
      status: "PROCESSING",
      paymentStatus: "PAID",
      items: 1,
      total: 89.99,
    },
    {
      id: 4,
      orderCode: "ORD-1004",
      date: "2024-10-18",
      status: "PENDING",
      paymentStatus: "PENDING",
      items: 4,
      total: 345.67,
    },
  ]

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold">My Orders</h1>
        <p className="text-muted-foreground mt-2">
          View and track all your orders
        </p>
      </div>

      <Card>
        <CardHeader>
          <div className="flex items-center gap-4">
            <div className="relative flex-1">
              <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />
              <Input placeholder="Search orders..." className="pl-9" />
            </div>
          </div>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Order Code</TableHead>
                <TableHead>Date</TableHead>
                <TableHead>Status</TableHead>
                <TableHead>Payment</TableHead>
                <TableHead>Items</TableHead>
                <TableHead>Total</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {orders.map((order) => (
                <TableRow key={order.id}>
                  <TableCell className="font-medium">{order.orderCode}</TableCell>
                  <TableCell>{order.date}</TableCell>
                  <TableCell>
                    <Badge
                      variant={
                        order.status === "DELIVERED"
                          ? "success"
                          : order.status === "SHIPPED"
                          ? "default"
                          : order.status === "PROCESSING"
                          ? "secondary"
                          : "warning"
                      }
                    >
                      {order.status}
                    </Badge>
                  </TableCell>
                  <TableCell>
                    <Badge variant={order.paymentStatus === "PAID" ? "success" : "warning"}>
                      {order.paymentStatus}
                    </Badge>
                  </TableCell>
                  <TableCell>{order.items} items</TableCell>
                  <TableCell className="font-semibold">${order.total.toFixed(2)}</TableCell>
                  <TableCell>
                    <Button variant="ghost" size="sm">
                      View Details
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </div>
  )
}

