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