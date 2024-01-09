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

  hide = true;
  loginErrorMessage = '';

  regexUsername =`[a-zA-Z0-9_]+`;

  usernameCheck: FormControl = new FormControl('');
  passwordCheck: FormControl = new FormControl('');
  loginForm!: FormGroup;

  restrictInput(event: KeyboardEvent, regexPattern: string): void {
    const inputChar = event.key;
    const regex = new RegExp(regexPattern);

    if (!regex.test(inputChar)) {
      event.preventDefault();
    }
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: this.usernameCheck,
      password: this.passwordCheck
    });
  }

  onLogin(){
    if (this.loginForm.valid) {
      const email = this.loginForm.get('username')?.value;
      const password = this.loginForm.get('password')?.value;

      this.authService.login(email, password).subscribe(
        accessToken => {
          console.log("Login effettuato", accessToken);
          localStorage.setItem('access_token', accessToken.toString()); // TODO salvare il cookie
          this.loginErrorMessage = '';
        },
        error => {
          console.log("Login non effettuato", error);
          this.loginErrorMessage = 'Login non riuscito. Email o Password errati.';
        }
      );
      // Meglio non resettare il form così da poter correggere più facilmente errori
      // this.loginForm.reset();
    } else {
      console.log("Form non valido");
      this.loginErrorMessage = 'Form non valido. Si prega di controllare i dati inseriti.';
    }
  }

}
