import {Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { AuthService } from '../../services/auth-service';
@Component({
    selector: 'app-dashboard',
    template: `
                <app-navbar [managerId]="managerId"></app-navbar>
                <div class="container">
                    <router-outlet></router-outlet>
                </div>
    `
})
export class DashboardComponent implements OnInit{
    manager;
    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private authService: AuthService
    ) {

    }
    async ngOnInit() {
        try {
            this.manager = await this.authService.getCurrentManager();
        } catch(err) {
            console.error(err);
        }

    }

}