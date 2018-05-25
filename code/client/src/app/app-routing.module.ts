import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { DashboardModule } from './components/dashboard/dashboard.module';
import { QuestionComponent } from './components/question/question.component';
import { BlockComponent } from './components/block/block.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { TestPreviewComponent } from './components/test-preview/test-preview.component';
import { LoginGuard, AuthGuard } from './services/auth-service/auth-guard';
import { CreateTestComponent } from './components/create-test/create-test.component';
import { CreateQuestionComponent } from './components/create-question/create-question.component';
import { DrillDownQuestionComponent } from './components/drill-down-question/drill-down-question.component';
import { EditTestComponent } from './components/edit-test/edit-test.component';
import { TestPageComponent } from './components/test-page/test-page.component';

/*
Here we define the different routes in the application.
*/
const appRoutes: Routes = [
    //the login page
    {
        path: 'login',
        component: LoginPageComponent,
        canActivate: [LoginGuard]
    },
    {//previews a specific test according to it's id.
        path:'test-preview/:testId',
        component: TestPreviewComponent,
        canActivate: [AuthGuard]
    },
    {//create test page.
        path: 'create-test',
        component: CreateTestComponent,
        canActivate: [AuthGuard]
    },
    {//edit test page.
        path: 'edit-test/:testId',
        component: EditTestComponent,
        canActivate: [AuthGuard]
    },
    {
        path: 'test/:testId',
        component: TestPageComponent
    },
    {//in every other case , route to the dashboard.
        path: '**',
        redirectTo: 'dashboard'
    },
    
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes),DashboardModule],
    exports: [RouterModule]
})
export class AppRoutingModule {}
