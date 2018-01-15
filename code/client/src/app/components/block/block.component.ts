import { Component, OnInit } from '@angular/core';
import { QuestionListComponent } from './question-list/question-list.component';
import {MatDialog} from '@angular/material';
import {CreateQuestionComponent} from '../create-question/create-question.component';
import { Router } from '@angular/router';
import {Question, QuestionData} from '../../models/index'
import { QuestionComponent } from '../question/question.component';
import { Input } from '@angular/core/';
import { SessionService } from '../../services/session-service/index';
@Component({
  selector: 'app-block',
  templateUrl: './block.component.html',
  styleUrls: ['./block.component.css']
})
export class BlockComponent implements OnInit {


  @Input() blockNumber:number;
  @Input() questionsList:QuestionListComponent;
  hidden: boolean = true;
  
  constructor(private dialog: MatDialog,private router:Router,public questionDataService:SessionService) {
    this.questionsList=new QuestionListComponent(this.router);
   }
  

  openDialog(){
    let dialogRef = this.dialog.open(CreateQuestionComponent, {
      height: '100%',
      width:'100%',
      disableClose: true
    });
  }
  ngOnInit() {
    
  }

  toggleHidden() {
    this.hidden = !this.hidden;
  }

  previewBlock(){
    let tmp:QuestionData[]=new Array();
    for(let i=0;i<this.questionsList.questions.length;i++)
    {
      tmp[i] = this.questionsList.questions[i];
    }
    console.log(tmp);
    this.questionDataService.setData(tmp);
    console.log("sent Data");
    this.router.navigate(['question-viewer']);
  }


}
