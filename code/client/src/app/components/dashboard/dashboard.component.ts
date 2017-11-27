import {Component} from '@angular/core';

@Component({
    selector: 'app-dashboard',
    template: `
                <app-navbar></app-navbar>
                <router-outlet></router-outlet>
    `
})
export class DashboardComponent {

}