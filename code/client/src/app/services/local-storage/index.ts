import { Injectable } from '@angular/core';

@Injectable()
export class LocalStorageService {
    get(key: string) {
        return JSON.parse(localStorage.getItem(key));
    }
    set(key: string, object: any) {
        localStorage.setItem(key, JSON.stringify(object))
    }
}
