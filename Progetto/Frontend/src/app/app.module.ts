
import { LOCALE_ID, NgModule} from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatCardModule } from "@angular/material/card";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from "@angular/material/select";
import { MatInputModule } from "@angular/material/input";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from "@angular/material/core";
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatGridListModule } from '@angular/material/grid-list';

// Components
import { AppComponent } from './app.component';
import { ContattiComponent } from './componenti/contatti/contatti.component';
import { GalleriaComponent } from './componenti/galleria/galleria.component';
import { HomeComponent } from './componenti/home/home.component';
import { LoginComponent } from './componenti/login/login.component';
import { NavbarComponent } from './componenti/navbar/navbar.component';
import { NotfoundComponent } from './componenti/notfound/notfound.component';
import { ProdottiComponent } from './componenti/prodotti/prodotti.component';
import { PrenotaComponent } from './componenti/prenota/prenota.component';
import { RegistrazioneComponent } from './componenti/registrazione/registrazione.component';

// Routing
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import { ProdottiComponent } from './componenti/prodotti/prodotti.component';
import { PrenotaComponent } from './componenti/prenota/prenota.component';
import { GalleriaComponent } from './componenti/galleria/galleria.component';
import { ContattiComponent } from './componenti/contatti/contatti.component';
import { HomeComponent } from './componenti/home/home.component';
import { NotfoundComponent } from './componenti/notfound/notfound.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSelectModule} from "@angular/material/select";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from "@angular/material/core";
import {MatToolbarModule} from '@angular/material/toolbar';
import {NgOptimizedImage, registerLocaleData} from '@angular/common';
import localeIt from '@angular/common/locales/it';
import { ReactiveFormsModule} from "@angular/forms";
import {MatTooltipModule} from "@angular/material/tooltip";
import { UsernameComponent } from './componenti/form/username/username.component';
import { PasswordComponent } from './componenti/form/password/password.component';
import { EmailComponent } from './componenti/form/email/email.component';



registerLocaleData(localeIt, 'it');
@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ProdottiComponent,
    PrenotaComponent,
    GalleriaComponent,
    ContattiComponent,
    HomeComponent,
    NotfoundComponent,
    LoginComponent,
    RegistrazioneComponent,
    UsernameComponent,
    PasswordComponent,
    EmailComponent,

  ],
  imports: [
    RouterModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatToolbarModule,
    ReactiveFormsModule,
    NgOptimizedImage,

  ],
  providers: [
    provideClientHydration(),
    { provide: LOCALE_ID, useValue: 'it' }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
