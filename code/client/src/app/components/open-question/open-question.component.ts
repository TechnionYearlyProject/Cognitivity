import { Component, OnInit } from '@angular/core';
import { OpenQuestion } from '../../models';

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
    this.question = {
      questionText: 'Which one is better, Iphone or Galaxy?',
      answerText: 'Iphone'
    };
  }

  ngOnInit() {
  }

  onSubmit(event: Event){
    //this.currentAnswer = answer;
    console.log('The answer is: ' + this.currentAnswer);
  }
}
