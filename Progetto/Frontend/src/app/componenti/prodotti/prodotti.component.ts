import { Component, OnInit } from '@angular/core';
import {ProductsService} from "../../services/products.service";

@Component({
  selector: 'app-prodotti',
  templateUrl: './prodotti.component.html',
  styleUrls: ['./prodotti.component.css']
})
export class ProdottiComponent implements OnInit {
  prodotti: any[] = []; // o una tipizzazione più specifica se disponibile

  constructor(private productsService: ProductsService) {}

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

  convertBase64ToImageUrl(base64: string): string {
    return `data:image/png;base64,${base64}`;
  }
}
