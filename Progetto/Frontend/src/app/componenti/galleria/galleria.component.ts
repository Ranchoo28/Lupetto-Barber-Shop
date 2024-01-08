import { Component,OnInit } from '@angular/core';


@Component({
  selector: 'app-galleria',
  templateUrl: './galleria.component.html',
  styleUrl: './galleria.component.css'
})

export class GalleriaComponent{

  slides = [
    { src: './assets/images/Tagli/taglio.png', title: 'Titolo 1', subtitle: 'Sottotitolo 1' },
    { src: './assets/images/Tagli/taglio-donna1.png', title: 'Titolo 2', subtitle: 'Sottotitolo 2' },
    { src: './assets/images/Tagli/taglio-uomo1.png', title: 'Titolo 2', subtitle: 'Sottotitolo 2' },
    // ecc.
  ];

}

