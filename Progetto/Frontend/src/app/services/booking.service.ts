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
  httpOptions2 = {
    headers: new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    })
  };

  constructor(private http: HttpClient) { }

  pagamentoInCorso = false;

  prezzoTrattamento: number = 0;
  bookingDate: number = 0;
  email: string = "";

  resetVars(){
    this.prezzoTrattamento = 0;
    this.bookingDate = 0;
    this.email = "";
  }

  setVars(bookingDate: number, email: string){
    this.bookingDate = bookingDate;
    this.email = email;
  }

  insertFromPayment(_intent: string): Observable<any> {
    const body = { intent: _intent};

    return this.http.post(`${this.baseUrl}/api/booking/insertWithIntent`, body, {
      params: {
        email: this.email,
        idBookingDate: this.bookingDate.toString()
      },
      responseType: 'text'
    });
  }


  insertBooking(idBookingDate: number, email: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/booking/insert`, "", {
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
      headers: this.httpOptions2.headers

    });
  }

  updateBooking(booking: any, email: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/booking/update`, booking, {params: {email: email}});
  }

  getUserBooking(email: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/user/booking`, {
      params: {email: email},
      headers:this.httpOptions.headers
    });
  }

  getAllBooking(email: string): Observable<any>{
    return this.http.get(`${this.baseUrl}/api/hairdresser/bookings`, {
      params: {email: email},
      headers:this.httpOptions.headers
    });

  }

  getBookingsByDate(email: string, data:string): Observable<any>{
    return this.http.get(`${this.baseUrl}/api/hairdresser/bookings/getByDate`, {
      params: {email: email, date: data},
      headers:this.httpOptions.headers
    });

  }







}
