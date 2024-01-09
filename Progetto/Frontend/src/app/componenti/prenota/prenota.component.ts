import {Component, OnInit} from '@angular/core';
import { FormControl, FormGroup, Validators} from "@angular/forms";
import {BookingService} from "../../services/booking.service";
import {catchError, of, tap} from "rxjs";
import {ServiceService} from "../../service.service";


@Component({
  selector: 'app-prenota',
  templateUrl: './prenota.component.html',
  styleUrl: './prenota.component.css'
})
export class PrenotaComponent implements OnInit{

  constructor(private bookingService: BookingService, private serviceService: ServiceService) {
  }


  // vettore servizi
  servizi: string[] = [];

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

  sessoCheck = new FormControl('', [
    Validators.required
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
      sessoCheck: this.sessoCheck,
      servizio: this.servizioCheck,
      data: this.dataCheck,
      orario: this.orarioCheck,
    });

    this.sessoCheck.valueChanges.subscribe((newSexValue) => {
      this.serviceService.getServiceBySex(newSexValue!).subscribe((response) => {
        // Gestisci la risposta qui
        console.log(response);
        this.servizi = response.map((service: any) => service.name);
      });
    });
  }




  dateFilter = (d: Date | null): boolean => {
    const day = (d || new Date()).getDay();
    // Prevent Saturday and Sunday from being selected.
    return day !== 0 && day !== 1;
  };

  effettuaPrenotazione() {
    if (this.prenotaForm.valid) {

    }
  }

}
