import {Component, EventEmitter, Output} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-username',
  templateUrl: './username.component.html',
  styleUrl: './username.component.css'
})
export class UsernameComponent {

  label ="Nome Utente"
  usernameInvalidMessage =`
  L'username deve essere compreso tra 3 e 20 caratteri
  e può contenere solo lettere, numeri, underscore e trattini \n
  (ES: MarioRossi_123))`;

  // Crea un FormControl con i validatori appropriati.
  usernameCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(3),
    Validators.maxLength(20),
    Validators.pattern('[a-zA-Z0-9_-]+')
  ]);

  // Usa EventEmitter per comunicare il FormControl al parent component.
  @Output() usernameControlOut: EventEmitter<FormControl> = new EventEmitter()

  // Emetti il FormControl appena il componente è pronto, ad esempio nel metodo ngOnInit.
  ngOnInit(): void {
    this.usernameControlOut.emit(this.usernameCheck);
  }

}
