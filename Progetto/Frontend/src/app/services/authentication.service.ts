// authentication.service.ts
import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import moment from 'moment';
import {shareReplay, tap} from "rxjs";

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

  constructor(private http:HttpClient) { }

  isUserLoggedIn(): boolean {
    return localStorage.getItem('accessToken') !== null;
  }

  private setSession(accessToken: string) {
    const expiresIn = 3600; // 1 ora di scadenza, modificare secondo necessità
    const expiresAt = moment().add(expiresIn,'second');

    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem("expires_at", JSON.stringify(expiresAt.valueOf()) );
  }

  logout() {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("expires_at");
  }

  public isLoggedIn() {
    return moment().isBefore(this.getExpiration());
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  getExpiration() {
    const expiration = localStorage.getItem("expires_at");
    if (!expiration) {
      return moment(0);
    }
    const expiresAt = JSON.parse(expiration);
    return moment(expiresAt);
  }


  login(_email: string, _password: string) {
    const loginRequest = {
      email: _email,
      password: _password,
    }
    console.log(loginRequest);
    return this.http.post(this.loginURL, loginRequest, this.httpOptions)
      .pipe(
        tap((res: any) => this.setSession(res.accessToken)), shareReplay());
  }
// TODO RIMETTI NEL LOGIN QUANDO SISTEMANO IL BACKEND
  /*
      return this.http.post(this.loginURL, loginRequest, this.httpOptions)
      .pipe(
      tap((res: any) => this.setSession(res)), shareReplay());

      o



  */

  register(_email: string, _password: string, _name: string, _surname: string, _number: string) {
    const registrationRequest = {
      email: _email,
      password: _password,
      name: _name,
      surname: _surname,
      number: _number,
    };
    console.log(registrationRequest);
    return this.http.post(this.registerURL, registrationRequest, this.httpOptions);
  }

}
