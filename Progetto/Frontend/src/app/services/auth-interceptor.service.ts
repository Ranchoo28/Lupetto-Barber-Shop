import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { Router } from '@angular/router';
import { AuthenticationService } from "./authentication.service";
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  constructor(
    private authService: AuthenticationService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (isPlatformBrowser(this.platformId)) {
      const accessToken = sessionStorage.getItem("accessToken");
      const expiration = sessionStorage.getItem("tokenExpiration");

      if (accessToken && expiration) {
        const now = new Date();
        const expiresAt = new Date(expiration);

        if (now > expiresAt) {
          console.log('Token scaduto');
          this.authService.logout();
          this.router.navigate(['login']);
          return throwError(() => new Error('Token scaduto'));
        }

        const cloned = req.clone({
          headers: req.headers.set("Authorization", "Bearer " + accessToken)
        });

        return next.handle(cloned);
      } else {
        return next.handle(req);
      }
    } else {
      return next.handle(req);
    }
  }
}
