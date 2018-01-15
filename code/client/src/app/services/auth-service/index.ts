import { Injectable } from '@angular/core';
import { Manager } from '../../models';
import { AngularFireAuth } from 'angularfire2/auth';
import { LocalStorageService } from '../local-storage';
import { Observable } from 'rxjs';
@Injectable()
export class AuthService {
  currentUser: Manager;
  constructor(
    private localStorageService: LocalStorageService,
    private afAuth: AngularFireAuth
  ) {
    /*Here we'll need to get the session token from the local storage and if it exists,
    for loggin the manager in automatically. */
  }
  login(email:string, password:string) {
    return new Promise((resolve,reject) => {
      this.afAuth.auth.signInWithEmailAndPassword(email,password)
        .then(userData => resolve(userData),
        err => reject(err));
    });
  }

  register(email:string, password:string) {
    return new Promise((resolve,reject) => {
      this.afAuth.auth.createUserWithEmailAndPassword(email,password)
        .then(userData => resolve(userData),
        err => reject(err));
    });
  }
  getCurrentManager() {
    return this.afAuth.auth.currentUser;
  }

  logout() {
    this.afAuth.auth.signOut();
  }

}