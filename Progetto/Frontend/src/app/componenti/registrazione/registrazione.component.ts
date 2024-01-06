import { Component } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-registrazione',
  templateUrl: './registrazione.component.html',
  styleUrl: './registrazione.component.css'
})
export class RegistrazioneComponent {

  repeatPasswordInvalidMessage =`
  Le password non coincidono`;

  usernameCheck: FormControl = new FormControl('');
  passwordCheck: FormControl = new FormControl('');
  repeatPasswordCheck: FormControl = new FormControl('');
  registrationForm!: FormGroup;

  ngOnInit(): void {
    this.registrationForm = new FormGroup({
      username: this.usernameCheck,
      password: this.passwordCheck,
      repeatPassword: this.repeatPasswordCheck
    });
  }

  onUsernameControlLoaded(control: FormControl): void {
    this.registrationForm.setControl('username', control);
  }

  onPasswordControlLoaded(control: FormControl): void {
    this.registrationForm.setControl('password', control);
  }

  onRepeatPasswordControlLoaded(control: FormControl): void {
    this.registrationForm.setControl('repeatPassword', control);
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
