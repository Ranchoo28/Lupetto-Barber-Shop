import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  hide = true;


  usernameControl = new FormControl('', [
    Validators.required,
    Validators.minLength(3),
    Validators.maxLength(20),
    Validators.pattern('[a-zA-Z0-9_-]+') // Assicurati che il pattern sia corretto
  ]);

  passwordControl = new FormControl('', [
    Validators.required,
    Validators.minLength(6)
  ]);

  loginForm: FormGroup = new FormGroup({
    username: this.usernameControl,
    password: this.passwordControl
  });


  onLogin(){
    if(this.loginForm.valid){
      console.log(this.loginForm.value);
    }
    else{
      console.log("Form non valido");
    }
  }



}
