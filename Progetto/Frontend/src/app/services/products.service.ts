import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  private productsUrl = '/api/products';
  private addProductUrl = '/api/hairdresser/addProduct';
  private deleteProductUrl = '/api/hairdresser/deleteProduct';
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

  addProduct(_email: string, _nome: string, _descrizione: string, _image: string, _categoria: string, _prezzo: number): Observable<any> {
    let params = new HttpParams().set('email', _email);
    const productRequest = {
      name: _nome,
      description: _descrizione,
      category: _categoria,
      price: _prezzo,
      image: _image,
    };

    console.log(productRequest);
    return this.http.post<any>(this.addProductUrl, productRequest, this.httpOptions);
  }


}
