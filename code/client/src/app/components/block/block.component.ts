import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material';
import {CreateQuestionComponent} from '../create-question/create-question.component';
import { Router } from '@angular/router';
import {Question,  QuestionInBlock} from '../../models'
import { QuestionComponent } from '../question/question.component';
import { Input } from '@angular/core/';
import { SessionService } from '../../services/session-service/index';
@Component({
  selector: 'app-block',
  templateUrl: './block.component.html',
  styleUrls: ['./block.component.css']
})

/*
This Class represents the whole block component.
The block holds a list of questions and serves as a basic building block
in the structure of the whole test.
*/
export class BlockComponent implements OnInit {
  //to specify the number of the block in the blocks list.
  @Input() blockNumber:number;
  //to collapse and uncollapse the block.
  hidden: boolean = true;
  //the actual list of the questions.
  questionList: Array<QuestionInBlock> = new Array<QuestionInBlock>();

  //default constructor.
  constructor(private dialog: MatDialog,private router:Router, private transferData: SessionService){}
  
  //this function pops up the dialog for creating a question. 
  openDialog(){
    this.transferData.setData({editMode: false, value: null});
    let dialogRef = this.dialog.open(CreateQuestionComponent, {
      height: '90%',
      width:'80%',
      disableClose: true
    });
    dialogRef.afterClosed().subscribe(result => {
      let question_object = this.transferData.getData();
      console.log('data is:::');
      console.log(question_object);
      if(question_object != null){
        this.questionList.splice(this.questionList.length, 0,{question: question_object, indexInBlock: this.questionList.length, id:''});  
      }
      
    });
  }

  //default ngOnInit() function.
  ngOnInit() {
  }

  //this function is responsible for collapsing and uncollapsing the block.
  toggleHidden() {
    this.hidden = !this.hidden;
  }

  /*
  this function move the block up in the blocks list.
  Input - the currentIndex of the block in the list.
  Output - the block is moved in the list.
  */
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


  /*
  this function move the block down in the blocks list.
  Input - the currentIndex of the block in the list.
  Output - the block is moved in the list.
  */
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


  /*
  this function creates a new question.
  Input - the number of the current question to be created
  Output - a question block is newly created. 
  */
  editQuestion(index: number){
    this.transferData.setData({editMode: true, value: this.questionList[index].question});
    let dialogRef = this.dialog.open(CreateQuestionComponent, {
      height: '100%',
      width:'100%',
      disableClose: true
    });
    dialogRef.afterClosed().subscribe(result => {
      let question_object = this.transferData.getData();
      if(question_object != null){
        this.questionList.splice(index, 1,{question: question_object, indexInBlock: index, id:''});  
      }
      
    });
  }

  /*
  this function deletes a question.
  Input - the number of the question to diminish
  Output - the question is diminished
  */
  deleteQuestion(index: number){
    this.questionList.splice(index,1);
  }

  /*
  this function is a getter for the questions array property.
  */
  getQuestions(): Array<QuestionInBlock>{
    return this.questionList;
  }
}
