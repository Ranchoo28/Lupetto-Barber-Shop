import { Component } from '@angular/core';
import { AuthenticationService } from "../../services/authentication.service";
import swal from "sweetalert";
import {CartService} from "../../services/cart.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})

export class NavbarComponent {


  constructor(protected authService: AuthenticationService,
              private cartService: CartService) {
  }

  chiudiCarrello() {
    this.cartService.visible = false;
  }

  logout() {
    this.authService.logout();

    this.cartService.clearCart();

    console.log("Logout effettuato");
    swal(`Logout effettuato con successo.
                  A presto !`, {
      icon: "success",
      timer: 1000
    });

  }


}
