import { Component, OnInit } from '@angular/core';
import { QuestionListComponent } from './question-list/question-list.component';
import {MatDialog} from '@angular/material';
import {CreateQuestionComponent} from '../create-question/create-question.component';
import { Router } from '@angular/router';
import {Question, QuestionData, QuestionInBlock} from '../../models'
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
  hidden: boolean = true;
  questionList: Array<QuestionInBlock> = new Array<QuestionInBlock>();
  constructor(private dialog: MatDialog,private router:Router, private transferData: SessionService) {
    //this.questionsList=new QuestionListComponent(this.router);
   }
  


  openDialog(){
    this.transferData.setData({editMode: false, value: null});
    let dialogRef = this.dialog.open(CreateQuestionComponent, {
      height: '90%',
      width:'80%',
      disableClose: true
    });
    dialogRef.afterClosed().subscribe(result => {
      let question_object = this.transferData.getData();
      if(question_object != null){
        this.questionList.splice(this.questionList.length, 0,{question: question_object, indexInBlock: this.questionList.length, id:''});  
      }
      
    });
  }
  ngOnInit() {
    
  }

  toggleHidden() {
    this.hidden = !this.hidden;
  }

  previewBlock(){
    let tmp:QuestionInBlock[]=new Array();
    for(let i=0;i<this.questionList.length;i++)
    {
      tmp[i] = this.questionList[i];
    }
    console.log(tmp);
    this.transferData.setData(tmp);
    console.log("sent Data");
    this.router.navigate(['question-viewer']);
  }

  moveMeUp(currentIndex) {
    let arrayLastIndex = this.questionList.length - 1;
    if (currentIndex == 0) {
      let tmpMe = this.questionList.shift();
      for (let num = 0; num < arrayLastIndex; num++) {
        this.questionList[num].indexInBlock--;
      }
      this.questionList.push(tmpMe);
      tmpMe.indexInBlock = arrayLastIndex;
    }
    let tmpHolder = this.questionList[currentIndex - 1];
    let mememe = this.questionList[currentIndex];
    mememe.indexInBlock--;
    tmpHolder.indexInBlock++;
    this.questionList[currentIndex - 1] = mememe;
    this.questionList[currentIndex] = tmpHolder;
  }

  moveMeDown(currentIndex) {
    let arrayLastIndex = this.questionList.length - 1;
    if (currentIndex == arrayLastIndex) {
      let tmpMe = this.questionList.pop();
      for (let num = arrayLastIndex-1; num > 0; num--) {
        this.questionList[num].indexInBlock++;
      }
      this.questionList.unshift(tmpMe);
      tmpMe.indexInBlock = 0;
    }
    let tmpHolder = this.questionList[currentIndex + 1];
    let mememe = this.questionList[currentIndex];
    mememe.indexInBlock++;
    tmpHolder.indexInBlock--;
    this.questionList[currentIndex + 1] = mememe;
    this.questionList[currentIndex] = tmpHolder;
  }

  editQuestion(index: number){
    this.transferData.setData({editMode: true, value: this.questionList[index].question});
    let dialogRef = this.dialog.open(CreateQuestionComponent, {
      height: '90%',
      width:'80%',
      disableClose: true
    });
    dialogRef.afterClosed().subscribe(result => {
      let question_object = this.transferData.getData();
      if(question_object != null){
        this.questionList.splice(index, 1,{question: question_object, indexInBlock: index, id:''});  
      }
      
    });
  }

  deleteQuestion(index: number){
    this.questionList.splice(index,1);
  }

}
