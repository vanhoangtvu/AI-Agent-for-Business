"use client"

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Search, Plus } from "lucide-react"

export default function BusinessesPage() {
  const businesses = [
    {
      id: 1,
      name: "ABC Shop",
      email: "abc@shop.com",
      plan: "PREMIUM",
      status: "ACTIVE",
      customers: 156,
      revenue: "$12,450",
      createdAt: "2024-01-15",
    },
    {
      id: 2,
      name: "XYZ Store",
      email: "xyz@store.com",
      plan: "BASIC",
      status: "ACTIVE",
      customers: 89,
      revenue: "$5,230",
      createdAt: "2024-02-20",
    },
    {
      id: 3,
      name: "Demo Business",
      email: "demo@business.com",
      plan: "FREE",
      status: "SUSPENDED",
      customers: 12,
      revenue: "$0",
      createdAt: "2024-03-10",
    },
  ]

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold">Businesses</h1>
          <p className="text-muted-foreground mt-2">
            Manage all registered businesses
          </p>
        </div>
        <Button>
          <Plus className="mr-2 h-4 w-4" />
          Add Business
        </Button>
      </div>

      <Card>
        <CardHeader>
          <div className="flex items-center gap-4">
            <div className="relative flex-1">
              <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />
              <Input placeholder="Search businesses..." className="pl-9" />
            </div>
          </div>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Business Name</TableHead>
                <TableHead>Email</TableHead>
                <TableHead>Plan</TableHead>
                <TableHead>Status</TableHead>
                <TableHead>Customers</TableHead>
                <TableHead>Revenue</TableHead>
                <TableHead>Created</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {businesses.map((business) => (
                <TableRow key={business.id}>
                  <TableCell className="font-medium">{business.name}</TableCell>
                  <TableCell>{business.email}</TableCell>
                  <TableCell>
                    <Badge variant={business.plan === "PREMIUM" ? "default" : "secondary"}>
                      {business.plan}
                    </Badge>
                  </TableCell>
                  <TableCell>
                    <Badge variant={business.status === "ACTIVE" ? "success" : "destructive"}>
                      {business.status}
                    </Badge>
                  </TableCell>
                  <TableCell>{business.customers}</TableCell>
                  <TableCell>{business.revenue}</TableCell>
                  <TableCell>{business.createdAt}</TableCell>
                  <TableCell>
                    <Button variant="ghost" size="sm">
                      View
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

