/* This service will help pass data in the app 
   without feching it again from the DB
 */

/*
  Usage example:
    At the constructor of the component, declare a variable of SessionService with the desired type (let's say Test). 
    When routing from component A to component B and wishing to pass a Test object,
    at the click event of the button that does the routing, call this.sessionService.setData(test) where 'test' is the object.
    In component B, at ngOnInit, save the object returned from this.sessionService.getData() .
    If you wish to clear the object in the service, call this.sessionService.clearData(),
    and remember to check with isNull if data is available! */

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