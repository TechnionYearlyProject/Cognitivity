import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { TestManagerService } from '../../../services/database-service/index';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  @Input() managerId: string;
  showRegister = true;
  isLoggedIn = true;
  loggedInUser;
  constructor(
    private router: Router,
    private tmService: TestManagerService
  ) { }

  async ngOnInit() {
    console.log(await this.tmService.getTestManager(2))
    this.loggedInUser = (await this.tmService.getTestManager(1)).name;
  }


}
