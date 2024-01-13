import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  items = new Map<number, { product: any, quantity: number }>();

  constructor() { }

  addToCart(product: any) {
    if (this.items.has(product.id)) {
      this.items.get(product.id)!.quantity++;
    } else {
      this.items.set(product.id, { product, quantity: 1 });
    }
  }

  increaseQuantity(productId: number) {
    if (this.items.has(productId)) {
      this.items.get(productId)!.quantity++;
      console.log(this.items.get(productId)!.quantity);
    }
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
  }

  removeFromCart(productId: number) {
    this.items.delete(productId);
  }

  getItems() {
    return Array.from(this.items.values());
  }


}
