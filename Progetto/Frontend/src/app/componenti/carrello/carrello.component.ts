// cart.component.ts
import { Component, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';
import swal from "sweetalert";
import {MatDialog} from "@angular/material/dialog";
import {PagamentoComponent} from "../pagamento/pagamento.component";

@Component({
  selector: 'app-carrello',
  templateUrl: './carrello.component.html',
  styleUrls: ['./carrello.component.css']
})
export class CarrelloComponent {

  items: Array<{ product: any, quantity: number }> = [];

  constructor(private cartService: CartService, public dialog: MatDialog) {
  }

  ngAfterContentChecked(): void {
    this.items = this.cartService.getItems();
  }

  increaseQuantity(productId: number) {
    console.log(productId);
    this.cartService.increaseQuantity(productId);
    this.items = this.cartService.getItems();
  }

  decreaseQuantity(productId: number) {
    this.cartService.decreaseQuantity(productId);
    this.items = this.cartService.getItems();
  }

  removeItem(productId: number) {
    swal({
      title: "Sicuro di voler eliminare il prodotto dal carrello?",
      icon: "warning",
      buttons: [
        'No, annulla',
        'Si, sicuro!'
      ],
      dangerMode: true,
    }).then((isConfirm) => {
      if (isConfirm) {
        this.cartService.removeFromCart(productId);
        this.items = this.cartService.getItems();
        swal({
          title: 'Cancellato',
          icon: 'success',
          timer: 500
        });
      }
    })

  }

  convertBase64ToImageUrl(base64: string): string {
    return `data:image/png;base64,${base64}`;
  }


  apriPagamento() {
    this.dialog.open(PagamentoComponent, {
      width: '8000px', // Imposta la larghezza che preferisci
      // Puoi anche passare dei dati se necessario
    });
  }

}
