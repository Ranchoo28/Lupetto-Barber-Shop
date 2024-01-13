import { Component } from '@angular/core';
import {CartService} from "../../services/cart.service";

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent {

  constructor(private cartService: CartService) {
  }

  prezzo = this.cartService.getTotalPrice();
  quantita = this.cartService.getNumberOfItems();


}
