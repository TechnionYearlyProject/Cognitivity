import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common';
import {DashboardRoutingModule} from './dashboard-routing.module'

import {DashboardComponent} from './dashboard.component'
import {NavbarComponent} from './navbar/navbar.component'

@NgModule({
    imports: [
        DashboardRoutingModule,
        CommonModule
    ],
    declarations: [
        DashboardComponent,
        NavbarComponent
    ] 
})
export class DashboardModule {}