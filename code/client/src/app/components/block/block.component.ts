import { Component, OnInit } from '@angular/core';
import { QuestionListComponent } from './question-list/question-list.component';

@Component({
  selector: 'app-block',
  templateUrl: './block.component.html',
  styleUrls: ['./block.component.css']
})
export class BlockComponent implements OnInit {

  blockNumber:number;
  hidden: boolean = true;
  questionsList:QuestionListComponent;
  constructor() { }

  ngOnInit() {
    this.questionsList=new QuestionListComponent();
  }

  toggleHidden() {
    this.hidden = !this.hidden;
  }

}
