import { Component, OnInit } from '@angular/core';
import {ProductsService} from "../../services/products.service";
import {CartService} from "../../services/cart.service";
import {DescrizioneProxyComponent} from "../descrizione-proxy/descrizione-proxy.component";
import {MatDialog} from "@angular/material/dialog";
import {JwttokenhandlerService} from "../../services/jwttokenhandler.service";

@Component({
  selector: 'app-prodotti',
  templateUrl: './prodotti.component.html',
  styleUrls: ['./prodotti.component.css']
})
export class ProdottiComponent implements OnInit {
  prodotti: any[] = [];
  role: string = "";

  constructor(private productsService: ProductsService,
              private cartService: CartService,
              public dialog: MatDialog,
              private jwtTokenService:JwttokenhandlerService) {}

  isHidden = false;

  ngOnInit() {
    this.productsService.getProducts().subscribe({
      next: (data) => {
        this.prodotti = data;
      },
      error: (error) => {
        console.error(error);
      }
    });

    if (typeof sessionStorage !== 'undefined') {
      const accessToken = sessionStorage.getItem('accessToken');
      if (accessToken !== null) {
        this.role = this.jwtTokenService.getRole(accessToken);
      }
    }

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
