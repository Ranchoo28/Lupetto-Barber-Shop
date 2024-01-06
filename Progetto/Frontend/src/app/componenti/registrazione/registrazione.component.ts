import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-registrazione',
  templateUrl: './registrazione.component.html',
  styleUrl: './registrazione.component.css'
})
export class RegistrazioneComponent {


  usernameCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(3),
    Validators.maxLength(20),
    Validators.pattern('[a-zA-Z0-9_-]+') // Assicurati che il pattern sia corretto
  ]);

  passwordCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(30),
    Validators.pattern('[a-zA-Z0-9!@#$%&*()_+{}[].?]+')
  ]);

  repeatPasswordCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(30),
    Validators.pattern('[a-zA-Z0-9!@#$%&*()_+{}[].?]+')
  ]);

  registrationForm: FormGroup = new FormGroup({
    username: this.usernameCheck,
    password: this.passwordCheck,
    repeatPassword: this.repeatPasswordCheck
  });


}
