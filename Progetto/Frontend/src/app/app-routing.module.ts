import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

/* Componenti */
import { HomeComponent} from "./componenti/home/home.component";
import { ProdottiComponent } from './componenti/prodotti/prodotti.component';
import { PrenotaComponent } from './componenti/prenota/prenota.component';
import { GalleriaComponent} from "./componenti/galleria/galleria.component";
import { ContattiComponent} from "./componenti/contatti/contatti.component";
import {NotfoundComponent} from "./componenti/notfound/notfound.component";

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'prodotti', component: ProdottiComponent },
  { path: 'prenota', component: PrenotaComponent },
  { path: 'galleria', component: GalleriaComponent },
  { path: 'contatti', component: ContattiComponent },
  { path: 'pagina_non_trovata', component: NotfoundComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', redirectTo: '/pagina_non_trovata' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})


export class AppRoutingModule { }
