import {Component, OnInit, ViewChild} from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import {MatPaginator} from "@angular/material/paginator";
import {SelectionModel} from '@angular/cdk/collections';
import {BookingService} from "../../services/booking.service";
import {BookingDateService} from "../../services/booking-data.service";
import swal from "sweetalert";
import {TokenService} from "../../services/token.service";

@Component({
  selector: 'app-prenotazioni',
  templateUrl: './prenotazioni.component.html',
  styleUrls: ['./prenotazioni.component.css']
})
export class PrenotazioniComponent implements OnInit {
  dataRicerca!: string;
  role: string = this.tokenService.getRoleFromToken()!;
  email: string = this.tokenService.getEmailFromToken()!;
  dataSource!: MatTableDataSource<any>;
  displayedColumns: string[] = this.role === 'HAIRDRESSER' ? ['select', 'Servizio', 'Data', 'Orario', 'idBooking', 'user_surname','user_name', 'tel_number'] : ['select', 'Servizio', 'Data', 'Orario', 'idBooking'];
  selection = new SelectionModel<any>(true, []);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  isLoading = false;

  constructor(private bookingService: BookingService,private tokenService: TokenService) { }

  ngOnInit(): void {
    if(this.role=="USER"){
      this.visualizzaPrenotazioni();
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
          swal("La tua prenotazione è stata eliminata!", {
            icon: "success",
          },).then(() => {
          // Aggiorna la tabella per rimuovere la riga eliminata
          this.selection.clear();
          this.visualizzaPrenotazioni();
        });
      }, 1000);
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
      // Se non è stata selezionata nessuna data, mostra un messaggio di errore e interrompi l'esecuzione del metodo
      if (!this.dataRicerca) {
        swal('Errore: Nessuna data selezionata', {
          icon: "error",
          timer: 3000
        });
        return;
      }
      this.isLoading = true;
      setTimeout(() => {
        this.bookingService.getBookingsByDate(this.email, this.dataRicerca).subscribe((data) => {
          this.isLoading = false;
          if (data.length === 0) {
            swal(`Errore: Nessuna prenotazione trovata`, {
              icon: "error",
              timer: 3000
            });
          } else {
            swal(`Prenotazione trovate!`, {
              icon: "success",
              timer: 3000
            });
          }
          this.dataSource = new MatTableDataSource(data);
          this.dataSource.paginator = this.paginator;
          this.dataRicerca = '';
        });
      },1000);
    }

}
