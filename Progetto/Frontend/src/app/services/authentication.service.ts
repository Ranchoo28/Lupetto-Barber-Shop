// authentication.service.ts
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private cookieService: CookieService) { }

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
