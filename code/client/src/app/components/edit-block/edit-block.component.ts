import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material';
import {CreateQuestionComponent} from '../create-question/create-question.component';
import { Router } from '@angular/router';
import {Question,  QuestionInBlock, QuestionInDB} from '../../models'
import { QuestionComponent } from '../question/question.component';
import { Input } from '@angular/core/';
import { SessionService } from '../../services/session-service/index';
@Component({
  selector: 'app-edit-block',
  templateUrl: './edit-block.component.html',
  styleUrls: ['./edit-block.component.css']
})

/*
This Class represents the whole block component.
The block holds a list of questions and serves as a basic building block
in the structure of the whole test.
*/
export class EditBlockComponent implements OnInit {
  //to specify the number of the block in the blocks list.
  @Input() blockNumber:number;
  @Input() block: any;
  //to collapse and uncollapse the block.
  hidden: boolean = true;
  //the actual list of the questions.
  questionList: Array<QuestionInBlock> = new Array<QuestionInBlock>();
  //tags 
  questionTags: string[];

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
        this.questionList.splice(this.questionList.length, 0,{question: question_object, id:''});  
      }
      
    });
  }

  //default ngOnInit() function.
  ngOnInit() {
    if (this.block != null){
      for (let i = 0; i < this.block.questions.length; i++) {
        this.questionList[i] = 
        { 
          question: JSON.parse(this.block.questions[i].question)
        };
      }
      console.log('block in edit');
      console.log(this.block);
      this.tags = JSON.parse(this.block.tag);
  
    }
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
    if(currentIndex != 0){
      let removed = this.questionList.splice(currentIndex,1);
      this.questionList.splice(currentIndex - 1, 0, removed[0]);  
    }
  }


  /*
  this function move the block down in the blocks list.
  Input - the currentIndex of the block in the list.
  Output - the block is moved in the list.
  */
  moveMeDown(currentIndex) {
    if(currentIndex != this.questionList.length - 1){
      let removed = this.questionList.splice(currentIndex, 1);
      this.questionList.splice(currentIndex + 1, 0 , removed[0]);
    }
  }


  /*
  this function creates a new question.
  Input - the number of the current question to be created
  Output - a question block is newly created. 
  */
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
        this.questionList.splice(index, 1,{question: question_object, id:''});  
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

  



    /*
   * returns the tags array
   */
  getTags(){
    return this.tags;
}

//this will hold all the tags for the block.
tags:any[];
//tags count
tags_count = 0;

//can add more functionality
addTag(){
 this.tags_count++;
}

////can add more functionality
removeTag(){
 this.tags_count--;
}
}

