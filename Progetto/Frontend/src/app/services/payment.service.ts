import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private baseUrl = 'http://localhost:8080/api/payment';

  constructor(private http: HttpClient) {}

  creaIntentPagamento(amount: number, description: string): Promise<any> {
    const params = new HttpParams()
      .set('amount', amount.toString())
      .set('description', description);
    return firstValueFrom(this.http.get(`${this.baseUrl}/create`, { params }));
  }





}
