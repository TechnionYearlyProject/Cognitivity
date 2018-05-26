import {AnimationStyleMetadata, Component, Input, OnInit, Output, EventEmitter} from "@angular/core";
import {QuestionPosition, QuestionAnswer, TypeQuestion, RateQuestionAnswer} from "../../../models";
import {stringify} from "@angular/core/src/util";
@Component({
  selector: 'app-test-page-rate-question',
  templateUrl: './rate-question.component.html',
  styleUrls: ['./rate-question.component.css']
})

/*
 A component to represent the Rate question type.
*/
export class TestPageRateQuestionComponent implements OnInit {
  //the question's data is passed in as input.
  @Input() question: any;

  @Input() testId: number;
  //the array that holds all the possible answers.
  answers: Array<null>;
  //an array to indicate what answers are currently marked.
  markedAnswers: Array<boolean>;
  //the final marked answer
  markedAnswer : number;
  //slider value.
  range_value : number = 50;
  /*
  different CSS styles for the positions of the question's text and answers.
  */
  positionUp : any;
  positionMiddle : any;
  positionButtom : any;
  // Event emitter to determine if the subject filled an answer
  @Output() answered: EventEmitter<boolean> = new EventEmitter();
  //default constructor.
  constructor() {}

  /*
  default initialization function.
  we initialize all the data structures and set all the marked question to "not marked"
  */
  ngOnInit() {
    this.buildPositionOfQuestion();
    this.answers = new Array<null>(this.question.heightOfRate);
    this.markedAnswers = new Array<boolean>(this.question.heightOfRate);
    for(let i = 0; i < this.question.heightOfRate; i++){
      this.markedAnswers[i] = false;
    }

    this.answered.emit(false);
  }

  /*
  Input - index of the question to be marked.
   Output - the question is marked.
  */
  markAnswer(index: number){
    for (let i = 0; i < this.answers.length; i++) {
      if(i == index){
        this.markedAnswers[i] = true;
        this.markedAnswer = i;
        this.answered.emit(true);

      }else{
        this.markedAnswers[i] = false;
      }
    }
  }

  /*
  this function creates the CSS styles menthioned above.
  */
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

  buildAnswer(): QuestionAnswer {
    let answer = {
      answer: this.markedAnswer,
      confidence: this.range_value
    }
    let questionAnswer : QuestionAnswer = {
      questionId: this.question.id,
      testeeId: /* will come later, for now hard-coded */ 1,
      finalAnswer: JSON.stringify(answer),
      testId: this.testId
    }

    console.log('????',questionAnswer);

    return questionAnswer;
  }

  //checking if we have a marked answer.
  get_is_marked():boolean{
    for (let i = 0; i < this.markedAnswers.length; i++) {
        if(this.markedAnswers[i]){
          return true;
        }
    }
    return false;
  }
}
