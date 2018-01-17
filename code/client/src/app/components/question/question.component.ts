import { Component, OnInit, Input } from '@angular/core';
import { TypeQuestion,QuestionData,Question, OpenQuestion, RateQuestion,MultipleChoiceQuestion } from '../../models';
import {Router} from '@angular/router';
import {SessionService} from '../../services/session-service'
import { Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {
  

  hidden: boolean = true;

  
  @Input() myData:any;
  @Input() index: any;
  constructor(private router:Router,public questionDataService:SessionService) {
    
  }
  
  ngOnInit() {
    //Here we will pull the question from the DB
  }
  toggleHidden() {
    this.hidden = !this.hidden;
  }

  connectViewer(){
    //console.log("sending id: "+this.myData.id);
    this.questionDataService.setData(this.myData);
    this.router.navigate(['question-viewer']);
  }
}
