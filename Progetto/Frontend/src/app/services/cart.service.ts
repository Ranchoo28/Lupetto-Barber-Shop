import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  items = new Map<number, { product: any, quantity: number }>();

  constructor() { }

  visible = false;

  isCartVisible() {
    return this.visible;
  }

  isCartEmpty() {
    return this.items.size == 0;
  }

  addToCart(product: any) {
    if (this.items.has(product.idProduct)) {
      this.items.get(product.idProduct)!.quantity++;
    } else {
      this.items.set(product.idProduct, { product, quantity: 1 });
    }

    if (!this.visible) {
      this.visible = true;
    }
  }

  increaseQuantity(productId: number) {
    if (this.items.has(productId)) {
      this.items.get(productId)!.quantity++;
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

    if (this.isCartEmpty()) {
      this.visible = false;
    }
  }

  removeFromCart(productId: number) {
    this.items.delete(productId);

    if (this.isCartEmpty()) {
      this.visible = false;
    }
  }

  getItems() {
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
