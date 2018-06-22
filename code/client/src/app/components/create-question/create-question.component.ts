import {Component, OnInit, ViewChild} from "@angular/core";
import {QuestionPosition, TypeMultipleQuestion, TypeQuestion} from "../../models";
import {MatDialogRef} from "@angular/material";
import {SessionService} from "../../services/session-service";
import {CreateRateQuestionComponent} from "../create-rate-question/create-rate-question.component";
import {CreateOpenQuestionComponent} from "../create-open-question/create-open-question.component";
import {CreateVerticalHorizontalMultipleComponent} from "../create-vertical-horizontal-multiple/create-vertical-horizontal-multiple.component";
import {CreateMatrixMultipleQuestionComponent} from "../create-matrix-multiple-question/create-matrix-multiple-question.component";
import {CreateDrillDownQuestionComponent} from "../create-drill-down-question/create-drill-down-question.component";
//Import Mark
//import {TypeMyQuestion} from ../../models;
//import {CreateMyQuestion} from '../create-my-question/create-my-question.component';
@Component({
  selector: 'app-create-question',
  templateUrl: './create-question.component.html',
  styleUrls: ['./create-question.component.css']
})
export class CreateQuestionComponent implements OnInit {
  my_num: number = 7;
  @ViewChild(CreateRateQuestionComponent) rateQuestion: CreateRateQuestionComponent;
  @ViewChild(CreateOpenQuestionComponent) openQuestion: CreateOpenQuestionComponent;
  @ViewChild(CreateVerticalHorizontalMultipleComponent) verticalHrizontalQuestion: CreateVerticalHorizontalMultipleComponent;
  @ViewChild(CreateMatrixMultipleQuestionComponent) matrixQuestion: CreateMatrixMultipleQuestionComponent;
  @ViewChild(CreateDrillDownQuestionComponent) drillDownQeustion: CreateDrillDownQuestionComponent;
  //ViewChild Directive Mark
  //@ViewChild(CreateMyQuestion) createMyQuestion: CreateMyQuestion;
  /*
    ------------ general question details -------------------
  */
  questionText: string;
  typeQuestion: TypeQuestion;
  questionPosition: QuestionPosition;
  typeMultipleQuestion?: TypeMultipleQuestion;
  typedMultipleAnswer: boolean = false;


  /*
    --------------- drill down question details ---------------
  */
  //saves flag for knowing if the main answer was already submitted
  typedMainAnswer: boolean = false;
  //saces flag for knowing if the secondary answer was already submitted
  typedSecondaryAnswer: boolean = false;
  // //flag for the state when the secondary question is being submitted
  submitSecondaryQuestion?: boolean = false;
  submit: boolean = false;
  //edit mode
  editionMode: boolean = false;
  //index of answer to edit
  indexAnswerInEdit: number = -1;
  //show/hide distractions
  showDistractions:boolean = true;
  distractionsSeconds:number=0;

  //The question that created and sent to the block component. Will be null in case the user exited the creation of the question before its completion
  question_object: any;
  //String that describes the question type
  type_question_desc: string = 'Question Types';
  //string that describes the question position
  positon_desc: string = 'Question Position';
  //string that describes the multiple choice question type
  multiple_type: string = 'Answer Organization';

  /*
    Params:
    dialogRef - reference for the dialog between the block component to this component.
                This is for making the creation of the
                question as a modal and not in a different web page.
    transferData - Injected service for transfering the object that describes the
   question that the user has created to the block component.
  */
  constructor(public dialogRef: MatDialogRef<CreateQuestionComponent>, private transferData: SessionService) {

  }


  ngOnInit() {
    if (this.transferData.getData().editMode == true) {//Which mean we are in edition mode of the question
      this.question_object = this.transferData.getData().value;
      console.log(this.question_object);
      this.editQuestion(this.question_object);
    }
    this.transferData.clearData();
  }

