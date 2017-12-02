/* 
    This file will contain interfaces 
    which will represent data objects from the DB 
*/

/*
    Form for the multiple choice question.
    Vertical - The answers will be formed vertically
    Horizontal - The answers will be formed horizontally
    Matrix - The answers will be formed in a matrix structure
*/
export enum TypeMultipleQuestion {
    Vertical,
    Horizontal,
    Matrix
}
/*
    An interface for the object that describes the answer for multiple choice questions
*/
export interface MultipleAnswer {
    answer: string;
    isMarked: boolean;
}

/*
    An interface for the object that describes a multiple choice question
*/
export interface MultipleAnsQuestion {
    text:string;
    answers: MultipleAnswer[];
    correctAnswer: number;
    typeMultipleQuestion: TypeMultipleQuestion;
}
/*
    An interface for the object that describes an open question
*/
export interface OpenQuestion {
    questionText: string,
    answerText: string
}

/*
    An interface for the object that describes a rating question
*/
export interface RateQuestion {
    questionText: string,
    heightOfRate: number
}
