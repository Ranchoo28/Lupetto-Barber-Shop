import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable, throwError } from "rxjs"; // Importa throwError
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const accessToken = localStorage.getItem("accessToken");
    const expiration = localStorage.getItem("tokenExpiration"); // Assicurati di salvare la scadenza del token quando lo ottieni

    if (accessToken && expiration) {
      const now = new Date();
      const expiresAt = new Date(expiration);

      if (now > expiresAt) {
        console.log('Token scaduto');
        // Reindirizza l'utente al login o rinnova il token
        this.router.navigate(['login']);
        return throwError(() => new Error('Token scaduto')); // Usa throwError qui
      }

      const cloned = req.clone({
        headers: req.headers.set("Authorization", "Bearer " + accessToken)
      });

      return next.handle(cloned);
    } else {
      return next.handle(req);
    }
  }
}
