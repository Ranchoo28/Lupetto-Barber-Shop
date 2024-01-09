import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {
  private baseUrl = 'http://localhost:8080/api/service';

  constructor(private http: HttpClient) { }

  insertService(service: any, email: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/insert`, service, {params: {email: email}});
  }

  deleteService(idService: number, email: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/delete`, {params: {idService: idService.toString(), email: email}});
  }

  updateService(service: any, email: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/update`, service, {params: {email: email}});
  }

  getService(): Observable<any> {
    return this.http.get(`${this.baseUrl}/get`);
  }

  getServiceBySex(sex: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/getBySex`, {params: {sex: sex}});
  }
}
