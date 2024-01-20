import { Component, OnInit } from '@angular/core';
import {ProductsService} from "../../services/products.service";
import {CartService} from "../../services/cart.service";
import {DescrizioneProxyComponent} from "../descrizione-proxy/descrizione-proxy.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-prodotti',
  templateUrl: './prodotti.component.html',
  styleUrls: ['./prodotti.component.css']
})
export class ProdottiComponent implements OnInit {
  prodotti: any[] = [];

  constructor(private productsService: ProductsService,
              private cartService: CartService,
              public dialog: MatDialog) {}

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


  proxyDescrizione(idProdotto: number, event: Event){
    event.stopPropagation();
    this.productsService.getDatiProxy(idProdotto).subscribe(
      (data) => {
        this.productsService.descrizionePopUp = data.description;

        const dialogRef = this.dialog.open(DescrizioneProxyComponent, {
          width: '550px',
        });

      },
      (error) => {
        console.error(error);
      }
    );
  }

  addToCart(prodotto: any) {
    this.cartService.addToCart(prodotto);
  }

  mostraCarrello() {
    this.cartService.visible = true;
    this.isHidden = true;
  }

}