  toggleDistractions(){
    console.log("toggling distractions...")
    this.showDistractions = !this.showDistractions;
    console.log(this.showDistractions)
  }

  updateDistractionCountdown(seconds:number){
    this.distractionsSeconds = seconds;

    console.log("set seconds -"+seconds);
  }

  getDistractionSeconds(){
    return this.distractionsSeconds;
  }


  /*
    Closing the dialog between this component to the block component.
    Could happen in case the user exited this component via the Cancel button
    or by finishing the creation of the question.
  */
  closeDialog() {
    this.dialogRef.close();
  }

  /*
   ----------- setters for the type of the created question
   */

  /*
   Setting the type of the created question, to multiple choice question
  */
  setMultipleQuestion() {
    this.submit = false;
    this.typedMultipleAnswer = false;
    this.typedSecondaryAnswer = false;
    this.submitSecondaryQuestion = false;
    this.submitSecondaryQuestion = false;
    this.typedMainAnswer = false;
    if (this.typeQuestion == TypeQuestion.MultipleChoice) {
      this.typeQuestion = null;
      this.type_question_desc = 'Question Types';
    } else {
      this.typeQuestion = TypeQuestion.MultipleChoice;
      this.type_question_desc = 'Multiple Choice Question';
    }
  }

  /*
   Setting the type of the created question, to rate question
  */
  setRateQuestion() {
    this.submit = false;
    this.typedMultipleAnswer = false;
    this.typedSecondaryAnswer = false;
    this.submitSecondaryQuestion = false;
    this.typedMainAnswer = false;
    if (this.typeQuestion == TypeQuestion.RateQuestion) {
      this.typeQuestion = null;
      this.type_question_desc = 'Question Types';
    } else {
      this.typeQuestion = TypeQuestion.RateQuestion;
      this.type_question_desc = 'Rate Question';
    }
  }

  //setMytype Method Mark
  /*
  *  setMytype(){
    this.submit = false;
    this.typedMultipleAnswer = false;
    this.submitSecondaryQuestion = false;
    this.typedSecondaryAnswer = false;
    this.typedMainAnswer = false;
    if(this.typeQuestion == TypeQuestion.OpenQuestion){
      this.typeQuestion = null;
      this.type_question_desc = 'Question Types';
    }else{
      this.typeQuestion = TypeQuestion.OpenQuestion;
      this.type_question_desc = 'My Type';
    }
  }
  */

  /*
   Setting the type of the created question, to open question
  */
  setOpenQuestion() {
    this.submit = false;
    this.typedMultipleAnswer = false;
    this.submitSecondaryQuestion = false;
    this.typedSecondaryAnswer = false;
    this.typedMainAnswer = false;
    if (this.typeQuestion == TypeQuestion.OpenQuestion) {
      this.typeQuestion = null;
      this.type_question_desc = 'Question Types';
    } else {
      this.typeQuestion = TypeQuestion.OpenQuestion;
      this.type_question_desc = 'Open Text Question';
    }
  }

  /*
   Setting the type of the created question, to drill down question
  */
  setDrillDownQuestion() {
    this.submit = false;
    this.submitSecondaryQuestion = false;
    this.typedMultipleAnswer = false;
    this.typedSecondaryAnswer = false;
    this.typedMainAnswer = false;
    if (this.typeQuestion == TypeQuestion.DrillDownQuestion) {
      this.typeQuestion = null;
      this.type_question_desc = 'Question Types';
    } else {
      this.typeQuestion = TypeQuestion.DrillDownQuestion;
      this.type_question_desc = 'Drill Down Question';
    }
  }

  /*
   Getters for the type of the question
  */
  didChoseMultipleQuestion(): boolean {
    return this.typeQuestion == TypeQuestion.MultipleChoice;
  }

  didChoseRateQuestion(): boolean {
    return this.typeQuestion == TypeQuestion.RateQuestion;
  }

