import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

import { AuthService } from './';

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}
  async canActivate() {
    let user = await this.authService.getCurrentManager()//.toPromise();
    if (user) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }
  
}