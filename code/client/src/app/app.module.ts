import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {AppRoutingModule} from './app-routing.module';

/* Components */
import { AppComponent } from './app.component';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import { MultipleQuestionComponent } from './components/multiple-question/multiple-question.component';
import { OpenQuestionComponent } from './components/open-question/open-question.component';

/* Services */
import { TestManagerService, TestAnswerService, TestService, SubjectService, QuestionService } from './services/database-service';


const PROVIDED_SERVICES = [
  TestManagerService, 
  TestAnswerService, 
  TestService, 
  SubjectService, 
  QuestionService
];

@NgModule({
  declarations: [
    AppComponent,
    MultipleQuestionComponent,
    OpenQuestionComponent,
    
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [
    ...PROVIDED_SERVICES
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
