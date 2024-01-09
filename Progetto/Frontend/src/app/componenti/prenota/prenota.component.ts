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
      servizio: this.servizioCheck,
      data: this.dataCheck,
      orario: this.orarioCheck,
    });
  }

  dateFilter = (d: Date | null): boolean => {
    const day = (d || new Date()).getDay();
    // Prevent Saturday and Sunday from being selected.
    return day !== 0 && day !== 1;
  };

  effettuaPrenotazione() {
    if (this.prenotaForm.valid) {

      const dataUTC = new Date(this.prenotaForm.get('data')?.value);
      const dataLocale = dataUTC.toLocaleDateString('en-CA');

      this.bookingService.prenotaAppuntamento(
        // TODO cookie di autenticazione o email
        "email? boh",
        this.prenotaForm.get('servizio')?.value,
        dataLocale,
        this.prenotaForm.get('orario')?.value,
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
