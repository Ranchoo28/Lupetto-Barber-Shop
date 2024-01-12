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
        role: string = sessionStorage.getItem("role") || '';
        dataSource!: MatTableDataSource<any>;
        displayedColumns: string[] = this.role === 'HAIRDRESSER' ? ['select', 'Servizio', 'Data', 'Orario', 'idBooking', 'user_surname','user_name'] : ['select', 'Servizio', 'Data', 'Orario', 'idBooking'];
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
          console.log("Elimina prenotazione");
          // Controlla se almeno una riga è selezionata
          if (this.selection.selected.length > 0) {
            // Itera su ogni riga selezionata
            this.selection.selected.forEach(row => {
              console.log(row);
              this.bookingService.deleteBooking(row.id_booking,sessionStorage.getItem("email")!).subscribe((data) => {
                swal("Prenotazione rimossa", {
                  icon: "success",
                  timer: 3000
                })
              });
              });


            // Aggiorna la tabella
            this.visualizzaPrenotazioni();

            // Pulisci la selezione
            this.selection.clear();
          } else {
            console.log("Nessuna riga selezionata");
          }
        }

    }
