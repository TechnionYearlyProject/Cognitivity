import { Component, OnInit } from '@angular/core';
import { TestService, TestManagerService } from '../../services/database-service/index';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { Test } from '../../models';
@Component({
  selector: 'app-test-page',
  templateUrl: './test-page.component.html',
  styleUrls: ['./test-page.component.css']
})
export class TestPageComponent implements OnInit {
  test : Test;
  //variable to hold the blocks array
  blocks: any[];
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private testService: TestService,
    private tmService: TestManagerService,
    private authService: AuthService
  ) { }

  async ngOnInit() {
    let email = this.authService.getCurrentManagerEmail();
    let testId = this.route.snapshot.params['testId'];
    if (isNaN(testId) || testId == '') {
      //Here we will navigate to a 404 page
      //this.router.navigate(['/dashboard']);
    }
    this.test = await this.testService.findCognitiveTestById(testId);
    if (this.test == null) {
      //Here we will navigate to a 404 page
      //this.router.navigate(['/dashboard']);
    }

    this.blocks = this.test.blocks;
  }

}
