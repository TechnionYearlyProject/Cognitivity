import { Component, OnInit } from '@angular/core';
import {SessionService} from '../../services/session-service/index'
import {QuestionData } from '../../models'
import { Router } from '@angular/router';
@Component({
  selector: 'app-question-viewer',
  templateUrl: './question-viewer.component.html',
  styleUrls: ['./question-viewer.component.css']
})
export class QuestionViewerComponent implements OnInit {
  
  hidden: boolean = true;
  myQuestions:QuestionData[];
  myCurrIndex:number=0;

  constructor(public questionDataService:SessionService,private router:Router) { 
    console.log("im alive");
    this.myQuestions = new Array();
  }

  ngOnInit() {
    this.load();
  }

  load(){
    this.myQuestions = this.questionDataService.getData();
    console.log("-------------------------------------------");
    console.log(this.myQuestions);
    this.myCurrIndex=0;
  }

  hide(loopIndex){
    return loopIndex!=this.myCurrIndex;
  }


  nextquestion(){
    this.myCurrIndex++;
    if(this.myCurrIndex==this.myQuestions.length-1)
    {
      this.router.navigate(['edit-test']);
    }
  }
}
