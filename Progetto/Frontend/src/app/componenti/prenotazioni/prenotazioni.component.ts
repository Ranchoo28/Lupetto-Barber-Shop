import {Component, OnInit, ViewChild} from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import {MatPaginator} from "@angular/material/paginator";
import {SelectionModel} from '@angular/cdk/collections';
import {BookingService} from "../../services/booking.service";
import {BookingDateService} from "../../services/booking-data.service";
import {JwttokenhandlerService} from "../../services/jwttokenhandler.service";
import swal from "sweetalert";


@Component({
  selector: 'app-prenotazioni',
  templateUrl: './prenotazioni.component.html',
  styleUrls: ['./prenotazioni.component.css']
})
export class PrenotazioniComponent implements OnInit {

  dataRicerca!: string;
  role: string = this.jtwtoken.getRole(sessionStorage.getItem('accessToken')!);
  email: string = this.jtwtoken.getEmail(sessionStorage.getItem('accessToken')!);
  dataSource!: MatTableDataSource<any>;
  displayedColumns: string[] = this.role === 'HAIRDRESSER' ? ['select', 'Servizio', 'Data', 'Orario', 'idBooking', 'user_surname','user_name', 'tel_number'] : ['select', 'Servizio', 'Data', 'Orario', 'idBooking','intent'];
  selection = new SelectionModel<any>(true, []);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  isLoading = false;
  isOneSelected = false;


  onCheckboxChange(row: any): void {
    if (this.selection.isSelected(row)) {
      this.selection.deselect(row);
    } else {
      this.selection.clear();
      this.selection.select(row);
    }
  }


constructor(private bookingService: BookingService, private jtwtoken:JwttokenhandlerService) { }

  ngOnInit(): void {
    if(this.role=="USER"){
      this.visualizzaPrenotazioni();
      this.selection.changed.subscribe(() => {
        this.isOneSelected = this.selection.selected.length > 0;
      });
    }
  }

  visualizzaPrenotazioni() {
    this.bookingService.getUserBooking(this.email).subscribe((data) => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
    });
  }
  visualizzaPrenotazioniHairD() {
    this.dataRicerca = '';
    this.isLoading = true;
    setTimeout(() => {

      this.bookingService.getAllBooking(this.email).subscribe((data) => {
        if (data.length === 0) {
          swal(`Errore: Nessuna prenotazione trovata`, {
            icon: "error",
            timer: 800
          });
        }
        else{
          swal(`Prenotazione trovate!`, {
            icon: "success",
            timer: 800
          });
        }
        this.isLoading = false;
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.paginator = this.paginator;
      });
    }, 1000);
  }
  eliminaPrenotazione() {
  const selected = this.selection.selected;
  if (selected.length === 1) {
    const idBooking = selected[0].id_booking;
    swal({
      title: "Sei sicuro?",
      text: "Una volta eliminata, non sarai in grado di recuperare questa prenotazione!",
      icon: "warning",
      buttons: ["Annulla", "OK"],
      dangerMode: true,
    })
    .then((willDelete) => {
    if (willDelete) {
      this.isLoading = true;
      setTimeout(() => {
        this.bookingService.deleteBooking(idBooking, this.email).subscribe(() => {},);
        this.isLoading = false;
        console.log(selected[0].intent);
        if (selected[0].intent !== null ) {
          swal("La tua prenotazione è stata eliminata!"+ "\nContatta il Parrucchiere per ricevere il tuo rimborso", {
            icon: "success",
          }).then(() => {
            this.selection.clear();
            this.visualizzaPrenotazioni();
          });
        } else {
          swal("Prenotazione eliminata con successo", {
            icon: "success",
          }).then(() => {
            this.selection.clear();
            this.visualizzaPrenotazioni();
          });
        }
      }, 2000);
    }
  });
  } else {
    if(selected.length === 0){
    swal('Errore: Nessuna prenotazione selezionata', {
      icon: "error",
      timer: 3000
    });
  }else {
      swal('Errore: Seleziona solo una prenotazione da eliminare', {
        icon: "error",
        timer: 3000
      });
    }
  }
}
  visualizzaPrenotazioniHairDbyDate() {

      if (!this.dataRicerca) {
        swal('Errore: Nessuna data selezionata', {
          icon: "error",
          timer: 1700
        });
        return;
      }
      this.isLoading = true;
      setTimeout(() => {

        const localDate = new Date(this.dataRicerca);
        const offset = localDate.getTimezoneOffset();
        localDate.setMinutes(localDate.getMinutes() - offset);
        const localIsoString = localDate.toISOString().split('T')[0];

        this.bookingService.getBookingsByDate(this.email, localIsoString).subscribe((data) => {
          this.isLoading = false;
          if (data.length === 0) {
            swal(`Errore: Nessuna prenotazione trovata`, {
              icon: "error",
              timer: 900
            });
          } else {
            swal(`Prenotazione trovate!`, {
              icon: "success",
              timer: 900
            });
          }
          this.dataSource = new MatTableDataSource(data);
          this.dataSource.paginator = this.paginator;
          this.dataRicerca = '';
        });
      },1000);
    }

}
