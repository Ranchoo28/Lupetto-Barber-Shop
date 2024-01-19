import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingDateService {
  private baseUrl = 'http://localhost:8080/api/bookingdate';
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  insertBookingDate(bookingDate: any, username: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/insert`, bookingDate, {
      params: {username: username},
      headers:this.httpOptions.headers});
  }

  deleteBookingDate(idBookingDate: number, username: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/delete`, {
      params: {idBookingDate: idBookingDate.toString(), username: username},
      headers:this.httpOptions.headers
    });
  }

  updateBookingDate(bookingDate: any, username: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/update`, bookingDate, {
      params: {username: username},
      headers:this.httpOptions.headers
    });
  }

  getBookingDateByService(idService: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/getDatebyService`, {
      params: {idService: idService.toString()},
      headers:this.httpOptions.headers
    });
  }

  getBookingDateByTime(date: string, idService: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/getTimebyDate`, {
      params: {date: date, idService: idService.toString()},
      headers:this.httpOptions.headers
    });
  }
}
