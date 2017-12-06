import { Injectable } from '@angular/core';
import { Test } from '../../models'

@Injectable()
export class TestManagerService {
    //functions
    constructor() {}
}

@Injectable()
export class SubjectService {
    //functions
    constructor() {}
}

@Injectable()
export class TestService {
    //functions
    async getTestsForTestManager(managerId: string) {
        if (managerId == 'undefined') {
            console.log(managerId)
            return [];
        }
        //This is a dummy function that returns hard coded values.
        /*TODO: Implement proper data fetching */
        let test: Test = 
        {
            id: 1,
            name: 'test',
            numberOfQuestions: 3,
            status: 0,
            managerId: 2,
            lastAnswered: new Date().toDateString(),
            lastModified: new Date().toDateString(),
            numberOfFiledCopies: 0
        };
        let testList = [test];
        return testList
    }
    constructor() {}
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