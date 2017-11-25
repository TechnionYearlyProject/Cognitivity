import { Component, OnInit } from '@angular/core';
import { MultipleAnsQuestion } from '../../models';
@Component({
  selector: 'app-multiple-question',
  templateUrl: './multiple-question.component.html',
  styleUrls: ['./multiple-question.component.css']
})
export class MultipleQuestionComponent implements OnInit {
  question: MultipleAnsQuestion;
  markedAnswer: number;
  correct: boolean;
  isSubmit: boolean;
  constructor() {
    this.question = 
    {
      text:'Who directed Inception?',
      answers:[{answer:'Christopher Nolan', isMarked:false}, {answer:'Ridely Scott', isMarked:false}, {answer:'Quantin Tarantino',isMarked:false}, {answer:'Robert Downy Jr.', isMarked:false}],
      correctAnswer:1
    };
   }

  ngOnInit() {
  }

  markAnswer(answerText:string){
    for(let i = 0; i < this.question.answers.length; i++){   
      if(this.question.answers[i].answer == answerText){
        this.question.answers[i].isMarked = true;
        this.markedAnswer = i;

      }else{
        this.question.answers[i].isMarked = false;
      }
    }
  }
  
  onSubmit(){
    this.isSubmit = true;
    if(this.markedAnswer == this.question.correctAnswer - 1){
      this.correct = true;
    }else{
      this.correct = false;
    }
  }
}
