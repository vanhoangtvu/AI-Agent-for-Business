import { NextResponse } from 'next/server'
import type { NextRequest } from 'next/server'

export function middleware(request: NextRequest) {
  const token = request.cookies.get('token')?.value || 
                request.headers.get('authorization')?.replace('Bearer ', '')

  const { pathname } = request.nextUrl

  // Public routes
  const publicRoutes = ['/', '/login', '/register']
  if (publicRoutes.includes(pathname)) {
    return NextResponse.next()
  }

  // Protected routes - check if user is authenticated
  if (!token) {
    return NextResponse.redirect(new URL('/login', request.url))
  }

  // TODO: Decode JWT and check role-based access
  // For now, allow all authenticated users
  return NextResponse.next()
}

export const config = {
  matcher: [
    '/((?!api|_next/static|_next/image|favicon.ico|public).*)',
  ],
}