  didChoseOpenQuestion(): boolean {
    return this.typeQuestion == TypeQuestion.OpenQuestion;
  }

  didChoseDrillDownQuestion(): boolean {
    return this.typeQuestion == TypeQuestion.DrillDownQuestion;
  }

  /*
    Setters and Getters for the position of the question
  */

  /*
    Upper Middle position
  */
  setUpperMiddlePosition() {
    if (this.questionPosition == QuestionPosition.UpperMiddle) {
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    } else {
      this.questionPosition = QuestionPosition.UpperMiddle;
      this.positon_desc = 'Upper Middle';
    }

  }

  didChoseUpperMiddle(): boolean {
    return this.questionPosition == QuestionPosition.UpperMiddle;
  }

  /*
    Upper Right position
  */
  setUpperRightPosition() {
    if (this.questionPosition == QuestionPosition.UpperRight) {
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    } else {
      this.questionPosition = QuestionPosition.UpperRight;
      this.positon_desc = 'Upper Right';
    }
  }

  didChoseUpperRight(): boolean {
    return this.questionPosition == QuestionPosition.UpperRight;
  }

  /*
    Upper Left position
  */
  setUpperLeftPosition() {
    if (this.questionPosition == QuestionPosition.UpperLeft) {
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    } else {
      this.questionPosition = QuestionPosition.UpperLeft;
      this.positon_desc = 'Upper Left';
    }
  }

  didChoseUpperLeft(): boolean {
    return this.questionPosition == QuestionPosition.UpperLeft;
  }

  /*
    Buttom Right position
  */
  setButtomRightPosition() {
    if (this.questionPosition == QuestionPosition.ButtomRight) {
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    } else {
      this.questionPosition = QuestionPosition.ButtomRight;
      this.positon_desc = 'Bottom Right';
    }
  }

  didChoseButtomRight(): boolean {
    return this.questionPosition == QuestionPosition.ButtomRight;
  }

  /*
    Buttom Left position
  */
  setButtomLeftPosition() {
    if (this.questionPosition == QuestionPosition.ButtomLeft) {
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    } else {
      this.questionPosition = QuestionPosition.ButtomLeft;
      this.positon_desc = 'Bottom Left';
    }
  }

  didChoseButtomLeft(): boolean {
    return this.questionPosition == QuestionPosition.ButtomLeft;
  }

  /*
    Buttom Middle position
  */
  setButtomMiddlePosition() {
    if (this.questionPosition == QuestionPosition.ButtomMiddle) {
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    } else {
      this.questionPosition = QuestionPosition.ButtomMiddle;
      this.positon_desc = 'Bottom Middle';
    }
  }

  didChoseButtomMiddle(): boolean {
    return this.questionPosition == QuestionPosition.ButtomMiddle;
  }

  /*
    Middle Right position
  */
  setMiddleRightPosition() {
    if (this.questionPosition == QuestionPosition.MiddleRight) {
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    } else {
      this.questionPosition = QuestionPosition.MiddleRight;
      this.positon_desc = 'Middle Right';
    }
  }

  didChoseMiddleRight(): boolean {
    return this.questionPosition == QuestionPosition.MiddleRight;
  }

  /*
    Middle Left position
  */
  setMiddleLeftPosition() {
    if (this.questionPosition == QuestionPosition.MiddleLeft) {
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    } else {
      this.questionPosition = QuestionPosition.MiddleLeft;
      this.positon_desc = 'Middle Left';
    }
  }

  didChoseMiddleLeft(): boolean {
    return this.questionPosition == QuestionPosition.MiddleLeft;
  }

  /*
    Middle Middle position
  */
  setMiddleMiddlePosition() {
    if (this.questionPosition == QuestionPosition.MiddleMiddle) {
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    } else {
      this.questionPosition = QuestionPosition.MiddleMiddle;
      this.positon_desc = 'Middle Middle';
    }
  }

