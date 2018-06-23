import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common';
import { DashboardRoutingModule } from './dashboard-routing.module';
//import { SharedModule } from '../shared-module/shared-module.module';
import { FormsModule } from '@angular/forms';


import { DashboardComponent } from './dashboard.component'
import { NavbarComponent } from './navbar/navbar.component'
import { TestListComponent } from './test-list/test-list.component';


@NgModule({
    imports: [
        DashboardRoutingModule,
        CommonModule,
        FormsModule
    ],
    declarations: [
        DashboardComponent,
        NavbarComponent,
        TestListComponent
    ]
})
export class DashboardModule {}
