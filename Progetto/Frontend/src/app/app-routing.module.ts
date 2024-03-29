import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent} from "./componenti/home/home.component";
import { ProdottiComponent } from './componenti/prodotti/prodotti.component';
import { PrenotaComponent } from './componenti/prenota/prenota.component';
import { NotfoundComponent } from "./componenti/notfound/notfound.component";
import { LoginComponent } from "./componenti/login/login.component";
import { RegistrazioneComponent } from "./componenti/registrazione/registrazione.component";
import { PrenotazioniComponent } from "./componenti/prenotazioni/prenotazioni.component";
import { ContattiComponent } from "./componenti/contatti/contatti.component";
import { AggiungiProdottoComponent } from "./componenti/aggiungi-prodotto/aggiungi-prodotto.component";


import { AuthGuard} from "./services/auth-guard.service";
import { UnAuthGuard } from "./services/unauth-guard.service";
import { UserGuard } from "./services/user-guard.service";
import { HairdresserGuard } from "./services/hairdresser-guard.service";


const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'contatti', component: ContattiComponent },
  { path: 'prodotti', component: ProdottiComponent },
  { path: 'prenota', component: PrenotaComponent, canActivate: [UserGuard]},
  { path: 'prenotazioni', component: PrenotazioniComponent, canActivate: [AuthGuard]},
  { path: 'aggiungi-prodotto', component: AggiungiProdottoComponent, canActivate: [HairdresserGuard]},
  { path: 'login', component: LoginComponent, canActivate: [UnAuthGuard] },
  { path: 'registrazione', component: RegistrazioneComponent, canActivate: [UnAuthGuard]  },
  { path: 'pagina_non_trovata', component: NotfoundComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', redirectTo: '/pagina_non_trovata' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [AuthGuard, UnAuthGuard]
})


export class AppRoutingModule { }