  didChoseMiddleMiddle(): boolean {
    return this.questionPosition == QuestionPosition.MiddleMiddle;
  }

  /*
    Setters and Getters of the multiple choice Type: Matrix, Vertical or Hrizontal
  */

  /*
   Matrix type
  */
  setMatrixType() {
    this.submit = false;
    this.typedMultipleAnswer = false;
    if (this.typeMultipleQuestion == TypeMultipleQuestion.Matrix) {
      this.typeMultipleQuestion = null;
      this.multiple_type = 'Answer Organization';
    } else {
      this.typeMultipleQuestion = TypeMultipleQuestion.Matrix;
      this.multiple_type = 'Matrix';
    }
  }

  didChoseMatrixType() {
    return this.typeMultipleQuestion == TypeMultipleQuestion.Matrix;
  }

  /*
   Vertical type
  */
  setVerticalType() {
    this.submit = false;
    this.typedMultipleAnswer = false;
    if (this.typeMultipleQuestion == TypeMultipleQuestion.Vertical) {
      this.typeMultipleQuestion = null;
      this.multiple_type = 'Answer Organization';
    } else {
      this.typeMultipleQuestion = TypeMultipleQuestion.Vertical;
      this.multiple_type = 'Vertical';
    }
  }

  didChoseVerticalType() {
    return this.typeMultipleQuestion == TypeMultipleQuestion.Vertical;
  }

  /*
   Horizontal type
  */
  setHorizontalType() {
    this.submit = false;
    this.typedMultipleAnswer = false;
    if (this.typeMultipleQuestion == TypeMultipleQuestion.Horizontal) {
      this.typeMultipleQuestion = null;
      this.multiple_type = 'Answer Organization';
    } else {
      this.typeMultipleQuestion = TypeMultipleQuestion.Horizontal;
      this.multiple_type = 'Horizontal';
    }
  }

  didChoseHorizontalType() {
    return this.typeMultipleQuestion == TypeMultipleQuestion.Horizontal;
  }


  /*
    Submission question functions
  */

  /*
    The main function - checks if all the information related to the question was filled, if not
                        the function doesn't allow the finish of the dialog. If all the information is valid
                        and completed properly the dialog finishes and move back to the block compnent
   and transfering the question object via the SessionService service.
  */
  onSubmit() {
    if (this.questionText != null) {
      if (this.questionText != '' && !this.isSpacePrefix(this.questionText)) {
        if (this.didChosetype()) {

          if (this.didChoseMultipleQuestion()) {
            if (this.didChoseMultipleChoiceType() && this.didChoseQuestionPosition()) {
              if (this.didChoseVerticalType() || this.didChoseHorizontalType()) {
                if (this.haveAnswers()) {
                  this.findCorretAnswer();
                  this.constructMultipleQuestion();
                  this.transferData.setData(this.question_object);
                  this.closeDialog();
                }
              } else if (this.didChoseMatrixType()) {
                if (!this.missingAnswers()) {
                  this.findCorretAnswer();
                  this.constructMatrixQuestion();
                  this.transferData.setData(this.question_object);
                  this.closeDialog();
                }

              }
            }
          } else if (this.didChoseRateQuestion() && this.didChoseQuestionPosition()) {
            this.constructRateQuestion();
            this.transferData.setData(this.question_object);
            this.closeDialog();
          } else if (this.didChoseOpenQuestion() && this.didChoseQuestionPosition()) {
            this.constructOpenQuestion();
            this.transferData.setData(this.question_object);
            this.closeDialog();
          } else if (this.didChoseDrillDownQuestion()) {
            if (this.haveMainAnswers()) {
              this.constructDrillDownQuestion();
              this.transferData.setData(this.question_object);
              this.closeDialog();
            }
          }
          //Mark onSumit
        }


      }
    }

    this.submit = true;
  }


