import { Component,OnInit } from '@angular/core';
export interface Tile {
  color: string;
  cols: number;
  rows: number;
  text: string;
}

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
  ];

  tiles = [
    {cols: 3, rows: 1,image: './assets/images/Carousel/taglio.png'},
    {cols: 1, rows: 2,image: './assets/images/Carousel/taglio-donna1.png'},
    {cols: 1, rows: 1,image: './assets/images/Carousel/taglio-uomo1.png'},
    {cols: 2, rows: 1,image: './assets/images/Carousel/taglio.png'},

];
  tiles2 = [
    {cols: 1, rows: 2,image: './assets/images/Carousel/taglio-uomo1.png'},
    {cols: 1, rows: 1,image: './assets/images/Carousel/taglio-donna1.png'},
    {cols: 2, rows: 1,image: './assets/images/Carousel/taglio.png'},
    {cols: 3, rows: 1,image: './assets/images/Carousel/taglio.png'},


  ];



}

