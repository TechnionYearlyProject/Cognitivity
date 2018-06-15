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
  Vertical = 0,
  Horizontal,
  Matrix
}

/*
 enum for the types of the questions.
 we allow - multiple choice , rate , open text , and drilldown types.
 */
export enum TypeQuestion {
  MultipleChoice = 0,
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
  UpperRight = 0,
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
  showDistractions?: boolean;
  distractionsSeconds?:number;
  distractionsMinutes?:number;
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
  testId?: number,
  numberOfQuestions?: number,
  tag?: string
}

/*
 Interface that represents test object that holds its list of blocks.
 */
export interface Test {
    id?: number;
    name?: string;
    project?: string;
    notes?: string;
    numberOfQuestions?: number;
    state?: number;
    numberOfFiledCopies?: number;
    blocks?: Block[];
    numberOfSubjects?: number;
    testManager?: any;

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
export class SecondaryQuestionObject {
  index: number;
  questionText: string;
  answers: Array<string>;
  markedAnswer: number = -1;
}


export interface QuestionInDB {
  id?: number;
  question?: string;
  tag?: string;
  type?: number;
  questionPosition?: number;

  // list of given tags by the user
  tags?: string[];
}

//------------------------------------------------------------------------------------------
/**
 * This interfaces will be used by the timing class.
 * Author: Ben
 */
// Interface for a single question
export interface question_timing {
  //The question's ID
  qID: number;
  //Variable to indicate if the current question is being measured already
  qIsMeasured: boolean;
  //The question's start timestamp
  qStartTS: number;
  //The question's end timestamp
  qEndTS: number;
  //The question's total running time
  qTotTS: number;
  //The question's confidence bar start timestamp
  qConBarStartTS: number;
  //The question's confidence bar end timestamp
  qConBarEndTS: number;
  //The question's confidence bar total running time
  qConBarTotTS: number;
  //The ID of the containing block
  qBlockID: number;
}
//Interface to represent a single block in the timing class
export interface block_timing {
  //The block's ID
  bID: number;
  //The number of contained questions
  bQuestionsNum: number;
  //Variable to indicate if the current block is being measured already
  bIsMeasured: boolean;
  //The block's start TS
  bStartTS: number;
  //The block's end TS
  bEndTS: number;
  //The block's total running time
  bTotTS: number;
  //An array of all the questions and their information
  questionTimes: question_timing[];
}

/**
 * Interface to represent a single test in the timing class.
 * This interface will also contain the final result array being returned.
 */
export interface test_timing {
  //The test's ID
  tID: number;
  //The number of contained blocks
  tBlocksNum: number;
  //Variable to indicate if the current test is being measured already
  tIsMeasured: boolean;
  //The test's start TS
  tStartTS: number;
  //The test's end TS
  tEndTS: number;
  //The test's total running time
  tTotTS: number;
  //An array of all the blocks and their information
  resultArr: block_timing[];
}

export interface TimeMeasurment {
  // are those fileds in use?? by whom?
  timeForAnswering: number;
  numberOfAnswerChanges: number;

  //The following will contain the result test object
  testObject: test_timing;
}

//------------------------------------------------------------------------------------------

export interface TestSubject {
  id?: number;
  name: string;
  ipAddress?: string;
  browser?: string;
  occupation: string;
  birthDate: string;
  martialStatus: string;

}

// models to represent answers for all types of questions
export interface QuestionAnswer {
  questionId: number;
  testeeId: number;
  finalAnswer: string;
  id?: number;
  testId: number;
}

export interface QuestionAnswerForDB {
  question: QuestionInDB;
  cognitiveTest: Test;
  finalAnswer: string;
  id?: number;
  testSubject: TestSubject;

}
export interface OpenQuestionAnswer extends QuestionAnswer {
  answer: string;
}

export interface DrillDownQuestionAnswer extends QuestionAnswer {
  primaryAnswer: number;
  secnodaryAnswer: number;
}

export interface MultipleChoiceQuestionAnswer extends QuestionAnswer {
  answer: number;
}

export interface RateQuestionAnswer extends QuestionAnswer {
  answer: number;
}

export interface BlockAnswers {
  answers: QuestionAnswer[];
}

export interface Error {
  errorClass: string;
  message: string;
  type: string;
}

export interface ParsedQuestionAnswer {
  question_id: number;
  subject_id: number;
  name?: string;
  question_type: number;
  conf_value: number;
  is_time_distraction: boolean;
  changes_of_answer: number;
  time: number;
  time_conf: number;
  answer: string;
}
