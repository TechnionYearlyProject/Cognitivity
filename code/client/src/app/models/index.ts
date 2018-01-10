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

export enum TypeQuestion {
    MultipleChoice,
    RateQuestion,
    OpenQuestion
}

export enum QuestionPosition {
    UpperRight,
    UpperMiddle,
    UpperLeft,
    MiddleRight,
    MiddleMiddle,
    MiddleLeft,
    ButtomRight,
    ButtomMiddle,
    ButtomLeft
}


/*
    General question interface that all question types inherit from.
 */
export interface Question {
    questionText: string;
    type: TypeQuestion;
    questionPosition?: QuestionPosition;
}

/*
    An interface for the object that describes a multiple choice question
*/


export interface MultipleChoiceQuestion extends Question {
    answers: string[];
    correctAnswer: number;
    typeMultipleQuestion: TypeMultipleQuestion;
}
/*
    An interface for the object that describes an open question
*/
export interface OpenQuestion extends Question {
    answerText: string
}

export interface Test {
    id?: number,
    name: string,
    numberOfQuestions: number,
    status: number,
    managerId: string,
    lastModified: string,
    lastAnswered: string,
    numberOfFiledCopies: number

}
/* Manager properties */
//TODO: Check with Guy and Peer about the properties of the user.
export interface Manager {
    id?: number,
    name: string,
    userName: string
}
/*
    An interface for the object that describes a rating question
*/
export interface RateQuestion extends Question {
    heightOfRate: number
}
