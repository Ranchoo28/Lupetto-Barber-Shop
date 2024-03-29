import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication.service";
import {catchError, of, tap} from "rxjs";
import {Router} from "@angular/router";
import swal from 'sweetalert';
import {JwttokenhandlerService} from "../../services/jwttokenhandler.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{

  constructor(private authService: AuthenticationService,
              private router: Router,
              private jwtHandler: JwttokenhandlerService) {
  }

  hide = true;

  regexEmail =`^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$`;

  emailCheck = new FormControl('', [
    Validators.required,
    Validators.pattern(this.regexEmail)
  ]);
  passwordCheck: FormControl = new FormControl('', [
    Validators.required,
  ]);
  loginForm!: FormGroup;


  ngOnInit(): void {
    this.authService.logout();
    this.loginForm = new FormGroup({
      email: this.emailCheck,
      password: this.passwordCheck
    });
  }

  loginUtente() {
    if (this.loginForm.valid) {
      this.authService.login(
        this.loginForm.get('email')?.value,
        this.loginForm.get('password')?.value
      ).pipe(
        tap((data) => {
          console.log("Login effettuato");

          this.loginForm.reset();
          swal("Login effettuato con successo", {
            icon: "success",
            timer: 1000
          });
          this.router.navigate(['/home']);
        }),
        catchError((error) => {
            if(error.error.errorCode == "INVALID_CREDENTIALS") {
              swal(`Errore: Email o Password errati`, {
                icon: "error",
                timer: 2000
              });
            }
          return of(null);
        })
      ).subscribe();
    } else {
      swal(`Errore: Compila tutti i campi`, {
        icon: "error",
        timer: 2000
      });
    }
  }


}
