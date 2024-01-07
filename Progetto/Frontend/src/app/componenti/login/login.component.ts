import {Component} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication.service";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(private authService: AuthenticationService){

  }

  loginInvalidMessage =`
  Username o password errati
  `

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
    if (this.loginForm.valid) {
      const email = this.loginForm.value.username;
      const password = this.loginForm.value.password;

      let a = this.authService.login(email, password);
      console.log(a);

      if (this.authService.login(email, password)) {
        console.log("Login effettuato");
      } else {
        console.log("Login non effettuato");
      }
      this.loginForm.reset();
    } else {
      console.log("Form non valido");
    }
  }

  /*
  registerFake(){
    this.authService.register('pippo','Password1!', 'Nome', 'Cognome', 'email@example.com', 'USER').subscribe(
      data => {
        console.log(data);
      },
      error => {
        console.error('Errore durante la registrazione', error);
      }
    );
  }
  */

}
