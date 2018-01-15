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
  question_object: any;
  constructor(private dialog: MatDialog,private router:Router, private transferData: SessionService) {
    this.questionsList=new QuestionListComponent(this.router);
   }
  

  

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
    this.transferData.setData(tmp);
    console.log("sent Data");
    this.router.navigate(['question-viewer']);
  }
  showQuestion(){
    console.log(this.question_object);
  }

}
