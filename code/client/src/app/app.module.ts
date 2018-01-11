import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {AppRoutingModule} from './app-routing.module';
import { HttpModule } from '@angular/http';
import { AngularFireModule } from 'angularfire2';
import { AngularFireDatabase, AngularFireDatabaseModule } from 'angularfire2/database';
import {AngularFireAuthModule } from 'angularfire2/auth'

/* Components */
import { AppComponent } from './app.component';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import { OpenQuestionComponent } from './components/open-question/open-question.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { registrationFormComponent } from './components/registration-form/registration-form.component';
import { MyDatePickerModule } from 'mydatepicker';
import { BlockComponent } from './components/block/block.component';
import { QuestionComponent } from './components/question/question.component';
/* Services */
import { TestManagerService, TestAnswerService, TestService, SubjectService, QuestionService } from './services/database-service';
import { RateQuestionComponent } from './components/rate-question/rate-question.component';
import { AuthService } from './services/auth-service';
import { LocalStorageService } from './services/local-storage';
import { CreateTestComponent } from './components/create-test/create-test.component';
import { CreateQuestionComponent } from './components/create-question/create-question.component';
import { QuestionListComponent } from './components/block/question-list/question-list.component';
import { MultipleChoiceQuestionComponent } from './components/multiple-choice-question/multiple-choice-question.component';
import { SessionService } from './services/session-service';
import { AuthGuard } from './services/auth-service/auth-guard';
import { environment } from '../environments/environment';








const PROVIDED_SERVICES = [
  TestManagerService, 
  TestAnswerService, 
  TestService, 
  SubjectService, 
  QuestionService,
  AuthService,
  AuthGuard,
  LocalStorageService,
  SessionService,
  AngularFireDatabase,
  AngularFireDatabaseModule
];

@NgModule({
  declarations: [
    AppComponent,
    OpenQuestionComponent,
    RateQuestionComponent,
    LoginPageComponent,
    CreateTestComponent,
    CreateQuestionComponent,
    registrationFormComponent,
    BlockComponent,
    QuestionComponent,
    QuestionListComponent,
    MultipleChoiceQuestionComponent
    
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    MyDatePickerModule,
    HttpModule,
    AngularFireAuthModule,
    AngularFireModule.initializeApp(environment.firebase,'Cognitivity')
  ],
  providers: [
    ...PROVIDED_SERVICES
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
