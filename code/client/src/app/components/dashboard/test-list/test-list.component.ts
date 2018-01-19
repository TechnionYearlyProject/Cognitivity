import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { Test } from '../../../models'
import {TestService} from '../../../services/database-service'
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-test-list',
  templateUrl: './test-list.component.html',
  styleUrls: ['./test-list.component.css']
})

/*
this component holds all the tests created in the system.
*/
export class TestListComponent implements OnInit {
  //the actual list of the tests objects.
  testList: Test[];
  //an object to represent the current manager. it hold the current logged in user's credentials.
  manager;
  
  //default constructor.
  constructor(
    private testService: TestService,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {}

  //default ngOnInit function, gets the user's credentials while initialized. 
  async ngOnInit() {
    try {
      this.manager = this.authService.getCurrentManager();
      console.log(this.manager.email);
      this.testList = await this.testService.findTestsForTestManager('1');
      console.log(this.testList)
    } catch(err) {
      console.log(err);
    }
  }

  /* 
  Input - number of a specific test in the tests list.
  Output - returns the status of the test.
  */
  showStatus(statusCode: number) {
    switch(statusCode) {
      case 0: return 'Active';
      case 1: return 'Inactive';
    }
  }

  /*
  Input - the index of the test from the tests list.
  Output - send a color that'll match the active or inactive status of the test.
  */
  getColorForStatus(statusCode: number) {
    switch(statusCode) {
      case 0: return 'green';
      case 1: return 'red';
    }
  }

  /*
  Input - none. called by a click event from the HTML form.
  Output - adds a new test object to the tests list.
  */
 async addTest() {
   let test = this.testList[0];
   test.name = "test";
    this.testService.saveCognitiveTest(test);
    this.testList = await this.testService.findTestsForTestManager('1');
  }

}
