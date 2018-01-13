import { Component, OnInit } from '@angular/core';
import { QuestionComponent } from '../../question/question.component';
import { TypeQuestion } from '../../../models/index';
import { Router } from '@angular/router';
@Component({
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.css']
})
export class QuestionListComponent implements OnInit {

  questions: QuestionComponent[] = new Array();

  constructor(private router:Router) { }

  ngOnInit() {
    this.questions[0] = new QuestionComponent(this.router);
    this.questions[0].indexInBlock = 0;
    this.questions[0].questionName = "i am 1";
    this.questions[0].type = 0;

    this.questions[1] = new QuestionComponent(this.router);
    this.questions[1].indexInBlock = 1;
    this.questions[1].questionName = "i am 2";
    this.questions[1].type = 1;

    this.questions[2] = new QuestionComponent(this.router);
    this.questions[2].indexInBlock = 2;
    this.questions[2].questionName = "i am 3";
    this.questions[2].type = 2;
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


