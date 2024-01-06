import {Component, EventEmitter, Output} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

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

  // Crea un FormControl con i validatori appropriati.
  passwordCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(30),
    Validators.pattern('[a-zA-Z0-9!@#$%&*()_\+.?]+')
  ]);

  // Usa EventEmitter per comunicare il FormControl al parent component.
  @Output() passwordControlOut: EventEmitter<FormControl> = new EventEmitter();

  // Emetti il FormControl appena il componente è pronto, ad esempio nel metodo ngOnInit.
  ngOnInit(): void {
    this.passwordControlOut.emit(this.passwordCheck);
  }

}
