import {Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { AuthService } from '../../services/auth-service';
@Component({
    selector: 'app-dashboard',
    template: `<div class="body" [style.backgroundSize]="'cover'">
                <app-navbar [managerId]="managerId"></app-navbar>
                <div class="container">
                    <router-outlet></router-outlet>
                </div>
                </div>
    `,
    styleUrls:['./dashboard.component.css']
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
            this.manager = await this.authService.getCurrentManager().first().toPromise();
        } catch(err) {
            console.error(err);
        }

    }

}