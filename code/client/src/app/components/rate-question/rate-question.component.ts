import { Component, OnInit, AnimationStyleMetadata } from '@angular/core';
import { RateQuestion, TypeQuestion, QuestionPosition } from '../../models';
import { stringify } from '@angular/core/src/util';
@Component({
  selector: 'app-rate-question',
  templateUrl: './rate-question.component.html',
  styleUrls: ['./rate-question.component.css']
})
export class RateQuestionComponent implements OnInit {
  question: RateQuestion;
  answers: Array<null>;
  markedAnswers: Array<boolean>;
  markedAnswer : number;
  range_value : number = 50;
  positionUp : any;
  positionMiddle : any;
  positionButtom : any;
  constructor() {
    /*
    hardcoded question object, when services will be added this piece of code will not be needed
    */ 
      this.question = {
        questionText: 'How is your social life?',
        questionPosition: QuestionPosition.UpperMiddle,
        type: TypeQuestion.RateQuestion,
        heightOfRate: 5
      }
      //End of harcoded question
      this.buildPositionOfQuestion();
      this.answers = new Array<null>(this.question.heightOfRate);
      this.markedAnswers = new Array<boolean>(this.question.heightOfRate);
      for(let i = 0; i < this.question.heightOfRate; i++){
        this.markedAnswers[i] = false;
      }
   }

  ngOnInit() {
  }

  markAnswer(index: number){
    for(let i = 0; i < this.answers.length; i++){   
      if(i == index){
        this.markedAnswers[i] = true;
        this.markedAnswer = i;

      }else{
        this.markedAnswers[i] = false;
      }
    }
  }

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
  onSubmit(evet: Event){
    
  }
}
