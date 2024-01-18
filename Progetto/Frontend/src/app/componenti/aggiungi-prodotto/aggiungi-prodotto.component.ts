import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {ProductsService} from "../../services/products.service";
import swal from 'sweetalert';
import {catchError, of, tap, throwError} from "rxjs";

@Component({
  selector: 'app-aggiungi-prodotto',
  templateUrl: './aggiungi-prodotto.component.html',
  styleUrls: ['./aggiungi-prodotto.component.css']
})
export class AggiungiProdottoComponent implements OnInit {
  nomeProdotto = new FormControl('', Validators.required);
  descrizioneProdotto = new FormControl('', Validators.required);
  immagineProdotto = new FormControl('', Validators.required);
  categoriaProdotto = new FormControl('', Validators.required);
  prezzoProdotto = new FormControl('', Validators.required);

  aggiungiProdottoForm!: FormGroup;

  constructor(private productService: ProductsService) { }

  ngOnInit(): void {
    this.aggiungiProdottoForm = new FormGroup({
      nomeProdotto: this.nomeProdotto,
      descrizioneProdotto: this.descrizioneProdotto,
      immagineProdotto: this.immagineProdotto,
      categoriaProdotto: this.categoriaProdotto,
      prezzoProdotto: this.prezzoProdotto
    });
  }

  onFileSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files![0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      this.immagineProdotto.setValue(reader.result!.toString());
    };
  }

  aggiungiProdotto() {

    if (this.aggiungiProdottoForm.valid) {
      const email = sessionStorage.getItem('email')!;

      this.productService.addProduct(
        sessionStorage.getItem('email')!,
        this.aggiungiProdottoForm.get('nomeProdotto')!.value,
        this.aggiungiProdottoForm.get('descrizioneProdotto')!.value,
        this.aggiungiProdottoForm.get('immagineProdotto')!.value,
        this.aggiungiProdottoForm.get('categoriaProdotto')!.value,
        this.aggiungiProdottoForm.get('prezzoProdotto')!.value
      ).pipe(
        tap((data) => {
          this.aggiungiProdottoForm.reset();
          swal("Prodotto aggiunto con successo", {
            icon: "success",
            timer: 1000
          });
        }),
        catchError((error) => {
          swal(`Errore nell'inserimento del prodotto`, {
            icon: "error",
            timer: 2000
          });
          return of(null);
        })
      ).subscribe();
    }



  }

}
