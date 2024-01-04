import { Component } from '@angular/core';

@Component({
  selector: 'app-contatti',
  templateUrl: './contatti.component.html',
  styleUrl: './contatti.component.css'
})
export class ContattiComponent {
  telefono="1234567890";
  email="gatto@gmail.miao";
  indirizzo="Via dei gatti 1";
  constructor() { }

}
