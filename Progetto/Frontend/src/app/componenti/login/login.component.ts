import { Component } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  usernameCheck: FormControl = new FormControl('');
  passwordCheck: FormControl = new FormControl('');
  loginForm!: FormGroup;

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: this.usernameCheck,
      password: this.passwordCheck
    });
  }

  onUsernameControlLoaded(control: FormControl) {
    this.loginForm.setControl('username', control);
  }

  onPasswordControlLoaded(control: FormControl) {
    this.loginForm.setControl('password', control);
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
