import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  private baseUrl = 'http://localhost:8080';
  private productsUrl = '/api/products';
  private addProductUrl = '/api/hairdresser/addProduct';
  private deleteProductUrl = '/api/hairdresser/deleteProduct';

  descrizionePopUp: string = "";

  constructor(private http: HttpClient) {}

  getProducts(): Observable<any[]> {
    return this.http.get<any[]>(this.productsUrl);
  }

  addProduct(email: string, name: string, description: string, category: string, price: number, image: string ): Observable<any> {
    let productRequest = {
      name: name,
      description: description,
      category: category,
      price: price,
      image: image,
    };

    let httpOptions = {
      params: {email},
      headers: new HttpHeaders({
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      })
    };

    return this.http.post(this.baseUrl+this.addProductUrl, productRequest, httpOptions);
  }


  getDatiProxy(idProdotto: number){
    return this.http.get<any>('http://localhost:8080/api/hairdresser/productProxy?id_product='+idProdotto);
  }
}
