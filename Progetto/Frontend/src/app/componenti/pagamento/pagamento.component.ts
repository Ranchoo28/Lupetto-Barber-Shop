import { Component, OnInit } from '@angular/core';
import { loadStripe, Stripe, StripeCardElement } from '@stripe/stripe-js';
import { PaymentService } from "../../services/payment.service";
import { CartService } from "../../services/cart.service";
import swal from "sweetalert";
import {Router} from "@angular/router";
import {BookingService} from "../../services/booking.service";

@Component({
  selector: 'app-pagamento',
  templateUrl: './pagamento.component.html',
  styleUrls: ['./pagamento.component.css']
})
export class PagamentoComponent implements OnInit {
  stripe: Stripe | null = null;
  card: StripeCardElement | null = null;

  constructor(private paymentService: PaymentService,
              private cartService: CartService,
              private router: Router,
              private bookingService: BookingService) {
  }

  async ngOnInit() {
    this.stripe = await loadStripe('pk_test_51OX4YdJkGG7c6naXyn6rwgvQpsiw9NmvKzKZHy4yuGRdJSzblmelo4ffIzTas8nvjKIDnj7U7H5Un17PXaGMWLt300tAGxdxF7');
    const elements = this.stripe!.elements();
    this.card = elements.create('card');
    this.card.mount('#card-element');
  }

  handlePayment() {
    if(this.router.url === '/prenota') {
      this.gestisciPagamentoPrenotazione();
    }
    else{
      this.gestisciPagamentoCarrello();
    }
  }

  async gestisciPagamentoCarrello() {
    if (!this.stripe || !this.card) {
      console.error('Stripe o card non sono stati inizializzati correttamente.');
      return;
    }

    // Assicurati che il valore restituito sia una stringa
    let paymentIntentId = await this.paymentService.creaIntentPagamento(this.cartService.getTotalPrice(),`Acquisto di ${this.cartService.getNumberOfItems()} prodotti`)
      .then(data => data.intent)
      .catch(error => {
        console.log(error);
        return '';
      });

    const result = await this.stripe.confirmCardPayment(paymentIntentId, {
      payment_method: {
        card: this.card,
      }
    });

    if (result.error) {
      const errorElement = document.getElementById('card-errors');
      if (errorElement) {
        // @ts-ignore
        errorElement.textContent = result.error.message;
      }
    } else if (result.paymentIntent && result.paymentIntent.status === 'succeeded') {
      this.cartService.clearCart();
      this.cartService.visible = false;
      this.cartService.pagamentoInCorso = false;

      swal({
        title: 'Pagamento Completato Con Successo',
        text: 'Grazie per aver acquistato da noi!',
        icon: 'success',
        timer: 2500
      });
    }
  }

  async gestisciPagamentoPrenotazione() {
    if (!this.stripe || !this.card) {
      console.error('Stripe o card non sono stati inizializzati correttamente.');
      return;
    }

    // Assicurati che il valore restituito sia una stringa
    let paymentIntentId = await this.paymentService.creaIntentPagamento(1000, 'Esempio di descrizione')
      .then(data => data.intent)
      .catch(error => {
        return '';
      });

    const result = await this.stripe.confirmCardPayment(paymentIntentId, {
      payment_method: {
        card: this.card,
      }
    });

    if (result.error) {
      const errorElement = document.getElementById('card-errors');
      if (errorElement) {
        // @ts-ignore
        errorElement.textContent = result.error.message;
      }
    } else if (result.paymentIntent && result.paymentIntent.status === 'succeeded') {

      this.bookingService.insertFromPayment(paymentIntentId).subscribe(data => {

        this.bookingService.pagamentoInCorso = false;



        swal({
          title: 'Prenotazione effettuata e pagamento completato con successo',
          text: 'Ti aspettiamo!',
          icon: 'success',
          timer: 2500
        }).then(() => {
          window.location.reload();
        },);
        this.bookingService.resetVars()
      });
    }
  }
}
