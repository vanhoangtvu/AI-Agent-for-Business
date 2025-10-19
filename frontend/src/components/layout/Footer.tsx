export function Footer() {
  return (
    <footer className="border-t bg-background">
      <div className="container flex h-16 items-center justify-between px-4">
        <p className="text-sm text-muted-foreground">
          © 2025 AI Agent for Business. All rights reserved.
        </p>
        <div className="flex items-center gap-4">
          <a href="#" className="text-sm text-muted-foreground hover:text-foreground">
            Privacy
          </a>
          <a href="#" className="text-sm text-muted-foreground hover:text-foreground">
            Terms
          </a>
          <a href="#" className="text-sm text-muted-foreground hover:text-foreground">
            Support
          </a>
        </div>
      </div>
    </footer>
  )
}

