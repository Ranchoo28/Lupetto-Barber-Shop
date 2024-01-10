// authentication.service.ts
import {Injectable} from '@angular/core';
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
    if (typeof window !== 'undefined' && window.localStorage) {
      return moment().isBefore(this.getExpiration());
    } else {
      return false;
    }
  }

  getJwtExpiration(token: string): Date {
    const tokenParts = token.split('.');
    const encodedPayload = tokenParts[1];
    const decodedPayload = atob(encodedPayload);
    const payloadObj = JSON.parse(decodedPayload);
    return new Date(payloadObj.exp * 1000);
  }

  private setSession(accessToken: string) {
    const expirationDate = this.getJwtExpiration(accessToken);

    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem("tokenExpiration", expirationDate.valueOf().toString());
  }

  logout() {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("tokenExpiration");
  }

  public isLoggedIn() {
    return moment().isBefore(this.getExpiration());
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  getExpiration() {
    if (typeof window !== 'undefined' && window.localStorage) {
      const expiration = localStorage.getItem("tokenExpiration");
      if (!expiration) {
        return moment(0);
      }
      const expiresAt = JSON.parse(expiration);
      return moment(expiresAt);
    }
    else{
      return null;
    }
  }


  login(_email: string, _password: string) {
    const loginRequest = {
      email: _email,
      password: _password,
    }
    return this.http.post(this.loginURL, loginRequest, this.httpOptions)
      .pipe(
        tap((res: any) => {
          this.setSession(res.accessToken)
        }), shareReplay());
  }


  register(_email: string, _password: string, _name: string, _surname: string, _number: string) {
    const registrationRequest = {
      email: _email,
      password: _password,
      name: _name,
      surname: _surname,
      number: _number,
    };
    return this.http.post(this.registerURL, registrationRequest, this.httpOptions);
  }

}
