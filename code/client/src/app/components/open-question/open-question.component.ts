import { Component, OnInit } from '@angular/core';
import { OpenQuestion, TypeQuestion } from '../../models';

@Component({
  selector: 'app-open-question',
  templateUrl: './open-question.component.html',
  styleUrls: ['./open-question.component.css']
})
export class OpenQuestionComponent implements OnInit {
  question: OpenQuestion;
  currentAnswer: string;
  range_value: number = 50;
  constructor() { 
    /*
    hardcoded question object, when services will be added this piece of code will not be needed
    */ 
    this.question = {
      questionText: 'Which one is better, Iphone or Galaxy?',
      type: TypeQuestion.OpenQuestion,
      answerText: 'Iphone'
    };
    //End of hardcoded question
  }

  ngOnInit() {
  }

  onSubmit(event: Event){
    //this.currentAnswer = answer;
    console.log('The answer is: ' + this.currentAnswer);
  }
}
