/* 
    This file will contain interfaces 
    which will represent data objects from the DB 
*/

export interface MultipleAnswer {
    answer: string;
    isMarked: boolean;
}


export interface MultipleAnsQuestion {
    text:string;
    answers: MultipleAnswer[];
    correctAnswer: number;
}

export interface OpenQuestion {
    questionText: string,
    answerText: string
}

export interface Test {
    id: number,
    name: string,
    numberOfQuestions: number,
    status: number,
    managerId: number,
    lastModified: string,
    lastAnswered: string,
    numberOfFiledCopies: number

}