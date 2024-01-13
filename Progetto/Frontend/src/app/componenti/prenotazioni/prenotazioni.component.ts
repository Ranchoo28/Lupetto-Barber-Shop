import {Component, OnInit, ViewChild} from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import {MatPaginator} from "@angular/material/paginator";
import {SelectionModel} from '@angular/cdk/collections';
import {BookingService} from "../../services/booking.service";
import {BookingDateService} from "../../services/booking-data.service";
import swal from "sweetalert";

@Component({
  selector: 'app-prenotazioni',
  templateUrl: './prenotazioni.component.html',
  styleUrls: ['./prenotazioni.component.css']
})
export class PrenotazioniComponent implements OnInit {
  dataRicerca!: string;
  role: string = sessionStorage.getItem("role") || '';
  dataSource!: MatTableDataSource<any>;
  displayedColumns: string[] = this.role === 'HAIRDRESSER' ? ['select', 'Servizio', 'Data', 'Orario', 'idBooking', 'user_surname','user_name', 'tel_number'] : ['select', 'Servizio', 'Data', 'Orario', 'idBooking'];
  selection = new SelectionModel<any>(true, []);
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private bookingService: BookingService, private bookingdataservice:BookingDateService) { }

  ngOnInit(): void {
    if(this.role=="USER"){
      this.visualizzaPrenotazioni();
    }else{
      this.visualizzaPrenotazioniHairD(sessionStorage.getItem("email")!);
    }
  }

  visualizzaPrenotazioni() {
    this.bookingService.getUserBooking(sessionStorage.getItem("email")!).subscribe((data) => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
    });
  }
  private visualizzaPrenotazioniHairD(email: string) {
    this.bookingService.getAllBooking(email).subscribe((data) => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
    });
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
        this.bookingService.deleteBooking(idBooking, sessionStorage.getItem("email")!).subscribe(() => {},);
          swal("La tua prenotazione è stata eliminata!", {
            icon: "success",
          },).then(() => {
          // Aggiorna la tabella per rimuovere la riga eliminata
          this.selection.clear();
          this.visualizzaPrenotazioni();
        });
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
  this.bookingService.getBookingsByDate(sessionStorage.getItem("email")!, this.dataRicerca).subscribe((data) => {
      if (data.length === 0) {
        swal(`Errore: Nessuna prenotazione trovata`, {
          icon: "error",
          timer: 3000
        });

      }else {
        swal(`Prenotazione trovate!`, {
          icon: "success",
          timer: 3000
        });
      }
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;

    });
  }

}
