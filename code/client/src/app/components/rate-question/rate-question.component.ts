import { Component, OnInit } from '@angular/core';
import { RateQuestion, MultipleAnswer, TypeQuestion } from '../../models';
import { stringify } from '@angular/core/src/util';
@Component({
  selector: 'app-rate-question',
  templateUrl: './rate-question.component.html',
  styleUrls: ['./rate-question.component.css']
})
export class RateQuestionComponent implements OnInit {
  question: RateQuestion;
  answers: Array<MultipleAnswer>;
  markedAnswer : number;
  range_value : number = 50;
  constructor() {
    /*
    hardcoded question object, when services will be added this piece of code will not be needed
    */ 
      this.question = {
        questionText: 'How is your social life?',
        type: TypeQuestion.RateQuestion,
        heightOfRate: 5
      }
      //End of harcoded question
      this.answers = new Array<MultipleAnswer>(this.question.heightOfRate);
      for(let i = 0; i < this.question.heightOfRate; i++){
        this.answers[i] = {answer: (i + 1).toString(), isMarked: false};
      }
   }

  ngOnInit() {
  }

  markAnswer(answerText:string){
    for(let i = 0; i < this.answers.length; i++){   
      if(this.answers[i].answer == answerText){
        this.answers[i].isMarked = true;
        this.markedAnswer = i;

      }else{
        this.answers[i].isMarked = false;
      }
    }
  }

  onSubmit(evet: Event){
    
  }
}
