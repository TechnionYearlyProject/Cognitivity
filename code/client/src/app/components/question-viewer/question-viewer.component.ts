import { Component, OnInit } from '@angular/core';
import {SessionService} from '../../services/session-service/index'
import {QuestionData } from '../../models'
@Component({
  selector: 'app-question-viewer',
  templateUrl: './question-viewer.component.html',
  styleUrls: ['./question-viewer.component.css']
})
export class QuestionViewerComponent implements OnInit {
  
 
  myQuestion:QuestionData;

  constructor(public questionDataService:SessionService) { 
    console.log("im alive");
    this.myQuestion=new QuestionData(0);
  }

  ngOnInit() {

  }

  load(){
    console.log("im called");
    this.myQuestion=this.questionDataService.getData();  
    console.log("got id: "+this.myQuestion.id);
  }

}
