import {Component, Input, OnInit, Output, EventEmitter} from "@angular/core";
import { QuestionAnswer, DrillDownQuestionAnswer, TypeQuestion } from "../../../models";
@Component({
  selector: 'app-test-page-drill-down-question',
  templateUrl: './drill-down-question.component.html',
  styleUrls: ['./drill-down-question.component.css']
})

/*
 The component for the Drill-down question type.
*/
export class TestPageDrillDownQuestionComponent implements OnInit {
  @Input() question: any;
  //the object that holds the question.
  //@Input() question1: any;
  //an array to specify the current status of all the question (correct/incorrect)
  //it serves us as a helper array to keep track on all the user's choices.
  markedAnswersMain: Array<boolean>;
  //string to display for choosing an answer.
  markedAnswerMain: string = 'Choose an answer';
  //string for choosing a secondary question's answer.
  markedAnswerSecondery: string = 'Choose an answer';
  //same array as "markedAnswersMain" just for the sub question.
  markedAnswersSecondary: Array<boolean>;
  //string for the secondary question's title.
  secondaryQuestion: string;
  //same array as "markedAnswersMain" just for the sub question answers.
  currentSecondaryAnswers: Array<string>;
  //slider value
  range_value: number = 50;
  //to indicate if we're using the secondary question option.
  secondaryQuestionMode: boolean = false;
  // Event emitter to determine if the subject filled an answer
  @Output() answered: EventEmitter<boolean> = new EventEmitter();

  //default constructor.
  constructor() { }

  //default initialization function.
  ngOnInit() {

    this.markedAnswersMain = new Array<boolean>(this.question.answersForMain.length);
    for(let i = 0; i < this.question.answersForMain.length; i++){
      this.markedAnswersMain[i] = false;
    }


  }
  /*
    A function that helps to align al buttom in the drop down bar according
    to the largest string in the array
  */
  generateHighestBox(answers: Array<string>): string{
    let max: number = -1;
    for(let i = 0; i < answers.length; i++){
      if(answers[i].length > max){
        max = answers[i].length;
      }
    }
    let size = Math.max((max * 20), 300);

    let returnedSize: string = (size.toString()) + 'px';
    return returnedSize;
  }
  /*
    The answer marks an answer for the main question
  */
  markAnswerForMain(index: number){
    for(let i = 0; i < this.markedAnswersMain.length; i++){
      if(index == i){
        if(this.markedAnswersMain[i]){
          this.markedAnswerMain = 'Choose an answer';
          this.secondaryQuestionMode = false;
        }else{
          this.markedAnswerMain = this.question.answersForMain[i];
          if(this.question.secondaryQuestionsText[i] != null){
            this.secondaryQuestion = this.question.secondaryQuestionsText[i];
            let sizeOfSecondaryanswers = this.question.answersForSecondary[i].length;
            this.markedAnswersSecondary = new Array<boolean>(sizeOfSecondaryanswers);
            this.currentSecondaryAnswers = new Array<string>(sizeOfSecondaryanswers);
            for(let j = 0; j < this.markedAnswersSecondary.length; j++){
              this.markedAnswersSecondary[j] = false;
              this.currentSecondaryAnswers[j] = this.question.answersForSecondary[index][j];
              this.markedAnswerSecondery = 'Choose an answer';
              this.secondaryQuestionMode = true;
            }

          }else{
            this.secondaryQuestionMode = false;
          }
        }

        this.markedAnswersMain[i] = !this.markedAnswersMain[i];

      }else{
        this.markedAnswersMain[i] = false;
      }

    }
  }
  /*
    The function marks an answer for the secondary question
  */
  markAnswerForSecondary(index: number){
    for(let i = 0; i < this.currentSecondaryAnswers.length; i++){
      if(i == index){
        if(this.markedAnswersSecondary[i]){
          this.markedAnswerSecondery = 'Choose an answer';
        }else{
          this.markedAnswerSecondery = this.currentSecondaryAnswers[i];
        }
        this.markedAnswersSecondary[i] = !this.markedAnswersSecondary[i];
      }else{
        this.markedAnswersSecondary[i] = false;
      }
    }
  }

  returnAnswers(){
    let mainAnswerIndex = -1, secondAnswerIndex = -1;
    for(let i = 0; i < this.markedAnswersMain.length; i++){
      if(this.markAnswerForMain[i]){
        mainAnswerIndex = i;
        break;
      }
    }
    if(this.secondaryQuestionMode){
      for(let i = 0; i < this.markedAnswersSecondary.length; i++){
        if(this.markedAnswersSecondary[i]){
          secondAnswerIndex = i;
        }
      }
    }
    return {mainAnswer : mainAnswerIndex, secondAnswer: secondAnswerIndex};
    
  }

  buildAnswer(): QuestionAnswer {
    let answers = this.returnAnswers();
    let questionAnswer : DrillDownQuestionAnswer = {
      questionId: this.question.id,
      subjectId: /* will come later, for now hard-coded */ 1,
      questionType: TypeQuestion.DrillDownQuestion,
      primaryAnswer: answers.mainAnswer,
      secnodaryAnswer: answers.secondAnswer,
      confidence: this.range_value
    }

    return questionAnswer;
  }

}
