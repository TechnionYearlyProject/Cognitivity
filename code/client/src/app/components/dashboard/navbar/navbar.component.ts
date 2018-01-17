import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { TestManagerService } from '../../../services/database-service/index';
import { AuthService } from '../../../services/auth-service';
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
    private tmService: TestManagerService,
    private authService: AuthService
  ) { }

  async ngOnInit() {
    this.loggedInUser = this.authService.getCurrentManager().email;
  }

  onLogOut() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }


}
