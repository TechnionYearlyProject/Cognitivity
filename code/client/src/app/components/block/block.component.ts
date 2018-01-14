import { Component, OnInit } from '@angular/core';
import { QuestionListComponent } from './question-list/question-list.component';
import {MatDialog} from '@angular/material';
import {CreateQuestionComponent} from '../create-question/create-question.component';
import { Router } from '@angular/router';
import {Question} from '../../models/index'
import { QuestionComponent } from '../question/question.component';
@Component({
  selector: 'app-block',
  templateUrl: './block.component.html',
  styleUrls: ['./block.component.css']
})
export class BlockComponent implements OnInit {


  blockNumber:number;
  hidden: boolean = true;
  questionsList:QuestionListComponent;
  constructor(private dialog: MatDialog,private router:Router) { }
  

  openDialog(){
    let dialogRef = this.dialog.open(CreateQuestionComponent, {
      height: '100%',
      width:'100%',
      disableClose: true
    });
  }
  ngOnInit() {
    this.questionsList=new QuestionListComponent(this.router);
  }

  toggleHidden() {
    this.hidden = !this.hidden;
  }

  createQuestion(){
      
  }


}
