import { Component,OnInit } from '@angular/core';


@Component({
  selector: 'app-galleria',
  templateUrl: './galleria.component.html',
  styleUrl: './galleria.component.css'
})

export class GalleriaComponent{

  slides = [
    { src: 'taglio.png', title: 'Titolo 1', subtitle: 'Sottotitolo 1' },
    { src: 'taglio-donna1.png', title: 'Titolo 2', subtitle: 'Sottotitolo 2' },
    { src: 'taglio-uomo1.png', title: 'Titolo 2', subtitle: 'Sottotitolo 2' },
    // ecc.
  ];

}

