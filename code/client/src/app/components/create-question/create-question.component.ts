import { Component, OnInit } from '@angular/core';
import { TypeMultipleQuestion, TypeQuestion, QuestionPosition} from '../../models';
import {MatDialogRef} from '@angular/material';
import {SessionService} from '../../services/session-service';
import { SecondaryQuestionObject } from '../../models';
@Component({
  selector: 'app-create-question',
  templateUrl: './create-question.component.html',
  styleUrls: ['./create-question.component.css']
})
export class CreateQuestionComponent implements OnInit {
  /*
    ------------ general question details -------------------
  */
  questionText: string;
  typeQuestion: TypeQuestion;
  questionPosition: QuestionPosition;

  /*
    ------------ multiple choice question details -----------
  */
  typeMultipleQuestion?: TypeMultipleQuestion;
  answers?: Array<string> = new Array();
  answerTextForMultiple?: string;
  correctAnswer?: number;
  markedAnswers?: Array<boolean> = new Array();
  //--------- data regarding the matrix type of multiple choice question
  dimSize?: number = 2;
  markedAnswersMatrix?: Array<Array<boolean>>;
  matrixAnswers?: Array<Array<string>>;
  centeringMatrix?: any;
  iteratorArray?: Array<Array<null>>;

  /*
    ------------ rate question details ----------------------
  */
  rateSize?: number = 1;

  /*
    ------------ open question details ----------------------
  */
  answerText?: string;

  /*
    --------------- drill down question details ---------------
  */
  
  currentMainAnswer?: string;
  showMainAnswer: boolean;
  indexOfMainanswerToShow: number = -1;
  mainAnswers?: Array<string> = new Array();
  markedMainCorrectAnswer?: Array<boolean> = new Array();
  secondaryQuestionList? : Array<SecondaryQuestionObject> = new Array();
  secondaryAnswers?: Array<string> = new Array();
  submitSecondaryQuestion?: boolean = false;
  currentSecondaryQuestion?: string = '';
  currentSecondaryAnswer?: string = '';
  secondaryQuestions?: Array<string> = new Array();
  editionQuestionSecondary?: boolean = false;
  markedSecondaryCorrectAnswer?: Array<boolean> = new Array();
  editionModeSecondary: boolean = false;
  indexAnswerInEditSecondary: number = -1;
  viewSecondary: boolean = false;
  viewAnswers: Array<string> = new Array();
  questionView: string = '';
  //secondaryAnswers?: Array<Array<string>>;
  editionModeMain?: boolean;
  secondaryAnswerMode?: boolean = false;
  indexAnswerInEditMain: number = -1;
  submit: boolean = false;
  editionMode: boolean = false;
  indexAnswerInEdit: number = -1;

  //The question that created and sent to the block component. Will be null in case the user exited the creation of the question before its completion 
  question_object: any;

  /*
    Params:
    dialogRef - reference for the dialog between the block component to this component.
                This is for making the creation of the
                question as a modal and not in a different web page.
    transferData - Injected service for transfering the object that describes the
                   question that the user has created to the block component. 
  */
  constructor(public dialogRef: MatDialogRef<CreateQuestionComponent>, private transferData: SessionService) {
    console.log('hi there');
    console.log(this.transferData.getData());
    if(this.transferData.getData().editMode == true){//Which mean we are in edition mode of the question
      this.editQuestion(this.transferData.getData().value)
    }
    this.transferData.clearData();
   }


  

  ngOnInit() {

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
    if(this.typeQuestion == TypeQuestion.MultipleChoice){
      this.typeQuestion = null;
    }else{
      this.typeQuestion = TypeQuestion.MultipleChoice;
    }
  }

  /*
    Setting the type of the created question, to rate question
  */
  setRateQuestion(){
    if(this.typeQuestion == TypeQuestion.RateQuestion){
      this.typeQuestion = null;
    }else{
      this.typeQuestion = TypeQuestion.RateQuestion;
    }
  }

  /*
    Setting the type of the created question, to open question
  */
  setOpenQuestion(){
    if(this.typeQuestion == TypeQuestion.OpenQuestion){
      this.typeQuestion = null;
    }else{
      this.typeQuestion = TypeQuestion.OpenQuestion;
    }
  }

