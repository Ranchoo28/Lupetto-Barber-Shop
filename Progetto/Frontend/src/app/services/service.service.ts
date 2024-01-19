import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {
  private baseUrl = 'http://localhost:8080/api/service';
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }


  getServiceBySex(sex: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/getBySex`, {
      params: {sex: sex},
      headers:this.httpOptions.headers});
  }
}
