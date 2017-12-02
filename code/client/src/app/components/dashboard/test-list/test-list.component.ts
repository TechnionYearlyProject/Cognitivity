import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { Test } from '../../../models'
import {TestService} from '../../../services/database-service'

@Component({
  selector: 'app-test-list',
  templateUrl: './test-list.component.html',
  styleUrls: ['./test-list.component.css']
})
export class TestListComponent implements OnInit {
  testList: Test[];
  managerId: string;
  constructor(
    private testService: TestService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  async ngOnInit() {
    this.managerId = this.route.snapshot.params['id'];
    try {
      this.testList = await this.testService.getTestsForTestManager(this.managerId);
    } catch(err) {
      console.log(err);
    }
  }

}
