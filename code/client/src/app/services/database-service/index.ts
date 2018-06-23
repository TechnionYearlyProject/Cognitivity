import {Injectable} from '@angular/core';
import {EmailsDist, Manager, Question, QuestionAnswer, QuestionAnswerForDB, Test, TestSubject} from '../../models';
import {Headers, Http} from '@angular/http'
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/empty';
import 'rxjs/add/operator/retry';
import 'rxjs/add/operator/do';

//The following code is meant to make the error handling more modular, please ignore it for now

// @Injectable()
// export class HttpErrorInterceptor implements HttpInterceptor {
//   intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
//     return next.handle(request)
//       .catch((err: HttpErrorResponse) => {

//         if (err.error instanceof Error) {
//           // A client-side or network error occurred. Handle it accordingly.
//           console.error('An error occurred:', err.error.message);
//           alert("WTF WTF WT WT WTF ");
//         } else {
//           // The backend returned an unsuccessful response code.
//           // The response body may contain clues as to what went wrong,
//           console.error(`Backend returned code ${err.status}, body was: ${err.error}`);
//           alert("DAMNN DAMNN DAMNN DAMNN");
//         }

//         // ...optionally return a default fallback value so app can continue (pick one)
//         // which could be a default value (which has to be a HttpResponse here)
//         // return Observable.of(new HttpResponse({body: [{name: "Default value..."}]}));
//         // or simply an empty observable
//         alert("ALL IS WELL ALL IS WELL ALL IS WELL!");
//         return Observable.empty<HttpEvent<any>>();
//       });
//   }
// }

class HttpTarget {
  private static deployedUrl: string = 'https://cognitivity.azurewebsites.net';
  private static httpTarget: string = 'http://localhost:8181';

  static getHttpTaraget(): string {
    return this.deployedUrl;
  }
}

// Error handler class, holds behavior when errors are returned from server
class ErrorHandler {
  static handleError(error: any) {

    let errorMessage = JSON.parse(error._body).message;
    alert("Error:\ncould not perform the last operation.\n" + errorMessage);

    return Promise.reject(error.message || error);
  }


}


/*
This service connects our front-end components against the DB.
Each function is described well by is name.
*/
@Injectable()
export class TestManagerService {
  private target: string = HttpTarget.getHttpTaraget();
  //tuples for representing the way we send data between the front-end and DB.
  private headers = new Headers({'Content-Type': 'application/json'});

  //the basic route mapping.
  base_mapping = '/test-managers';

  //default constructor, getting the HTTP service.
  constructor(private http: Http) {
  }


