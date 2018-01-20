import {Routes, RouterModule} from '@angular/router'

import { NgModule } from '@angular/core'

import {DashboardComponent} from './dashboard.component'
import {TestListComponent} from './test-list/test-list.component'
import { AuthGuard } from '../../services/auth-service/auth-guard';
import { TestPreviewComponent } from '../test-preview/test-preview.component';

const routes: Routes = [
    {
        path: 'dashboard',
        component: DashboardComponent,
        children: [
            {
                path:'test-list',
                component: TestListComponent
            },
            {
                path:'',
                redirectTo: 'test-list',
                pathMatch: 'full'
            },
            {
                path:'**',
                redirectTo: 'test-list',
            }
        ],
        canActivate: [AuthGuard]
    },
]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DashboardRoutingModule{}