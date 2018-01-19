import { Injectable } from '@angular/core';
/*
Used for saving data localy in the front-end code. 
keep in mind that the data passed/saved is discarded in every run of the front-end's code.

*/
@Injectable()
export class LocalStorageService {
    get(key: string) {
        return JSON.parse(localStorage.getItem(key));
    }
    set(key: string, object: any) {
        localStorage.setItem(key, JSON.stringify(object))
    }
}
