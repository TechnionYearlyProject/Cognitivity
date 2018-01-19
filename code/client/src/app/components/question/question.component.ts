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

/*
component to hold the question object and display it.
this component is actually a "wrapper" that just adds the head of the question info 
and collapse ability.
the data of the actual question is passed as input.
*/
export class QuestionComponent implements OnInit {

  //to indicate if the question is currently collapsed or not.
  hidden: boolean = true;

  //the question's data itself 
  @Input() myData:any;
  //the question's index in the list.
  @Input() index: any;

  //default constructor.
  constructor(private router:Router,public questionDataService:SessionService) {}
  
  //default initialization function.
  ngOnInit() {}

  //called by a click event from the HTML form, changes the collapse status of the question's data.
  toggleHidden() {
    this.hidden = !this.hidden;
  }
}
