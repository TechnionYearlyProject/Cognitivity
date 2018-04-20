import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core';

@Component({
  selector: 'app-create-matrix-multiple-question',
  templateUrl: './create-matrix-multiple-question.component.html',
  styleUrls: ['./create-matrix-multiple-question.component.css']
})
export class CreateMatrixMultipleQuestionComponent implements OnInit {
  dimSize?: number = 2;
  markedAnswersMatrix?: Array<Array<boolean>>;
  matrixAnswers?: Array<Array<string>>;
  centeringMatrix?: any;
  iteratorArray?: Array<Array<null>>;
  correctAnswer: number;
  answers: Array<string>;
  @Input() question: any;
  constructor() { }

  ngOnInit() {
    this.answers = new Array();
    this.constructMatrix();
    if(this.question != null){
      this.constructMatrixInEdit();
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

  constructMatrixInEdit(){
    this.dimSize = Math.sqrt(this.question.answers.length);
    this.markedAnswersMatrix = new Array<Array<boolean>>(this.dimSize);
    this.matrixAnswers = new Array<Array<string>>(this.dimSize);
    this.iteratorArray = new Array<Array<null>>(this.dimSize);
    for(let i = 0; i < this.dimSize; i++){
      this.markedAnswersMatrix[i] = new Array<boolean>(this.dimSize);
      this.matrixAnswers[i] = new Array<string>(this.dimSize);
      this.iteratorArray[i] = new Array<null>(this.dimSize); 
      for(let j = 0; j < this.dimSize; j++){
        if(this.question.correctAnswer == this.dimSize*j + i){
          this.markedAnswersMatrix[i][j] = true;
        }else{
          this.markedAnswersMatrix[i][j] = false;
        }
        this.matrixAnswers[i][j] = this.question.answers[j*this.dimSize + i];
      }
    }
    this.centeringMatrix = {
      'two_col' : this.dimSize == 2,
      'three_col' : this.dimSize == 3,
      'four_col' : this.dimSize == 4
    };
    
  }


  /*
    For constructing the whole multiple choice question, after all its information is ready, according to the markings
    the corect answer is found and saved in this.correctAnswer
  */
  findCorretAnswer(){
      for(let i = 0; i < this.dimSize; i++){
        for(let j = 0; j < this.dimSize; j++){
          if(this.markedAnswersMatrix[i][j] == true){
            this.correctAnswer = j * this.dimSize + i;
          }
        }
      }
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
  
  markAnswer(index_row: number, index_col: number){
    for(let i = 0; i < this.dimSize; i++){
      for(let j = 0; j < this.dimSize; j++){
        if(i == index_row && j == index_col){
          this.markedAnswersMatrix[i][j] = !this.markedAnswersMatrix[i][j];
        }else{
          this.markedAnswersMatrix[i][j] = false;
        }
      }
    }
  }
  


}
