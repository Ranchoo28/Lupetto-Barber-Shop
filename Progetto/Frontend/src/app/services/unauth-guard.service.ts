
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class UnAuthGuard implements CanActivate {

  constructor(private authService: AuthenticationService, private router: Router) { }

  canActivate(): boolean {
    if (this.authService.isUserLoggedIn()) {
      this.router.navigate(['/home']);
      return false;
    }
    return true;
  }
}
