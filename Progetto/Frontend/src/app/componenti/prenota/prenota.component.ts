import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {BookingService} from "../../services/booking.service";
import {ServiceService} from "../../services/service.service";
import {BookingDateService} from "../../services/booking-data.service";
import {MatSelectChange} from "@angular/material/select";
import {MatDatepickerInputEvent} from "@angular/material/datepicker";
import swal from "sweetalert";
import {JwttokenhandlerService} from "../../services/jwttokenhandler.service";
@Component({
  selector: 'app-prenota',
  templateUrl: './prenota.component.html',
  styleUrl: './prenota.component.css'
})
export class PrenotaComponent implements OnInit{
  servizi: any;
  orari: any;
  bookingDates: any[] = [];
  idService!: number;

  sessoSelected = false;
  servizioSelected = false;
  dataSelected = false;

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
  isLoading = false;

  constructor(private bookingService: BookingService,
              private serviceService: ServiceService,
              private bookingDateService: BookingDateService) {
  }

  ngOnInit(): void {
    this.prenotaForm = new FormGroup({
      sesso: this.sessoCheck,
      servizio: this.servizioCheck,
      data: this.dataCheck,
      orario: this.orarioCheck,
    });

    this.prenotaForm.get('servizio')!.disable();
    this.prenotaForm.get('data')!.disable();
    this.prenotaForm.get('orario')!.disable();
  }

  effettuaPrenotazione() {
    let idBookingDate = Number(this.prenotaForm.get('orario')!.value);
    let email = sessionStorage.getItem('email')!;
    this.isLoading = true;
    setTimeout(() => {
      this.bookingService.insertBooking(idBookingDate, email).subscribe((response) => {
        this.isLoading = false;
        swal("Prenotazione effettuata con successo", {
          icon: "success",
          timer: 3000
        }).then(() => {
          window.location.reload();
        });
      });
    }, 1000);
  }

  dateFilter = (d: Date | null): boolean => {
    const date = (d || new Date()).toISOString().split('T')[0];
    // Abilita solo le date che sono nell'array bookingDates
    return this.bookingDates.includes(date);
  };

  changeSesso($event: MatSelectChange) {

    this.prenotaForm.get('servizio')!.enable();
    this.prenotaForm.get('data')!.disable();
    this.prenotaForm.get('orario')!.disable();

    let sesso = $event.value;

    this.prenotaForm.get('servizio')!.setValue('');
    this.prenotaForm.get('data')!.setValue('');
    this.prenotaForm.get('orario')!.setValue('');

    this.serviceService.getServiceBySex(sesso!).subscribe((response) => {
      this.servizi = response.map((service: any) => {
        return {
          id: service.idService,
          name: service.name
        };
      });
    });
  }

  changeServizio($event: MatSelectChange) {

    this.prenotaForm.get('data')!.enable();
    this.prenotaForm.get('orario')!.disable();

    let idService = Number($event.value);

    this.servizioSelected = true;

    this.prenotaForm.get('data')!.setValue('');
    this.prenotaForm.get('orario')!.setValue('');
    this.bookingDateService.getBookingDateByService(idService).subscribe((response) => {
      this.bookingDates = response.map((bookingDate: any) => {
        const parts = bookingDate.date.split('-');
        const date = new Date(parts[0], parts[1] - 1, parts[2]);
        return date.toISOString().split('T')[0]; // Restituisce la data nel formato 'yyyy-mm-dd'
      });
    });
  }

  changeDate($event: MatDatepickerInputEvent<any, any>) {
    this.prenotaForm.get('orario')!.enable();

    this.prenotaForm.get('orario')!.setValue('');

    this.dataSelected = true;

    const localDate = new Date($event.value!);
    const offset = localDate.getTimezoneOffset();
    localDate.setMinutes(localDate.getMinutes() - offset);
    const localIsoString = localDate.toISOString().split('T')[0];
    this.bookingDateService.getBookingDateByTime(localIsoString, Number(this.prenotaForm.get('servizio')!.value)).subscribe((response) => {
      // Gestisci la risposta qui
      this.orari = response.map((bookingDate: any) => {
        return {
          "idBookingDate": bookingDate.idBookingDate,
          "ora": bookingDate.time
        };
      });
    });
  }

}
