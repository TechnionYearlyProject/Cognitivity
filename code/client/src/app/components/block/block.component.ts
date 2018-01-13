import { Component, OnInit } from '@angular/core';
import { QuestionListComponent } from './question-list/question-list.component';
import { Router } from '@angular/router';
@Component({
  selector: 'app-block',
  templateUrl: './block.component.html',
  styleUrls: ['./block.component.css']
})
export class BlockComponent implements OnInit {

  blockNumber:number;
  hidden: boolean = true;
  questionsList:QuestionListComponent;
  constructor(private router:Router) { }

  ngOnInit() {
    this.questionsList=new QuestionListComponent(this.router);
  }

  toggleHidden() {
    this.hidden = !this.hidden;
  }

}