  saveTestManager(email: string): Promise<void> {
    return this.http.post(`${this.target}${this.base_mapping}/saveTestManager`, JSON.stringify({'email': email}), {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(ErrorHandler.handleError);
  }

  updateTestManager(manager: Manager): Promise<Manager> {
    return this.http.post(`${this.target}${this.base_mapping}/updateTestManager`, JSON.stringify(manager), {headers: this.headers})
      .toPromise()
      .then(res => res.json() as Manager)
      .catch(ErrorHandler.handleError);
  }

  getManagerId(email: string): Promise<number> {
    return this.http.get(`${this.target}${this.base_mapping}/findTestManagerIdByEmail?email=${email}`)
      .toPromise()
      .then(res => {
        if (res.status == 204)
          return -1;

        return parseInt(res.text())
      })
      .catch(() => {
        return -1;
      })
  }

  deleteTestManager(id: number): Promise<void> {
    return this.http.delete(`${this.target}${this.base_mapping}/deleteTestManager` + '/${id}', {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(ErrorHandler.handleError);
  }

  getTestManager(managerId: number): Promise<any> {
    return this.http.get(`${this.target}${this.base_mapping}/findTestManagersForTestCriteria?testManagerId=${managerId}&testId=-1`)
      .toPromise()
      .then(res => {
        if (res.text() == '') return null;
        return res.json()
      })
  }
}


@Injectable()
export class TestService {
  //functions
  private target: string = HttpTarget.getHttpTaraget();
  private headers = new Headers({'Content-Type': 'application/json'});
  base_mapping = '/tests';

  findTestsForTestManager(managerId: number): Promise<Test[]> {
    return this.http.get(`${this.target}${this.base_mapping}/findTestsForTestManagerWithoutQuestions?managerId=${managerId}`)
      .toPromise()
      .then(response => response.json() as Test[])
      .catch(ErrorHandler.handleError)
  }

  saveCognitiveTest(test: Test): Promise<void> {
    return this.http.post(`${this.target}${this.base_mapping}/saveCognitiveTest`, JSON.stringify(test), {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(ErrorHandler.handleError);
  }

  deleteCognitiveTest(testId: number): Promise<void> {
    return this.http.delete(`${this.target}${this.base_mapping}/deleteCognitiveTest?testId=${testId}`, {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(ErrorHandler.handleError)
  }

  updateCognitiveTest(test: Test): Promise<Test> {
    return this.http.post(`${this.target}${this.base_mapping}/updateCognitiveTest`, JSON.stringify(test), {headers: this.headers})
      .toPromise()
      .then(res => res.json() as Test)
      .catch(ErrorHandler.handleError)
  }

  findCognitiveTestById(testId: number): Promise<Test> {
    return this.http.get(`${this.target}${this.base_mapping}/findCognitiveTestById?testId=${testId}`)
      .toPromise()
      .then(response => response.json() as Test)
      .catch(() => {
        return null;
      })
  }


  constructor(private http: Http) {
  }
}


@Injectable()
export class QuestionService {
  //functions
  base_mapping = '/test-questions';
  private target = HttpTarget.getHttpTaraget();
  private headers = new Headers({'Content-Type': 'application/json'});


  constructor(private http: Http) {
  }

  findTestQuestionsForManager(managerId: number, testId: number = null): Promise<Question[]> {
    if (testId == null) {
      return this.http.get(`${this.target}${this.base_mapping}/findTestQuestionsForTestCriteriaById?managerId=${managerId}`)
        .toPromise()
        .then(res => res.json() as Question[])
        .catch(ErrorHandler.handleError);
    }
    return this.http.get(`${this.target}${this.base_mapping}/findTestQuestionsForTestCriteriaById?managerId=${managerId}&testId=${testId}`)
      .toPromise()
      .then(res => res.json() as Question[])
      .catch(ErrorHandler.handleError);
  }

  deleteTestQuestion(questionId: number): Promise<void> {
    return this.http.delete(`${this.target}${this.base_mapping}/deleteTestQuestion?questionId=${questionId}`, {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(ErrorHandler.handleError);
  }


}


/*
 *
 * Author: Mark Erlikh Date: 19.5.18
 *
 */

/*
 * This service os for pulling all the results information on a specific test
 */
@Injectable()
export class TestAnswersService {
  base_mapping = '/test-answers';
  private target: string = HttpTarget.getHttpTaraget();
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http) {
  }

  findAllAnswersForTest(testId: number): Promise<QuestionAnswerForDB[]> {
    return this.http.get(`${this.target}${this.base_mapping}/findAllTestAnswersForATest?testId=${testId}`, {headers: this.headers})
      .toPromise()
      .then(res => res.json() as QuestionAnswerForDB[])
      .catch(ErrorHandler.handleError);
  }

  saveTestAnswer(answer: QuestionAnswerForDB): Promise<QuestionAnswerForDB> {
    return this.http.post(`${this.target}${this.base_mapping}/saveTestAnswer`, JSON.stringify(answer), {headers: this.headers})
      .toPromise()
      .then(res => res.json() as QuestionAnswerForDB)
      .catch(ErrorHandler.handleError);
  }

  deleteTestAnswer(questionId: number, testAnswerId: number = -1): Promise<void> {
    return this.http.delete(`${this.target}${this.base_mapping}/deleteTestAnswer?questionId=${questionId}&testAnswerId=${testAnswerId}`, {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(ErrorHandler.handleError);
  }

  updateTestAnswer(answer: QuestionAnswer): Promise<QuestionAnswer> {
    return this.http.post(`${this.target}${this.base_mapping}/updateTestAnswer`, JSON.stringify(answer), {headers: this.headers})
      .toPromise()
      .then(res => res.json() as QuestionAnswer)
      .catch(ErrorHandler.handleError)
  }

  findTestAnswersBySubjectId(subjectId: number): Promise<QuestionAnswer[]> {
    return this.http.get(`${this.target}${this.base_mapping}//findTestAnswersBySubjectId?subjectId=${subjectId}`, {headers: this.headers})
      .toPromise()
      .then(res => res.json() as QuestionAnswer[])
      .catch(ErrorHandler.handleError);
  }


}

//TODO: this is in use?
@Injectable()
export class TestAnswerService {
  //functions
  constructor() {
  }
}

@Injectable()
export class FileUploadService {
  base_mapping = '/load-from-file';

  private headers = new Headers({'Content-Type': 'application/json'});

  private target: string = HttpTarget.getHttpTaraget();

  constructor(private http: Http) {
  }

  uploadCognitiveTest(test, managerId): Promise<void> {
    return this.http.post(`${this.target}${this.base_mapping}/loadFromJSONFile?managerId=${managerId}`, JSON.stringify(test), {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(ErrorHandler.handleError);
  }


}

@Injectable()
export class SubjectService {
  private target: string = HttpTarget.getHttpTaraget();
  private headers = new Headers({'Content-Type': 'application/json'});
  base_mapping = '/test-subjects';

  //functions
  constructor(private http: Http) {
  }

  saveTestSubject(testSubject: TestSubject): Promise<TestSubject> {
    return this.http.post(`${this.target}${this.base_mapping}/saveTestSubject`, JSON.stringify(testSubject), {headers: this.headers})
      .toPromise()
      .then(res => res.json() as TestSubject)
      .catch(ErrorHandler.handleError);
  }

}

@Injectable()
export class EmailsService {
  private target: string = HttpTarget.getHttpTaraget();
  private headers = new Headers({'Content-Type': 'application/json'});
  base_mapping = '/send-links';

  //functions
  constructor(private http: Http) {
  }

  sendLinks(emails: EmailsDist): Promise<EmailsDist> {
    console.log(JSON.stringify(emails.link));
    console.log(emails.emails);
    let request = `${this.target}${this.base_mapping}/sendLinksToSubjects?link=${emails.link}`;
    console.log(request);
    return this.http.post(request, emails.emails, {headers: this.headers})
      .toPromise()
      .then(res => null)
      .catch(ErrorHandler.handleError);
  }
}

class STRING {
  body: string;
}

@Injectable()
export class CheckBackService {
  private target: string = HttpTarget.getHttpTaraget();
  private headers = new Headers({"Content-Type": "application/json"});
  private base_mapping = '/send-links';

  //functions
  constructor(private http: Http) {
  }

  checkBackEnd(): Promise<string> {
    return this.http.get(`${this.target}${this.base_mapping}/hi`, {headers: this.headers})
      .toPromise()
      .then(response => response.text())
      .catch(ErrorHandler.handleError)
  }
}


@Injectable()
export class PictureLinkService {
  //functions
  base_mapping = '/picture-links';
  private target = HttpTarget.getHttpTaraget();
  private headers = new Headers({'Content-Type': 'application/json'});


  constructor(private http: Http) {
  }


  savePictureLink(link: string, name: string): Promise<void> {
    let PLink = {link: link, name: name};
    return this.http.post(`${this.target}${this.base_mapping}/savePictureLink`, JSON.stringify(PLink), {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(ErrorHandler.handleError);
  }

  findAllPictureLinks(): Promise<string[]> {
    return this.http.get(`${this.target}${this.base_mapping}/findAllPictureLinksInTheSystem`, {headers: this.headers})
      .toPromise()
      .then(res => res.json() as string[])
      .catch(ErrorHandler.handleError);
  }

  deletePictureLink(LinkName: string): Promise<void> {
    return this.http.delete(`${this.target}${this.base_mapping}/deletePictureLink?PictureLinkName=${LinkName}`, {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(ErrorHandler.handleError);
  }


}
