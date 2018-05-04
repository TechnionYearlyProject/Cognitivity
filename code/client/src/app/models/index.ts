import { OnInit } from "@angular/core/src/metadata/lifecycle_hooks";


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
enum for the types of the questions.
we allow - multiple choice , rate , open text , and drilldown types.
*/
export enum TypeQuestion {
    MultipleChoice,
    RateQuestion,
    OpenQuestion,
    DrillDownQuestion
}
//TypeQuestion.MyType Mark
//Add at the enum above MyType to include the new type question

/*
enum for all the different positions in the screen that we allow to choose for the question's
text/answers.
*/
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
    id?: number
    questionText: string;
    type: TypeQuestion;
    questionPosition?: QuestionPosition;
    showConfidenceBar?: boolean; 
}

/*
class the represent the question as part of a block. 
in addition the the question object,  we add the index in the block , and the id of the 
question.
*/
export class QuestionInBlock {
    question: Question;
    indexInBlock?: number;
    id?: string;
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

/*
    Interface that represents block object that holds a list of questions.    
*/
export interface Block {
    id?: number,
    questions: QuestionInDB[],
    randomize?: boolean,
    projectId?: number,
    numberOfQuestions?: number
}

/*
    Interface that represents test object that holds its list of blocks.
 */
export interface Test {
    id?: number,
    name?: string,
    numberOfQuestions?: number,
    state?: number,
    numberOfFiledCopies?: number,
    blocks?: Block[],
    numberOfSubjects?: number,
    testManager?: any

}

/* Manager properties */
export interface Manager {
    id?: number,
    email: string
}

/*
    An interface for the object that describes a rating question
*/
export interface RateQuestion extends Question {
    heightOfRate: number
}

/*
    An interface for the dril down questions
    we are adding the option for optional secnodary questions after one question
*/
export interface DrillDownQuestion extends Question {
    answersForMain: Array<string>;    
    correctMainQuestion: number;
    secondaryQuestionsText: Array<string>;
    answersForSecondary: Array<Array<string>>;
    correctAnswerSecondary: Array<number>;
}

/*
class to represent the sub question as part of the drill down question type.
*/
export class SecondaryQuestionObject{
    index: number;
    questionText: string;
    answers: Array<string>; 
    markedAnswer: number = -1;
}


export interface QuestionInDB {
    id?: number;
    question?: string;
    tag?: string;
    questionType?: number;
    answer?: string;
    questionPosition?: number;
}


