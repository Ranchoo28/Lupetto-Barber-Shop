import {LOCALE_ID, NgModule} from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { MatCardModule } from "@angular/material/card";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

// Components
import { AppComponent } from './app.component';
import { NavbarComponent } from './componenti/navbar/navbar.component';


<<<<<<< HEAD
// Routing between pages
=======

// Routing
>>>>>>> 64fb9f0b05fbe329a86b49b05baa0e1179419b2d
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import { ProdottiComponent } from './componenti/prodotti/prodotti.component';
import { PrenotaComponent } from './componenti/prenota/prenota.component';
import { GalleriaComponent } from './componenti/galleria/galleria.component';
import { ContattiComponent } from './componenti/contatti/contatti.component';
import { HomeComponent } from './componenti/home/home.component';
import { NotfoundComponent } from './componenti/notfound/notfound.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
<<<<<<< HEAD
import { LoginComponent } from './componenti/login/login.component';

=======
import {MatSelectModule} from "@angular/material/select";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from "@angular/material/core";
import { registerLocaleData } from '@angular/common';
import localeIt from '@angular/common/locales/it';
import {ReactiveFormsModule} from "@angular/forms";
>>>>>>> 64fb9f0b05fbe329a86b49b05baa0e1179419b2d

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
<<<<<<< HEAD
    LoginComponent
  ],
  imports: [
    RouterModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    NgbModule
=======

>>>>>>> 64fb9f0b05fbe329a86b49b05baa0e1179419b2d
  ],
    imports: [
        RouterModule,
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatButtonModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatSelectModule,
        MatInputModule,
        MatDatepickerModule,
        MatNativeDateModule,
        ReactiveFormsModule,
    ],
  providers: [
    provideClientHydration(),
    { provide: LOCALE_ID, useValue: 'it' }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

