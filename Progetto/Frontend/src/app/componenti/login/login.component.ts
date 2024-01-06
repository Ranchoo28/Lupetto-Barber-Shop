import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  hide = true;


  usernameCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(3),
    Validators.maxLength(20),
    Validators.pattern('[a-zA-Z0-9_-]+') // Assicurati che il pattern sia corretto
  ]);

  passwordCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(6)
  ]);

  loginForm: FormGroup = new FormGroup({
    username: this.usernameCheck,
    password: this.passwordCheck
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
