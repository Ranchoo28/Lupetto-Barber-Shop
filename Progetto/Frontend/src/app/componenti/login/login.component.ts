import { Component } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  usernameControl: FormControl = new FormControl('');
  passwordCheck: FormControl = new FormControl('');
  loginForm!: FormGroup;

  ngOnInit(): void {
    this.initForm();
  }

  onUsernameControlLoaded(control: FormControl): void {
    this.usernameControl = control;
  }

  onPasswordControlLoaded(control: FormControl): void {
    this.passwordCheck = control;
  }
  initForm(): void {
    this.loginForm = new FormGroup({
      username: this.usernameControl,
      password: this.passwordCheck
    });
  }

  onLogin(){
    if(this.loginForm.valid){
      console.log(this.loginForm.value);
    }
    else{
      console.log("Form non valido");
    }
  }



}
