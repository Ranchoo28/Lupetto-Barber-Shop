import { Component, Input } from '@angular/core';
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

  @Input() usernameControl : FormControl = new FormControl('');


}
