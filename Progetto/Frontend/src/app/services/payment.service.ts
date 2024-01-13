import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private baseUrl = 'http://localhost:4200/api/payment'; // Assicurati che questo sia l'URL corretto del tuo backend

  constructor(private http: HttpClient) {}

  createPaymentIntent(amount: number, description: string): Observable<{ paymentIntentId: string }> {
    const params = new HttpParams()
      .set('amount', amount.toString())
      .set('description', description);

    return this.http.get<{ paymentIntentId: string }>(`${this.baseUrl}/create`, { params });
  }
}
