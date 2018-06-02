/**
 * @author Daniel Lyubin
 * @date 12/5/2018
 */
import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {QuestionAnswer, TypeQuestion} from '../../../models/index';
import {TestPageOpenQuestionComponent} from '../open-question/open-question.component';
import {TestPageMultipleChoiceQuestionComponent} from '../multiple-choice-question/multiple-choice-question.component';
import {TestPageDrillDownQuestionComponent} from '../drill-down-question/drill-down-question.component';
import {TestPageRateQuestionComponent} from '../rate-question/rate-question.component';
import {ApplicationInsightsTracker} from '../../../models/ApplicationInsights/ApplicationInsightsTracker'
import {SwitchCounterTracker} from '../../../models/ApplicationInsights/AnswerSwitchCounter'
import { TimeMeasurer } from '../../../Utils/index';

@Component({
  selector: 'app-test-page-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class TestPageQuestionComponent implements OnInit {
//default constructor.
  constructor() {
  }

//we get the question as a passed input, thats the question we'll preview.
  @Input() question: any;

  @Input() testId: number;

//we need to get the timing object from the block
  @Input() timing: TimeMeasurer;//TimeMeasurer
//also, we need to know the ID of the containing block.
  @Input() fatherBlockID: number;

// Output event that emits the time took answering the question
  @Output() finished: EventEmitter<any> = new EventEmitter();

  
//Answer switch counter instance
  answerSwitcher: SwitchCounterTracker;
  appInsightsTrackerIns: ApplicationInsightsTracker;
// Get instances of all question components
  @ViewChild(TestPageOpenQuestionComponent) openQuestion: TestPageOpenQuestionComponent;
  @ViewChild(TestPageMultipleChoiceQuestionComponent) multipleChoiceQuestion: TestPageMultipleChoiceQuestionComponent;
  @ViewChild(TestPageDrillDownQuestionComponent) drillDownQuestion: TestPageDrillDownQuestionComponent;
  @ViewChild(TestPageRateQuestionComponent) rateQuestion: TestPageRateQuestionComponent;

//default initialization function.
  ngOnInit() {
    this.finished.emit(false);
    this.answerSwitcher = new SwitchCounterTracker(0);
    this.appInsightsTrackerIns = ApplicationInsightsTracker.getInstance;
    //start the time measurment for the first question
    //console.log("### starting timing for question "+this.question.id.toString()+" in block "+this.fatherBlockID.toString()+" ###");
    this.timing.timing_startQuestionMeasure(this.fatherBlockID,this.question.id);
  }


// emit the time measurements and answers.
  onQuestionFinish() {

    var questionAnswer: QuestionAnswer;
    
    switch (this.question.type) {
      case TypeQuestion.OpenQuestion:
        questionAnswer = this.openQuestion.buildAnswer();
        break;
      case TypeQuestion.DrillDownQuestion:
        questionAnswer = this.drillDownQuestion.buildAnswer();
        break;
      case TypeQuestion.MultipleChoice:
        questionAnswer = this.multipleChoiceQuestion.buildAnswer();
        break;
      case TypeQuestion.RateQuestion:
        questionAnswer = this.rateQuestion.buildAnswer();
        break;
    }
  //we can also finish the confidence bar measuring.
    this.finished.emit(questionAnswer);
    let qID = this.question.id;
    let qType = this.question.type.toString();
    this.appInsightsTrackerIns.trackNumberOfAnswersSwitches(this.answerSwitcher, qID, qType);
  }

  getAnswer() {
    var questionAnswer: QuestionAnswer;
    switch (parseInt( this.question.type)) {
      case TypeQuestion.OpenQuestion:
        questionAnswer = this.openQuestion.buildAnswer();
        break;
      case TypeQuestion.DrillDownQuestion:
        questionAnswer = this.drillDownQuestion.buildAnswer();
        break;
      case TypeQuestion.MultipleChoice:
        questionAnswer = this.multipleChoiceQuestion.buildAnswer();
        break;
      case TypeQuestion.RateQuestion:
        questionAnswer = this.rateQuestion.buildAnswer();
        break;
    }

    return questionAnswer;
  }

  onAnswering(didAnswer: boolean) {
    //meaning we're gonna show the conf bar, so we can start timing it.
    //console.log("### starting conf bar timing for question "+this.question.id.toString()+" in block "+this.fatherBlockID.toString()+"###");       
    this.timing.timing_startConfidenceMeasure(this.question.id,this.fatherBlockID);
    this.finished.emit(didAnswer);
    //Should be called every time a subject changes an answer.
    this.answerSwitcher.switchAnswer();
    

            
  }

}


