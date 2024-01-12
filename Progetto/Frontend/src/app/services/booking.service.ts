import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  // ATTENZIONE!!!
  // O si usa http://localhost:4200 con il reverse proxy di Angular
  // oppure si usa http://localhost:8080 ma DOVETE AGGIUSTARE IL CORS!!!
  private baseUrl = 'http://localhost:4200';
  httpOptions = {
    headers: new HttpHeaders({
      // Se vuoi usare Accept e accettare JSON, prima accertati che da backend invii JSON!!!
      //'Accept': 'application/json',
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
      // ATTENZIONE!!!
      // Viene aggiunto il responseType text perché il backend non invia JSON!!!
      responseType: 'text'
    });
  }


  deleteBooking(idBooking: number, email: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/booking/delete`, "", {
      params: {
        idBooking: idBooking.toString(),
        email: email
      },
      // ATTENZIONE!!!
      // Viene aggiunto il responseType text perché il backend non invia JSON!!!
      responseType: 'text'
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
}
