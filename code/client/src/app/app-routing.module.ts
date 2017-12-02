import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { DashboardModule } from './components/dashboard/dashboard.module';

const appRoutes: Routes = [
    {
        path: '**',
        redirectTo: 'dashboard'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes),DashboardModule],
    exports: [RouterModule]
})
export class AppRoutingModule {}