import { Injectable } from '@angular/core';
import { Manager } from '../../models';
import { LocalStorageService } from '../local-storage';

@Injectable()
export class AuthService {
  currentUser: Manager;
  constructor(
    private localStorageService: LocalStorageService,
  ) {
    /*Here we'll need to get the session token from the local storage and if it exists,
    for loggin the manager in automatically. */
  }
  async getCurrentManager() {
    //dummy user
    this.currentUser = {
      id: 2,
      name: 'test',
      userName:'test'
    };
    return this.currentUser;
  }

}