  /*
    Setting the type of the created question, to drill down question
  */
  setDrillDownQuestion(){
    if(this.typeQuestion == TypeQuestion.DrillDownQuestion){
      this.typeQuestion = null;
    }else{
      this.typeQuestion = TypeQuestion.DrillDownQuestion;
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
    }else{
      this.questionPosition = QuestionPosition.UpperMiddle;
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
    }else{
      this.questionPosition = QuestionPosition.UpperRight;
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
    }else{
      this.questionPosition = QuestionPosition.UpperLeft;
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
    }else{
      this.questionPosition = QuestionPosition.ButtomRight;
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
    }else{
      this.questionPosition = QuestionPosition.ButtomLeft;
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
    }else{
      this.questionPosition = QuestionPosition.ButtomMiddle;
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
    }else{
      this.questionPosition = QuestionPosition.MiddleRight;
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
    }else{
      this.questionPosition = QuestionPosition.MiddleLeft;
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
    }else{
      this.questionPosition = QuestionPosition.MiddleMiddle;
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
    this.answers = new Array<string>();
    this.markedAnswers = new Array<boolean>();
    this.constructMatrix();
    if(this.typeMultipleQuestion == TypeMultipleQuestion.Matrix){
      this.typeMultipleQuestion = null;
    }else{
      this.typeMultipleQuestion = TypeMultipleQuestion.Matrix;
    }
  }
  didChoseMatrixType(){
    return this.typeMultipleQuestion == TypeMultipleQuestion.Matrix;
  }

  /*
    Vertical type
  */
  setVerticalType(){
    if(this.typeMultipleQuestion == TypeMultipleQuestion.Vertical){
      this.typeMultipleQuestion = null;
    }else{
      this.typeMultipleQuestion = TypeMultipleQuestion.Vertical;
    }
  }
  didChoseVerticalType(){
    return this.typeMultipleQuestion == TypeMultipleQuestion.Vertical;
  }

  /*
    Horizontal type
  */
  setHorizontalType(){
    if(this.typeMultipleQuestion == TypeMultipleQuestion.Horizontal){
      this.typeMultipleQuestion = null;
    }else{
      this.typeMultipleQuestion = TypeMultipleQuestion.Horizontal;
    }
  }
  didChoseHorizontalType(){
    return this.typeMultipleQuestion == TypeMultipleQuestion.Horizontal;
  }

  /*
    Determining the size of the range of the rate question that is created
  */
  increaseRate(){
    this.rateSize++;
  }
  decreaseRate(){
    if(this.rateSize > 1){
      this.rateSize--;
    }
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
    console.log('asfsdifdfosd;fdsf');
    if(this.questionText != null){
     if(this.questionText != ''){
      if(this.didChoseQuestionPosition() && this.didChoseQuestionType()){
        console.log('12121');
        if(this.didChoseMultipleQuestion()){
          if(this.didChoseMultipleChoiceType()){
            if(this.didChoseVerticalType() || this.didChoseHorizontalType()){
              if(this.haveAnswers()){
                this.findCorretAnswer();
                this.constructMultipleQuestion();
                console.log(this.question_object);//FOR DEBUGGING
                this.transferData.setData(this.question_object);
                this.closeDialog();
              }
            }else if(this.didChoseMatrixType()){
              if(!this.missingAnswers()){
                this.findCorretAnswer();
                this.constructMatrixQuestion();
                this.transferData.setData(this.question_object);
                console.log(this.question_object);//FOR DEBUGGING
                this.closeDialog();
              }
              
            }
          }
        }else if(this.didChoseRateQuestion()){
            this.constructRateQuestion();
            this.transferData.setData(this.question_object);
            console.log(this.question_object);//FOR DEBUGGING
            this.closeDialog();
        }else if(this.didChoseOpenQuestion()){
              this.constructOpenQuestion();
              console.log(this.question_object);//FOR DEBUGGING
              this.transferData.setData(this.question_object);
              this.closeDialog();
          }
        console.log(this.didChoseDrillDownQuestion());
        }if(this.didChoseDrillDownQuestion()){
          console.log('hererererer');
          if(this.haveMainAnswers()){
            this.constructDrillDownQuestion();
            this.transferData.setData(this.question_object);
            this.closeDialog();
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
    if(this.answerText == null){
      this.answerText = '';
    }
    this.question_object = {
      questionText: this.questionText,
      type: this.typeQuestion,
      questionPosition: this.questionPosition,
      answerText: this.answerText
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
      heightOfRate: this.rateSize
    }
  }

  /*
    Constructing the matrix type of multiple choice question object
  */
  constructMatrixQuestion(){
    for(let i = 0; i < this.dimSize; i++){
      for(let j = 0; j < this.dimSize; j++){
        this.answers.splice(this.answers.length, 0 ,this.matrixAnswers[j][i]);
      }
    }
    this.constructMultipleQuestion();
  }
  /*
    Constructing the multiple choice question object
  */
  constructMultipleQuestion(){
    if(this.correctAnswer == null){
      this.correctAnswer = -1;
    }
    this.question_object = {
      questionText: this.questionText,
      type: this.typeQuestion,
      questionPosition: this.questionPosition,
      answers: this.answers,
      correctAnswer: this.correctAnswer,
      typeMultipleQuestion: this.typeMultipleQuestion
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
    return this.didChoseUpperLeft() || this.didChoseUpperMiddle() || this.didChoseUpperRight()||
            this.didChoseMiddleLeft() || this.didChoseMiddleMiddle() || this.didChoseMiddleRight() ||
            this.didChoseButtomLeft() || this.didChoseButtomMiddle() || this.didChoseButtomLeft();
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
  */
  addAnswer(){
    this.answers.splice(this.answers.length, 0, this.answerTextForMultiple);
    this.markedAnswers.splice(this.markedAnswers.length, 0, false);
    this.answerTextForMultiple = '';
  }

  /*
    Control flow function that checks if the user inserted at least one answer
  */
  haveAnswers(): boolean{
    return this.answers.length > 0;
  }

  /*
    Deletes an answer to the question
  */
  deleteAnswer(index: number){
    this.answers.splice(index,1);
    
    this.markedAnswers.splice(index, 1);
  }
  /*
    Edits an answer to the question
  */
  editAnswer(index: number){
    this.answerTextForMultiple = this.answers[index];
    this.editionMode = true;
    this.indexAnswerInEdit = index;
  }
  /*
    Applies the edition of the answer
  */
  applyEdit(){
    this.answers.splice(this.indexAnswerInEdit, 1,  this.answerTextForMultiple)
    this.editionMode = false;
    this.indexAnswerInEdit = -1;
    this.answerTextForMultiple = '';
  }
  /*
    Undo the edition that was made
  */
  undo(){
    this.editionMode = false;
    this.answerTextForMultiple = '';
    this.indexAnswerInEdit = -1;
  }
  /*
    Moving the answer up in the answer's organization
  */
  goUp(index: number){
    if(index != 0){
      let removed = this.answers.splice(index, 1)
      let removed_marked = this.markedAnswers.splice(index, 1);
      this.answers.splice(index - 1, 0, removed[0]);
      this.markedAnswers.splice(index - 1, 0, removed_marked[0]);
    } 
  }
  /*
    Moving the answer down in the answer's organization
  */
  goDown(index: number){
    if(index != this.answers.length - 1){
      let removed = this.answers.splice(index, 1);
      let removed_marked = this.markedAnswers.splice(index, 1);
      this.answers.splice(index + 1, 0, removed[0]);
      this.markedAnswers.splice(index + 1, 0, removed_marked[0]);
    }
  }
  print(){
    console.log('hi');
  }

  /*
    Marks the answer at the right index as correct.
    Params:
    index - In case the question is Horizontal or Vertical the index is only detemined by this one,
            because there's on;y one dimension
    index_col - In case the question is matrix type this index is needed, because there are 2 dimentions.
                Otherwise it will be -1, by default. 
  */
  markAnswer(index: number, index_col: number = -1){
    if(index_col == -1){

      for(let i = 0; i < this.markedAnswers.length; i++){
        if(i == index){
          this.markedAnswers[i] = !this.markedAnswers[i];
        }else{
          this.markedAnswers[i] = false;
        }
      }  
    }else{
      for(let i = 0; i < this.dimSize; i++){
        for(let j = 0; j < this.dimSize; j++){
          if(i == index && j == index_col){
            this.markedAnswersMatrix[i][j] = !this.markedAnswersMatrix[i][j];
          }else{
            this.markedAnswersMatrix[i][j] = false;
          }
        }
      }
    }
  }

  /*
    Determing the size of the matrix by increasing and deacrsing the size
  */
  increaseDim(){
    if(this.dimSize < 3){
      this.dimSize++;
      this.constructMatrix();
    }
  }
  decreaseDim(){
    if(this.dimSize > 2){
      this.dimSize--;
      this.constructMatrix();
    }
  }
  /*
    The function constructs all the elements in the matrix, according to its size
  */
  constructMatrix(){
    this.markedAnswersMatrix = new Array<Array<boolean>>(this.dimSize);
    this.matrixAnswers = new Array<Array<string>>(this.dimSize);
    this.iteratorArray = new Array<Array<null>>(this.dimSize);
    for(let i = 0; i < this.dimSize; i++){
      this.markedAnswersMatrix[i] = new Array<boolean>(this.dimSize);
      this.matrixAnswers[i] = new Array<string>(this.dimSize);
      this.iteratorArray[i] = new Array<null>(this.dimSize); 
      for(let j = 0; j < this.dimSize; j++){
        this.markedAnswersMatrix[i][j] = false;
        this.matrixAnswers[i][j] = '';
      }
    }
    this.centeringMatrix = {
      'two_col' : this.dimSize == 2,
      'three_col' : this.dimSize == 3,
      'four_col' : this.dimSize == 4
    };
  }

  constructMatrixInEdit(question_to_edit: any){
    console.log(question_to_edit.answers);
    this.markedAnswersMatrix = new Array<Array<boolean>>(this.dimSize);
    this.matrixAnswers = new Array<Array<string>>(this.dimSize);
    this.iteratorArray = new Array<Array<null>>(this.dimSize);
    for(let i = 0; i < this.dimSize; i++){
      this.markedAnswersMatrix[i] = new Array<boolean>(this.dimSize);
      this.matrixAnswers[i] = new Array<string>(this.dimSize);
      this.iteratorArray[i] = new Array<null>(this.dimSize); 
      for(let j = 0; j < this.dimSize; j++){
        if(question_to_edit.correctAnswer == this.dimSize*j + i){
          this.markedAnswersMatrix[i][j] = true;
        }else{
          this.markedAnswersMatrix[i][j] = false;
        }
        console.log('i: ' + i + ' j: ' + j + ' answer is: ' + question_to_edit.answers[j*this.dimSize + i]);  
        this.matrixAnswers[i][j] = question_to_edit.answers[j*this.dimSize + i];
      }
    }
    console.log(this.matrixAnswers);
    this.centeringMatrix = {
      'two_col' : this.dimSize == 2,
      'three_col' : this.dimSize == 3,
      'four_col' : this.dimSize == 4
    };
    
  }

  /*
    Control flow function which returns TRUE if there are missing answers in the matrix and FALSE other wise.
  */
  missingAnswers():boolean{
    for(let i = 0; i < this.dimSize; i++){
      for(let j = 0; j < this.dimSize; j++){
        if(this.matrixAnswers[i][j] == ''){
          return true;
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
      for(let i = 0; i < this.markedAnswers.length; i++){
        if(this.markedAnswers[i] == true){
          this.correctAnswer = i;
          break;
        }
      }
    }else{
      for(let i = 0; i < this.dimSize; i++){
        for(let j = 0; j < this.dimSize; j++){
          if(this.markedAnswersMatrix[i][j] == true){
            this.correctAnswer = j * this.dimSize + i;
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

  editQuestion(question: any){
    this.questionText = question.questionText;
    this.typeQuestion = question.type;
    this.questionPosition = question.questionPosition;
    if(this.typeQuestion == TypeQuestion.OpenQuestion){
      this.answerText = question.answerText;
    }else if(this.typeQuestion == TypeQuestion.RateQuestion){
      this.rateSize = question.heightOfRate;
    }else if(this.typeQuestion == TypeQuestion.MultipleChoice){
      this.typeMultipleQuestion = question.typeMultipleQuestion;
      if(this.typeMultipleQuestion == TypeMultipleQuestion.Horizontal || this.typeMultipleQuestion == TypeMultipleQuestion.Vertical){
        for(let i = 0; i < question.answers.length; i++){
          this.answers.splice(this.answers.length, 0, question.answers[i]);
          if(i == question.correctAnswer){
            this.markedAnswers.splice(this.markedAnswers.length, 0, true);
          }else{
            this.markedAnswers.splice(this.markedAnswers.length, 0, false);
          }
          
        }
      }else if(this.typeMultipleQuestion == TypeMultipleQuestion.Matrix){
        this.dimSize = Math.sqrt(question.answers.length);
        this.constructMatrixInEdit(question);
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
      let size_to_iterate = question.secondaryQuestionsText.legnth;
      for(let i = 0; i < size_to_iterate; i++ ){
        if(question.secondaryQuestionsText[i] != null){
          let obj_to_insert: SecondaryQuestionObject;
          obj_to_insert.index = i;
          obj_to_insert.questionText = question.seconderyQuestionText[i];
          obj_to_insert.markedAnswer = question.correctAnswerSecondary[i];
          obj_to_insert.answers = Object.assign([], question.answersForSecondary[i]);
          this.secondaryQuestionList.splice(this.secondaryQuestionList.length, 0, obj_to_insert);
        }
      }
    }

  }

  addMainAnswer(){
 
    this.mainAnswers.splice(this.mainAnswers.length, 0, this.currentMainAnswer);
    this.markedMainCorrectAnswer.splice(this.markedMainCorrectAnswer.length, 0, false);
    this.currentMainAnswer = '';
  }

  haveMainAnswers(): boolean {
    return this.mainAnswers.length >= 1;
  }

  markMainAnswer(index: number){
    this.secondaryAnswerMode = false;
    if(this.indexOfMainanswerToShow == index){
      this.showMainAnswer = false;

      this.indexOfMainanswerToShow = -1;
    }else{
      this.showMainAnswer = true;
      this.indexOfMainanswerToShow = index;
    }
    
  }

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
    if(this.showMainAnswer){
      this.showMainAnswer = false;
      this.secondaryAnswerMode = false;
      this.indexOfMainanswerToShow = -1;
    }
    for(let i = 0; i < this.secondaryQuestionList.length; i++){
      if(index == this.secondaryQuestionList[i].index){
        this.secondaryQuestionList.splice(i,0);
      }
    }
    this.mainAnswers.splice(index,1);
    this.markedMainCorrectAnswer.splice(index, 1);
  }

  editMainAnswer(index: number){
    this.currentMainAnswer = this.mainAnswers[index];
    this.editionModeMain = true;
    this.indexAnswerInEditMain = index;
  }

  undoEditMain(){
    this.editionModeMain = false;
    this.currentMainAnswer = '';
    this.indexAnswerInEditMain = -1;
  }

  applyEditMain(){
    this.mainAnswers.splice(this.indexAnswerInEditMain, 1,  this.currentMainAnswer)
    this.editionModeMain = false;
    this.indexAnswerInEditMain = -1;
    this.currentMainAnswer = '';
  }

  goMainUp(index: number){
    
    if(index != 0){
      for(let i = 0; i < this.secondaryQuestionList.length; i++){
        if(index == this.secondaryQuestionList[i].index){
          this.secondaryQuestionList[i].index--;
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

  goMainDown(index: number){
    if(index != this.mainAnswers.length - 1){
      for(let i = 0; i < this.secondaryQuestionList.length; i++){
        if(index == this.secondaryQuestionList[i].index){
          this.secondaryQuestionList[i].index++;
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

  addSecondaryQuestion(index: number){
    if(this.secondaryExists()){
      this.secondaryAnswerMode = !this.secondaryAnswerMode;
    }else{
      this.secondaryAnswerMode = true;
    }
    
  }
  whatToShow(index: number):boolean{
    return index == this.indexOfMainanswerToShow;
  }
  undoSecondary(){
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
  finishSecondaryQuestion(){
    console.log('hereeee');
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
      console.log('correct answer is: ' + correct_answer);
      this.secondaryAnswerMode = false;
      this.secondaryAnswers = new Array();
      this.currentSecondaryAnswer = '';

      this.currentSecondaryQuestion = '';
      this.markedSecondaryCorrectAnswer = new Array();
    }
    
    this.submitSecondaryQuestion = true;
    console.log('Finish');
    console.log(this.secondaryQuestionList);
  }

  hasSecondaryAnswer(): boolean{
    return this.secondaryAnswers.length >= 1;
  }
  secondaryExists(): boolean{
    for(let i = 0; i < this.secondaryQuestionList.length; i++){
      if(this.secondaryQuestionList[i].index == this.indexOfMainanswerToShow){
        return true;
      }
    }
    return false;
  }

  emptySecondary():boolean{
    return this.currentSecondaryQuestion == '';
  }
  addSecondaryAnswer(){
    this.secondaryAnswers.splice(this.secondaryAnswers.length, 0, this.currentSecondaryAnswer);
    this.markedSecondaryCorrectAnswer.splice(this.markedSecondaryCorrectAnswer.length, 0, false);
    this.currentSecondaryAnswer = '';
  }
  hasSecondaryAnswers(): boolean{
    return this.secondaryAnswers.length >= 1;
  }
  markCorretSecondaryAnswer(index: number){
    for(let i = 0; i < this.markedSecondaryCorrectAnswer.length; i++){
      if(i == index){
        this.markedSecondaryCorrectAnswer[i] = !this.markedSecondaryCorrectAnswer[i];
      }else{
        this.markedSecondaryCorrectAnswer[i] = false;
      }
    } 
  }
  goSecondaryUp(index: number){
    if(index != 0){
      let removed = this.secondaryAnswers.splice(index, 1)
      let removed_marked_correct = this.markedSecondaryCorrectAnswer.splice(index,1);
      this.secondaryAnswers.splice(index - 1, 0, removed[0]);
      this.markedSecondaryCorrectAnswer.splice(index - 1, 0, removed_marked_correct[0]);
    }
  }

  goSecondaryDown(index: number){
    if(index != this.secondaryAnswers.length - 1){
      let removed = this.secondaryAnswers.splice(index, 1);
      let removed_marked_correct = this.markedSecondaryCorrectAnswer.splice(index, 1);
      this.secondaryAnswers.splice(index + 1, 0, removed[0]);
      this.markedSecondaryCorrectAnswer.splice(index + 1, 0, removed_marked_correct[0]);
      
    }
  }

  deleteSecondaryAnswer(index: number){
    this.secondaryAnswers.splice(index,1);
    this.markedSecondaryCorrectAnswer.splice(index, 1);
  }

  editSecondaryAnswer(index: number){
    this.currentSecondaryAnswer = this.secondaryAnswers[index];
    this.editionModeSecondary = true;
    this.indexAnswerInEditSecondary = index;
  }

  applySecondaryEdit(){
    this.secondaryAnswers.splice(this.indexAnswerInEditSecondary, 1,  this.currentSecondaryAnswer)
    this.editionModeSecondary = false;
    this.indexAnswerInEditSecondary = -1;
    this.currentMainAnswer = '';
    this.currentSecondaryAnswer = '';
  }

  undoSecondaryEdit(){
    
    this.editionModeSecondary = false;
    this.currentSecondaryAnswer = '';
    this.indexAnswerInEditSecondary = -1;
  }

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

  backToView(){
    this.editionQuestionSecondary = false;
    this.viewSecondaryAnswer();
  }
  
  hasMainAnswres():boolean{
    return this.mainAnswers.length >= 1;
  }

  
}
