import { Component } from '@angular/core';
import { AuthenticationService } from "../../services/authentication.service";
import swal from "sweetalert";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})

export class NavbarComponent {

  constructor(protected authService: AuthenticationService) {
  }

  logout() {
    this.authService.logout();
    console.log("Logout effettuato");
    swal(`Logout effettuato con successo.
                  A presto !`, {
      icon: "success",
      timer: 1000
    });

  }


}
