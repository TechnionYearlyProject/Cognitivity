import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import {AppRoutingModule} from './app-routing.module';
import { HttpModule } from '@angular/http';
import { AngularFireModule } from 'angularfire2';
import { AngularFireDatabase, AngularFireDatabaseModule } from 'angularfire2/database';
import {AngularFireAuthModule } from 'angularfire2/auth';
import { TagInputModule } from 'ngx-chips';



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
import { MultipleChoiceQuestionComponent } from './components/multiple-choice-question/multiple-choice-question.component';
import { SessionService } from './services/session-service';
import { AuthGuard, LoginGuard } from './services/auth-service/auth-guard';
import { environment } from '../environments/environment';


import { MatDialog, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { QuestionPreviewComponent } from './components/question-preview/question-preview.component';
import { BlockPreviewComponent } from './components/block-preview/block-preview.component';
import { TestPreviewComponent } from './components/test-preview/test-preview.component';
import { DrillDownQuestionComponent } from './components/drill-down-question/drill-down-question.component';
import { EditBlockComponent } from './components/edit-block/edit-block.component';
import { EditTestComponent } from './components/edit-test/edit-test.component';
import { CreateRateQuestionComponent } from './components/create-rate-question/create-rate-question.component';
import { CreateOpenQuestionComponent } from './components/create-open-question/create-open-question.component';
import { CreateDrillDownQuestionComponent } from './components/create-drill-down-question/create-drill-down-question.component';
import { CreateVerticalHorizontalMultipleComponent } from './components/create-vertical-horizontal-multiple/create-vertical-horizontal-multiple.component';
import { CreateMatrixMultipleQuestionComponent } from './components/create-matrix-multiple-question/create-matrix-multiple-question.component';







const PROVIDED_SERVICES = [
  TestManagerService, 
  TestAnswerService, 
  TestService, 
  SubjectService, 
  QuestionService,
  AuthService,
  AuthGuard,
  LoginGuard,
  LocalStorageService,
  SessionService,
  AngularFireDatabase,
  AngularFireDatabaseModule,
  MatDialog
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
    MultipleChoiceQuestionComponent,
    QuestionPreviewComponent,
    BlockPreviewComponent,
    TestPreviewComponent,
    DrillDownQuestionComponent,
    EditBlockComponent,
    EditTestComponent,
    CreateRateQuestionComponent,
    CreateOpenQuestionComponent,
    CreateDrillDownQuestionComponent,
    CreateVerticalHorizontalMultipleComponent,
    CreateMatrixMultipleQuestionComponent
    
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    MyDatePickerModule,
    HttpModule,
    AngularFireAuthModule,
    AngularFireModule.initializeApp(environment.firebase,'Cognitivity'),
    MatDialogModule,
    BrowserAnimationsModule,
    TagInputModule, 
    ReactiveFormsModule
  ],
  providers: [
    ...PROVIDED_SERVICES
  ],
  bootstrap: [AppComponent],
  entryComponents: [CreateQuestionComponent]
})
export class AppModule { }
