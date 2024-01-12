import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

/* Componenti */
import { HomeComponent} from "./componenti/home/home.component";
import { ProdottiComponent } from './componenti/prodotti/prodotti.component';
import { PrenotaComponent } from './componenti/prenota/prenota.component';
import { GalleriaComponent} from "./componenti/galleria/galleria.component";
import { NotfoundComponent } from "./componenti/notfound/notfound.component";
import { LoginComponent } from "./componenti/login/login.component";
import { RegistrazioneComponent } from "./componenti/registrazione/registrazione.component";
import { PrenotazioniComponent } from "./componenti/prenotazioni/prenotazioni.component";
import { ContattiComponent } from "./componenti/contatti/contatti.component";
import { PrenotazioniHairdresserComponent } from "./componenti/prenotazioni-hairdresser/prenotazioni-hairdresser.component";

/* Guards */
import { AuthGuard} from "./services/auth-guard.service";
import { UnAuthGuard } from "./services/unauth-guard.service";
import { UserGuard } from "./services/user-guard.service";
import { HairdresserGuard } from "./services/hairdresser-guard.service";


const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'contatti', component: ContattiComponent},
  { path: 'prodotti', component: ProdottiComponent },
  { path: 'galleria', component: GalleriaComponent },
  { path: 'prenota', component: PrenotaComponent, canActivate: [UserGuard]},
  { path: 'prenotazioni', component: PrenotazioniComponent, canActivate: [UserGuard] },
  { path: 'riepilogo', component: PrenotazioniHairdresserComponent, canActivate: [HairdresserGuard]},
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
