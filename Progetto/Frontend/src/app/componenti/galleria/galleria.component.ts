import { Component,OnInit } from '@angular/core';


@Component({
  selector: 'app-galleria',
  templateUrl: './galleria.component.html',
  styleUrl: './galleria.component.css'
})
export class GalleriaComponent implements OnInit{
  images = [
    {path: 'assets/images/taglio.jpg'},
    {path: 'assets/images/taglio-donna1.jpg'},
    {path: 'assets/images/taglio-uomo1.jpg'},
    // {path: 'assets/images/text-logo.png'},
  ]
  currentImageIndex = 0;



  ngOnInit(): void {
    setInterval(() => {
      this.currentImageIndex++;
      if (this.currentImageIndex >= this.images.length) {
        this.currentImageIndex = 0;
      }
    }, 2000); // Ca
  }


}
