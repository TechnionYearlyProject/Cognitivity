import { Component, OnInit } from '@angular/core';
import { QuestionComponent } from '../../question/question.component';
import { TypeQuestion } from '../../../models/index';

@Component({
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.css']
})
export class QuestionListComponent implements OnInit {

  questions:QuestionComponent[]=new Array();

  constructor() { }

  ngOnInit() {
    this.questions[0]=new QuestionComponent();
    this.questions[0].type=0;

    this.questions[1]=new QuestionComponent();
    this.questions[1].type=1;

    this.questions[2]=new QuestionComponent();
    this.questions[2].type=2;
  }

}
