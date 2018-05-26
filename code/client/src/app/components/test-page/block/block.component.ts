import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {BlockAnswers, Question, QuestionAnswer, QuestionInDB} from '../../../models/index';
import {TestPageQuestionComponent} from '../question/question.component';
import {TimeMeasurer} from '../../../Utils/index';
import {ApplicationInsightsTracker} from "../../../models/ApplicationInsights/ApplicationInsightsTracker";

@Component({
  selector: 'app-test-page-block',
  templateUrl: './block.component.html',
  styleUrls: ['./block.component.css']
})
export class TestPageBlockComponent implements OnInit {
  @ViewChild(TestPageQuestionComponent) question: TestPageQuestionComponent;
  //the block input given to the class, that's the block we're about to preview.
  @Input() block:any;

  @Input() testId: number;
  //we want to notify when we finish previewing the block.
  @Output() finished: EventEmitter<any> = new EventEmitter();
  //local variable to indicate when we finish previewing the block.
  finish: boolean = false;
  //the current index of the question in block.
  currIndex: number;

  //we need to get the timing class object from the test.
  @Input() timing:TimeMeasurer;


  questionAnswers: QuestionAnswer[];

  questions: QuestionInDB[];

  didAnswerQuestion: boolean = false;

  //array to indicate for every index , if the question in that index in the block's questions array was displayed.
  wasShownArr: boolean[];


  //default constructor.
  constructor() { }


  ngOnInit() {
    this.currIndex = 0;
    this.questionAnswers = new Array<QuestionAnswer>(this.block.questions.length); //Init array for time measurements for each question.
    this.questions = this.block.questions;
    //start to measure the current block
    //this.timing.timing_startBlockMeasure(this.block.id,this.block.numberOfQuestions);
    console.log(this.testId);
  }

  /*
  in the HTML file, each question is shown , only if the show question function returns true for it's index.
  meaning the only question that'll get true is the function that it's index is identical to our
  current index.
  Input - the index of some question in the list.
  Output - if the question index is identical to our current index we return true , so the ngIf if the HTML
  will get true and present the question.
  */
  showQuestion(index) {
    return index == this.currIndex;
  }


  /**
   * This function iterates over all the wasShown array and returns the number of questions that HASNT been seen.
   */
  private wasAllShown():number{
    let counter = 0;
    for(let i = 0; i<this.wasShownArr.length;i++){
      if(!this.wasShownArr[i]){
        counter++;
      }
    }
    return counter;
  }

  /**
   * This function gets a number (lets say X) and returns the X first question index that hasn't been seen.
   * (X>=1)
   */
  private xFirstFree(x:number):number{
    let freeCounter = x;
    for(let i = 0; i<this.wasShownArr.length;i++){
      if(!this.wasShownArr[i]){
        freeCounter--;
      }
      if(freeCounter == 0){
        return i;
      }
    }
  }

  /**
   * This function generates a random index for a question that hasn't been shown yet.
   */
  private generateRandomIndex():number{
    let possibleAnswers = this.wasAllShown();//that'll be the higg limit we'll get a random index from
    let randomIndex = Math.floor(Math.random() * possibleAnswers);//low is 0, we add -1 so the 1 dissapears
    return this.xFirstFree(randomIndex);
  }

  /**
   * TODO
   * replace the this.currIndex++ with the generateRandomIndex function.
   * after testing the implementation.
   *
   */
  /*
  this function increments out index and checks if we finished the list.
  if we did - it triggers an event to notify our caller that the preview of the block is done.
  */
  nextQuestion() {
    // Insert Time measuring of last question
    console.log('finish question button');
    ApplicationInsightsTracker.getInstance.trackNumberOfAnswersSwitches(
      this.question.answerSwitcher,
      this.question.question.id,
      this.question.question.type.toString()
    );

    this.questionAnswers[this.currIndex] = this.question.getAnswer();
    this.currIndex++;
    this.didAnswerQuestion = false;
    if (this.currIndex == this.block.questions.length) {
      this.finish = true;
      this.finished.emit();
    }
  }

  parseToQuestion(question: QuestionInDB): Question {
    let actualQuestion: Question = JSON.parse(question.question);
    actualQuestion.id = question.id;
    return actualQuestion;
  }

  onQuestionFinish(didFinish: boolean) {
    this.didAnswerQuestion = didFinish;
    //finish the measurment for the current question.
    //this.timing.timing_stopQuestionMeasure(this.block.questions[this.currIndex].id,this.block.id);
  }

  getQuestionAnswers() {
    let blockAnswer: BlockAnswers = {
      answers: this.questionAnswers
    }
    return blockAnswer;
  }

}
