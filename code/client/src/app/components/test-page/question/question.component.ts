/**
 * @author Daniel Lyubin
 * @date 12/5/2018
 */
import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { TimeMeasurment, QuestionAnswer, TypeQuestion } from '../../../models';
import { TestPageOpenQuestionComponent } from '../open-question/open-question.component';
import { TestPageMultipleChoiceQuestionComponent } from '../multiple-choice-question/multiple-choice-question.component';
import { TestPageDrillDownQuestionComponent } from '../drill-down-question/drill-down-question.component';
import { TestPageRateQuestionComponent } from '../rate-question/rate-question.component';

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

// Get instances of all question components
@ViewChild(TestPageOpenQuestionComponent) openQuestion: TestPageOpenQuestionComponent;
@ViewChild(TestPageMultipleChoiceQuestionComponent) multipleChoiceQuestion: TestPageMultipleChoiceQuestionComponent;
@ViewChild(TestPageDrillDownQuestionComponent) drillDownQuestion: TestPageDrillDownQuestionComponent;
@ViewChild(TestPageRateQuestionComponent) rateQuestion: TestPageRateQuestionComponent;

//default initialization function.
ngOnInit() {}

// emit the time measurements and answers.
onQuestionFinish() {
  
  var questionAnswer: QuestionAnswer;
  switch(this.question.type) {
    case TypeQuestion.OpenQuestion: questionAnswer = this.openQuestion.buildAnswer(); break;
    case TypeQuestion.DrillDownQuestion: questionAnswer = this.drillDownQuestion.buildAnswer(); break;
    case TypeQuestion.MultipleChoice: questionAnswer = this.multipleChoiceQuestion.buildAnswer(); break;
    case TypeQuestion.RateQuestion: questionAnswer = this.rateQuestion.buildAnswer(); break;
  }

  this.finished.emit(questionAnswer);
}
}


