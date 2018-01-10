import { Injectable } from '@angular/core';
import { Test, Manager, Question, Block } from '../../models';
import { Http, Headers } from '@angular/http'
import { RequestOptionsArgs } from '@angular/http/src/interfaces';

@Injectable()
export class TestManagerService {
    //functions

    private headers = new Headers({'Content-Type': 'application/json'});
    base_mapping = '/test-managers';
    constructor(private http : Http) {}

    private handleError(error: any): Promise<any> {
        console.error('Error', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }

    saveTestManager(managerName: string, pass: string): Promise<Manager> {
        return this.http.post(`${this.base_mapping}/saveTestManager`, JSON.stringify({'name': managerName, 'password': pass}),{headers: this.headers})
        .toPromise()
        .then(res => res.json() as Manager) //not so sure about this line, may need to change it
        .catch(this.handleError);
    }

    updateTestManager(manager: Manager): Promise<Manager> {
        return this.http.post(`${this.base_mapping}/updateTestManager`, JSON.stringify(manager), {headers: this.headers})
        .toPromise()
        .then(res => res.json() as Manager)
        .catch(this.handleError);
    }

    deleteTestManager(id: number): Promise<void> {
        return this.http.delete(`${this.base_mapping}/deleteTestManager`+'/${id}',{headers: this.headers})
        .toPromise()
        .then(() => null)
        .catch(this.handleError);
    }
}

@Injectable()
export class SubjectService {
    //functions
    constructor() {}
}

@Injectable()
export class TestService {
    //functions
    private headers = new Headers({'Content-Type': 'application/json'});
    base_mapping = '/tests';
    findTestsForTestManager(managerId: string): Promise<Test[]> {
        return this.http.get(`${this.base_mapping}/findTestsForTestManager?managerId=${managerId}`)
        .toPromise()
        .then(response => response.json() as Test[])
        .catch(this.handleError)
    }
    saveCognitiveTest(name: string, managerId: number, state: number, numberOfQuestions: number, blockList: Block[]): Promise<void> {
        var test: Test = {
            name: name,
            numberOfQuestions: numberOfQuestions,
            managerId: managerId,
            status: state,
            lastModified: new Date().toDateString(),
            lastAnswered: new Date().toDateString(),
            numberOfFiledCopies: 0,
            blockList: blockList
        };
        return this.http.post(`${this.base_mapping}/saveCognitiveTest`,JSON.stringify(test),{headers : this.headers})
        .toPromise()
        .then(() => null)
        .catch(this.handleError);
    }

    deleteCognitiveTest(testId: number): Promise<void> {
        return this.http.delete(`${this.base_mapping}/deleteCognitiveTest/${testId}`, {headers: this.headers})
        .toPromise()
        .then(() => null)
        .catch(this.handleError)
    }
    updateCognitiveTest(test: Test): Promise<Test> {
        return this.http.post(`${this.base_mapping}/updateCognitiveTest`, JSON.stringify(test), {headers: this.headers})
        .toPromise()
        .then(res => res.json() as Test)
        .catch(this.handleError)
    }
    private handleError(error: any): Promise<any> {
        console.error('Error', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
    constructor(private http : Http) {}
}

@Injectable()
export class QuestionService {
    //functions
    base_mapping = '/test-questions';

    private headers = new Headers({'Content-Type': 'application/json'});

    private handleError(error: any): Promise<any> {
        console.error('Error', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
    constructor(private http: Http) {}

    findTestQuestionsForManager(managerId: number, testId: number = null): Promise<Question[]> {
        if (testId == null) {
            return this.http.get(`${this.base_mapping}/findTestQuestionsForTestCriteriaById?managerId=${managerId}`)
            .toPromise()
            .then(res => res.json() as Question[])
            .catch(this.handleError);
        }
        return this.http.get(`${this.base_mapping}/findTestQuestionsForTestCriteriaById?managerId=${managerId}&testId=${testId}`)
            .toPromise()
            .then(res => res.json() as Question[])
            .catch(this.handleError);
    }

    deleteTestQuestion(questionId: number): Promise<void> {
        return this.http.delete(`${this.base_mapping}/deleteTestQuestion?questionId=${questionId}`, {headers: this.headers})
        .toPromise()
        .then(() => null)
        .catch(this.handleError);
    }

}

@Injectable()
export class TestAnswerService {
    //functions
    constructor() {}
}