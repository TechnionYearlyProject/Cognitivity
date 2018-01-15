import { Component, OnInit } from '@angular/core';
import { QuestionListComponent } from './question-list/question-list.component';
import {MatDialog} from '@angular/material';
import {CreateQuestionComponent} from '../create-question/create-question.component';
import { Router } from '@angular/router';
import {Question} from '../../models/index'
import { QuestionComponent } from '../question/question.component';
import { SessionService } from '../../services/session-service/index';
@Component({
  selector: 'app-block',
  templateUrl: './block.component.html',
  styleUrls: ['./block.component.css']
})
export class BlockComponent implements OnInit {


  blockNumber:number;
  hidden: boolean = true;
  questionsList:QuestionListComponent;
  question_object: any;
  constructor(private dialog: MatDialog,private router:Router, private transferData: SessionService) { }
  

  openDialog(){
    let dialogRef = this.dialog.open(CreateQuestionComponent, {
      height: '90%',
      width:'80%',
      disableClose: true
    });
    dialogRef.afterClosed().subscribe(result => {
      this.question_object = this.transferData.getData();
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
  showQuestion(){
    console.log(this.question_object);
  }

}
