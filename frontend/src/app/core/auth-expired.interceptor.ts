import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

const PUBLIC_ENDPOINTS = [
  '/auth/login',
  '/auth/register',
  '/activate/account',
  '/download/'
];

function isPublicEndpoint(url: string): boolean {
  try {
    const u = new URL(url);
    url = u.pathname;
  } catch {}
  return PUBLIC_ENDPOINTS.some(endpoint => url.includes(endpoint));
}

@Injectable()
export class AuthExpiredInterceptor implements HttpInterceptor {
  constructor(private auth: AuthService, private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (isPublicEndpoint(req.url)) {
          return throwError(() => error);
        }
        if (error.status === 401) {
          this.auth.clearTokens();
          this.router.navigate(['/login'], { queryParams: { sessionExpired: 1 } });
        }
        return throwError(() => error);
      })
    );
  }
}
