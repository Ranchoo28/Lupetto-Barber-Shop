// authentication.service.ts
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  loginURL = 'http://localhost:8080/api/login';
  registerURL = 'http://localhost:8080/api/register';

  constructor(private http:HttpClient,  private cookieService: CookieService) { }

  login(username: string, password: string){
    return this.http.post(this.loginURL, {username, password});
  }

  setCookie(username: string) {
    this.cookieService.set('username', username);
  }

  getCookie(): string {
    return this.cookieService.get('username');
  }

  deleteCookie() {
    this.cookieService.delete('username');
  }

  isUserLoggedIn(): boolean {
    return this.cookieService.check('username');
  }


}
