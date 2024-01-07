// authentication.service.ts
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  loginURL = 'http://localhost:8080/api/login';
  registerURL = 'http://localhost:8080/api/register';
  httpOptions = {
    headers: new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    })
  };

  constructor(private http:HttpClient,  private cookieService: CookieService) { }

  login(username: string, password: string){
    const loginRequest = {
      username: username,
      password: password,
    }
    return this.http.post(this.loginURL, loginRequest, this.httpOptions)
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

  register(username: string, password: string, name: string, surname: string, email: string, role: string) {
    const user = {
      username: username,
      password: password,
      name: name,
      surname: surname,
      email: email,
      role: role,
    };
    return this.http.post(this.registerURL, user, this.httpOptions);
  }

}
