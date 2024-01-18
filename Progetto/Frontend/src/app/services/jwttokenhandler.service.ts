import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import moment from 'moment';

@Injectable({
  providedIn: 'root'
})
export class JwttokenhandlerService {

  constructor() { }

  decodeToken(token: string): any {
    try {
      return jwtDecode(token);
    } catch (Error) {
      console.error("Errore nella decodifica del token: ", Error);
      return null;
    }
  }

  getEmail(token: string): string {
    let jwtDecoded = this.decodeToken(token);
    return jwtDecoded.email;
  }

  getRole(token: string): string {
    let jwtDecoded = this.decodeToken(token);
    return jwtDecoded.role;
  }

  getExpiration(token: string): Date {
    let jwtDecoded = this.decodeToken(token);
    return new Date(jwtDecoded.exp * 1000);
  }

}
