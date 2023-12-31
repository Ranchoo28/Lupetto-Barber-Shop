import {Component, OnInit} from '@angular/core';
import { FormControl, FormGroup, Validators} from "@angular/forms";
import {BookingService} from "../../services/booking.service";
import {catchError, of, switchMap, tap} from "rxjs";
import {ServiceService} from "../../services/service.service";
import {BookingDateService} from "../../services/booking-data.service";


@Component({
  selector: 'app-prenota',
  templateUrl: './prenota.component.html',
  styleUrl: './prenota.component.css'
})
export class PrenotaComponent implements OnInit{
  servizi: any;
  orari: string[] = [];
  bookingDates: any[] = [];
  idService!: number;

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

  constructor(private bookingService: BookingService, private serviceService: ServiceService,private bookingDateService: BookingDateService) {
  }
  ngOnInit(): void {
    this.prenotaForm = new FormGroup({
      sessoCheck: this.sessoCheck,
      servizio: this.servizioCheck,
      data: this.dataCheck,
      orario: this.orarioCheck,
    });

    this.sessoCheck.valueChanges.subscribe((newSexValue) => {
      if (newSexValue == 'M'){
        this.orari = this.ottieniOrariPrenotabiliUomo();
      }else {
        this.orari = this.ottieniOrariPrenotabiliDonna();
      }
      this.serviceService.getServiceBySex(newSexValue!).subscribe((response) => {
        // Gestisci la risposta qui
        console.log(response);
        this.servizi = response.map((service: any) => {
          return {
            id: service.idService,
            name: service.name
          };
        });
      });
    });

    this.prenotaForm.get('servizio')!.valueChanges.pipe(
      switchMap((value) => {
        return this.bookingDateService.getBookingDateByService(Number(value))
      })
    ).subscribe((response) => {
      // Gestisci la risposta qui
      console.log(response);
      this.bookingDates = response.map((bookingDate: any) => {
        const parts = bookingDate.date.split('-');
        const date = new Date(parts[0], parts[1] - 1, parts[2]);
        return date.toISOString().split('T')[0]; // Restituisce la data nel formato 'yyyy-mm-dd'
      });
    });

  }
  effettuaPrenotazione() {

  }

  ottieniOrariPrenotabiliUomo(): string[] {
    let slots: string[] = [];
    for (let i = 8; i <= 19; i++) {
      slots.push(i + ':00');
      slots.push(i + ':30');
    }
    return slots;
  }
  ottieniOrariPrenotabiliDonna(): string[] {
    let slots: string[] = [];
    for (let i = 8; i <= 19; i++) {
      slots.push(i + ':00');
    }
    return slots;
  }

  dateFilter = (d: Date | null): boolean => {
    const date = (d || new Date()).toISOString().split('T')[0];
    // Abilita solo le date che sono nell'array bookingDates
    return this.bookingDates.includes(date);
  };



}
