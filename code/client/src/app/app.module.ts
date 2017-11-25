import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
/* Components */
import { AppComponent } from './app.component';

/* Services */
import { TestManagerService, TestAnswerService, TestService, SubjectService, QuestionService } from './services/database-service';
import { MultipleQuestionComponent } from './components/multiple-question/multiple-question.component';
import { OpenQuestionComponent } from './components/open-question/open-question.component';

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
    FormsModule
  ],
  providers: [
    ...PROVIDED_SERVICES
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
