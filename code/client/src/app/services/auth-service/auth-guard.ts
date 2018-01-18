import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AngularFireAuth } from 'angularfire2/auth';
import { Observable } from 'rxjs/Observable';

import { AuthService } from './';

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router,
    private afAuth: AngularFireAuth
  ) {}
  canActivate() {
    return this.afAuth.authState.map(auth => {
      if (!auth) {
        //console.log(this.afAuth.auth.currentUser.email);
          this.router.navigate(['/login']);
          return false;
      } else {
          return true;
      }
  });
  }
  
}

@Injectable()
export class LoginGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private afAuth: AngularFireAuth
  ) {}
  canActivate() {
    return this.afAuth.authState.map(auth => {
      this.authService.logout();
      return true;
    })
  }
  
}