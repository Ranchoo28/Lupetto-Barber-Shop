import {Component, Input} from '@angular/core';
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrl: './password.component.css'
})
export class PasswordComponent {

  hide = true;

  label ="Password"
  passwordInvalidMessage =`
  La password deve essere compresa tra 6 e 30 caratteri
  e può contenere lettere, numeri e caratteri speciali \n \n
  ( !@#$%&*()_+{}[].? )  `;

  @Input() passwordControl : FormControl = new FormControl('');

}
