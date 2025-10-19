"use client"

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { ScrollArea } from "@/components/ui/scroll-area"
import { Search, Send } from "lucide-react"
import { useConversations } from "@/hooks/useConversations"
import { Skeleton } from "@/components/ui/skeleton"
import { useState } from "react"

export default function ConversationsPage() {
  const { conversations, isLoading } = useConversations()
  const [selectedConv, setSelectedConv] = useState<any>(null)

  if (isLoading) {
    return (
      <div className="space-y-6">
        <Skeleton className="h-12 w-64" />
        <Skeleton className="h-[600px]" />
      </div>
    )
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold">Conversations</h1>
        <p className="text-muted-foreground mt-2">
          Manage customer conversations across all channels
        </p>
      </div>

      <div className="grid gap-4 md:grid-cols-3">
        {/* Conversation List */}
        <Card className="md:col-span-1">
          <CardHeader>
            <div className="relative">
              <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />
              <Input placeholder="Search..." className="pl-9" />
            </div>
          </CardHeader>
          <CardContent className="p-0">
            <ScrollArea className="h-[600px]">
              {conversations && conversations.length > 0 ? (
                conversations.map((conv: any) => (
                  <div
                    key={conv.id}
                    className={`p-4 border-b cursor-pointer hover:bg-accent ${
                      selectedConv?.id === conv.id ? "bg-accent" : ""
                    }`}
                    onClick={() => setSelectedConv(conv)}
                  >
                    <div className="flex items-start justify-between mb-2">
                      <p className="font-medium">Customer #{conv.customerId}</p>
                      {conv.unreadCount > 0 && (
                        <Badge className="h-5 w-5 rounded-full p-0 flex items-center justify-center text-xs">
                          {conv.unreadCount}
                        </Badge>
                      )}
                    </div>
                    <div className="flex items-center gap-2 mb-1">
                      <Badge variant="outline" className="text-xs">
                        {conv.channel}
                      </Badge>
                      <Badge
                        variant={
                          conv.status === "OPEN"
                            ? "success"
                            : conv.status === "PENDING"
                            ? "warning"
                            : "secondary"
                        }
                        className="text-xs"
                      >
                        {conv.status}
                      </Badge>
                    </div>
                    <p className="text-xs text-muted-foreground">
                      {new Date(conv.lastMessageAt).toLocaleString()}
                    </p>
                  </div>
                ))
              ) : (
                <div className="p-8 text-center text-muted-foreground">
                  No conversations
                </div>
              )}
            </ScrollArea>
          </CardContent>
        </Card>

        {/* Message Thread */}
        <Card className="md:col-span-2">
          {selectedConv ? (
            <>
              <CardHeader className="border-b">
                <div className="flex items-center justify-between">
                  <CardTitle>Customer #{selectedConv.customerId}</CardTitle>
                  <div className="flex items-center gap-2">
                    <Badge variant="outline">{selectedConv.channel}</Badge>
                    <Badge variant={selectedConv.aiMode === "AUTO" ? "success" : "warning"}>
                      AI: {selectedConv.aiMode}
                    </Badge>
                  </div>
                </div>
              </CardHeader>
              <ScrollArea className="h-[500px] p-4">
                <div className="space-y-4">
                  {[1, 2, 3, 4, 5].map((i) => (
                    <div
                      key={i}
                      className={`flex ${i % 2 === 0 ? "justify-end" : "justify-start"}`}
                    >
                      <div
                        className={`max-w-[70%] rounded-lg p-3 ${
                          i % 2 === 0
                            ? "bg-primary text-primary-foreground"
                            : "bg-muted"
                        }`}
                      >
                        <p className="text-sm">
                          This is a sample message in the conversation.
                        </p>
                        <p className="text-xs opacity-70 mt-1">
                          {new Date().toLocaleTimeString()}
                        </p>
                      </div>
                    </div>
                  ))}
                </div>
              </ScrollArea>
              <div className="border-t p-4">
                <div className="flex gap-2">
                  <Input placeholder="Type a message..." />
                  <Button size="icon">
                    <Send className="h-4 w-4" />
                  </Button>
                </div>
              </div>
            </>
          ) : (
            <div className="h-[600px] flex items-center justify-center text-muted-foreground">
              Select a conversation to view messages
            </div>
          )}
        </Card>
      </div>
    </div>
  )
}

