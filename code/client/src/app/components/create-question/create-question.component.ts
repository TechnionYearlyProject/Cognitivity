import { Component, OnInit } from '@angular/core';
import { TypeMultipleQuestion, TypeQuestion, QuestionPosition} from '../../models';
import {MatDialogRef} from '@angular/material';
import {SessionService} from '../../services/session-service';
import { SecondaryQuestionObject } from '../../models';
import { ViewChild } from '@angular/core';
import { CreateRateQuestionComponent } from '../create-rate-question/create-rate-question.component';
import { CreateOpenQuestionComponent } from '../create-open-question/create-open-question.component';
import { CreateVerticalHorizontalMultipleComponent } from '../create-vertical-horizontal-multiple/create-vertical-horizontal-multiple.component';
import { CreateMatrixMultipleQuestionComponent } from '../create-matrix-multiple-question/create-matrix-multiple-question.component';
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
  /*
    ------------ general question details -------------------
  */
  questionText: string;
  typeQuestion: TypeQuestion;
  questionPosition: QuestionPosition;
  typeMultipleQuestion?: TypeMultipleQuestion;
  typedMultipleAnswer: boolean = false;

  //--------- data regarding the matrix type of multiple choice question
  // dimSize?: number = 2;
  // markedAnswersMatrix?: Array<Array<boolean>>;
  // matrixAnswers?: Array<Array<string>>;
  // centeringMatrix?: any;
  // iteratorArray?: Array<Array<null>>;

  /*
    --------------- drill down question details ---------------
  */
  //saves flag for knowing if the main answer was already submitted
  typedMainAnswer: boolean = false;
  //saces flag for knowing if the secondary answer was already submitted
  typedSecondaryAnswer: boolean = false;
  //current main answer that is submitted
  currentMainAnswer?: string;
  //flag for the state when the user pressed a main answer
  showMainAnswer: boolean;
  //the index of the answer that the user wants to see
  indexOfMainanswerToShow: number = -1;
  //an array of all the main answers
  mainAnswers?: Array<string> = new Array();
  //the marking of all the main answers
  markedMainCorrectAnswer?: Array<boolean> = new Array();
  //an array of all the secondary questions
  secondaryQuestionList? : Array<SecondaryQuestionObject> = new Array();
  //array of the answers to the current secondary question
  secondaryAnswers?: Array<string> = new Array();
  //flag for the state when the secondary question is being submitted
  submitSecondaryQuestion?: boolean = false;
  //current secondary question that the user inputs
  currentSecondaryQuestion?: string = '';
  //current secondary answer that the user inputs
  currentSecondaryAnswer?: string = '';
  //array of all secondary question
  secondaryQuestions?: Array<string> = new Array();
  //edit mode of editing the whole secondary question
  editionQuestionSecondary?: boolean = false;
  //array of the marked secondary answers
  markedSecondaryCorrectAnswer?: Array<boolean> = new Array();
  //edit mode of a secondary answer
  editionModeSecondary: boolean = false;
  //index of an answer that being edited
  indexAnswerInEditSecondary: number = -1;
  //view mode of a secondary question
  viewSecondary: boolean = false;
  //answers of the secondary that is being viewd
  viewAnswers: Array<string> = new Array();
  //answers of the secondary question that is being 
  questionView: string = '';
  //secondaryAnswers?: Array<Array<string>>;
  editionModeMain?: boolean;
  //flag for the state when the user creates a secondary question
  secondaryAnswerMode?: boolean = false;
  //index of the main answer to edit
  indexAnswerInEditMain: number = -1;
  //flag for the state when the user submits the question
  submit: boolean = false;
  //edit mode
  editionMode: boolean = false;
  //index of answer to edit
  indexAnswerInEdit: number = -1;

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
    if(this.transferData.getData().editMode == true){//Which mean we are in edition mode of the question
      this.question_object = this.transferData.getData().value;
      console.log(this.question_object);
      this.editQuestion(this.question_object);
    }
    this.transferData.clearData();
  }


  /*
    Closing the dialog between this component to the block component.
    Could happen in case the user exited this component via the Cancel button
    or by finishing the creation of the question.
  */
  closeDialog(){
    this.dialogRef.close();
  }
  /*
   ----------- setters for the type of the created question
   */
  /*
    Setting the type of the created question, to multiple choice question
  */
  setMultipleQuestion(){
    this.submit = false;
    this.typedMultipleAnswer = false;
    this.typedSecondaryAnswer = false;
    this.submitSecondaryQuestion = false;
    this.submitSecondaryQuestion = false;
    this.typedMainAnswer = false;
    if(this.typeQuestion == TypeQuestion.MultipleChoice){
      this.typeQuestion = null;
      this.type_question_desc = 'Question Types';
    }else{
      this.typeQuestion = TypeQuestion.MultipleChoice;
      this.type_question_desc = 'Multiple Choice Question';
    }
  }

  /*
    Setting the type of the created question, to rate question
  */
  setRateQuestion(){
    this.submit = false;
    this.typedMultipleAnswer = false;
    this.typedSecondaryAnswer = false;
    this.submitSecondaryQuestion = false;
    this.typedMainAnswer = false;
    if(this.typeQuestion == TypeQuestion.RateQuestion){
      this.typeQuestion = null;
      this.type_question_desc = 'Question Types';
    }else{
      this.typeQuestion = TypeQuestion.RateQuestion;
      this.type_question_desc = 'Rate Question';
    }
  }

  /*
    Setting the type of the created question, to open question
  */
  setOpenQuestion(){
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
      this.type_question_desc = 'Open Text Question';
    }
  }

  /*
    Setting the type of the created question, to drill down question
  */
  setDrillDownQuestion(){
    this.submit = false;
    this.submitSecondaryQuestion = false;
    this.typedMultipleAnswer = false;
    this.typedSecondaryAnswer = false;
    this.typedMainAnswer = false;
    if(this.typeQuestion == TypeQuestion.DrillDownQuestion){
      this.typeQuestion = null;
      this.type_question_desc = 'Question Types';
    }else{
      this.typeQuestion = TypeQuestion.DrillDownQuestion;
      this.type_question_desc = 'Drill Down Question';
    }
  }
  /*
    Getters for the type of the question
  */
  didChoseMultipleQuestion() : boolean{
    return this.typeQuestion == TypeQuestion.MultipleChoice;
  }
  didChoseRateQuestion() : boolean{
    return this.typeQuestion == TypeQuestion.RateQuestion;
  }
  didChoseOpenQuestion() : boolean{
    return this.typeQuestion == TypeQuestion.OpenQuestion;
  }
  didChoseDrillDownQuestion() : boolean{
    return this.typeQuestion == TypeQuestion.DrillDownQuestion;
  }

  /*
    Setters and Getters for the position of the question
  */

  /*
    Upper Middle position
  */
  setUpperMiddlePosition() {
    if(this.questionPosition == QuestionPosition.UpperMiddle){
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    }else{
      this.questionPosition = QuestionPosition.UpperMiddle;
      this.positon_desc = 'Upper Middle';
    }
    
  }
  didChoseUpperMiddle():boolean {
    return this.questionPosition == QuestionPosition.UpperMiddle;
  }

  /*
    Upper Right position
  */
  setUpperRightPosition() {
    if(this.questionPosition == QuestionPosition.UpperRight){
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    }else{
      this.questionPosition = QuestionPosition.UpperRight;
      this.positon_desc = 'Upper Right';
    }
  }
  didChoseUpperRight():boolean {
    return this.questionPosition == QuestionPosition.UpperRight;
  }

  /*
    Upper Left position
  */
  setUpperLeftPosition() {
    if(this.questionPosition == QuestionPosition.UpperLeft){
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    }else{
      this.questionPosition = QuestionPosition.UpperLeft;
      this.positon_desc = 'Upper Left';
    }
  }
  didChoseUpperLeft():boolean {
    return this.questionPosition == QuestionPosition.UpperLeft;
  }

  /*
    Buttom Right position
  */
  setButtomRightPosition() {
    if(this.questionPosition == QuestionPosition.ButtomRight){
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    }else{
      this.questionPosition = QuestionPosition.ButtomRight;
      this.positon_desc = 'Bottom Right';
    }
  }
  didChoseButtomRight():boolean {
    return this.questionPosition == QuestionPosition.ButtomRight;
  }

  /*
    Buttom Left position
  */
  setButtomLeftPosition() {
    if(this.questionPosition == QuestionPosition.ButtomLeft){
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    }else{
      this.questionPosition = QuestionPosition.ButtomLeft;
      this.positon_desc = 'Bottom Left';
    }
  }
  didChoseButtomLeft():boolean {
    return this.questionPosition == QuestionPosition.ButtomLeft;
  }

  /*
    Buttom Middle position
  */
  setButtomMiddlePosition() {
    if(this.questionPosition == QuestionPosition.ButtomMiddle){
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    }else{
      this.questionPosition = QuestionPosition.ButtomMiddle;
      this.positon_desc = 'Bottom Middle';
    }
  }
  didChoseButtomMiddle():boolean {
    return this.questionPosition == QuestionPosition.ButtomMiddle;
  }

  /*
    Middle Right position
  */
  setMiddleRightPosition() {
    if(this.questionPosition == QuestionPosition.MiddleRight){
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    }else{
      this.questionPosition = QuestionPosition.MiddleRight;
      this.positon_desc = 'Middle Right';
    }
  }
  didChoseMiddleRight():boolean {
    return this.questionPosition == QuestionPosition.MiddleRight;
  }
  
  /*
    Middle Left position
  */
  setMiddleLeftPosition() {
    if(this.questionPosition == QuestionPosition.MiddleLeft){
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    }else{
      this.questionPosition = QuestionPosition.MiddleLeft;
      this.positon_desc = 'Middle Left';
    }
  }
  didChoseMiddleLeft():boolean {
    return this.questionPosition == QuestionPosition.MiddleLeft;
  }

  /*
    Middle Middle position
  */
  setMiddleMiddlePosition() {
    if(this.questionPosition == QuestionPosition.MiddleMiddle){
      this.questionPosition = null;
      this.positon_desc = 'Question Position';
    }else{
      this.questionPosition = QuestionPosition.MiddleMiddle;
      this.positon_desc = 'Middle Middle';
    }
  }
  didChoseMiddleMiddle():boolean {
    return this.questionPosition == QuestionPosition.MiddleMiddle;
  }

  /*
    Setters and Getters of the multiple choice Type: Matrix, Vertical or Hrizontal
  */

  /*
    Matrix type
  */
  setMatrixType(){
    this.submit = false;
    this.typedMultipleAnswer = false;
    //this.answers = new Array<string>();
    //this.markedAnswers = new Array<boolean>()
    if(this.typeMultipleQuestion == TypeMultipleQuestion.Matrix){
      this.typeMultipleQuestion = null;
      this.multiple_type = 'Answer Organization';
    }else{
      this.typeMultipleQuestion = TypeMultipleQuestion.Matrix;
      this.multiple_type = 'Matrix';
    }
  }
  didChoseMatrixType(){
    return this.typeMultipleQuestion == TypeMultipleQuestion.Matrix;
  }

  /*
    Vertical type
  */
  setVerticalType(){
    this.submit = false;
    this.typedMultipleAnswer = false;
    if(this.typeMultipleQuestion == TypeMultipleQuestion.Vertical){
      this.typeMultipleQuestion = null;
      this.multiple_type = 'Answer Organization';
    }else{
      this.typeMultipleQuestion = TypeMultipleQuestion.Vertical;
      this.multiple_type = 'Vertical';
    }
  }
  didChoseVerticalType(){
    return this.typeMultipleQuestion == TypeMultipleQuestion.Vertical;
  }

  /*
    Horizontal type
  */
  setHorizontalType(){
    this.submit = false;
    this.typedMultipleAnswer = false;
    if(this.typeMultipleQuestion == TypeMultipleQuestion.Horizontal){
      this.typeMultipleQuestion = null;
      this.multiple_type = 'Answer Organization';
    }else{
      this.typeMultipleQuestion = TypeMultipleQuestion.Horizontal;
      this.multiple_type = 'Horizontal';
    }
  }
  didChoseHorizontalType(){
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
  onSubmit(){
    if(this.questionText != null){
     if(this.questionText != '' && !this.isSpacePrefix(this.questionText)){
      if(this.didChoseQuestionType()){

        if(this.didChoseMultipleQuestion()){
          if(this.didChoseMultipleChoiceType() && this.didChoseQuestionPosition() ){
            if(this.didChoseVerticalType() || this.didChoseHorizontalType()){
              if(this.haveAnswers()){
                this.findCorretAnswer();
                this.constructMultipleQuestion();
                this.transferData.setData(this.question_object);
                this.closeDialog();
              }
            }else if(this.didChoseMatrixType()){
              if(!this.missingAnswers()){
                this.findCorretAnswer();
                this.constructMatrixQuestion();
                this.transferData.setData(this.question_object);
                this.closeDialog();
              }
              
            }
          }
        }else if(this.didChoseRateQuestion() && this.didChoseQuestionPosition()){
            this.constructRateQuestion();
            this.transferData.setData(this.question_object);
            this.closeDialog();
        }else if(this.didChoseOpenQuestion() && this.didChoseQuestionPosition()){
              this.constructOpenQuestion();
              this.transferData.setData(this.question_object);
              this.closeDialog();
        }else if(this.didChoseDrillDownQuestion()){
          if(this.haveMainAnswers()){
            this.constructDrillDownQuestion();
            this.transferData.setData(this.question_object);
            this.closeDialog();
          }
        }
      }

  
     } 
    }
    
    this.submit = true;
  }


  constructDrillDownQuestion(){
    let correct_main_answer = -1;
    for(let i = 0; i < this.markedMainCorrectAnswer.length; i++){
      if(this.markedMainCorrectAnswer[i]){
        correct_main_answer = i;
      }
    }
    let secondary_question_text: Array<string> = new Array(this.mainAnswers.length);
    let secondary_question_answers: Array<Array<string>> = new Array(this.mainAnswers.length);
    let secondary_correct_answers: Array<number> = new Array(this.mainAnswers.length);
    for(let i = 0; i < this.mainAnswers.length; i++){
      let found_secondary: boolean = false;
      for(let j = 0; j < this.secondaryQuestionList.length; j++){
        if(i == this.secondaryQuestionList[j].index){
          secondary_question_text[i] = this.secondaryQuestionList[j].questionText;
          secondary_question_answers[i] = Object.assign([], this.secondaryQuestionList[j].answers);
          secondary_correct_answers[i] = this.secondaryQuestionList[j].markedAnswer;
          found_secondary = true;
        }
      }
      if(!found_secondary){
        secondary_question_text[i] = null;
        secondary_question_answers[i] = null;
        secondary_correct_answers[i] = -1;
      }
    }
    let answers_main = Object.assign([], this.mainAnswers); 
    this.question_object = {
      questionText: this.questionText,
      type: this.typeQuestion,
      questionPosition: QuestionPosition.UpperMiddle,
      answersForMain:  answers_main,    
      correctMainQuestion: correct_main_answer,
      secondaryQuestionsText: secondary_question_text,
      answersForSecondary: secondary_question_answers,
      correctAnswerSecondary: secondary_correct_answers
    };
 
  }
  /*
    Construction the diffeerent types of questions after the form completed properly
  */
  /*
    Constructing the open question object
  */
  constructOpenQuestion(){
    if(this.openQuestion.answerText == null){
      this.openQuestion.answerText = '';
    }
    this.question_object = {
      questionText: this.questionText,
      type: this.typeQuestion,
      questionPosition: this.questionPosition,
      answerText: this.openQuestion.answerText
    }
  }

  /*
    Constructing the rate question object
  */
  constructRateQuestion(){
    this.question_object = {
      questionText: this.questionText,
      type: this.typeQuestion,
      questionPosition: this.questionPosition,
      heightOfRate: this.rateQuestion.rateSize
    }
  }

  /*
    Constructing the matrix type of multiple choice question object
  */
  constructMatrixQuestion(){
    for(let i = 0; i < this.matrixQuestion.dimSize; i++){
      for(let j = 0; j < this.matrixQuestion.dimSize; j++){
        this.matrixQuestion.answers.splice(this.matrixQuestion.answers.length, 0 ,this.matrixQuestion.matrixAnswers[j][i]);
      }
    }
    if(this.matrixQuestion.correctAnswer == null){
      this.matrixQuestion.correctAnswer = -1;
    }
    this.question_object = {
      questionText: this.questionText,
      type: this.typeQuestion,
      questionPosition: this.questionPosition,
      answers: this.matrixQuestion.answers,
      correctAnswer: this.matrixQuestion.correctAnswer,
      typeMultipleQuestion: this.typeMultipleQuestion
    }
  }
  /*
    Constructing the multiple choice question object
  */
  constructMultipleQuestion(){
    if(this.verticalHrizontalQuestion.correctAnswer == null){
      this.verticalHrizontalQuestion.correctAnswer = -1;
    }
    this.question_object = {
      questionText: this.questionText,
      type: this.typeQuestion,
      questionPosition: this.questionPosition,
      answers: this.verticalHrizontalQuestion.answers,
      correctAnswer: this.verticalHrizontalQuestion.correctAnswer,
      typeMultipleQuestion: this.typeMultipleQuestion
    }
    if(this.verticalHrizontalQuestion.answers.indexOf("I don't know") == -1 && this.typeMultipleQuestion != TypeMultipleQuestion.Matrix)
    {
      this.verticalHrizontalQuestion.answers.splice(this.verticalHrizontalQuestion.answers.length, 0, "I don't know");
    }
  }

  /*
    Control flow functions that checks if the user entered all the related information for creating a question
  */
  /*
    Returns TRUE if the user have chosen type for his question, FALSE otherwise.
  */
  didChoseQuestionType():boolean {
    return this.didChoseOpenQuestion() || this.didChoseRateQuestion() || this.didChoseMultipleQuestion() || this.didChoseDrillDownQuestion(); 
  }
  /*
    Returns TRUE if the user have chosen position for his question, FALSE otherwise.
  */
  didChoseQuestionPosition():boolean{
    let x = this.didChoseUpperLeft() || this.didChoseUpperMiddle() || this.didChoseUpperRight()||
    this.didChoseMiddleLeft() || this.didChoseMiddleMiddle() || this.didChoseMiddleRight() ||
    this.didChoseButtomLeft() || this.didChoseButtomMiddle() || this.didChoseButtomLeft() || this.didChoseButtomRight();
    return x;
  }

  /*
    Returns TRUE if the user entered a type for the multiple choice question,
    in case he chose a multiple choice question type, FLASE otherwise
  */
  didChoseMultipleChoiceType():boolean {
    return this.didChoseMatrixType() || this.didChoseVerticalType() || this.didChoseHorizontalType();
  }

  /*
    Functions related for creating a Horizontal or Vertical multiple choice question
  */
  /*
    Adding an answer for the question
  *//*
  addAnswer(){
    if(this.answerTextForMultiple != null && this.answerTextForMultiple.length >= 1 && !this.isSpacePrefix(this.answerTextForMultiple)
    && this.doesHaveMultiAnswer(this.answerTextForMultiple) != true){
      this.typedMultipleAnswer = false;
      this.answers.splice(this.answers.length, 0, this.answerTextForMultiple);
      this.markedAnswers.splice(this.markedAnswers.length, 0, false);
      this.answerTextForMultiple = '';
  
    }else{
      this.typedMultipleAnswer = true;
    }
  }*/

  /*
    Control flow function that checks if the user inserted at least one answer
  */
  haveAnswers(): boolean{
    if(this.verticalHrizontalQuestion != null){
      return this.verticalHrizontalQuestion.answers.length > 0;
    }
    return false;
    
  }



  constructMatrixInEdit(question_to_edit: any){
    this.matrixQuestion.markedAnswersMatrix = new Array<Array<boolean>>(this.matrixQuestion.dimSize);
    this.matrixQuestion.matrixAnswers = new Array<Array<string>>(this.matrixQuestion.dimSize);
    this.matrixQuestion.iteratorArray = new Array<Array<null>>(this.matrixQuestion.dimSize);
    for(let i = 0; i < this.matrixQuestion.dimSize; i++){
      this.matrixQuestion.markedAnswersMatrix[i] = new Array<boolean>(this.matrixQuestion.dimSize);
      this.matrixQuestion.matrixAnswers[i] = new Array<string>(this.matrixQuestion.dimSize);
      this.matrixQuestion.iteratorArray[i] = new Array<null>(this.matrixQuestion.dimSize); 
      for(let j = 0; j < this.matrixQuestion.dimSize; j++){
        if(question_to_edit.correctAnswer == this.matrixQuestion.dimSize*j + i){
          this.matrixQuestion.markedAnswersMatrix[i][j] = true;
        }else{
          this.matrixQuestion.markedAnswersMatrix[i][j] = false;
        }
        this.matrixQuestion.matrixAnswers[i][j] = question_to_edit.answers[j*this.matrixQuestion.dimSize + i];
      }
    }
    this.matrixQuestion.centeringMatrix = {
      'two_col' : this.matrixQuestion.dimSize == 2,
      'three_col' : this.matrixQuestion.dimSize == 3,
      'four_col' : this.matrixQuestion.dimSize == 4
    };
    
  }
    /*
    Control flow function which returns TRUE if there are missing answers in the matrix and FALSE other wise.
  */
  missingAnswers():boolean{
    if(this.matrixQuestion != null){
      for(let i = 0; i < this.matrixQuestion.dimSize; i++){
        for(let j = 0; j < this.matrixQuestion.dimSize; j++){
          if(this.matrixQuestion.matrixAnswers[i][j] == null){
            return true;
          }
          if(this.matrixQuestion.matrixAnswers[i][j].length < 2){
            return true;
          }
          if(this.isSpacePrefix(this.matrixQuestion.matrixAnswers[i][j])){
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
  findCorretAnswer(){
    if(!this.didChoseMatrixType()){
      for(let i = 0; i < this.verticalHrizontalQuestion.markedAnswers.length; i++){
        if(this.verticalHrizontalQuestion.markedAnswers[i] == true){
          this.verticalHrizontalQuestion.correctAnswer = i;
          break;
        }
      }
    }else{
      for(let i = 0; i < this.matrixQuestion.dimSize; i++){
        for(let j = 0; j < this.matrixQuestion.dimSize; j++){
          if(this.matrixQuestion.markedAnswersMatrix[i][j] == true){
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
  cancelDialog(){
    this.transferData.clearData();
    this.closeDialog();
  }
  /*
    This function is for editing an existing question. The function gets
    question and the form is being filled with the details of the question
  */
  editQuestion(question: any){
    this.questionText = question.questionText;
    this.typeQuestion = question.type;
    this.initializeTypeString();
    this.questionPosition = question.questionPosition;
    this.initializePositionString();
    if(this.typeQuestion == TypeQuestion.OpenQuestion){
      //this.answerText = question.answerText;
    }else if(this.typeQuestion == TypeQuestion.RateQuestion){
      //this.rateQuestion.rateSize = question.heightOfRate;
    }else if(this.typeQuestion == TypeQuestion.MultipleChoice){
      this.typeMultipleQuestion = question.typeMultipleQuestion;
      this.initializeMultipleString();
      if(this.typeMultipleQuestion == TypeMultipleQuestion.Horizontal || this.typeMultipleQuestion == TypeMultipleQuestion.Vertical){
        for(let i = 0; i < question.answers.length; i++){
          //this.answers.splice(this.answers.length, 0, question.answers[i]);
          if(i == question.correctAnswer){
            //this.markedAnswers.splice(this.markedAnswers.length, 0, true);
          }else{
            //this.markedAnswers.splice(this.markedAnswers.length, 0, false);
          }
          
        }
      }else if(this.typeMultipleQuestion == TypeMultipleQuestion.Matrix){
        //this.matrixQuestion.dimSize = Math.sqrt(question.answers.length);
        //this.constructMatrixInEdit(question);
      }
    }else if(this.typeQuestion == TypeQuestion.DrillDownQuestion){
      this.mainAnswers = Object.assign([], question.answersForMain);
      this.markedMainCorrectAnswer = new Array(this.mainAnswers.length);
      for(let i = 0 ; i < this.markedMainCorrectAnswer.length; i++){
        if(i == question.correctMainQuestion){
          this.markedMainCorrectAnswer[i] = true;
        }else{
          this.markCorretSecondaryAnswer[i] = false;
        }
      }
      let real_size = 0;
      let size_to_iterate = question.answersForMain.length;
      for(let i = 0; i < size_to_iterate; i++ ){
        if(question.secondaryQuestionsText[i] != null){
          let obj_to_insert = {
            index: i,
            questionText: question.secondaryQuestionsText[i],
            markedAnswer: question.correctAnswerSecondary[i],
            answers: Object.assign([], question.answersForSecondary[i])
          }
          this.secondaryQuestionList.splice(this.secondaryQuestionList.length, 0, obj_to_insert);
        }
      }
    }

  }
  /*
    Intializing the description of the type
  */
  initializeTypeString(){
      if(this.typeQuestion == TypeQuestion.DrillDownQuestion){
          this.type_question_desc = 'Drill Down Question';
      }else if(this.typeQuestion == TypeQuestion.MultipleChoice){
          this.type_question_desc = 'Multiple Choice Question'
      }else if(this.typeQuestion == TypeQuestion.OpenQuestion){
          this.type_question_desc = 'Open Text Question';
      }else if(this.typeQuestion == TypeQuestion.RateQuestion){
          this.type_question_desc = 'Rate Question';
      }
  }

  /*
    Intializing the description of the position
  */
  initializePositionString(){
      if(this.questionPosition == QuestionPosition.UpperRight){
          this.positon_desc = 'Upper Right';
      }else if(this.questionPosition == QuestionPosition.UpperLeft){
          this.positon_desc = 'Upper Left';
      }else if(this.questionPosition == QuestionPosition.UpperMiddle){
          this.positon_desc = 'Upper Middle';
      }else if(this.questionPosition == QuestionPosition.ButtomRight){
          this.positon_desc = 'Bottom Right';
      }else if(this.questionPosition == QuestionPosition.ButtomLeft){
          this.positon_desc = 'Bottom Left';
      }else if(this.questionPosition == QuestionPosition.ButtomMiddle){
          this.positon_desc = 'Bottom Middle';
      }else if(this.questionPosition == QuestionPosition.MiddleLeft){
          this.positon_desc = 'Middle Left';
      }else if(this.questionPosition == QuestionPosition.MiddleRight){
          this.positon_desc = 'Middle Right';
      }else if(this.questionPosition == QuestionPosition.MiddleMiddle){
          this.positon_desc = 'Middle Middle';
      }
  }
  /*
    Intialize the type of the multiple choice question
  */
  initializeMultipleString(){
      if(this.typeMultipleQuestion == TypeMultipleQuestion.Matrix){
          this.multiple_type = 'Matrix';
      }else if(this.typeMultipleQuestion == TypeMultipleQuestion.Vertical){
          this.multiple_type = 'Vertical';
      }else if(this.typeMultipleQuestion == TypeMultipleQuestion.Horizontal){
          this.multiple_type = 'Horizontal';
      }
  }
  /*

    The function adds an answer for the main question
  */
  addMainAnswer(){
    if(this.currentMainAnswer != null && this.currentMainAnswer.length >= 1 && !this.isSpacePrefix(this.currentMainAnswer)){
      this.mainAnswers.splice(this.mainAnswers.length, 0, this.currentMainAnswer);
      this.markedMainCorrectAnswer.splice(this.markedMainCorrectAnswer.length, 0, false);
      this.currentMainAnswer = '';
      this.submitSecondaryQuestion = false;
      this.typedMainAnswer = false;
    }else{
      this.typedMainAnswer = true;
    }
  }
  /*
    The function returns TRUE if there are answers to the main question,
    FALSE otherwise
  */
  haveMainAnswers(): boolean {
    return this.mainAnswers.length >= 1;
  }

  /*
    The function displays the main answer that was chosen
  */
  markMainAnswer(index: number){
    this.secondaryAnswerMode = false;
    if(this.indexOfMainanswerToShow == index){
      this.showMainAnswer = false;

      this.indexOfMainanswerToShow = -1;
    }else{
      this.showMainAnswer = true;
      this.indexOfMainanswerToShow = index;
    }
    this.secondaryAnswerMode = false;
    this.editionModeSecondary = false;
    this.editionQuestionSecondary = false;
    this.viewSecondary = false;
    this.currentSecondaryQuestion = '';
    this.currentSecondaryAnswer = '';
    this.markedSecondaryCorrectAnswer = new Array();
    this.secondaryAnswers = new Array();

    
  }
  /*
    The function saves the answer that was marked as correct
  */
  markCorretMainAnswer(index:number){
    for(let i = 0; i < this.markedMainCorrectAnswer.length; i++){
      if(i == index){
        this.markedMainCorrectAnswer[i] = !this.markedMainCorrectAnswer[i];
      }else{
        this.markedMainCorrectAnswer[i] = false;
      }
    } 
  }
  /*
    Deletes an answer to the question
  */
  deleteMainAnswer(index: number){
    this.viewSecondary = false;
    this.secondaryAnswerMode = false;
    this.editionQuestionSecondary = false;
    this.editionModeSecondary = false;
    if(this.showMainAnswer){
      this.showMainAnswer = false;
      this.secondaryAnswerMode = false;
      this.indexOfMainanswerToShow = -1;
    }
    for(let i = 0; i < this.secondaryQuestionList.length; i++){
      if(index == this.secondaryQuestionList[i].index){
        this.secondaryQuestionList.splice(i,1);
      }
    }
    this.mainAnswers.splice(index,1);
    this.markedMainCorrectAnswer.splice(index, 1);
    this.secondaryAnswers = new Array();
    this.currentSecondaryAnswer = '';
    this.markedSecondaryCorrectAnswer = new Array();
    this.currentSecondaryQuestion = '';
  }
  /*
    The function edits an answer for the main question
  */
  editMainAnswer(index: number){
    this.viewSecondary = false;
    this.secondaryAnswerMode = false;
    this.editionModeSecondary = false;
    this.editionQuestionSecondary = false;
    this.currentSecondaryAnswer = '';
    this.currentSecondaryQuestion = '';
    this.markedSecondaryCorrectAnswer = new Array();
    this.secondaryAnswers = new Array();
    this.currentMainAnswer = this.mainAnswers[index];
    this.editionModeMain = true;
    this.indexAnswerInEditMain = index;
  }
  /*
    The function doesn't change the edited answer for the main question
  */
  undoEditMain(){
    this.editionModeMain = false;
    this.currentMainAnswer = '';
    this.indexAnswerInEditMain = -1;
    this.typedMainAnswer = false;
  }
  /*
    applies changes of an answer of the main question
  */
  applyEditMain(){
    if(this.currentMainAnswer != null && this.currentMainAnswer.length >= 1 && !this.isSpacePrefix(this.currentMainAnswer)){
      this.mainAnswers.splice(this.indexAnswerInEditMain, 1,  this.currentMainAnswer)
      this.editionModeMain = false;
      this.indexAnswerInEditMain = -1;
      this.currentMainAnswer = '';
      this.typedMainAnswer = false;
    }else{
      this.typedMainAnswer = true;
    }
  }
  /*
    The function moves up the requested answer to the main question
  */
  goMainUp(index: number){
    
    if(index != 0){
      for(let i = 0; i < this.secondaryQuestionList.length; i++){
        if(index - 1 == this.secondaryQuestionList[i].index){

          let tmp = {
            index: index,
            questionText: this.secondaryQuestionList[i].questionText,
            answers: Object.assign([], this.secondaryQuestionList[i].answers),
            markedAnswer: this.secondaryQuestionList[i].markedAnswer
          }
          this.secondaryQuestionList.splice(i,1);
          this.secondaryQuestionList.splice(0,0,tmp);
        }else if(index == this.secondaryQuestionList[i].index){
          let tmp = {
            index: index - 1,
            questionText: this.secondaryQuestionList[i].questionText,
            answers: Object.assign([], this.secondaryQuestionList[i].answers),
            markedAnswer: this.secondaryQuestionList[i].markedAnswer
          }
          let removed = this.secondaryQuestionList.splice(i,1);
          this.secondaryQuestionList.splice(0,0,tmp);
        }
      }
      if(this.showMainAnswer){
        if(this.indexOfMainanswerToShow <= index){
          this.indexOfMainanswerToShow = index - 1;
        }
      }
      let removed = this.mainAnswers.splice(index, 1)
      let removed_marked_correct = this.markedMainCorrectAnswer.splice(index,1);
      this.mainAnswers.splice(index - 1, 0, removed[0]);
      this.markedMainCorrectAnswer.splice(index - 1, 0, removed_marked_correct[0]);

    } 
  }
   /*
    The function moves down the requested answer to the main question
  */
  goMainDown(index: number){
    if(index != this.mainAnswers.length - 1){
      for(let i = 0; i < this.secondaryQuestionList.length; i++){
        if(index == this.secondaryQuestionList[i].index){
          let tmp = {
            index: index + 1,
            questionText: this.secondaryQuestionList[i].questionText,
            answers: Object.assign([], this.secondaryQuestionList[i].answers),
            markedAnswer: this.secondaryQuestionList[i].markedAnswer
          }
          this.secondaryQuestionList.splice(i,1);
          this.secondaryQuestionList.splice(0,0,tmp);
        }else if(index + 1 == this.secondaryQuestionList[i].index){
          let tmp = {
            index: index,
            questionText: this.secondaryQuestionList[i].questionText,
            answers: Object.assign([], this.secondaryQuestionList[i].answers),
            markedAnswer: this.secondaryQuestionList[i].markedAnswer
          }
          this.secondaryQuestionList.splice(i,1);
          this.secondaryQuestionList.splice(0,0,tmp);
        }
      }
      if(this.showMainAnswer){
        if(this.indexOfMainanswerToShow <= index){
          this.indexOfMainanswerToShow = index + 1;
        }
      }
      let removed = this.mainAnswers.splice(index, 1);
      let removed_marked_correct = this.markedMainCorrectAnswer.splice(index, 1);
      this.mainAnswers.splice(index + 1, 0, removed[0]);
      this.markedMainCorrectAnswer.splice(index + 1, 0, removed_marked_correct[0]);
      
    }
  }
  /*
    This function gets an array of strings to detrmine a dynamic style for the buttons in which all the string will be
    so the buttons will be aligned according to the longest string
  */
  generateHighestBox(answers: Array<string>): string{
    let max: number = -1;
    for(let i = 0; i < answers.length; i++){
      if(answers[i].length > max){
        max = answers[i].length;
      }
    }
    let size = Math.max((max * 20), 300);
    
    let returnedSize: string = (size.toString()) + 'px';
    return returnedSize;
  }
  /*
    The function adds secondary question
  */
  addSecondaryQuestion(index: number){
    if(this.secondaryExists()){
      this.secondaryAnswerMode = !this.secondaryAnswerMode;
    }else{
      this.secondaryAnswerMode = true;
    }
    
  }

  /*
    The function returns if the chosen question is what the user wants to work on
  */
  whatToShow(index: number):boolean{
    return index == this.indexOfMainanswerToShow;
  }

  /*
    A function that cancels the secondary question
  */
  undoSecondary(){
    this.typedSecondaryAnswer = false;
    this.editionQuestionSecondary = false;
    this.submitSecondaryQuestion = false;
    this.secondaryAnswers = new Array();
    this.currentSecondaryQuestion = '';
    this.secondaryAnswerMode = false;
    for(let i = 0; i < this.secondaryQuestionList.length; i++){
      if(this.indexOfMainanswerToShow == this.secondaryQuestionList[i].index){
        this.secondaryQuestionList.splice(i,1);
      }
    }
  }

  /*
    The function creates secondary question from the details the user has input
  */
  finishSecondaryQuestion(){
    this.editionQuestionSecondary = false;
    if(!this.emptySecondary() && this.hasSecondaryAnswer()){
      for(let i = 0 ; i < this.secondaryQuestionList.length; i++){
        if(this.secondaryQuestionList[i].index == this.indexOfMainanswerToShow){
          this.secondaryQuestionList.splice(i, 1);
        }
      }
      let correct_answer = -1;
      for(let i = 0; i < this.markedSecondaryCorrectAnswer.length; i++){
        if(this.markedSecondaryCorrectAnswer[i]){
          correct_answer = i;
        }
        
      }

      this.secondaryQuestionList.splice(this.secondaryQuestionList.length, 0, {
        index: this.indexOfMainanswerToShow,
        questionText: this.currentSecondaryQuestion,
        answers: this.secondaryAnswers,
        markedAnswer: correct_answer
      });
      this.secondaryAnswerMode = false;
      this.secondaryAnswers = new Array();
      this.currentSecondaryAnswer = '';

      this.currentSecondaryQuestion = '';
      this.markedSecondaryCorrectAnswer = new Array();
    }
    
    this.submitSecondaryQuestion = true;
    this.typedSecondaryAnswer = false;
  }
  /*
    The function returns TRUE if the secondary question have answers
  */
  hasSecondaryAnswer(): boolean{
    return this.secondaryAnswers.length >= 1;
  }

  /*
    The function returns TRUE if the secondary question exists and needs to be viewed
  */
  secondaryExists(): boolean{
    for(let i = 0; i < this.secondaryQuestionList.length; i++){
      if(this.secondaryQuestionList[i].index == this.indexOfMainanswerToShow){
        return true;
      }
    }
    return false;
  }
  /*
    The function returns TRUE if secondary question text wasn't filled
  */
  emptySecondary():boolean{
    return this.currentSecondaryQuestion == null || this.currentSecondaryQuestion.length < 2 || this.isSpacePrefix(this.currentSecondaryQuestion);
  }

  /*
    The function adds an answer to the secondary question
  */
  addSecondaryAnswer(){
    if(this.currentSecondaryAnswer != null && this.currentSecondaryAnswer.length >= 1 && !this.isSpacePrefix(this.currentSecondaryAnswer)){
      this.secondaryAnswers.splice(this.secondaryAnswers.length, 0, this.currentSecondaryAnswer);
      this.markedSecondaryCorrectAnswer.splice(this.markedSecondaryCorrectAnswer.length, 0, false);
      this.currentSecondaryAnswer = '';
      this.typedSecondaryAnswer = false;
    }else{
      this.typedSecondaryAnswer = true;
    }
  }

  /*
    The function returns TRUE if the secondary question has answers
  */
  hasSecondaryAnswers(): boolean{
    return this.secondaryAnswers.length >= 1;
  }
  
  /*
    The function marks the correct answer by the user
  */
  markCorretSecondaryAnswer(index: number){
    for(let i = 0; i < this.markedSecondaryCorrectAnswer.length; i++){
      if(i == index){
        this.markedSecondaryCorrectAnswer[i] = !this.markedSecondaryCorrectAnswer[i];
      }else{
        this.markedSecondaryCorrectAnswer[i] = false;
      }
    } 
  }
  /*
    The function moves the requested secondary question up
  */
  goSecondaryUp(index: number){
    if(index != 0){
      let removed = this.secondaryAnswers.splice(index, 1)
      let removed_marked_correct = this.markedSecondaryCorrectAnswer.splice(index,1);
      this.secondaryAnswers.splice(index - 1, 0, removed[0]);
      this.markedSecondaryCorrectAnswer.splice(index - 1, 0, removed_marked_correct[0]);
    }
  }
    /*
    The function moves the requested secondary question down
  */
  goSecondaryDown(index: number){
    if(index != this.secondaryAnswers.length - 1){
      let removed = this.secondaryAnswers.splice(index, 1);
      let removed_marked_correct = this.markedSecondaryCorrectAnswer.splice(index, 1);
      this.secondaryAnswers.splice(index + 1, 0, removed[0]);
      this.markedSecondaryCorrectAnswer.splice(index + 1, 0, removed_marked_correct[0]);
      
    }
  }
  /*
    The function deleted an answer to the secondary question
  */
  deleteSecondaryAnswer(index: number){
    this.secondaryAnswers.splice(index,1);
    this.markedSecondaryCorrectAnswer.splice(index, 1);
  }
  /*
    The function gets in edit mode to an answer of the secondary question
  */
  editSecondaryAnswer(index: number){
    this.currentSecondaryAnswer = this.secondaryAnswers[index];
    this.editionModeSecondary = true;
    this.indexAnswerInEditSecondary = index;
  }
  /*
    The function applies th changes of the edit mode in an answer of the seocndary question
  */
  applySecondaryEdit(){
    if(this.currentSecondaryAnswer != null && this.currentSecondaryAnswer.length >= 1 && !this.isSpacePrefix(this.currentSecondaryAnswer)){
      this.secondaryAnswers.splice(this.indexAnswerInEditSecondary, 1,  this.currentSecondaryAnswer)
      this.editionModeSecondary = false;
      this.indexAnswerInEditSecondary = -1;
      this.currentMainAnswer = '';
      this.currentSecondaryAnswer = '';
      this.typedSecondaryAnswer = false;
    }else{
      this.typedSecondaryAnswer = true;
    }
  }
  /*
    undos changes an answer of the secondary question
  */
  undoSecondaryEdit(){
    this.typedSecondaryAnswer = false;
    this.editionModeSecondary = false;
    this.currentSecondaryAnswer = '';
    this.indexAnswerInEditSecondary = -1;
  }
  /*
    The function gets into the view of a secondary question
  */
  viewSecondaryAnswer(){
    if(this.viewSecondary){
      this.viewAnswers = new Array();
      this.viewSecondary = false;
    }else{
      this.viewSecondary = true;
      for(let i = 0; i < this.secondaryQuestionList.length; i++){
        if(this.secondaryQuestionList[i].index == this.indexOfMainanswerToShow){
          this.questionView = this.secondaryQuestionList[i].questionText;
          this.viewAnswers = Object.assign([], this.secondaryQuestionList[i].answers);
          break;
        }
      }
    }
  }
  /*
    The function edits the whole secondary question
  */
  editSecondaryQuestion(){
    this.viewSecondary = false;
    this.editionQuestionSecondary = true;
    let correct_answer_index = -1;
    for(let i = 0; i < this.secondaryQuestionList.length; i++){
      if(this.secondaryQuestionList[i].index == this.indexOfMainanswerToShow){
        this.currentSecondaryQuestion = this.secondaryQuestionList[i].questionText;
        this.secondaryAnswers = Object.assign([], this.secondaryQuestionList[i].answers);
        correct_answer_index = this.secondaryQuestionList[i].markedAnswer;
        break;
      }
    }
    this.markedSecondaryCorrectAnswer = new Array(this.secondaryAnswers.length);
    for(let i = 0; i < this.markedSecondaryCorrectAnswer.length; i++){
      if(i == correct_answer_index){
        this.markedSecondaryCorrectAnswer[i] = true;
      }else{
        this.markCorretSecondaryAnswer[i] = false;
      }
    }
  }
  /*
    The function returns from view mode of a secondary question
  */
  backToView(){
    this.typedSecondaryAnswer = false;
    this.editionQuestionSecondary = false;
    this.viewSecondaryAnswer();
  }

  /*
    The function retunrs TRUE if the main question have answers
  */
  hasMainAnswres():boolean{
    return this.mainAnswers.length >= 1;
  }
  /*
    A function that returns true if the string starts with a white space, to control flawed input
  */
  isSpacePrefix(str: string): boolean{
      if(str == null){
          return false;
      }
      return str.charAt(0) == ' ';
  }
  
  /* ----------------------- New Create Question Code -------------------------- */
  QuestionWithPosition() : boolean{
    return this.didChoseRateQuestion() || this.didChoseOpenQuestion() || this.didChoseMultipleQuestion();
  }

  passParams() : any{
    console.log('fdfdfdfd');
  }  
  /*
  this function checks if an answer already exists in the multi choice question's answers 
  and returns True if it is, False otherwise.
  
  doesHaveMultiAnswer(givenStr: string): boolean{
    if(this.answers.indexOf(givenStr)!= -1){
      return true;
    }
    return false;
  }*/
}