  constructDrillDownQuestion() {
    let correct_main_answer = -1;
    for (let i = 0; i < this.drillDownQeustion.markedMainCorrectAnswer.length; i++) {
      if (this.drillDownQeustion.markedMainCorrectAnswer[i]) {
        correct_main_answer = i;
      }
    }
    let secondary_question_text: Array<string> = new Array(this.drillDownQeustion.mainAnswers.length);
    let secondary_question_answers: Array<Array<string>> = new Array(this.drillDownQeustion.mainAnswers.length);
    let secondary_correct_answers: Array<number> = new Array(this.drillDownQeustion.mainAnswers.length);
    for (let i = 0; i < this.drillDownQeustion.mainAnswers.length; i++) {
      let found_secondary: boolean = false;
      for (let j = 0; j < this.drillDownQeustion.secondaryQuestionList.length; j++) {
        if (i == this.drillDownQeustion.secondaryQuestionList[j].index) {
          secondary_question_text[i] = this.drillDownQeustion.secondaryQuestionList[j].questionText;
          secondary_question_answers[i] = Object.assign([], this.drillDownQeustion.secondaryQuestionList[j].answers);
          secondary_correct_answers[i] = this.drillDownQeustion.secondaryQuestionList[j].markedAnswer;
          found_secondary = true;
        }
      }
      if (!found_secondary) {
        secondary_question_text[i] = null;
        secondary_question_answers[i] = null;
        secondary_correct_answers[i] = -1;
      }
    }
    let answers_main = Object.assign([], this.drillDownQeustion.mainAnswers);
    this.question_object = {
      questionText: this.questionText,
      type: this.typeQuestion,
      questionPosition: QuestionPosition.UpperMiddle,
      answersForMain: answers_main,
      correctMainQuestion: correct_main_answer,
      secondaryQuestionsText: secondary_question_text,
      answersForSecondary: secondary_question_answers,
      correctAnswerSecondary: secondary_correct_answers,
      showDistractions: this.showDistractions,
      distractionsSeconds:this.distractionsSeconds
    };

  }

  /*
    Construction the diffeerent types of questions after the form completed properly
  */

  /*
    Constructing the open question object
  */
  constructOpenQuestion() {
    if (this.openQuestion.answerText == null) {
      this.openQuestion.answerText = '';
    }
    this.question_object = {
      questionText: this.questionText,
      type: this.typeQuestion,
      questionPosition: this.questionPosition,
      answerText: this.openQuestion.answerText,
      showDistractions: this.showDistractions,
      distractionsSeconds:this.distractionsSeconds
    }
  }

  /*
    Constructing the rate question object
  */
  constructRateQuestion() {
    this.question_object = {
      questionText: this.questionText,
      type: this.typeQuestion,
      questionPosition: this.questionPosition,
      heightOfRate: this.rateQuestion.rateSize,
      showDistractions: this.showDistractions,
      distractionsSeconds:this.distractionsSeconds
    }
  }

  /*
   Constructing the matrix type of multiple choice question object
  */
  constructMatrixQuestion() {
    for (let i = 0; i < this.matrixQuestion.dimSize; i++) {
      for (let j = 0; j < this.matrixQuestion.dimSize; j++) {
        this.matrixQuestion.answers.splice(this.matrixQuestion.answers.length, 0, this.matrixQuestion.matrixAnswers[j][i]);
      }
    }
    if (this.matrixQuestion.correctAnswer == null) {
      this.matrixQuestion.correctAnswer = -1;
    }
    this.question_object = {
      questionText: this.questionText,
      type: this.typeQuestion,
      questionPosition: this.questionPosition,
      answers: this.matrixQuestion.answers,
      correctAnswer: this.matrixQuestion.correctAnswer,
      typeMultipleQuestion: this.typeMultipleQuestion,
      showDistractions: this.showDistractions,
      distractionsSeconds:this.distractionsSeconds
    }
  }

