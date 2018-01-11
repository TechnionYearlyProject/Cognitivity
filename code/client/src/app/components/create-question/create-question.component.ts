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
  constructor() { }

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
    this.submit = true;
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
    this.answerTextForMultiple = '';
  }

  haveAnswers(): boolean{
    return this.answers.length > 0;
  }

  deleteAnswer(index: number){
    this.answers.splice(index,1);
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
      this.answers.splice(index - 1, 0, removed[0]);
    } 
  }

  goDown(index: number){
    if(index != this.answers.length - 1){
      let removed = this.answers.splice(index, 1)
      this.answers.splice(index + 1, 0, removed[0]);
    }
  }
}
