import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

/* Componenti */
import { HomeComponent} from "./componenti/home/home.component";
import { ProdottiComponent } from './componenti/prodotti/prodotti.component';
import { PrenotaComponent } from './componenti/prenota/prenota.component';
import { GalleriaComponent} from "./componenti/galleria/galleria.component";
import { ContattiComponent} from "./componenti/contatti/contatti.component";
import { NotFoundComponent } from './componenti/not-found/not-found.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' }, // Reindirizza alla home se la rotta è vuota
  { path: 'home', component: HomeComponent },
  { path: 'prodotti', component: ProdottiComponent },
  { path: 'prenota', component: PrenotaComponent },
  { path: 'galleria', component: GalleriaComponent },
  { path: 'contatti', component: ContattiComponent },
  { path: 'non-trovato', component: NotFoundComponent }, // Componente personalizzato per la pagina 404
  { path: '**', redirectTo: '/non-trovato' } // Reindirizza tutte le rotte non riconosciute
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})


export class AppRoutingModule { }