  /*
    Constructing the multiple choice question object
  */
  constructMultipleQuestion() {
    if (this.verticalHrizontalQuestion.correctAnswer == null) {
      this.verticalHrizontalQuestion.correctAnswer = -1;
    }
    this.question_object = {
      questionText: this.questionText,
      type: this.typeQuestion,
      questionPosition: this.questionPosition,
      answers: this.verticalHrizontalQuestion.answers,
      correctAnswer: this.verticalHrizontalQuestion.correctAnswer,
      typeMultipleQuestion: this.typeMultipleQuestion,
      showDistractions: this.showDistractions,
      distractionsSeconds:this.distractionsSeconds
    }
    if (this.verticalHrizontalQuestion.answers.indexOf("I don't know") == -1 && this.typeMultipleQuestion != TypeMultipleQuestion.Matrix) {
      this.verticalHrizontalQuestion.answers.splice(this.verticalHrizontalQuestion.answers.length, 0, "I don't know");
    }
  }

  /*
    Control flow functions that checks if the user entered all the related information for creating a question
  */

  /*
   Returns TRUE if the user have chosen type for his question, FALSE otherwise.
  */
  didChosetype(): boolean {
    return this.didChoseOpenQuestion() || this.didChoseRateQuestion() || this.didChoseMultipleQuestion() || this.didChoseDrillDownQuestion();
  }

  /*
    Returns TRUE if the user have chosen position for his question, FALSE otherwise.
  */
  didChoseQuestionPosition(): boolean {
    let x = this.didChoseUpperLeft() || this.didChoseUpperMiddle() || this.didChoseUpperRight() ||
      this.didChoseMiddleLeft() || this.didChoseMiddleMiddle() || this.didChoseMiddleRight() ||
      this.didChoseButtomLeft() || this.didChoseButtomMiddle() || this.didChoseButtomLeft() || this.didChoseButtomRight();
    return x;
  }

  /*
   Returns TRUE if the user entered a type for the multiple choice question,
   in case he chose a multiple choice question type, FLASE otherwise
  */
  didChoseMultipleChoiceType(): boolean {
    return this.didChoseMatrixType() || this.didChoseVerticalType() || this.didChoseHorizontalType();
  }

  /*
    Control flow function that checks if the user inserted at least one answer
  */
  haveAnswers(): boolean {
    if (this.verticalHrizontalQuestion != null) {
      return this.verticalHrizontalQuestion.answers.length > 0;
    }
    return false;

  }

  /*
  Control flow function which returns TRUE if there are missing answers in the matrix and FALSE other wise.
*/
  missingAnswers(): boolean {
    if (this.matrixQuestion != null) {
      for (let i = 0; i < this.matrixQuestion.dimSize; i++) {
        for (let j = 0; j < this.matrixQuestion.dimSize; j++) {
          if (this.matrixQuestion.matrixAnswers[i][j] == null) {
            return true;
          }
          if (this.matrixQuestion.matrixAnswers[i][j].length < 2) {
            return true;
          }
          if (this.isSpacePrefix(this.matrixQuestion.matrixAnswers[i][j])) {
            return true;
          }
        }
      }
    }


    return false;
  }

  /*
    For constructing the whole multiple choice question, after all its information is ready, according to the markings
    the corect answer is found and saved in this.correctAnswer
  */
  findCorretAnswer() {
    if (!this.didChoseMatrixType()) {
      for (let i = 0; i < this.verticalHrizontalQuestion.markedAnswers.length; i++) {
        if (this.verticalHrizontalQuestion.markedAnswers[i] == true) {
          this.verticalHrizontalQuestion.correctAnswer = i;
          break;
        }
      }
    } else {
      for (let i = 0; i < this.matrixQuestion.dimSize; i++) {
        for (let j = 0; j < this.matrixQuestion.dimSize; j++) {
          if (this.matrixQuestion.markedAnswersMatrix[i][j] == true) {
            this.matrixQuestion.correctAnswer = j * this.matrixQuestion.dimSize + i;
          }
        }
      }
    }

  }

