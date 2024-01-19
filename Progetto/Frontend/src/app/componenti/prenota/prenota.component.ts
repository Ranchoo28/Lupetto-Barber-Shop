import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {BookingService} from "../../services/booking.service";
import {ServiceService} from "../../services/service.service";
import {BookingDateService} from "../../services/booking-data.service";
import {MatSelectChange} from "@angular/material/select";
import {MatDatepickerInputEvent} from "@angular/material/datepicker";
import swal from "sweetalert";
import {JwttokenhandlerService} from "../../services/jwttokenhandler.service";
import {CheckoutComponent} from "../checkout/checkout.component";
import {MatDialog} from "@angular/material/dialog";
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

  isDisabled: boolean = true;

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
              private bookingDateService: BookingDateService,
              private jwtTokenHandler: JwttokenhandlerService,
              public dialog: MatDialog) {
  }

  ngOnInit(): void {

    this.bookingService.resetVars();

    this.prenotaForm = new FormGroup({
      sesso: this.sessoCheck,
      servizio: this.servizioCheck,
      data: this.dataCheck,
      orario: this.orarioCheck,
    });

    this.prenotaForm.get('servizio')!.disable();
    this.prenotaForm.get('data')!.disable();
    this.prenotaForm.get('orario')!.disable();
    this.isDisabled = true;
  }

  ngAfterContentChecked(): void {
    if(!this.bookingService.pagamentoInCorso) {
      this.dialog.closeAll();
    }
  }

  dateFilter = (d: Date | null): boolean => {
    const date = (d || new Date()).toISOString().split('T')[0];
    return this.bookingDates.includes(date);
  };

  changeSesso($event: MatSelectChange) {

    this.prenotaForm.get('servizio')!.enable();
    this.prenotaForm.get('data')!.disable();
    this.prenotaForm.get('orario')!.disable();

    this.isDisabled = true;

    let sesso = $event.value;

    this.prenotaForm.get('servizio')!.setValue('');
    this.prenotaForm.get('data')!.setValue('');
    this.prenotaForm.get('orario')!.setValue('');

    this.serviceService.getServiceBySex(sesso!).subscribe((response) => {

      this.servizi = response.map((service: any) => {
        return {
          id: service.idService,
          name: service.name,
          prezzo: service.price
        };
      });
    });
  }

  changeServizio($event: MatSelectChange) {

    let hlpID = Number($event.value);
    let hlpPrezzo = this.servizi.find((service: any) => service.id === hlpID).prezzo;

    this.bookingService.prezzoTrattamento = hlpPrezzo;

    this.prenotaForm.get('data')!.enable();
    this.prenotaForm.get('orario')!.disable();

    this.isDisabled = true;

    let idService = Number($event.value);

    this.prenotaForm.get('data')!.setValue('');
    this.prenotaForm.get('orario')!.setValue('');
    this.bookingDateService.getBookingDateByService(idService).subscribe((response) => {
      this.bookingDates = response.map((bookingDate: any) => {
        const parts = bookingDate.date.split('-');
        const date = new Date(parts[0], parts[1] - 1, parts[2]);
        return date.toISOString().split('T')[0];
      });
    });
  }

  changeDate($event: MatDatepickerInputEvent<any, any>) {
    this.prenotaForm.get('orario')!.enable();
    this.prenotaForm.get('orario')!.setValue('');

    this.isDisabled = true;

    const localDate = new Date($event.value!);
    const offset = localDate.getTimezoneOffset();
    localDate.setMinutes(localDate.getMinutes() - offset);
    const localIsoString = localDate.toISOString().split('T')[0];
    this.bookingDateService.getBookingDateByTime(localIsoString, Number(this.prenotaForm.get('servizio')!.value)).subscribe((response) => {
      this.orari = response.map((bookingDate: any) => {
        return {
          "idBookingDate": bookingDate.idBookingDate,
          "ora": bookingDate.time
        };
      });
    });
  }

  changeOrario() {
    this.isDisabled = false;
  }

  effettuaPrenotazione() {

      this.settaVariabili();

      this.bookingService.insertBooking(this.bookingService.bookingDate, this.bookingService.email).subscribe(data => {
        swal({
          title: 'Prenotazione Effettuata Con Successo',
          text: 'Grazie per aver prenotato da noi!',
          icon: 'success',
          timer: 2500
        }).then(() => {
          window.location.reload();
        });
      });



  }


  settaVariabili() {
    this.bookingService.setVars(
      Number(this.prenotaForm.get('orario')!.value),
      this.jwtTokenHandler.getEmail(sessionStorage.getItem('accessToken')!));
  }


  apriPagamento() {

    this.settaVariabili();

    this.bookingService.pagamentoInCorso = true;

    const dialogRef = this.dialog.open(CheckoutComponent, {
      width: '550px',
      data: {
        items: 10,
        prezzo: 500,
      }
    });


  }

}
