import { NextResponse } from "next/server";
import jwt_decode from "jwt-decode";
import { FRONTEND } from "./utils/fetch_wrapper";

export function middleware(request) {
  let tokenCookie = request.cookies.get("token")?.value;

  // If the token is not present in the cookie and path is not /login or /register, redirect to login page
  if (
    !tokenCookie &&
    !(
      request.nextUrl.pathname == "/login" ||
      request.nextUrl.pathname == "/register"
    )
  ) {
    return NextResponse.redirect(FRONTEND + "/login");
  }

  // If the token is present in the cookie, verify its grantedAuthorities
  else if (tokenCookie) {
    // If the token is present but the path is /login or /register, redirect to home page
    if (
      request.nextUrl.pathname == "/login" ||
      request.nextUrl.pathname == "/register"
    ) {
      return NextResponse.redirect(FRONTEND);
    }

    // Decode the token and get the grantedAuthorities
    const decoded = jwt_decode(tokenCookie);
    const authorities = decoded.grantedAuthorities;

    // If the user is not an owner and the path is /register/moderator, redirect to home page
    if (
      !authorities.includes("Owner") &&
      request.nextUrl.pathname == "/register/moderator"
    ) {
      return NextResponse.redirect(FRONTEND);
    }
  }

  // Correct path
  return NextResponse.next();
}

export const config = {
  matcher: [
    /*
     * Match all request paths except for the ones starting with:
     * - api (API routes)
     * - _next/static (static files)
     * - _next/image (image optimization files)
     * - favicon.ico (favicon file)
     */
    "/((?!api|_next/static|_next/image|favicon.ico).*)",
  ],
};
