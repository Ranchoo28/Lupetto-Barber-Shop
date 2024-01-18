import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  private productsUrl = '/api/products';
  private deleteProductUrl = '/api/hairdresser/deleteProduct';
  private baseUrl = 'http://localhost:4200';

  httpOptions = {
    headers: new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) {}

  getProducts(): Observable<any[]> {
    return this.http.get<any[]>(this.productsUrl);
  }

  addProduct(email: string, name: string, description: string, category: string, price: number, image: string ): Observable<any> {
    const productRequest = {
      name: name,
      description: description,
      category: category,
      price: price,
      image: image,
    };
    return this.http.post(`${this.baseUrl}/api/hairdresser/addProduct`, productRequest, {params: {email} });
  }


}
