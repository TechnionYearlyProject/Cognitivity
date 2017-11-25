import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

/* Components */
import { AppComponent } from './app.component';

/* Services */
import { TestManagerService, TestAnswerService, TestService, SubjectService, QuestionService } from './services/database-service';
import { MultipleQuestionComponent } from './components/multiple-question/multiple-question.component';

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
  ],
  imports: [
    BrowserModule
  ],
  providers: [
    ...PROVIDED_SERVICES
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
