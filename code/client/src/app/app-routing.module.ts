import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

const appRoutes: Routes = [
    {
    path:'dashboard', loadChildren:'app/components/dashboard/dashboard.module#DashboardModule'
    },
    {path: '**',redirectTo: 'dashboard'}
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})
export class AppRoutingModule {}