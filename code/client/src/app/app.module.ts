import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {AppRoutingModule} from './app-routing.module';

/* Components */
import { AppComponent } from './app.component';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import { MultipleQuestionComponent } from './components/multiple-question/multiple-question.component';
import { OpenQuestionComponent } from './components/open-question/open-question.component';
import { LoginPageComponent } from './components/login-page/login-page.component';

/* Services */
import { TestManagerService, TestAnswerService, TestService, SubjectService, QuestionService } from './services/database-service';
import { RateQuestionComponent } from './components/rate-question/rate-question.component';
import { AuthService } from './services/auth-service';
import { LocalStorageService } from './services/local-storage';
import { CreateTestComponent } from './components/create-test/create-test.component';
import { CreateQuestionComponent } from './components/create-question/create-question.component';






const PROVIDED_SERVICES = [
  TestManagerService, 
  TestAnswerService, 
  TestService, 
  SubjectService, 
  QuestionService,
  AuthService,
  LocalStorageService
];

@NgModule({
  declarations: [
    AppComponent,
    MultipleQuestionComponent,
    OpenQuestionComponent,
    RateQuestionComponent,
    LoginPageComponent,
    CreateTestComponent,
    CreateQuestionComponent,

    
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
