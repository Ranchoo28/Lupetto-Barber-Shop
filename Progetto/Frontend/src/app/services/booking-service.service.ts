import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private baseUrl = 'http://localhost:8080';
  constructor(private http: HttpClient) { }

  insertBooking(username: string, idService: number, date: string, time: string): Observable<any> {
    const url = `${this.baseUrl}/api/booking/insert`;
    const body = { username, idService, date, time };
    return this.http.post(url, body);
  }
}
