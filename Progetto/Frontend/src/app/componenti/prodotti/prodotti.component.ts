import { Component, OnInit } from '@angular/core';
import {ProductsService} from "../../services/products.service";
import {CartService} from "../../services/cart.service";

@Component({
  selector: 'app-prodotti',
  templateUrl: './prodotti.component.html',
  styleUrls: ['./prodotti.component.css']
})
export class ProdottiComponent implements OnInit {
  prodotti: any[] = [];

  constructor(private productsService: ProductsService, private cartService: CartService) {}

  isHidden = false;

  ngOnInit() {
    this.productsService.getProducts().subscribe(
      (data) => {
        this.prodotti = data;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  ngAfterContentChecked(): void {
    this.isHidden = this.cartService.visible;
  }

  addToCart(prodotto: any) {
    this.cartService.addToCart(prodotto);
  }

  mostraCarrello() {
    this.cartService.visible = true;
    this.isHidden = true;
  }

}
