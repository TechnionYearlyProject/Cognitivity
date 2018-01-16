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
    let user = this.authService.getCurrentManager();
    if (user) {
      return true;
    }
    console.log('no user here haha');
    this.router.navigate(['/login']);
    return false;
  }
  
}