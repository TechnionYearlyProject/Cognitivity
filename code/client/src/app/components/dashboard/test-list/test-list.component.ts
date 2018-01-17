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
export class TestListComponent implements OnInit {
  testList: Test[];
  manager;
  constructor(
    private testService: TestService,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {}

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

  showStatus(statusCode: number) {
    switch(statusCode) {
      case 0: return 'Active';
      case 1: return 'Inactive';
    }
  }
  getColorForStatus(statusCode: number) {
    switch(statusCode) {
      case 0: return 'green';
      case 1: return 'red';
    }
  }

 async addTest() {
   let test = this.testList[0];
   test.name = "test";
    this.testService.saveCognitiveTest(test);
    this.testList = await this.testService.findTestsForTestManager('1');
  }

}
