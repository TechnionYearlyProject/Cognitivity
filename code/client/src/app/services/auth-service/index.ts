import { Injectable } from '@angular/core';
import { Manager } from '../../models';
import { AngularFireAuth } from 'angularfire2/auth';
import { LocalStorageService } from '../local-storage';
import { Observable } from 'rxjs';

/*
the actual service for checking the users credentials.
*/
@Injectable()
export class AuthService {
  //the current user that tries to login.
  currentUser: Manager;

  /*
  the service's constructor. 
  we're connecting it to the DB for the full check.
  */
  constructor(
    private localStorageService: LocalStorageService,
    private afAuth: AngularFireAuth
  ) {
    /*Here we'll need to get the session token from the local storage and if it exists,
    for loggin the manager in automatically. */

  }


  /*
  this function activate the login option, it provides the credentials.
  and prevents login if the credentials are invalid.
  */
  login(email:string, password:string) {
    return new Promise((resolve,reject) => {
      this.afAuth.auth.signInWithEmailAndPassword(email,password)
        .then(userData => {this.localStorageService.set('currEmail', email);   return resolve(userData);},
        err => reject(err));
    });
  }

  /*
  registers the given user's email and password with the auth server. 
  used not in order to check the credentials , but to add new valid credentials
  */
  register(email:string, password:string) {
    return new Promise((resolve,reject) => {
      this.afAuth.auth.createUserWithEmailAndPassword(email,password)
        .then(userData => {
          this.localStorageService.set('currEmail', email); 
          return resolve(userData);
          },
        err => reject(err));
    });
  }

  /*
  simple getter for the current user.
  */
  getCurrentManagerEmail() {
    return this.localStorageService.get('currEmail');
  }

  /*
  simple caller to the auth service logout option.
  */
  logout() {
    this.localStorageService.set('currEmail', null);
    this.afAuth.auth.signOut();
  }

}