import { Component, OnInit } from '@angular/core';
import { loadStripe, Stripe, StripeElements, StripeCardElement } from '@stripe/stripe-js';
import {PaymentService} from "../../services/payment.service";
import {CartService} from "../../services/cart.service";

@Component({
  selector: 'app-pagamento',
  templateUrl: './pagamento.component.html',
  styleUrls: ['./pagamento.component.css']
})
export class PagamentoComponent implements OnInit {
  stripe: Stripe | null = null;
  card: StripeCardElement | null = null;

  constructor(private paymentService: PaymentService, private cartService: CartService) {
  }

  async ngOnInit() {
    this.stripe = await loadStripe('pk_test_51OX4YdJkGG7c6naXyn6rwgvQpsiw9NmvKzKZHy4yuGRdJSzblmelo4ffIzTas8nvjKIDnj7U7H5Un17PXaGMWLt300tAGxdxF7');
    // @ts-ignore
    const elements = this.stripe.elements();
    this.card = elements.create('card');
    this.card.mount('#card-element');
  }

  async gestisciPagamento() {
    if (!this.stripe || !this.card) {
      console.error('Stripe o card non sono stati inizializzati correttamente.');
      return;
    }
    
    const paymentIntentId = 'pi_3OX7hKJkGG7c6naX0wZI5L8o_secret_LG512hM8GvG0czL4mFodw3l6Z';

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
      console.log('Pagamento confermato:', result.paymentIntent);
    }
  }
}
