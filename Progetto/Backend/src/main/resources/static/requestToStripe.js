var stripe = Stripe('pk_test_51OX4YdJkGG7c6naXyn6rwgvQpsiw9NmvKzKZHy4yuGRdJSzblmelo4ffIzTas8nvjKIDnj7U7H5Un17PXaGMWLt300tAGxdxF7');
var elements = stripe.elements();
var card = elements.create('card');

card.mount('#card-element');

var form = document.getElementById('payment-form');

form.addEventListener('submit', function(event) {
    event.preventDefault();

    //da sistemare: qui bisogna chiamare il BE per ottenere l'id del payment intent (http://localhost:8080/api/payment/create?amount=100&description=culoculo)
    //N.B. amount è in centesimi di euro
    //per ora uso un id di un payment intent già creato dall'url del servizio REST del BE
    var paymentIntentId = 'pi_3OX7hKJkGG7c6naX0wZI5L8o_secret_LG512hM8GvG0czL4mFodw3l6Z';


    stripe.confirmCardPayment(paymentIntentId, {
        payment_method: {
            card: card,
        }
    }).then(function(result) {
        if (result.error) {
            var errorElement = document.getElementById('card-errors');
            errorElement.textContent = result.error.message;
        } else {
            console.log('Pagamento confermato:', result.paymentIntent);
        }
    });
});