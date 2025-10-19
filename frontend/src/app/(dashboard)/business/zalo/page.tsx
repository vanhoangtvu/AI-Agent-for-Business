"use client"

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { Switch } from "@/components/ui/switch"
import { Label } from "@/components/ui/label"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { useZaloPersonalConfig, useZaloQRLogin, useZaloLoginStatus } from "@/hooks/useZalo"
import { Skeleton } from "@/components/ui/skeleton"
import { Zap, QrCode, CheckCircle2, XCircle } from "lucide-react"
import Image from "next/image"
import { useEffect, useState } from "react"

export default function ZaloPage() {
  const { config, isLoading, updateConfig, disconnect, isUpdating, isDisconnecting } = useZaloPersonalConfig()
  const { qrCode, sessionId, isLoading: isQRLoading } = useZaloQRLogin()
  const [showQR, setShowQR] = useState(false)

  const { data: loginStatus } = useZaloLoginStatus(sessionId || "")

  useEffect(() => {
    if (loginStatus?.data?.status === "logged_in") {
      setShowQR(false)
      // Refresh config
    }
  }, [loginStatus])

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
        <h1 className="text-3xl font-bold">Zalo Integration</h1>
        <p className="text-muted-foreground mt-2">
          Connect your Zalo Personal Account for customer interactions
        </p>
      </div>

      <div className="grid gap-6 md:grid-cols-3">
        {/* Connection Status */}
        <Card className="md:col-span-1">
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <Zap className="h-5 w-5" />
              Connection Status
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {config?.isConnected ? (
                <>
                  <div className="flex items-center gap-2">
                    <CheckCircle2 className="h-5 w-5 text-green-600" />
                    <span className="font-medium text-green-600">Connected</span>
                  </div>
                  <div className="space-y-2">
                    <div className="flex items-center justify-center">
                      <div className="h-20 w-20 rounded-full bg-gray-200 flex items-center justify-center">
                        {config?.zaloUserAvatar ? (
                          <Image
                            src={config.zaloUserAvatar}
                            alt={config.zaloUserName}
                            width={80}
                            height={80}
                            className="rounded-full"
                          />
                        ) : (
                          <span className="text-2xl">{config?.zaloUserName?.[0]}</span>
                        )}
                      </div>
                    </div>
                    <p className="text-center font-medium">{config?.zaloUserName}</p>
                    <p className="text-center text-sm text-muted-foreground">
                      ID: {config?.zaloUserId}
                    </p>
                  </div>
                  <Button
                    variant="destructive"
                    className="w-full"
                    onClick={() => disconnect()}
                    disabled={isDisconnecting}
                  >
                    {isDisconnecting ? "Disconnecting..." : "Disconnect"}
                  </Button>
                </>
              ) : (
                <>
                  <div className="flex items-center gap-2">
                    <XCircle className="h-5 w-5 text-red-600" />
                    <span className="font-medium text-red-600">Not Connected</span>
                  </div>
                  <p className="text-sm text-muted-foreground">
                    Scan QR code with Zalo app to connect
                  </p>
                  <Button
                    className="w-full"
                    onClick={() => setShowQR(true)}
                    disabled={isQRLoading}
                  >
                    <QrCode className="mr-2 h-4 w-4" />
                    {isQRLoading ? "Loading..." : "Show QR Code"}
                  </Button>
                </>
              )}
            </div>
          </CardContent>
        </Card>

        {/* Configuration */}
        <Card className="md:col-span-2">
          <CardHeader>
            <CardTitle>Configuration</CardTitle>
            <CardDescription>Configure Zalo integration settings</CardDescription>
          </CardHeader>
          <CardContent>
            <Tabs defaultValue="ai" className="space-y-4">
              <TabsList>
                <TabsTrigger value="ai">AI Mode</TabsTrigger>
                <TabsTrigger value="automation">Automation</TabsTrigger>
                <TabsTrigger value="working-hours">Working Hours</TabsTrigger>
              </TabsList>

              <TabsContent value="ai" className="space-y-4">
                <div className="space-y-2">
                  <Label>AI Mode</Label>
                  <Select defaultValue={config?.mode || "SUGGESTION"}>
                    <SelectTrigger>
                      <SelectValue />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="AUTO">
                        <div className="flex flex-col">
                          <span className="font-medium">Auto Reply</span>
                          <span className="text-xs text-muted-foreground">
                            AI automatically responds to messages
                          </span>
                        </div>
                      </SelectItem>
                      <SelectItem value="SUGGESTION">
                        <div className="flex flex-col">
                          <span className="font-medium">Suggestion</span>
                          <span className="text-xs text-muted-foreground">
                            AI suggests replies for you to send
                          </span>
                        </div>
                      </SelectItem>
                      <SelectItem value="NOTIFICATION">
                        <div className="flex flex-col">
                          <span className="font-medium">Notification Only</span>
                          <span className="text-xs text-muted-foreground">
                            Only notify, no AI assistance
                          </span>
                        </div>
                      </SelectItem>
                    </SelectContent>
                  </Select>
                  <p className="text-sm text-muted-foreground">
                    {config?.mode === "AUTO" && "AI will automatically reply to customer messages."}
                    {config?.mode === "SUGGESTION" && "AI will suggest replies for you to approve."}
                    {config?.mode === "NOTIFICATION" && "You'll receive notifications only."}
                  </p>
                </div>

                <div className="flex items-center justify-between">
                  <div>
                    <Label>Use RAG (Documents)</Label>
                    <p className="text-sm text-muted-foreground">
                      Reference uploaded documents in responses
                    </p>
                  </div>
                  <Switch defaultChecked={config?.useRAG} />
                </div>
              </TabsContent>

              <TabsContent value="automation" className="space-y-4">
                <div className="flex items-center justify-between">
                  <div>
                    <Label>Auto Create Orders</Label>
                    <p className="text-sm text-muted-foreground">
                      Automatically create orders from Zalo conversations
                    </p>
                  </div>
                  <Switch defaultChecked={config?.autoCreateOrder} />
                </div>

                <div className="flex items-center justify-between">
                  <div>
                    <Label>Auto Reply</Label>
                    <p className="text-sm text-muted-foreground">
                      Enable automatic responses (based on AI mode)
                    </p>
                  </div>
                  <Switch defaultChecked={config?.autoReply} />
                </div>
              </TabsContent>

              <TabsContent value="working-hours" className="space-y-4">
                <div className="flex items-center justify-between">
                  <div>
                    <Label>Enable Working Hours</Label>
                    <p className="text-sm text-muted-foreground">
                      Only reply during specific hours
                    </p>
                  </div>
                  <Switch defaultChecked={config?.workingHoursEnabled} />
                </div>

                {config?.workingHoursEnabled && (
                  <div className="grid gap-4 md:grid-cols-2">
                    <div className="space-y-2">
                      <Label>Start Time</Label>
                      <Select defaultValue={config?.workingHoursStart || "09:00"}>
                        <SelectTrigger>
                          <SelectValue />
                        </SelectTrigger>
                        <SelectContent>
                          {Array.from({ length: 24 }, (_, i) => i).map((hour) => (
                            <SelectItem key={hour} value={`${hour.toString().padStart(2, "0")}:00`}>
                              {hour.toString().padStart(2, "0")}:00
                            </SelectItem>
                          ))}
                        </SelectContent>
                      </Select>
                    </div>

                    <div className="space-y-2">
                      <Label>End Time</Label>
                      <Select defaultValue={config?.workingHoursEnd || "18:00"}>
                        <SelectTrigger>
                          <SelectValue />
                        </SelectTrigger>
                        <SelectContent>
                          {Array.from({ length: 24 }, (_, i) => i).map((hour) => (
                            <SelectItem key={hour} value={`${hour.toString().padStart(2, "0")}:00`}>
                              {hour.toString().padStart(2, "0")}:00
                            </SelectItem>
                          ))}
                        </SelectContent>
                      </Select>
                    </div>
                  </div>
                )}
              </TabsContent>
            </Tabs>

            <div className="flex justify-end gap-2 mt-6 pt-4 border-t">
              <Button variant="outline">Reset</Button>
              <Button disabled={isUpdating}>
                {isUpdating ? "Saving..." : "Save Changes"}
              </Button>
            </div>
          </CardContent>
        </Card>
      </div>

      {/* QR Code Dialog */}
      {showQR && qrCode && (
        <div className="fixed inset-0 z-50 bg-black/50 flex items-center justify-center">
          <Card className="w-[400px]">
            <CardHeader>
              <CardTitle>Scan QR Code</CardTitle>
              <CardDescription>Use Zalo app to scan this QR code</CardDescription>
            </CardHeader>
            <CardContent className="flex flex-col items-center gap-4">
              <div className="bg-white p-4 rounded-lg">
                {qrCode && (
                  <Image src={qrCode} alt="QR Code" width={250} height={250} />
                )}
              </div>
              <Badge variant={loginStatus?.data?.status === "logged_in" ? "success" : "secondary"}>
                {loginStatus?.data?.status === "logged_in" ? "Connected!" : "Waiting..."}
              </Badge>
              <Button variant="outline" onClick={() => setShowQR(false)}>
                Cancel
              </Button>
            </CardContent>
          </Card>
        </div>
      )}
    </div>
  )
}

