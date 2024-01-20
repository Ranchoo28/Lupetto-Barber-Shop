import { Component, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';
import swal from "sweetalert";
import {MatDialog} from "@angular/material/dialog";
import {CheckoutComponent} from "../checkout/checkout.component";

@Component({
  selector: 'app-carrello',
  templateUrl: './carrello.component.html',
  styleUrls: ['./carrello.component.css']
})
export class CarrelloComponent {

  items: Array<{ product: any, quantity: number }> = [];

  constructor(private cartService: CartService, public dialog: MatDialog) {
  }

  visible: boolean = false;

  ngOnInit(): void {
    this.cartService.settaEmailEPrendiCarrello();
  }

  ngAfterContentChecked(): void {
    this.items = this.cartService.getItems();
    this.visible = this.cartService.isCartVisible();

    this.cartService

    if(!this.cartService.pagamentoInCorso && !this.cartService.descrizionePopUp) {
      this.dialog.closeAll();
    }
  }

  nascondiCarrello() {
    this.cartService.visible = false;
  }

  increaseQuantity(productId: number) {
    this.cartService.increaseQuantity(productId);
    this.items = this.cartService.getItems();
  }

  decreaseQuantity(productId: number) {
    if(this.cartService.getQuantity(productId) == 1) {
      this.removeItem(productId);
      return;
    }
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
          timer: 700
        });
      }
    })

  }


  apriPagamento() {
    if (this.cartService.getItems().length == 0) {
      swal({
        title: 'Carrello vuoto',
        text: 'Aggiungi almeno un prodotto al carrello',
        icon: 'warning',
        timer: 800
      });
      return;
    }

    this.cartService.pagamentoInCorso = true;

    const dialogRef = this.dialog.open(CheckoutComponent, {
      width: '550px',
      data: {
        items: this.cartService.getItems(),
        prezzo: this.cartService.getTotalPrice()
      }
    });

  }

}
