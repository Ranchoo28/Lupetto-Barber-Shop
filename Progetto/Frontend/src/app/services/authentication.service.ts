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

  setCookie(email: string) {
    this.cookieService.set('email', email);
  }

  getCookie(): string {
    return this.cookieService.get('email');
  }

  deleteCookie() {
    this.cookieService.delete('email');
  }

  isUserLoggedIn(): boolean {
    return this.cookieService.check('email');
  }

  login(_email: string, _password: string){
    const loginRequest = {
      email: _email,
      password: _password,
    }
    console.log(loginRequest);
    return this.http.post(this.loginURL, loginRequest, this.httpOptions)
  }

  register(_email: string, _password: string, _name: string, _surname: string, _telefono: string) {
    const registrationRequest = {
      email: _email,
      password: _password,
      name: _name,
      surname: _surname,
      telefono: _telefono,
    };
    console.log(registrationRequest);
    return this.http.post(this.registerURL, registrationRequest, this.httpOptions);
  }

}
