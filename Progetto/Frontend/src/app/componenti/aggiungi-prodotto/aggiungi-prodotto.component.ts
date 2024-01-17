import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

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

  constructor() { }

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

    console.log(this.aggiungiProdottoForm.value);
  }

}
