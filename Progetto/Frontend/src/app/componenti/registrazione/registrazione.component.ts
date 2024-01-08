import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, Validators } from "@angular/forms";
import { AuthenticationService } from "../../services/authentication.service";
import {catchError, of, tap} from "rxjs";

@Component({
  selector: 'app-registrazione',
  templateUrl: './registrazione.component.html',
  styleUrl: './registrazione.component.css'
})
export class RegistrazioneComponent {

  constructor(private authService: AuthenticationService) {
  }

  nomeExample =`Esempio: Mario`;
  cognomeExample =`Esempio: Rossi`;
  usernameExample =`Esempio: MarioRossi_55`;
  passwordExample =`Esempio: Password123!`;
  emailExample =`indirizzo@dominio.it`;

  regexNome =`[a-zA-Z]+`;
  regexCognome =`[a-zA-Z]+`;
  regexUsername =`[a-zA-Z0-9_]+`;
  regexEmail =`^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$`;

  hide = true;

  errors: string[] = [];

  nomeCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(2),
    Validators.maxLength(20),
    Validators.pattern('[a-zA-Z]+')
  ]);
  cognomeCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(2),
    Validators.maxLength(20),
    Validators.pattern('[a-zA-Z]+')
  ]);
  usernameCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(2),
    Validators.maxLength(30),
    Validators.pattern('[a-zA-Z0-9_]+')
  ]);
  emailCheck = new FormControl('', [
    Validators.required,
    Validators.pattern('^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$')
  ]);
  passwordCheck = new FormControl('', [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(30),
    Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%&*()_+-.?]).*$')
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
  UsernameCheck(): void {
    this.validateField('username', {
      required: true,
      minLength: 2,
      pattern: this.regexUsername
    });
  }
  EmailCheck(): void {
    this.validateField('email', {
      required: true,
      pattern: this.regexEmail
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

  formCheck(): void {
    this.errors = [];
    this.NomeCheck();
    this.CognomeCheck();
    this.UsernameCheck();
    this.EmailCheck();
    this.PasswordCheck();
    if(this.registrationForm.get('repeatPassword')?.enabled){
      this.RepeatPasswordCheck();
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
      username: this.usernameCheck,
      email: this.emailCheck,
      password: this.passwordCheck,
      repeatPassword: this.repeatPasswordCheck
    });
    this.registrationForm.setValidators(this.passwordMatchValidator);
  }

  ngAfterContentChecked(): void {
    this.formCheck();
  }

  onRegistration() {
    if (this.registrationForm.valid) {
      this.authService.register(
        this.registrationForm.get('username')?.value,
        this.registrationForm.get('password')?.value,
        this.registrationForm.get('nome')?.value,
        this.registrationForm.get('cognome')?.value,
        this.registrationForm.get('email')?.value,
        "USER"
      ).pipe(
        tap((data) => {
          console.log(data);
          this.registrationForm.reset();
          console.log("Registrazione effettuata con successo");
          // TODO
          // window.location.href = "/home";
        }),
        catchError((error) => {
          // Gestione degli errori qui
          console.error(error);
          return of(null); // Gestisce l'errore restituendo un observable
        })
      ).subscribe(); // Ancora necessario per attivare il flusso
    } else {
      console.log("Form non valido");
    }
  }

}
