import {Component} from '@angular/core';
import { FormControl, FormGroup, Validators} from "@angular/forms";
import {BookingService} from "../../services/booking.service";
import {catchError, of, tap} from "rxjs";


@Component({
  selector: 'app-prenota',
  templateUrl: './prenota.component.html',
  styleUrl: './prenota.component.css'
})
export class PrenotaComponent{

  constructor(private bookingService: BookingService) {
  }

  noteExample =`Esempio: Capelli capelli ricci, scuri ecc.`;
  regexNome =`[a-zA-Z]+`;
  regexCognome =`[a-zA-Z]+`;
  regexEmail =`^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$`;

  // vettore servizi
  servizi: string[] = ['Taglio e piega', 'Colore', 'Trattamento', 'Colore e trattamento', 'Taglio e piega + colore', 'Taglio e piega + trattamento', 'Taglio e piega + colore e trattamento'];

  ottieniOrariPrenotabili(): string[] {
    let slots: string[] = [];
    for (let i = 8; i <= 19; i++) {
      slots.push(i + ':00');
      slots.push(i + ':30');
    }
    return slots;
  }

  //vettore orari
  orari: string[] = this.ottieniOrariPrenotabili();

  nomeCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(2),
    Validators.maxLength(20),
    Validators.pattern(this.regexNome)
  ]);
  cognomeCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(2),
    Validators.maxLength(20),
    Validators.pattern(this.regexCognome)
  ]);
  emailCheck = new FormControl('', [
    Validators.required,
    Validators.pattern(this.regexEmail)
  ]);
  telefonoCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(10),
    Validators.maxLength(10),
  ]);
  servizioCheck = new FormControl('', [
    Validators.required
  ]);
  dataCheck = new FormControl('', [
    Validators.required
  ]);
  orarioCheck = new FormControl('', [
    Validators.required
  ]);


  prenotaForm!: FormGroup;


  ngOnInit(): void {
    this.prenotaForm = new FormGroup({
      nome: this.nomeCheck,
      cognome: this.cognomeCheck,
      email: this.emailCheck,
      telefono: this.telefonoCheck,
      servizio: this.servizioCheck,
      data: this.dataCheck,
      orario: this.orarioCheck,
    });
  }

  effettuaPrenotazione() {
    if (this.prenotaForm.valid) {
      this.bookingService.prenotaAppuntamento(
        // TODO cookie di autenticazione o email
        "email/username?",
        this.prenotaForm.get('servizio')?.value,
        this.prenotaForm.get('data')?.value,
        this.prenotaForm.get('ora')?.value,
      ).pipe(
        tap((data) => {
          console.log(data);
          this.prenotaForm.reset();
          console.log("Prenotazione effettuata con successo");
          // TODO
          // window.location.href = "/paginaConferma";
        }),
        catchError((error) => {
          console.error(error);
          return of(null); // Gestisce l'errore restituendo un observable
        })
      ).subscribe();
    } else {
      alert("Errore nella prenotazione, riprova");
    }
  }

}
