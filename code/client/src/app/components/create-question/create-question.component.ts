import { Component, OnInit } from '@angular/core';
import { TypeMultipleQuestion, TypeQuestion, QuestionPosition} from '../../models';
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



  submit: boolean = false;
  editionMode: boolean = false;
  indexAnswerInEdit: number = -1;

  question_object: any;
  constructor() {
    this.constructMatrix();
 
   }

  ngOnInit() {
  }

  setMultipleQuestion(){
    if(this.typeQuestion == TypeQuestion.MultipleChoice){
      this.typeQuestion = null;
    }else{
      this.typeQuestion = TypeQuestion.MultipleChoice;
    }
  }
  setRateQuestion(){
    if(this.typeQuestion == TypeQuestion.RateQuestion){
      this.typeQuestion = null;
    }else{
      this.typeQuestion = TypeQuestion.RateQuestion;
    }
  }
  setOpenQuestion(){
    if(this.typeQuestion == TypeQuestion.OpenQuestion){
      this.typeQuestion = null;
    }else{
      this.typeQuestion = TypeQuestion.OpenQuestion;
    }
  }
  didChoseMultipleQuestion() : boolean{
    return this.typeQuestion == TypeQuestion.MultipleChoice;
  }
  didChoseRateQuestion() : boolean{
    return this.typeQuestion == TypeQuestion.RateQuestion;
  }
  didChoseOpenQuestion() : boolean{
    return this.typeQuestion == TypeQuestion.OpenQuestion;
  }
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

  setMatrixType(){
    this.answers = new Array<string>();
    if(this.typeMultipleQuestion == TypeMultipleQuestion.Matrix){
      this.typeMultipleQuestion = null;
    }else{
      this.typeMultipleQuestion = TypeMultipleQuestion.Matrix;
    }
  }
  didChoseMatrixType(){
    return this.typeMultipleQuestion == TypeMultipleQuestion.Matrix;
  }

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

  increaseRate(){
    this.rateSize++;
  }
  decreaseRate(){
    if(this.rateSize > 1){
      this.rateSize--;
    }
  }

  onSubmit(f){
    console.log('hi');
    if(this.didChoseQuestionPosition() && this.didChoseQuestionType()){
      if(this.didChoseMultipleQuestion()){
        if(this.didChoseMultipleChoiceType()){
          if(this.didChoseVerticalType() || this.didChoseHorizontalType()){
            if(this.haveAnswers()){
              this.constructMultipleQuestion();
            }
          }else if(this.didChoseMatrixType()){
            if(!this.missingAnswers()){
              this.constructMatrixQuestion();
            }
            
          }
        }else if(this.didChoseRateQuestion()){
          this.constructRateQuestion();
        }else if(this.didChoseOpenQuestion()){
          if(this.answerText != ''){
            this.constructOpenQuestion();
          }
        }
      }
    }
    this.submit = true;
  }
  constructOpenQuestion(){
    this.question_object = {
      questionText: this.questionText,
      type: this.typeQuestion,
      questionPosition: this.questionPosition,
      answerText: this.answerText
    }
  }
  constructRateQuestion(){
    this.question_object = {
      questionText: this.questionText,
      type: this.typeQuestion,
      questionPosition: this.questionPosition,
      heightOfRate: this.rateSize
    }
  }
  constructMatrixQuestion(){
    for(let i = 0; i < this.dimSize; i++){
      for(let j = 0; j < this.dimSize; j++){
        this.answers.splice(this.answers.length, 0 ,this.matrixAnswers[j][i]);
      }
    }
    this.constructMultipleQuestion();
  }
  constructMultipleQuestion(){
    this.question_object = {
      questionText: this.questionText,
      type: this.typeQuestion,
      questionPosition: this.questionPosition,
      answers: this.answers,
      correctAnswer: this.correctAnswer,
      typeMultipleQuestion: this.typeMultipleQuestion
    }
  }
  didChoseQuestionType():boolean {
    return this.didChoseOpenQuestion() || this.didChoseRateQuestion() || this.didChoseMultipleQuestion(); 
  }
  didChoseQuestionPosition():boolean{
    return this.didChoseUpperLeft() || this.didChoseUpperMiddle() || this.didChoseUpperRight()||
            this.didChoseMiddleLeft() || this.didChoseMiddleMiddle() || this.didChoseMiddleRight() ||
            this.didChoseButtomLeft() || this.didChoseButtomMiddle() || this.didChoseButtomLeft();
  }
  didChoseMultipleChoiceType():boolean {
    return this.didChoseMatrixType() || this.didChoseVerticalType() || this.didChoseHorizontalType();
  }

  addAnswer(){
    this.answers.splice(this.answers.length, 0, this.answerTextForMultiple);
    this.markedAnswers.splice(this.markedAnswers.length, 0, false);
    this.answerTextForMultiple = '';
  }

  haveAnswers(): boolean{
    return this.answers.length > 0;
  }

  deleteAnswer(index: number){
    this.answers.splice(index,1);
    this.markedAnswers.splice(index, 1);
  }

  editAnswer(index: number){
    this.answerTextForMultiple = this.answers[index];
    this.editionMode = true;
    this.indexAnswerInEdit = index;
  }

  applyEdit(){
    this.answers.splice(this.indexAnswerInEdit, 1,  this.answerTextForMultiple)
    this.editionMode = false;
    this.indexAnswerInEdit = -1;
    this.answerTextForMultiple = '';
  }
  undo(){
    this.editionMode = false;
    this.answerTextForMultiple = '';
    this.indexAnswerInEdit = -1;
  }

  goUp(index: number){
    if(index != 0){
      let removed = this.answers.splice(index, 1)
      let removed_marked = this.markedAnswers.splice(index, 1);
      this.answers.splice(index - 1, 0, removed[0]);
      this.markedAnswers.splice(index - 1, 0, removed_marked[0]);
    } 
  }

  goDown(index: number){
    if(index != this.answers.length - 1){
      let removed = this.answers.splice(index, 1);
      let removed_marked = this.markedAnswers.splice(index, 1);
      this.answers.splice(index + 1, 0, removed[0]);
      this.markedAnswers.splice(index + 1, 0, removed_marked[0]);
    }
  }
  markAnswer(index: number, index_col: number = -1){
    if(index_col == -1){
      for(let i = 0; i < this.markedAnswers.length; i++){
        if(i == index){
          this.markedAnswers[i] = true;
          this.correctAnswer = i;
        }else{
          this.markedAnswers[i] = false;
        }
      }  
    }else{
      for(let i = 0; i < this.dimSize; i++){
        for(let j = 0; j < this.dimSize; j++){
          if(i == index && j == index_col){
            this.markedAnswersMatrix[i][j] = true;
            this.correctAnswer = i + this.dimSize+j;
          }else{
            this.markedAnswersMatrix[i][j] = false;
          }
        }
      }
    }
  }
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
  missingAnswers():boolean{
    for(let i = 0; i < this.dimSize; i++){
      for(let j = 0; j < this.dimSize; j++){
        console.log(this.matrixAnswers[i][j])
        if(this.matrixAnswers[i][j] == ''){
          return true;
        }
      }
    }
    
    return false;
  }
}
