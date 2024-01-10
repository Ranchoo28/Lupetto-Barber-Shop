import {Component, OnInit} from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, Validators } from "@angular/forms";
import { AuthenticationService } from "../../services/authentication.service";
import {catchError, of, tap} from "rxjs";
import {Router} from "@angular/router";
import swal from 'sweetalert';

@Component({
  selector: 'app-registrazione',
  templateUrl: './registrazione.component.html',
  styleUrl: './registrazione.component.css'
})
export class RegistrazioneComponent implements OnInit{

  constructor(private authService: AuthenticationService, private router: Router) {
  }

  nomeExample =`Esempio: Mario`;
  cognomeExample =`Esempio: Rossi`;
  emailExample =`indirizzo@dominio.it`;
  telefonoExample =`Esempio: 333 444 5555`;
  passwordExample =`Esempio: Password123!`;

  regexNome =`[a-zA-Z]+`;
  regexCognome =`[a-zA-Z]+`;
  regexEmail =`^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$`;
  regexPassword =`^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%&*()_+-.?]).*$`;

  hide = true;

  errors: string[] = [];

  nomeCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(2),
    Validators.maxLength(20),
    Validators.pattern(this.regexNome)
  ]);
  cognomeCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(2),
    Validators.maxLength(20),
    Validators.pattern(this.regexCognome)
  ]);
  emailCheck = new FormControl('', [
    Validators.required,
    Validators.pattern(this.regexEmail)
  ]);
  telefonoCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(10),
    Validators.maxLength(10),
  ]);
  passwordCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(30),
    Validators.pattern(this.regexPassword)
  ]);
  repeatPasswordCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(30),
    Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%&*()_+-.?]).*$')
  ]);

  registrationForm!: FormGroup;

  passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const passwordControl = control.get('password');
    const repeatPasswordControl = control.get('repeatPassword');

    if (passwordControl && repeatPasswordControl) {
      return passwordControl.value === repeatPasswordControl.value
        ? null : { 'mismatch': true };
    }

    return null;
  }

  validateField(nomeCampo: string, regole: any): void {
    const field = this.registrationForm.get(nomeCampo);
    if (!field) return; // Esci se il campo non esiste

    // Controllo required
    if (regole.required && field.errors?.['required']) {
      this.errors.push(`Il campo ${nomeCampo} è obbligatorio`);
    }
    else{
      // Controllo minLength
      if (regole.minLength && field.value.length < regole.minLength) {
        this.errors.push(`Il ${nomeCampo} deve contenere almeno ${regole.minLength} caratteri`);
      }

      // Controllo regex
      if (regole.pattern && !new RegExp(regole.pattern).test(field.value)) {
        this.errors.push(`Il formato del campo ${nomeCampo} non è valido`);
      }
    }

  }

  NomeCheck(): void {
    this.validateField('nome', {
      required: true,
      minLength: 2,
      pattern: this.regexNome
    });
  }
  CognomeCheck(): void {
    this.validateField('cognome', {
      required: true,
      minLength: 2,
      pattern: this.regexCognome
    });
  }
  EmailCheck(): void {
    this.validateField('email', {
      required: true,
      pattern: this.regexEmail
    });
  }
  TelefonoCheck(): void {
    this.validateField('telefono', {
      required: true,
      minLength: 10,
      maxLength: 10
    });
  }
  PasswordCheck(): void {
    let value = this.registrationForm.get('password')?.value;

    if (!value.match(/(?=.*[A-Z])/)) this.errors.push('La password deve contenere almeno una lettera maiuscola.');
    if (!value.match(/(?=.*[a-z])/)) this.errors.push('La password deve contenere almeno una lettera minuscola.');
    if (!value.match(/(?=.*[0-9])/)) this.errors.push('La password deve contenere almeno un numero.');
    if (!value.match(/(?=.*[!@#$%&*()_+\-.?])/)) this.errors.push('La password deve contenere almeno un carattere speciale: !@#$%&*()_+-.?');
    if (value.length < 6) this.errors.push('La password deve avere almeno 6 caratteri.');

    if (this.registrationForm.get('password')?.valid) {
      this.registrationForm.get('repeatPassword')?.enable();
    } else {
      this.registrationForm.get('repeatPassword')?.disable();
    }
  }
  RepeatPasswordCheck(): void {
    if(this.registrationForm.get('password')?.value != this.registrationForm.get('repeatPassword')?.value){
      this.errors.push("Le password non coincidono");
    }
  }

  restrictInput(event: KeyboardEvent, regexPattern: string): void {
    const inputChar = event.key;
    const regex = new RegExp(regexPattern);

    if (!regex.test(inputChar)) {
      event.preventDefault();
    }
  }

  ngOnInit(): void {
    this.registrationForm = new FormGroup({
      nome: this.nomeCheck,
      cognome: this.cognomeCheck,
      email: this.emailCheck,
      telefono: this.telefonoCheck,
      password: this.passwordCheck,
      repeatPassword: this.repeatPasswordCheck
    });
    this.registrationForm.setValidators(this.passwordMatchValidator);
  }

  ngAfterContentChecked(): void {
    this.errors = [];
    this.NomeCheck();
    this.CognomeCheck();
    this.EmailCheck();
    this.PasswordCheck();
    if(this.registrationForm.get('repeatPassword')?.enabled){
      this.RepeatPasswordCheck();
    }
  }

  registraUtente() {
    if (this.registrationForm.valid) {
      this.authService.register(
        this.registrationForm.get('email')?.value,
        this.registrationForm.get('password')?.value,
        this.registrationForm.get('nome')?.value,
        this.registrationForm.get('cognome')?.value,
        this.registrationForm.get('telefono')?.value
      ).pipe(
        tap((data) => {
          console.log(data);
          this.registrationForm.reset();
          swal("Registrazione effettuata con successo", {
            icon: "success",
            timer: 1000
          });
          console.log("Registrazione effettuata con successo");
          this.router.navigate(['/login']);
        }),
        catchError((error) => {
          console.error(error);
          return of(null); // Gestisce l'errore restituendo un observable
        })
      ).subscribe();
    } else {
      console.log("Form non valido");
    }
  }


}
