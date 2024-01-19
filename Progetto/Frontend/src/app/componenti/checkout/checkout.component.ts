import { Component } from '@angular/core';
import {CartService} from "../../services/cart.service";
import {Router} from "@angular/router";
import {BookingService} from "../../services/booking.service";

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent {

  constructor(private cartService: CartService,
              private router: Router,
              private bookingService: BookingService) {
  }

  prezzo: number = 0;
  quantita: number = 0;

  ngOnInit(): void {
    if(this.router.url === '/prenota') {
      this.prezzo=this.bookingService.prezzoTrattamento;
    }
    else{
      this.prezzo = this.cartService.getTotalPrice();
      this.quantita = this.cartService.getNumberOfItems();

    }
  }


}
