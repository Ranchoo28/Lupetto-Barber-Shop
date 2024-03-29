
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
import { MatTooltipModule } from "@angular/material/tooltip";
import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatRadioModule } from "@angular/material/radio";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";



import {  CarouselCaptionComponent, CarouselComponent,
          CarouselControlComponent, CarouselIndicatorsComponent,
          CarouselInnerComponent, CarouselItemComponent } from "@coreui/angular";
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
import { CarrelloComponent } from './componenti/carrello/carrello.component';
import { PagamentoComponent } from './componenti/pagamento/pagamento.component';
import { CheckoutComponent } from './componenti/checkout/checkout.component';
import { PrenotazioniComponent } from './componenti/prenotazioni/prenotazioni.component';
import { AggiungiProdottoComponent } from './componenti/aggiungi-prodotto/aggiungi-prodotto.component';



import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";


import { NgOptimizedImage, registerLocaleData} from '@angular/common';
import localeIt from '@angular/common/locales/it';
import { FormsModule, ReactiveFormsModule} from "@angular/forms";
import { AuthInterceptorService } from "./services/auth-interceptor.service";
import { DescrizioneProxyComponent } from './componenti/descrizione-proxy/descrizione-proxy.component';



registerLocaleData(localeIt, 'it');
@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ProdottiComponent,
    PrenotaComponent,
    GalleriaComponent,
    HomeComponent,
    NotfoundComponent,
    LoginComponent,
    RegistrazioneComponent,
    PrenotazioniComponent,
    ContattiComponent,
    CarrelloComponent,
    PagamentoComponent,
    CheckoutComponent,
    AggiungiProdottoComponent,
    DescrizioneProxyComponent,
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
        MatTooltipModule,
        MatGridListModule,
        MatDialogModule,
        ReactiveFormsModule,
        NgOptimizedImage,
        MatTableModule,
        MatCheckboxModule,
        MatRadioModule,
        HttpClientModule,
        CarouselControlComponent,
        CarouselCaptionComponent,
        CarouselInnerComponent,
        CarouselIndicatorsComponent,
        CarouselComponent,
        CarouselItemComponent,
        MatPaginatorModule,
        FormsModule,
        MatProgressSpinnerModule,
    ],
  providers: [
    provideClientHydration(),
    { provide: LOCALE_ID, useValue: 'it' },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
