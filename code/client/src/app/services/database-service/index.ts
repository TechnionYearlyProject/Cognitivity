import { Injectable } from '@angular/core';
import { Test, Manager } from '../../models';
import { Http, Headers } from '@angular/http'
import { RequestOptionsArgs } from '@angular/http/src/interfaces';

@Injectable()
export class TestManagerService {
    //functions

    private headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http : Http) {}

    private handleError(error: any): Promise<any> {
        console.error('Error', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }

    saveTestManager(managerName: string, pass: string): Promise<Manager> {
        return this.http.post('/test-managers'+'/saveTestManager', JSON.stringify({'name': managerName, 'password': pass}),{headers: this.headers})
        .toPromise()
        .then(res => res.json() as Manager) //not so sure about this line, may need to change it
        .catch(this.handleError);
    }

    updateTestManager(manager: Manager): Promise<Manager> {
        return this.http.post('test-managers'+'/updateTestManager', JSON.stringify(manager), {headers: this.headers})
        .toPromise()
        .then(res => res.json() as Manager)
        .catch(this.handleError);
    }

    deleteTestManager(id: number): Promise<void> {
        return this.http.delete('/test-managers'+'/deleteTestManager'+'/${id}',{headers: this.headers})
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

    findTestsForTestManager(managerId: string): Promise<Test[]> {
        return this.http.get('/tests'+'/findTestsForTestManager'+'/${managerId}')
        .toPromise()
        .then(response => response.json() as Test[])
        .catch(this.handleError)
    }
    saveCognitiveTest(name: string, managerId: string, state: number, numberOfQuestions: number): Promise<Test> {
        var test: Test = {
            name: name,
            numberOfQuestions: numberOfQuestions,
            managerId: managerId,
            status: state,
            lastModified: new Date().toDateString(),
            lastAnswered: new Date().toDateString(),
            numberOfFiledCopies: 0
        };
        return this.http.post('/tests'+'/saveCognitiveTest',JSON.stringify(test),{headers : this.headers})
        .toPromise()
        .then(res => res.json() as Test)
        .catch(this.handleError);
    }

    deleteCognitiveTest(testId: number): Promise<void> {
        return this.http.delete('/tests'+'/deleteCognitiveTest'+'/${testId}', {headers: this.headers})
        .toPromise()
        .then(() => null)
        .catch(this.handleError)
    }
    updateCognitiveTest(test: Test): Promise<Test> {
        return this.http.post('/tests/'+'/updateCognitiveTest', JSON.stringify(test), {headers: this.headers})
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
    constructor() {}
}

@Injectable()
export class TestAnswerService {
    //functions
    constructor() {}
}