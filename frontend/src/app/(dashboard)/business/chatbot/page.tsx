"use client"

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Textarea } from "@/components/ui/textarea"
import { Switch } from "@/components/ui/switch"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Badge } from "@/components/ui/badge"
import { useChatbotConfig, useDocuments } from "@/hooks/useChatbot"
import { Skeleton } from "@/components/ui/skeleton"
import { Upload, FileText, Trash2, Bot } from "lucide-react"

export default function ChatbotPage() {
  const { config, isLoading, updateConfig, isUpdating } = useChatbotConfig()
  const { documents, uploadDocument, deleteDocument, isUploading, isDeleting } = useDocuments()

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
        <h1 className="text-3xl font-bold">Chatbot Configuration</h1>
        <p className="text-muted-foreground mt-2">
          Configure AI chatbot settings and RAG documents
        </p>
      </div>

      <Tabs defaultValue="settings" className="space-y-4">
        <TabsList>
          <TabsTrigger value="settings">Settings</TabsTrigger>
          <TabsTrigger value="documents">Documents</TabsTrigger>
          <TabsTrigger value="preview">Preview</TabsTrigger>
        </TabsList>

        <TabsContent value="settings" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>AI Model Configuration</CardTitle>
              <CardDescription>Configure chatbot behavior and AI settings</CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="name">Chatbot Name</Label>
                <Input id="name" placeholder="e.g., Support Bot" defaultValue={config?.name} />
              </div>

              <div className="space-y-2">
                <Label htmlFor="description">Description</Label>
                <Input id="description" placeholder="Brief description" defaultValue={config?.description} />
              </div>

              <div className="grid gap-4 md:grid-cols-2">
                <div className="space-y-2">
                  <Label htmlFor="model">AI Model</Label>
                  <Select defaultValue={config?.aiModel || "gemini-pro"}>
                    <SelectTrigger>
                      <SelectValue />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="gemini-pro">Gemini Pro</SelectItem>
                      <SelectItem value="gemini-1.5-pro">Gemini 1.5 Pro</SelectItem>
                      <SelectItem value="gemini-ultra">Gemini Ultra</SelectItem>
                    </SelectContent>
                  </Select>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="temperature">Temperature: {config?.temperature || 0.7}</Label>
                  <Input
                    id="temperature"
                    type="range"
                    min="0"
                    max="1"
                    step="0.1"
                    defaultValue={config?.temperature || 0.7}
                  />
                </div>
              </div>

              <div className="space-y-2">
                <Label htmlFor="prompt">System Prompt</Label>
                <Textarea
                  id="prompt"
                  rows={6}
                  placeholder="Enter system prompt..."
                  defaultValue={config?.promptTemplate}
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="fallback">Fallback Message</Label>
                <Input
                  id="fallback"
                  placeholder="Message when AI can't answer"
                  defaultValue={config?.fallbackMessage}
                />
              </div>

              <div className="flex items-center justify-between border-t pt-4">
                <div className="space-y-1">
                  <Label>Auto Reply</Label>
                  <p className="text-sm text-muted-foreground">
                    Automatically respond to customer messages
                  </p>
                </div>
                <Switch defaultChecked={config?.autoReplyEnabled} />
              </div>

              <div className="flex items-center justify-between border-t pt-4">
                <div className="space-y-1">
                  <Label>Enable RAG</Label>
                  <p className="text-sm text-muted-foreground">
                    Use uploaded documents for responses
                  </p>
                </div>
                <Switch defaultChecked={config?.ragEnabled} />
              </div>

              <div className="border-t pt-4">
                <Label>Active Channels</Label>
                <div className="flex flex-wrap gap-2 mt-2">
                  {["WEBSITE", "ZALO_OA", "ZALO_PERSONAL"].map((channel) => (
                    <Badge
                      key={channel}
                      variant={config?.channels?.includes(channel) ? "default" : "outline"}
                      className="cursor-pointer"
                    >
                      {channel}
                    </Badge>
                  ))}
                </div>
              </div>

              <div className="flex justify-end gap-2 pt-4">
                <Button variant="outline">Reset</Button>
                <Button disabled={isUpdating}>
                  {isUpdating ? "Saving..." : "Save Changes"}
                </Button>
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="documents" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>RAG Documents</CardTitle>
              <CardDescription>Upload documents for AI to reference</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="border-2 border-dashed rounded-lg p-8 text-center mb-6">
                <Upload className="h-12 w-12 mx-auto text-muted-foreground mb-4" />
                <p className="text-sm text-muted-foreground mb-4">
                  Drag & drop files or click to upload
                </p>
                <Button disabled={isUploading}>
                  {isUploading ? "Uploading..." : "Upload Document"}
                </Button>
              </div>

              <div className="space-y-3">
                {documents && documents.length > 0 ? (
                  documents.map((doc: any) => (
                    <div key={doc.id} className="flex items-center justify-between p-4 border rounded-lg">
                      <div className="flex items-center gap-3">
                        <FileText className="h-8 w-8 text-blue-500" />
                        <div>
                          <p className="font-medium">{doc.name}</p>
                          <div className="flex items-center gap-2 mt-1">
                            <Badge variant="outline">{doc.type}</Badge>
                            <Badge variant={doc.status === "EMBEDDED" ? "success" : "warning"}>
                              {doc.status}
                            </Badge>
                            <span className="text-xs text-muted-foreground">
                              {doc.chunkCount} chunks
                            </span>
                          </div>
                        </div>
                      </div>
                      <Button
                        variant="ghost"
                        size="icon"
                        onClick={() => deleteDocument(doc.id)}
                        disabled={isDeleting}
                      >
                        <Trash2 className="h-4 w-4 text-destructive" />
                      </Button>
                    </div>
                  ))
                ) : (
                  <div className="text-center py-12 text-muted-foreground">
                    No documents uploaded yet
                  </div>
                )}
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="preview" className="space-y-4">
          <Card>
            <CardHeader>
              <CardTitle>Chatbot Preview</CardTitle>
              <CardDescription>Test your chatbot configuration</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="border rounded-lg p-4 h-[500px] flex flex-col">
                <div className="flex-1 overflow-y-auto space-y-4 mb-4">
                  <div className="flex items-start gap-3">
                    <div className="h-8 w-8 rounded-full bg-primary flex items-center justify-center">
                      <Bot className="h-4 w-4 text-primary-foreground" />
                    </div>
                    <div className="bg-muted rounded-lg p-3 max-w-[70%]">
                      <p className="text-sm">
                        Hello! How can I help you today?
                      </p>
                    </div>
                  </div>
                </div>
                <div className="flex gap-2">
                  <Input placeholder="Type a message..." />
                  <Button>Send</Button>
                </div>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  )
}

