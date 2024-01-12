import { Injectable } from '@angular/core';
import {CanActivate, Router} from "@angular/router";
import {AuthenticationService} from "./authentication.service";

@Injectable({
  providedIn: 'root'
})
export class UserGuard implements CanActivate{
  constructor(private authService: AuthenticationService, private router: Router) { }

  canActivate(): boolean {
    if (this.authService.isUser()) {
      return true;
    }
    this.router.navigate(['/pagina_non_trovata']);
    return false;
  }


}
