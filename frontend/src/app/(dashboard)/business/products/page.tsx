"use client"

import { Card, CardContent, CardHeader } from "@/components/ui/card"
import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Search, Plus, Package } from "lucide-react"
import { useProducts } from "@/hooks/useProducts"
import { Skeleton } from "@/components/ui/skeleton"
import Image from "next/image"

export default function ProductsPage() {
  const { products, isLoading } = useProducts()

  if (isLoading) {
    return (
      <div className="space-y-6">
        <Skeleton className="h-12 w-64" />
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
          {[1, 2, 3, 4, 5, 6, 7, 8].map((i) => (
            <Skeleton key={i} className="h-64" />
          ))}
        </div>
      </div>
    )
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold">Products</h1>
          <p className="text-muted-foreground mt-2">
            Manage your product catalog
          </p>
        </div>
        <Button>
          <Plus className="mr-2 h-4 w-4" />
          Add Product
        </Button>
      </div>

      <Card>
        <CardHeader>
          <div className="flex items-center gap-4">
            <div className="relative flex-1">
              <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />
              <Input placeholder="Search products..." className="pl-9" />
            </div>
          </div>
        </CardHeader>
        <CardContent>
          {products && products.length > 0 ? (
            <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
              {products.map((product: any) => (
                <Card key={product.id} className="overflow-hidden">
                  <div className="relative h-48 bg-gray-100 flex items-center justify-center">
                    {product.imageUrl ? (
                      <Image
                        src={product.imageUrl}
                        alt={product.name}
                        fill
                        className="object-cover"
                      />
                    ) : (
                      <Package className="h-12 w-12 text-gray-400" />
                    )}
                    {!product.isActive && (
                      <Badge className="absolute top-2 right-2" variant="destructive">
                        Inactive
                      </Badge>
                    )}
                  </div>
                  <CardContent className="p-4">
                    <h3 className="font-semibold truncate">{product.name}</h3>
                    <p className="text-sm text-muted-foreground truncate mt-1">
                      {product.category || "Uncategorized"}
                    </p>
                    <div className="flex items-center justify-between mt-3">
                      <div>
                        <p className="text-lg font-bold">${product.price.toFixed(2)}</p>
                        {product.salePrice && (
                          <p className="text-xs text-muted-foreground line-through">
                            ${product.salePrice.toFixed(2)}
                          </p>
                        )}
                      </div>
                      <Badge variant="outline">Stock: {product.stockQuantity}</Badge>
                    </div>
                    <Button variant="outline" className="w-full mt-3" size="sm">
                      Edit
                    </Button>
                  </CardContent>
                </Card>
              ))}
            </div>
          ) : (
            <div className="text-center py-12">
              <Package className="h-12 w-12 text-gray-400 mx-auto mb-4" />
              <p className="text-muted-foreground">No products found</p>
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  )
}

