import { Component, OnInit } from '@angular/core';
import { QuestionComponent } from '../../question/question.component';
import { TypeQuestion,Question, QuestionData } from '../../../models/index';
import { Router } from '@angular/router';
@Component({
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.css']
})
export class QuestionListComponent implements OnInit {

  questions: QuestionData[] = new Array();

  constructor(private router:Router) { 
    /*this.questions[0] = new QuestionData(0);
    this.questions[1] = new QuestionData(1);
    this.questions[2] = new QuestionData(2);
    this.questions[3] = new QuestionData(3);
    this.questions[4] = new QuestionData(4);
    this.questions[5] = new QuestionData(5);*/
  }

  ngOnInit() {
    
  }
  insertQuestion(question: QuestionData){
    console.log('inserting');
    console.log(question);
    this.questions.splice(this.questions.length,0, question);
    console.log(this.questions);
  }
  moveMeUp(currentIndex) {
    let arrayLastIndex = this.questions.length - 1;
    if (currentIndex == 0) {
      let tmpMe = this.questions.shift();
      for (let num = 0; num < arrayLastIndex; num++) {
        this.questions[num].indexInBlock--;
      }
      this.questions.push(tmpMe);
      tmpMe.indexInBlock = arrayLastIndex;
    }
    let tmpHolder = this.questions[currentIndex - 1];
    let mememe = this.questions[currentIndex];
    mememe.indexInBlock--;
    tmpHolder.indexInBlock++;
    this.questions[currentIndex - 1] = mememe;
    this.questions[currentIndex] = tmpHolder;
  }

  moveMeDown(currentIndex) {
    let arrayLastIndex = this.questions.length - 1;
    if (currentIndex == arrayLastIndex) {
      let tmpMe = this.questions.pop();
      for (let num = arrayLastIndex-1; num > 0; num--) {
        this.questions[num].indexInBlock++;
      }
      this.questions.unshift(tmpMe);
      tmpMe.indexInBlock = 0;
    }
    let tmpHolder = this.questions[currentIndex + 1];
    let mememe = this.questions[currentIndex];
    mememe.indexInBlock++;
    tmpHolder.indexInBlock--;
    this.questions[currentIndex + 1] = mememe;
    this.questions[currentIndex] = tmpHolder;
  }

}


