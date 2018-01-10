import { Component, OnInit } from '@angular/core';
import { MultipleChoiceQuestion, TypeMultipleQuestion, TypeQuestion, QuestionPosition } from '../../models';
@Component({
  selector: 'app-multiple-choice-question',
  templateUrl: './multiple-choice-question.component.html',
  styleUrls: ['./multiple-choice-question.component.css']
})
export class MultipleChoiceQuestionComponent implements OnInit {
  question: MultipleChoiceQuestion;
  markedAnswers?: Array<boolean>;
  //Objects that detemines the style of the question text itself
  positionUp: any;
  positionMiddle: any;
  positionButtom: any;

  range_value: number = 50;
  answerOrganization: TypeMultipleQuestion;
  /*
    ---------------------- special objects that are ment for multiple question of type matrix
  */
  markedAnswersMatrix?: Array<Array<boolean>>;
  dimMatrix?: number;
  matrixAnswers?: Array<Array<string>>;
  centeringMatrix?: any; //An object that centers the matrix in the web page acocording its size 
  constructor() {
  
   }

  ngOnInit() {
    this.question = {
      questionText:'Who directed Inception?',
      type: TypeQuestion.MultipleChoice,
      questionPosition: QuestionPosition.UpperMiddle,
      answers:['Christopher Nolan', 'Ridely Scott', 'Quantin Tarantino', 'Robert Downy Jr.'],
      correctAnswer:1, 
      typeMultipleQuestion: TypeMultipleQuestion.Matrix
    }
    this.answerOrganization = this.question.typeMultipleQuestion;
    this.constructMarking();
    this.buildPositionOfQuestion();
    if(this.answerOrganization == TypeMultipleQuestion.Matrix){
      this.constructMatrix();
    }
  }
  constructMatrix(){
    this.matrixAnswers = new Array<Array<string>>(this.dimMatrix);
    for(let i = 0; i < this.dimMatrix; i++){
      this.matrixAnswers[i] = new Array<string>(this.dimMatrix);
      for(let j = 0; j < this.dimMatrix; j++){
        this.matrixAnswers[i][j] = this.question.answers[j*this.dimMatrix + i];
      }
    }
    this.centeringMatrix = {
      'one_col' : this.isOneCol(),
      'two_col' : this.isTwoCol(),
      'three_col' : this.isThreeCol(),
      'four_col' : this.isFourCol(),
      'higherThanFour' : this.higherThanFourColNum()
    }
  }
  /*
    preparing the marking objects that will bind to the check boxes at the GUI.
  */
  constructMarking(){
    if(this.answerOrganization == TypeMultipleQuestion.Matrix){
      this.dimMatrix = Math.sqrt(this.question.answers.length);
      this.markedAnswersMatrix = new Array<Array<boolean>>(this.dimMatrix);
      for(let i = 0; i < this.dimMatrix; i++){
        this.markedAnswersMatrix[i] = new Array<boolean>(this.dimMatrix);
        for(let j = 0; j < this.dimMatrix; j++){
          this.markedAnswersMatrix[i][j] = false;
        }
      }
    }else{
      let answersSize: number = this.question.answers.length;
      this.markedAnswers = new Array<boolean>(answersSize);
      for(let i = 0; i < answersSize; i++){
        this.markedAnswers[i] = false;
      }
    }
    
  }
  /*
    chosing the right style according to the position that was chosen
  */
  buildPositionOfQuestion(){
    this.positionUp = {
      'right' : this.isUpperRight(),
      'middle' : this.isUpperMiddle(),
      'left' : this.isUpperLeft()
    }
    this.positionButtom = {
      'right' : this.isButtomRight(),
      'middle' : this.isButtomMiddle(),
      'left' : this.isButtomLeft()
    }
    this.positionMiddle = {
      'right' : this.isMiddleRight(),
      'middle' : this.isMiddleMiddle(),
      'left' : this.isMiddleLeft()
    }
  }
  /*
    A function that marks the answer that the user marked. In case the type of the question is Horizontal or vartical the secondary index isn't needed.
    The index specify which answer was marked. Due to the binding of this property it will affect the GUI.

  */
  markAnswer(main_index: number,secondary_index:number = -1){
    if(secondary_index == -1){
      for(let i = 0; i < this.question.answers.length; i++){
        if(i == main_index){
          this.markedAnswers[i] = true;    
        }else{
          this.markedAnswers[i] = false;
        }
      }
    }else{
      for(let i = 0; i < this.dimMatrix; i++){
        for(let j = 0; j < this.dimMatrix; j++){
          if(i == main_index && j == secondary_index){
            this.markedAnswersMatrix[i][j] = true;
          }else{
            this.markedAnswersMatrix[i][j] = false;
          }
        }
      }
    }
  }
   /*
    ------------- Asking the type of the multiple question ------------------
  */
  isVertical(): boolean{
    return this.answerOrganization == TypeMultipleQuestion.Vertical;
  }
  isHorizontal(): boolean{
    return this.answerOrganization == TypeMultipleQuestion.Horizontal;
  }

  isMatrix(): boolean {
    return this.answerOrganization == TypeMultipleQuestion.Matrix;
  }

    /*
    ------------------------------- functions for asking the position of the question text -----------------------
  */
  isUpperMiddle(): boolean{
    return this.question.questionPosition == QuestionPosition.UpperMiddle;
  }
  isUpperRight(): boolean{
    return this.question.questionPosition == QuestionPosition.UpperRight;
  }
  isUpperLeft(): boolean{
    return this.question.questionPosition == QuestionPosition.UpperLeft;
  }
  isButtomRight(): boolean{
    return this.question.questionPosition == QuestionPosition.ButtomRight;
  }
  isButtomMiddle(): boolean{
    return this.question.questionPosition == QuestionPosition.ButtomMiddle;
  }
  isButtomLeft(): boolean{
    return this.question.questionPosition == QuestionPosition.ButtomLeft;
  }
  isMiddleRight(): boolean{
    return this.question.questionPosition == QuestionPosition.MiddleRight;
  }
  isMiddleMiddle(): boolean{
    return this.question.questionPosition == QuestionPosition.MiddleMiddle;
  }
  isMiddleLeft(): boolean{
    return this.question.questionPosition == QuestionPosition.MiddleLeft;
  }
  isUp(): boolean{
    return this.isUpperMiddle() || this.isUpperRight() || this.isUpperLeft();
  }
  isMiddle(): boolean{
    return this.isMiddleMiddle() || this.isMiddleRight() || this.isMiddleLeft();
  }
  isButtom():boolean{
    return this.isButtomMiddle() || this.isButtomRight() || this.isButtomLeft();
  }
  
  /*
    -------- functions that helps centering the matrix depending on the number of columns
  */
  isOneCol():boolean {
    return this.dimMatrix == 1;
  }
  isTwoCol():boolean{
    return this.dimMatrix == 2;
  }
  isThreeCol():boolean{
    return this.dimMatrix == 3;
  }
  isFourCol():boolean{
    return this.dimMatrix == 4;
  }
  higherThanFourColNum():boolean{
    return this.dimMatrix > 4;
  }

}
