import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-registrazione',
  templateUrl: './registrazione.component.html',
  styleUrl: './registrazione.component.css'
})
export class RegistrazioneComponent {


  usernameCheck: FormControl = new FormControl('');
  passwordCheck: FormControl = new FormControl('');
  repeatPasswordCheck: FormControl = new FormControl('');
  registrationForm!: FormGroup;

  ngOnInit(): void {
    this.initForm();
  }

  onUsernameControlLoaded(control: FormControl): void {
    this.usernameCheck = control;
  }

  onPasswordControlLoaded(control: FormControl): void {
    this.passwordCheck = control;
  }

  onRepeatPasswordControlLoaded(control: FormControl): void {
    this.repeatPasswordCheck = control;
  }

  initForm(): void {
    this.registrationForm = new FormGroup({
      username: this.usernameCheck,
      password: this.passwordCheck,
      repeatPassword: this.repeatPasswordCheck
    });
  }

  onRegistration(){
    if(this.registrationForm.valid){
      console.log(this.registrationForm.value);
    }
    else{
      console.log("Form non valido");
    }
  }

}
