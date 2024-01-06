import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import { HttpClient } from '@angular/common/http';
import {BookingService} from "../services/booking-service.service";
import {coerceStringArray} from "@angular/cdk/coercion";


@Component({
  selector: 'app-prenota',
  templateUrl: './prenota.component.html',
  styleUrl: './prenota.component.css'
})
export class PrenotaComponent implements OnInit {
  timeSlots: string[] = this.generateTimeSlots();

  constructor(private fb: FormBuilder) {
  }

  ngOnInit(): void {
  }

  prenotaForm = this.fb.group({
    nome: ['', Validators.required],
    cognome: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    telefono: ['', Validators.required],
    servizio: ['', Validators.required],
    data: ['', Validators.required],
    orario: ['', Validators.required],
    note: ['']
  });

  myFilter = (d: Date | null): boolean => {
    const date = d || new Date();
    const now = new Date();
    const startOfMonth = new Date(now.getFullYear(), now.getMonth(), 1);
    const endOfNextMonth = new Date(now.getFullYear(), now.getMonth() + 2, 0);
    const day = date.getDay();
    return date >= startOfMonth && date <= endOfNextMonth && day !== 0 && day !== 1;
  }

  generateTimeSlots(): string[] {
    let slots: string[] = [];
    for (let i = 8; i <= 19; i++) {
      slots.push(i + ':00');
      slots.push(i + ':30');
    }
    return slots;
  }

  inviaDati() {
    console.log(this.prenotaForm.value.nome,this.prenotaForm.value.cognome)
    // if (this.prenotaForm.valid) {
    //   this.bookingService.inviaDati(this.prenotaForm.value)
    //     .subscribe(response => {
    //       console.log(response);
    //     }, error => {
    //       console.error(error);
    //     });
    // }
  }
}
