import { Component } from '@angular/core';
import {ProductsService} from "../../services/products.service";
import {CartService} from "../../services/cart.service";

@Component({
  selector: 'app-descrizione-proxy',
  templateUrl: './descrizione-proxy.component.html',
  styleUrl: './descrizione-proxy.component.css'
})
export class DescrizioneProxyComponent {

  constructor(private productService: ProductsService, private cartService: CartService) {
  }

  descrizione: string = "ciao";

  ngOnInit() {
    this.cartService.descrizionePopUp = true;
    this.descrizione= this.productService.descrizionePopUp
  }

}
