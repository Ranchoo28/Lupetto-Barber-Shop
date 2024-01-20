import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})

export class CartService {

  private addUrl = 'http://localhost:8080/api/user/cart/add';
  private getUrl = 'http://localhost:8080/api/user/cart/get';
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  items = new Map<number, { product: any, quantity: number }>();

  pagamentoInCorso = false;

  email: string= '';

  settaEmailEPrendiCarrello() {
    if (typeof window !== 'undefined') {
      this.email = sessionStorage.getItem('email') || '';
      this.getItemsFromDB();
    }
  }

  constructor(private http: HttpClient) {
    this.settaEmailEPrendiCarrello();
  }


  visible = false;

  isCartVisible() {
    return this.visible;
  }

  isCartEmpty() {
    return this.items.size == 0;
  }

  inviaCarrelloAlDB() {
    if (typeof window !== 'undefined') {
      if(this.email == '') {
        return;
      }
      else{

        let prodotti: any[] = [];

        this.items.forEach((item) => {
          let prodotto = {
            id_product: item.product.idProduct,
            name: item.product.name,
            price: item.product.price,
            quantity: item.quantity
          };
          prodotti.push(prodotto);
        });

        let jsonBody = JSON.stringify(prodotti);

        this.http.post(`${this.addUrl}`, jsonBody, {
          params: {email: this.email},
          headers:this.httpOptions.headers});
      }
    }
  }

  addToCart(product: any) {
    if (this.items.has(product.idProduct)) {
      this.items.get(product.idProduct)!.quantity++;
    } else {
      this.items.set(product.idProduct, { product, quantity: 1 });
    }

    this.inviaCarrelloAlDB();

    if (!this.visible) {
      this.visible = true;
    }
  }

  increaseQuantity(productId: number) {
    if (this.items.has(productId)) {
      this.items.get(productId)!.quantity++;
    }

    this.inviaCarrelloAlDB();

  }

  decreaseQuantity(productId: number) {
    if (this.items.has(productId)) {
      const currentQuantity = this.items.get(productId)!.quantity;
      if (currentQuantity > 1) {
        this.items.get(productId)!.quantity--;
      } else {
        this.removeFromCart(productId);
      }
    }

    this.inviaCarrelloAlDB();

    if (this.isCartEmpty()) {
      this.visible = false;
    }
  }

  removeFromCart(productId: number) {
    this.items.delete(productId);

    this.inviaCarrelloAlDB();

    if (this.isCartEmpty()) {
      this.visible = false;
    }
  }

  getItemsFromDB() {
    this.http.get(`${this.getUrl}`, {
      params: {email: this.email},
      headers:this.httpOptions.headers});
  }

  getItems(){
    return Array.from(this.items.values());
  }

  getQuantity(productId: number) {
    if (this.items.has(productId)) {
      return this.items.get(productId)!.quantity;
    } else {
      return 0;
    }
  }

  getNumberOfItems() {
    return this.items.size;
  }

  getTotalQuantity() {
    let total = 0;
    for (const item of this.items.values()) {
      total += item.quantity;
    }
    return total;
  }

  clearCart() {
    this.items.clear();
    return this.items;
  }

  getTotalPrice() {
    let total = 0;
    for (const item of this.items.values()) {
      total += item.product.price * item.quantity;
    }
    return total;
  }

}
