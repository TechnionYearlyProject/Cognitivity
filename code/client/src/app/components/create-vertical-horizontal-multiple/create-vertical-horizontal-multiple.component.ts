import {Component, Input, OnInit} from "@angular/core";
import {TypeMultipleQuestion} from "../../models/index";

@Component({
  selector: 'app-create-vertical-horizontal-multiple',
  templateUrl: './create-vertical-horizontal-multiple.component.html',
  styleUrls: ['./create-vertical-horizontal-multiple.component.css']
})
export class CreateVerticalHorizontalMultipleComponent implements OnInit {

  @Input() question: any;
    /*
    ------------ multiple choice question details -----------
  */
  typeMultipleQuestion?: TypeMultipleQuestion;
  answers?: Array<string> = new Array();
  answerTextForMultiple?: string;
  correctAnswer?: number;
  markedAnswers?: Array<boolean> = new Array();
  typedMultipleAnswer: boolean = false;
  //edit mode
  editionMode: boolean = false;
  //index of answer to edit
  indexAnswerInEdit: number = -1;

  constructor() { }

  ngOnInit() {
    if(this.question != null){
      for(let i = 0; i < this.question.answers.length; i++){
        this.answers.splice(this.answers.length, 0, this.question.answers[i]);
        if(i == this.question.correctAnswer){
          this.markedAnswers.splice(this.markedAnswers.length, 0, true);
        }else{
          this.markedAnswers.splice(this.markedAnswers.length, 0, false);
        }

      }
    }
  }


    /*
    Adding an answer for the question
  */
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
    if(this.answerTextForMultiple != null && this.answerTextForMultiple.length >= 1 && !this.isSpacePrefix(this.answerTextForMultiple)
    && this.doesHaveMultiAnswer(this.answerTextForMultiple) != true){
      this.answers.splice(this.indexAnswerInEdit, 1,  this.answerTextForMultiple)
      this.editionMode = false;
      this.indexAnswerInEdit = -1;
      this.answerTextForMultiple = '';
      this.typedMultipleAnswer = false;
    }else{
      this.typedMultipleAnswer = true;
    }
  }
  /*
    Undo the edition that was made
  */
  undo(){
    this.editionMode = false;
    this.answerTextForMultiple = '';
    this.indexAnswerInEdit = -1;
    this.typedMultipleAnswer = false;
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


  /*
    Marks the answer at the right index as correct.
    Params:
    index - In case the question is Horizontal or Vertical the index is only detemined by this one,
            because there's on;y one dimension
   index_col - In case the question is matrix questionType this index is needed, because there are 2 dimentions.
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
    }
  }

    /*
     this function checks if an answer already exists in the multi choice question's answers
  and returns True if it is, False otherwise.
  */
  doesHaveMultiAnswer(givenStr: string): boolean{
    if(this.answers.indexOf(givenStr)!= -1){
      return true;
    }
    return false;
  }
  findCorretAnswer(){
      for(let i = 0; i < this.markedAnswers.length; i++){
        if(this.markedAnswers[i] == true){
          this.correctAnswer = i;
          break;
        }
      }
  }

    /*
    Control flow function that checks if the user inserted at least one answer
  */
  haveAnswers(): boolean{
    return this.answers.length > 0;
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
}
