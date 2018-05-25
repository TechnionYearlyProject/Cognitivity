import { Component, OnInit, Input, EventEmitter, Output, ViewChild } from '@angular/core';
import { Question, TimeMeasurment, QuestionAnswer, BlockAnswers, Block } from '../../../models/index';
import { TestPageQuestionComponent } from '../question/question.component';
import { TimeMeasurer } from '../../../Utils/index';

@Component({
  selector: 'app-test-page-block',
  templateUrl: './block.component.html',
  styleUrls: ['./block.component.css']
})
export class TestPageBlockComponent implements OnInit {
  @ViewChild(TestPageQuestionComponent) question: TestPageQuestionComponent; 
  //the block input given to the class, that's the block we're about to preview.
  @Input() block:any;
  //we want to notify when we finish previewing the block.
  @Output() finished: EventEmitter<any> = new EventEmitter();
  //local variable to indicate when we finish previewing the block.
  finish: boolean = false;
  //the current index of the question in block.
  currIndex: number;

  //we need to get the timing class object from the test.
  @Input() timing:TimeMeasurer;


  questionAnswers: QuestionAnswer[];

  didAnswerQuestion: boolean = false;

  //default constructor.
  constructor() { }

  
  ngOnInit() {
    this.currIndex = 0;
    this.questionAnswers = new Array<QuestionAnswer>(this.block.questions.length); //Init array for time measurements for each question.
     //start to measure the current block 
    this.timing.timing_startBlockMeasure(this.block.id,this.block.numberOfQuestions);
  
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

  /*
  this function increments out index and checks if we finished the list.
  if we did - it triggers an event to notify our caller that the preview of the block is done.
  */
  nextQuestion() {
    // Insert Time measuring of last question
    this.questionAnswers[this.currIndex] = this.question.getAnswer();
    this.currIndex++;
    this.didAnswerQuestion = false;
    if (this.currIndex == this.block.questions.length) {
      this.finish = true;
      this.finished.emit();
    }
  }

  parseToQuestion(text: string): Question {
    return JSON.parse(text);
  }

  onQuestionFinish(didFinish: boolean) {
    this.didAnswerQuestion = didFinish;
    //finish the measurment for the current question.
    this.timing.timing_stopQuestionMeasure(this.block.questions[this.currIndex].id,this.block.id);
  }

  getQuestionAnswers() {
    let blockAnswer: BlockAnswers = {
      answers: this.questionAnswers
    }
    return blockAnswer;
  }

}
