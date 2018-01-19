import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { TestManagerService } from '../../../services/database-service/index';
import { AuthService } from '../../../services/auth-service';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})

/*
This component class creates the navigation bar in the upper section of out project.
*/
export class NavbarComponent implements OnInit {
  //the given manager id 
  @Input() managerId: string;
  //variable to show/hide the registeration option.
  showRegister = true;
  //variable to indicate if the user is logged in.
  isLoggedIn = true;
  //this variable hold the email of the currently logged in client.
  loggedInUser;

  //default constructor.
  constructor(
    private router: Router,
    private tmService: TestManagerService,
    private authService: AuthService
  ) { }

  //default ngInit function. gets it's current client email as an input.
  async ngOnInit() {
    this.loggedInUser = this.authService.getCurrentManager().email;
  }

  //this function reroute the user when he's logging out.
  onLogOut() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }


}
