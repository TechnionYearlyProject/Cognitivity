/* This service will help pass data in the app 
   without feching it again from the DB
 */

import { Injectable } from '@angular/core';

@Injectable()
export class SessionService<T> {
  private data: T = null;
  setData(data: T): void {
    this.data = data;
  }

  getData(): T {
    return this.data;
  }

  clearData(): void {
    this.data = null;
  }

  isNull(): boolean {
    return this.data == null;
  }
}