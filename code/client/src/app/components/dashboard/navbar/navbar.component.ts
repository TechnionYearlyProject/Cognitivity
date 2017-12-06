import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  @Input() managerId: string;
  showRegister = true;
  isLoggedIn = true;
  constructor(
    private router: Router,
  ) { }

  ngOnInit() {
  }


}
