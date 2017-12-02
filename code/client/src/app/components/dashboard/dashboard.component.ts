import {Component} from '@angular/core';

@Component({
    selector: 'app-dashboard',
    template: `
                <app-navbar></app-navbar>
                <div class="container">
                    <router-outlet></router-outlet>
                </div>
    `
})
export class DashboardComponent {

}