  /*
    Canceling the dialog with the block compnent, when the user exited the creation of the question
    When not competing to fill all information. No data is being transfered
  */
  cancelDialog() {
    this.transferData.clearData();
    this.closeDialog();
  }

  /*
    This function is for editing an existing question. The function gets
    question and the form is being filled with the details of the question
  */
  editQuestion(question: any) {
    this.questionText = question.questionText;
    this.showDistractions = question.showDistractions;
    this.distractionsSeconds = question.distractionsSeconds;
    this.typeQuestion = question.type;
    this.initializeTypeString();
    this.questionPosition = question.questionPosition;
    this.initializePositionString();
    if (this.typeQuestion == TypeQuestion.MultipleChoice) {
      this.typeMultipleQuestion = question.typeMultipleQuestion;
      this.initializeMultipleString();
    }

  }

  /*
   Intializing the description of the type
  */
  initializeTypeString() {
    if (this.typeQuestion == TypeQuestion.DrillDownQuestion) {
      this.type_question_desc = 'Drill Down Question';
    } else if (this.typeQuestion == TypeQuestion.MultipleChoice) {
      this.type_question_desc = 'Multiple Choice Question'
    } else if (this.typeQuestion == TypeQuestion.OpenQuestion) {
      this.type_question_desc = 'Open Text Question';
    } else if (this.typeQuestion == TypeQuestion.RateQuestion) {
      this.type_question_desc = 'Rate Question';
    }
  }

  /*
    Intializing the description of the position
  */
  initializePositionString() {
    if (this.questionPosition == QuestionPosition.UpperRight) {
      this.positon_desc = 'Upper Right';
    } else if (this.questionPosition == QuestionPosition.UpperLeft) {
      this.positon_desc = 'Upper Left';
    } else if (this.questionPosition == QuestionPosition.UpperMiddle) {
      this.positon_desc = 'Upper Middle';
    } else if (this.questionPosition == QuestionPosition.ButtomRight) {
      this.positon_desc = 'Bottom Right';
    } else if (this.questionPosition == QuestionPosition.ButtomLeft) {
      this.positon_desc = 'Bottom Left';
    } else if (this.questionPosition == QuestionPosition.ButtomMiddle) {
      this.positon_desc = 'Bottom Middle';
    } else if (this.questionPosition == QuestionPosition.MiddleLeft) {
      this.positon_desc = 'Middle Left';
    } else if (this.questionPosition == QuestionPosition.MiddleRight) {
      this.positon_desc = 'Middle Right';
    } else if (this.questionPosition == QuestionPosition.MiddleMiddle) {
      this.positon_desc = 'Middle Middle';
    }
  }

  /*
   Intialize the type of the multiple choice question
  */
  initializeMultipleString() {
    if (this.typeMultipleQuestion == TypeMultipleQuestion.Matrix) {
      this.multiple_type = 'Matrix';
    } else if (this.typeMultipleQuestion == TypeMultipleQuestion.Vertical) {
      this.multiple_type = 'Vertical';
    } else if (this.typeMultipleQuestion == TypeMultipleQuestion.Horizontal) {
      this.multiple_type = 'Horizontal';
    }
  }

  haveMainAnswers(): boolean {
    return this.drillDownQeustion.mainAnswers.length >= 1;
  }

  // /*
  //   A function that returns true if the string starts with a white space, to control flawed input
  // */
  isSpacePrefix(str: string): boolean {
    if (str == null) {
      return false;
    }
    return str.charAt(0) == ' ';
  }

  /* ----------------------- New Create Question Code -------------------------- */
  QuestionWithPosition(): boolean {
    return this.didChoseRateQuestion() || this.didChoseOpenQuestion() || this.didChoseMultipleQuestion();
  }

}
