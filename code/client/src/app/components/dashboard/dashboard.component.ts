import {Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { TestManagerService } from '../../services/database-service/index';
import { Manager } from '../../models/index';
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

/*
The wrapping component of the dashboard.
*/
export class DashboardComponent implements OnInit{
    //object for the currently logged-in user.
    manager;
    
    //default constructor 
    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private authService: AuthService,
        private tmService: TestManagerService
    ) {}

    //defualt initialization function.
    async ngOnInit() {
        this.manager = this.authService.getCurrentManager();
        let possibleId = await this.tmService.getManagerId(this.manager.email);
        if (possibleId == -1) {
            await this.tmService.saveTestManager(this.manager.email);
        }

    }

}