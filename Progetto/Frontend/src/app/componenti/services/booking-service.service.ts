import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(private http: HttpClient) { }

  inviaDati(formData: any): Observable<any> {
    const bookingData = {
      username: formData.nome,
      idService: formData.servizio,
      data: formData.data,
      time: formData.orario
    };

    return this.http.post('http://localhost:4200/api/login', bookingData);
  }
}
