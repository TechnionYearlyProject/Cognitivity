import {Routes, RouterModule} from '@angular/router'

import { NgModule } from '@angular/core'

import {DashboardComponent} from './dashboard.component'
import {TestListComponent} from './test-list/test-list.component'

const routes: Routes = [
    {
        path: 'dashboard',
        component: DashboardComponent,
        children: [
            {
                path:'test-list',
                component: TestListComponent
            }
        ]
    },
]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DashboardRoutingModule{}