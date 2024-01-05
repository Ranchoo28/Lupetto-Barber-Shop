import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  hide = true;

  usernameInvalidMessage ="L'username deve essere compreso tra 3 e 20 caratteri e può contenere solo lettere, numeri, underscore e trattini";

  loginForm = new FormGroup({
    username: new  FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(20),
      Validators.pattern('^[a-zA-Z0-9_-]+$') // Permette lettere, numeri, underscore e trattini
    ]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)])
  });

  get username() { return this.loginForm.get('username'); }
  get password() { return this.loginForm.get('password'); }

  onLogin(){
    if(this.loginForm.valid){
      console.log(this.loginForm.value);
    }
    else{
      console.log("Form non valido");
    }
  }



}
