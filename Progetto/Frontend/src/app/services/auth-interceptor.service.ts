import { Injectable } from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor{

  intercept(req: HttpRequest<any>,
            next: HttpHandler): Observable<HttpEvent<any>> {

    const accessToken = localStorage.getItem("accessToken");

    if (accessToken) {
      const cloned = req.clone({
        headers: req.headers.set("Authorization", "Bearer " + accessToken)
      });

      return next.handle(cloned);
    }
    else {
      return next.handle(req);
    }
  }
}
