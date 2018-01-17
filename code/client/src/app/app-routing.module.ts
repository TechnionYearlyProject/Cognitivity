import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { DashboardModule } from './components/dashboard/dashboard.module';
import { QuestionComponent } from './components/question/question.component';
import { QuestionViewerComponent } from './components/question-viewer/question-viewer.component';
import { QuestionListComponent } from './components/block/question-list/question-list.component';
import { BlockComponent } from './components/block/block.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { EditTestComponent } from './components/edit-test/edit-test.component';
import { TestPreviewComponent } from './components/test-preview/test-preview.component';
<<<<<<< HEAD
import { LoginGuard } from './services/auth-service/auth-guard';
=======
import { CreateTestComponent } from './components/create-test/create-test.component';
import { CreateQuestionComponent } from './components/create-question/create-question.component';
>>>>>>> 6aba9991690d3477ec49e0f7df031407f7be7746

const appRoutes: Routes = [
    
    {
        path: 'login',
        component: LoginPageComponent,
        canActivate: [LoginGuard]
    },
    {
        path: 'question-viewer',
        component:QuestionViewerComponent   
    },
    {
        path: 'block',
        component:BlockComponent
    },
    {
        path: 'edit-test',
        component:EditTestComponent
    },
    {
        path:'test-preview/:testId',
        component: TestPreviewComponent
    },
    {
        path: 'create-test',
        component: CreateTestComponent
    },
    {
        path: '**',
        redirectTo: 'login'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes),DashboardModule],
    exports: [RouterModule]
})
export class AppRoutingModule {}