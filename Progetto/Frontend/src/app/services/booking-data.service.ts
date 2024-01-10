import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingDateService {
  private baseUrl = 'http://localhost:8080/api/bookingdate';

  constructor(private http: HttpClient) { }

  insertBookingDate(bookingDate: any, username: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/insert`, bookingDate, {params: {username: username}});
  }

  deleteBookingDate(idBookingDate: number, username: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/delete`, {params: {idBookingDate: idBookingDate.toString(), username: username}});
  }

  updateBookingDate(bookingDate: any, username: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/update`, bookingDate, {params: {username: username}});
  }

  getBookingDate(): Observable<any> {
    return this.http.get(`${this.baseUrl}/get`);
  }

  getBookingDateByService(idService: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/getDatebyService`, {params: {idService: idService.toString()}});
  }

  getBookingDateByTime(date: string, idService: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/getTimebyDate`, {params: {date: date, idService: idService.toString()}});
  }
}
