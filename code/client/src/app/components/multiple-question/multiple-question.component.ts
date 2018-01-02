import { Component, OnInit } from '@angular/core';
import { MultipleAnsQuestion, TypeMultipleQuestion, MultipleAnswer, TypeQuestion, QuestionPosition } from '../../models';
@Component({
  selector: 'app-multiple-question',
  templateUrl: './multiple-question.component.html',
  styleUrls: ['./multiple-question.component.css']
})
export class MultipleQuestionComponent implements OnInit {
  question: MultipleAnsQuestion;
  markedAnswer: number;
  correct: boolean;
  isSubmit: boolean;
  answerOrganization: TypeMultipleQuestion  = TypeMultipleQuestion.Vertical;
  matrixAnswers? : Array<Array<MultipleAnswer>>
  range_value: number = 50;
  positionUp: any;
  positionButtom: any;
  positionMiddle: any; 
  constructor() {
     /*
    hardcoded question object, when services will be added this piece of code will not be needed
    */
    this.question = 
    {
      questionText:'Who directed Inception?',
      type: TypeQuestion.MultipleChoice,
      questionPosition: QuestionPosition.MiddleLeft,
      answers:[{answer:'Christopher Nolan', isMarked:false}, {answer:'Ridely Scott', isMarked:false}, {answer:'Quantin Tarantino',isMarked:false}, {answer:'Robert Downy Jr.', isMarked:false}],
      correctAnswer:1, 
      typeMultipleQuestion: TypeMultipleQuestion.Matrix
    };
    //End of hardcoded question
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
    this.answerOrganization = this.question.typeMultipleQuestion;
    if(this.answerOrganization == TypeMultipleQuestion.Matrix){
      this.constructMatrix();
    }
   }

  ngOnInit() {
    
  }

  markAnswerInMatrix(row: number, col: number){
    for(let i = 0; i < this.question.answers.length; i++){   
      if(i == row * this.matrixAnswers[0].length + col){
        this.question.answers[i].isMarked = true;
        this.markedAnswer = i;

      }else{
        this.question.answers[i].isMarked = false;
      }
    }
    
  }
  
  markAnswer(ansIndex: number){
    for(let i = 0; i < this.question.answers.length; i++){   
      if(i == ansIndex){
        this.question.answers[i].isMarked = true;
        this.markedAnswer = i;

      }else{
        this.question.answers[i].isMarked = false;
      }
    }
  }
  
  onSubmit(event: Event){
    console.log('check submit');
  }

  isVertical(): boolean{
    return this.answerOrganization == TypeMultipleQuestion.Vertical;
  }
  isHorizontal(): boolean{
    return this.answerOrganization == TypeMultipleQuestion.Horizontal;
  }

  isMatrix(): boolean {
    return this.answerOrganization == TypeMultipleQuestion.Matrix;
  }

 

  constructMatrix(){
    let answersSize = this.question.answers.length;
    let colNum = Math.ceil(Math.sqrt(answersSize));
    let rowNum = Math.ceil(answersSize / colNum);
    
    this.matrixAnswers = new Array(rowNum);
    for(let i = 0; i < rowNum; i++){
      let rowAnswers: Array<MultipleAnswer>
      if(i == rowNum - 1){
        let lastSize : number = answersSize - (rowNum - 1)*colNum;
        rowAnswers = new Array(lastSize);
      }else{
        rowAnswers = new Array(colNum);
      }
      for(let j = 0; j < colNum; j++){

        if(i*colNum + j < answersSize){
          rowAnswers[j] = this.question.answers[i*colNum + j];
        }
      }
      this.matrixAnswers[i] = rowAnswers;
    }
  }
  /*
    returning the value of the answer when the form of the answers is matrix.
    it's only necessary when the formation of the answers is matrix, due to the construction of the matrix, in all other forms the direct access to the field isMarked is enough
  */
  isMarkedInMatrix(row: number, col: number): boolean{
        return this.question.answers[row * this.matrixAnswers[0].length + col].isMarked;
  }
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
}
/*"../node_modules/bootstrap-datepicker3.css"*/

