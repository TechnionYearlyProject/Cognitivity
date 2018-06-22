import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppRoutingModule} from './app-routing.module';
import { SharedModule } from './components/shared-module/shared-module.module';
import {HttpModule} from '@angular/http';
import {AngularFireModule} from 'angularfire2';
import {AngularFireDatabase, AngularFireDatabaseModule} from 'angularfire2/database';
import {AngularFireAuthModule} from 'angularfire2/auth';
import {TagInputModule} from 'ngx-chips';
import {AgGridModule} from 'ag-grid-angular';
/* Components */
import {AppComponent} from './app.component';
import {OpenQuestionComponent} from './components/open-question/open-question.component';
import {LoginPageComponent} from './components/login-page/login-page.component';
import {registrationFormComponent} from './components/registration-form/registration-form.component';
import {MyDatePickerModule} from 'mydatepicker';
import {BlockComponent} from './components/block/block.component';
import {QuestionComponent} from './components/question/question.component';
import {TestPageBlockComponent} from './components/test-page/block/block.component';
import {TestPageOpenQuestionComponent} from './components/test-page/open-question/open-question.component';
import {TestPageDrillDownQuestionComponent} from './components/test-page/drill-down-question/drill-down-question.component';
import {TestPageRateQuestionComponent} from './components/test-page/rate-question/rate-question.component';
import {TestPageMultipleChoiceQuestionComponent} from './components/test-page/multiple-choice-question/multiple-choice-question.component';
import {TestPageQuestionComponent} from './components/test-page/question/question.component';
/* Services */
import { TestManagerService, TestAnswerService, TestService, SubjectService, QuestionService, FileUploadService, TestAnswersService, EmailsService, CheckBackService, PictureLinkService } from './services/database-service';
import { RateQuestionComponent } from './components/rate-question/rate-question.component';
import { AuthService } from './services/auth-service';
import { LocalStorageService } from './services/local-storage';
import { CreateTestComponent } from './components/create-test/create-test.component';
import { CreateQuestionComponent } from './components/create-question/create-question.component';
import { MultipleChoiceQuestionComponent } from './components/multiple-choice-question/multiple-choice-question.component';
import { SessionService } from './services/session-service';
import { AuthGuard, LoginGuard } from './services/auth-service/auth-guard';
import { environment } from '../environments/environment';


import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {QuestionPreviewComponent} from './components/question-preview/question-preview.component';
import {BlockPreviewComponent} from './components/block-preview/block-preview.component';
import {TestPreviewComponent} from './components/test-preview/test-preview.component';
import {DrillDownQuestionComponent} from './components/drill-down-question/drill-down-question.component';
import {EditBlockComponent} from './components/edit-block/edit-block.component';
import {EditTestComponent} from './components/edit-test/edit-test.component';
import {CreateRateQuestionComponent} from './components/create-rate-question/create-rate-question.component';
import {CreateOpenQuestionComponent} from './components/create-open-question/create-open-question.component';
import {CreateDrillDownQuestionComponent} from './components/create-drill-down-question/create-drill-down-question.component';
import {CreateVerticalHorizontalMultipleComponent} from './components/create-vertical-horizontal-multiple/create-vertical-horizontal-multiple.component';
import {CreateMatrixMultipleQuestionComponent} from './components/create-matrix-multiple-question/create-matrix-multiple-question.component';
import {ResultsPageComponent} from './components/results-page/results-page.component';
import {TestPageComponent} from './components/test-page/test-page.component';
import {TestFinishComponent} from './components/test-page/test-finish/test-finish.component';
import {TestPageGuard} from './services/test-page-service/test-page-guard';
import {NotFoundComponent} from './components/not-found/not-found.component';
import {PreviewImportBlockComponent} from './components/preview-import-block/preview-import-block.component';
import {UploadComponent} from './components/uploads/upload.component';
import {GalleryComponent} from './components/gallery/gallery.component';
import {ImageService} from './services/image/image.service';
import {UploadService} from './services/uploads/upload.service';
import {ImageDetailComponent} from './components/image-detail/image-detail.component';


const PROVIDED_SERVICES = [
  TestManagerService,
  TestAnswerService,
  TestService,
  FileUploadService,
  SubjectService,
  QuestionService,
  AuthService,
  AuthGuard,
  LoginGuard,
  LocalStorageService,
  SessionService,
  AngularFireDatabase,
  AngularFireDatabaseModule,
  MatDialog,
  TestAnswersService,
  TestPageGuard,
  EmailsService,
  CheckBackService,
  ImageService,
  UploadService,
  PictureLinkService
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
    CreateMatrixMultipleQuestionComponent,
    ResultsPageComponent,
    TestPageComponent,
    TestPageBlockComponent,
    TestPageQuestionComponent,
    TestPageDrillDownQuestionComponent,
    TestPageMultipleChoiceQuestionComponent,
    TestPageOpenQuestionComponent,
    TestPageRateQuestionComponent,
    TestFinishComponent,
    NotFoundComponent,
    PreviewImportBlockComponent,
    UploadComponent,
    GalleryComponent,
    ImageDetailComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    MyDatePickerModule,
    HttpModule,
    AngularFireAuthModule,
    AngularFireModule.initializeApp(environment.firebase),
    MatDialogModule,
    BrowserAnimationsModule,
    TagInputModule,
    ReactiveFormsModule,
    AgGridModule.withComponents([]),
    SharedModule
  ],
  providers: [
    ...PROVIDED_SERVICES
  ],
  bootstrap: [AppComponent],
  entryComponents: [CreateQuestionComponent]
})
export class AppModule { }
