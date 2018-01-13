import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { DashboardModule } from './components/dashboard/dashboard.module';
import { QuestionComponent } from './components/question/question.component';
import { QuestionViewerComponent } from './components/question-viewer/question-viewer.component';

const appRoutes: Routes = [
    {
        path: '',
        component:QuestionComponent
    },
    {
        path:'question-viewer',
        component:QuestionViewerComponent
    }
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes),DashboardModule],
    exports: [RouterModule]
})
export class AppRoutingModule {}