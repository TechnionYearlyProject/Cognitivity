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

  showClock:boolean;

  //default constructor.
  constructor() { }


  ngOnInit() {
    this.currIndex = 0;
    this.questionAnswers = new Array<QuestionAnswer>(this.block.questions.length); //Init array for time measurements for each question.
    this.questions = this.block.questions;
    //start to measure the current block

    this.timing.timing_startBlockMeasure(this.block.id,this.block.numberOfQuestions);

    this.wasShownArr = new Array(this.block.numberOfQuestions);
    for(let i=0;i<this.wasShownArr.length;i++){
      this.wasShownArr[i] = false;
    }
    this.currIndex = 0;
    let tmpFirstQuestion = this.parseToQuestion(this.questions[0]);
    if(tmpFirstQuestion.showDistractions && (tmpFirstQuestion.distractionsSeconds > 0)){
      this.tickTick(tmpFirstQuestion.distractionsSeconds);
      this.showClock = true;
    }
    else{
      this.showClock = false;
    }
  }

  countdownCallback(){
    console.log("countdown timer finished ! moving question");
    
    if (this.wasAllShown() > 0) {
      this.nextQuestion();
    }
  }

  seconds:string ="";
  minutes:string ="";
  clockDisplay:string=this.minutes + " : " + this.seconds;
  // clockDisplay:number;
  duration:number;
  interval;

  tickTick(duration) {
    this.duration = duration;
    if (this.duration > 0) {
        this.interval = setInterval(() => {
            this.duration--;

            if (this.duration == 0) {
                clearInterval(this.interval);
                this.nextQuestion();
            }
           if (this.duration % 60 < 10) {
                this.seconds = "0" + this.duration % 60;
            } else {
                this.seconds = (this.duration % 60).toString();
            }

            if (this.duration / 60 < 10) {
                this.minutes = "0" + parseInt("" + this.duration / 60, 10);
            } else {
                this.minutes = "" + parseInt((this.duration / 60).toString(), 10);
            }

           this.clockDisplay = this.minutes + " : " + this.seconds
        }, 1000);
    }
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
    let possibleAnswers = this.wasAllShown();//that'll be the high limit we'll get a random index from
    let randomIndex = Math.floor(Math.random() * possibleAnswers+1);//low is 1
    return this.xFirstFree(randomIndex);
  }
  /*
  this function increments out index and checks if we finished the list.
  if we did - it triggers an event to notify our caller that the preview of the block is done.
  */
  nextQuestion() {

  //console.log("### stopping timing for question "+this.block.questions[this.currIndex].id.toString()+" in block "+this.block.id.toString()+" ###");
  //finish the measurment for the current question.
  clearInterval(this.interval);
  this.timing.timing_stopQuestionMeasure(this.block.questions[this.currIndex].id,this.block.id);
    ApplicationInsightsTracker.getInstance.trackNumberOfAnswersSwitches(
      this.question.answerSwitcher,
      this.question.question.id,
      this.question.question.type.toString()
    );
    this.wasShownArr[this.currIndex] = true;
    this.questionAnswers[this.currIndex] = this.question.getAnswer();
    this.currIndex= this.generateRandomIndex();
    this.didAnswerQuestion = false;
    if (this.wasAllShown()==0) {
      this.finish = true;
      this.finished.emit();
    }
    else{
    let tmpQuestion = this.parseToQuestion(this.questions[this.currIndex]);
    if(tmpQuestion.showDistractions && (tmpQuestion.distractionsSeconds > 0)){
      this.showClock = true;
      this.tickTick(tmpQuestion.distractionsSeconds);
    }
    else{
      this.showClock = false;
    }
  }
  }

  parseToQuestion(question: QuestionInDB): Question {
    let actualQuestion: Question = JSON.parse(question.question);
    actualQuestion.id = question.id;
    return actualQuestion;
  }

  onQuestionFinish(didFinish: boolean) {
    this.didAnswerQuestion = didFinish;
         
  }

  getQuestionAnswers() {
    let blockAnswer: BlockAnswers = {
      answers: this.questionAnswers
    }
    return blockAnswer;
  }

}
