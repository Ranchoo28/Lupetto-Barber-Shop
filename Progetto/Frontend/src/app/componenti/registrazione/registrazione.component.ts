import { Component } from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors} from "@angular/forms";

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

    this.registrationForm.setValidators(this.passwordMatchValidator);
  }

  passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const passwordControl = control.get('password');
    const repeatPasswordControl = control.get('repeatPassword');

    if (passwordControl && repeatPasswordControl) {
      return passwordControl.value === repeatPasswordControl.value
        ? null : { 'mismatch': true };
    }

    return null;
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
