// authentication.service.ts
import { Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import moment from 'moment';
import { shareReplay, tap } from "rxjs";
import { JwttokenhandlerService } from "./jwttokenhandler.service";

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

  constructor(private http:HttpClient, private jwtHandler: JwttokenhandlerService) { }

  private setSession(accessToken: string) {
    if (typeof window !== 'undefined' && window.sessionStorage) {
      var expirationDate = this.jwtHandler.getExpiration(accessToken);
      sessionStorage.setItem('accessToken', accessToken);
      sessionStorage.setItem("tokenExpiration", expirationDate.valueOf().toString());
      sessionStorage.setItem("email", this.jwtHandler.getEmail(accessToken));
      sessionStorage.setItem("role", this.jwtHandler.getRole(accessToken));
    }
  }

  logout() {
    if (typeof window !== 'undefined' && window.sessionStorage) {
      sessionStorage.removeItem("accessToken");
      sessionStorage.removeItem("tokenExpiration");
      sessionStorage.removeItem("email");
      sessionStorage.removeItem("role");
    }
  }

  public isLoggedIn() {
    return moment().isBefore(this.getExpiration());
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  isUser() {
    if(!this.isLoggedIn()) return false;
    if (typeof window !== 'undefined' && window.sessionStorage) {
      return sessionStorage.getItem("role") === "USER";
    } else {
      return false;
    }
  }

  isHairdresser() {
    if(!this.isLoggedIn()) return false;
    if (typeof window !== 'undefined' && window.sessionStorage) {
      return sessionStorage.getItem("role") === "HAIRDRESSER";
    } else {
      return false;
    }
  }

  getExpiration() {
    if (typeof window !== 'undefined' && window.sessionStorage) {
      const expiration = sessionStorage.getItem("tokenExpiration");
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
