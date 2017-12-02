import { Component, OnInit } from '@angular/core';
import { MultipleAnsQuestion, TypeMultipleQuestion, MultipleAnswer } from '../../models';
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
  constructor() {
    this.question = 
    {
      text:'Who directed Inception?',
      answers:[{answer:'Christopher Nolan', isMarked:false}, {answer:'Ridely Scott', isMarked:false}, {answer:'Quantin Tarantino',isMarked:false}, {answer:'Robert Downy Jr.', isMarked:false}, {answer:'Mark Erlich', isMarked:false}],
      correctAnswer:1, 
      typeMultipleQuestion: TypeMultipleQuestion.Matrix
    };
    this.answerOrganization = this.question.typeMultipleQuestion;
    if(this.answerOrganization == TypeMultipleQuestion.Matrix){
      this.constructMatrix();
    }
   }

  ngOnInit() {
  }

  markAnswer(answerText:string){
    for(let i = 0; i < this.question.answers.length; i++){   
      if(this.question.answers[i].answer == answerText){
        this.question.answers[i].isMarked = true;
        this.markedAnswer = i;

      }else{
        this.question.answers[i].isMarked = false;
      }
    }
  }
  
  onSubmit(event: Event){
    event.preventDefault();
    this.isSubmit = true;
    if(this.markedAnswer == this.question.correctAnswer - 1){
      this.correct = true;
    }else{
      this.correct = false;
    }
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
  isMarkedInMatrix(answer: string): boolean{
    for(let i = 0; i < this.question.answers.length; i++){
      if(this.question.answers[i].answer == answer){
        return this.question.answers[i].isMarked;
      }
    }
  }
}


