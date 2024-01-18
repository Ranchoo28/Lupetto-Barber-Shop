import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private baseUrl = 'http://localhost:4200';
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  insertBooking(idBookingDate: number, email: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/booking/insert`, null, {
      params: {
        email: email,
        idBookingDate: idBookingDate.toString()
      },
      responseType: 'text'
    });
  }


  deleteBooking(idBooking: number, email: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/booking/delete`, "", {
      params: {
        idBooking: idBooking,
        email: email
      },

    });
  }

  updateBooking(booking: any, email: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/booking/update`, booking, {params: {email: email}});
  }

  getUserBooking(email: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/user/booking`, {params: {email: email}});
  }

  getAllBooking(email: string): Observable<any>{
    return this.http.get(`${this.baseUrl}/api/hairdresser/bookings`, {params: {email: email}});

  }

  getBookingsByDate(email: string, data:string): Observable<any>{
    return this.http.get(`${this.baseUrl}/api/hairdresser/bookings/getByDate`, {params: {email: email, date: data}});

  }
}
