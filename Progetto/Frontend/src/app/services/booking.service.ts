import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  insertURL = 'http://localhost:8080/api/booking/insert';
  httpOptions = {
    headers: new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  prenotaAppuntamento(_email: string, _idService: number, _date: string, _ora: string): Observable<any> {
    const url = this.insertURL;
    const prenotazione = {
      email: _email,
      idService: _idService,
      date: _date,
      ora: _ora,
    };
    console.log(prenotazione);
    return this.http.post(url, prenotazione, this.httpOptions);
  }
}
