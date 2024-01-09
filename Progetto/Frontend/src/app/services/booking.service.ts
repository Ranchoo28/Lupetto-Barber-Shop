import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private baseUrl = 'http://localhost:8080';
  httpOptions = {
    headers: new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  insertBooking(booking: any, email: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/booking/insert`, booking, {params: {email: email}});
  }

  deleteBooking(idBooking: number, email: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/booking/delete`, {params: {idBooking: idBooking.toString(), email: email}});
  }

  updateBooking(booking: any, email: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/booking/update`, booking, {params: {email: email}});
  }

  getBooking(email: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/booking/get`, {params: {email: email}});
  }
}
