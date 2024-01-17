import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  getEmailFromToken(): string | null {
    let token = sessionStorage.getItem('accessToken');
    if (token) {
      let decodedPayload = JSON.parse(atob(token.split('.')[1]));
      return decodedPayload['email'];
    }
    return null;
  }

  getRoleFromToken(): string | null {
    let token = sessionStorage.getItem('accessToken');
    if (token) {
      let decodedPayload = JSON.parse(atob(token.split('.')[1]));
      return decodedPayload['role'];
    }
    return null;
  }
}
