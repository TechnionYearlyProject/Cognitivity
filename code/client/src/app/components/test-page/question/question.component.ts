/**
 * @author Daniel Lyubin
 * @date 12/5/2018
 */
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TimeMeasurment, QuestionAnswer } from '../../../models';

@Component({
  selector: 'app-test-page-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class TestPageQuestionComponent implements OnInit {
//default constructor.
constructor() { }
  
//we get the question as a passed input, thats the question we'll preview.
@Input() question: any;

// Output event that emits the time took answering the question
@Output() finished: EventEmitter<any> = new EventEmitter();

//default initialization function.
ngOnInit() {}

// emit the time measurements and answers.
onQuestionFinish() {
  var timeAnswered: TimeMeasurment = {
    timeForAnswering: 3,
    numberOfAnswerChanges: 1
  };

  var questionAnswer: QuestionAnswer = {
    questionId: 1,
    subjectId: 1,
    answer: "texty text",
    timeMeasurement: timeAnswered
  }

  this.finished.emit(questionAnswer);
}
}


