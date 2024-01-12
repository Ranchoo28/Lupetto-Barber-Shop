import { Injectable } from '@angular/core';
import {AuthenticationService} from "./authentication.service";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class HairdresserGuard {
  constructor(private authService: AuthenticationService, private router: Router) { }

  canActivate(): boolean {
    if (this.authService.isHairdresser()) {
      return true;
    }
    this.router.navigate(['/pagina_non_trovata']);
    return false;
  }
}
