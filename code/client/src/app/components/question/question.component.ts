import { Component, OnInit, Input } from '@angular/core';
import { TypeQuestion,Question, OpenQuestion, RateQuestion,MultipleChoiceQuestion, QuestionInDB, QuestionPosition } from '../../models';
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

/**
 * This class aims to allow adding functions easily to the questions interfaces.
 * as well as providing one class that implements both questions representaions interfaces.
 * 
 */
export class oneQuestionToRuleThemAll implements OnInit{

  // the instances 
  question_instance: Question;
  questionInDB_instance: QuestionInDB;

  //just two fields to inform which instances are in use. for cases that one would use just one instance.
  is_question_in_use: boolean;
  is_questionInDB_in_use: boolean;

  constructor(question_ins:Question,questionDB_ins:QuestionInDB){
    if(question_ins != null){
      this.question_instance = question_ins;
      this.is_question_in_use = true;
    }
    else{
      this.is_question_in_use = false;
    }
    if(questionDB_ins != null){
      this.questionInDB_instance = questionDB_ins;
      this.is_questionInDB_in_use = true;
    }
    else{
      this.is_questionInDB_in_use = false;
    }
  }
  
  ngOnInit(){}

  //getters for Question instance
  get_question_ins_id():number{
    return this.question_instance.id;
  }

  get_question_ins_text():string{
    return this.question_instance.questionText;
  }

  get_question_ins_type():TypeQuestion{
    return this.question_instance.type;
  }

  get_question_ins_position():QuestionPosition{
    return this.question_instance.questionPosition;
  }

  get_question_ins_conf_bar():boolean{
    return this.question_instance.showConfidenceBar;
  }

  get_question_ins_startTS():number{
    return this.question_instance.startTS;
  }

  get_question_ins_endTS():number{
    return this.question_instance.endTS;
  }

  get_question_ins_diffTS():number{
    return this.question_instance.diffTS;
  }

  get_question_ins_beingMeasured():boolean{
    return this.question_instance.isBeingMeasured;
  }

  get_question_ins_tags():string[]{
    return this.question_instance.tags;
  }
  
  //setters for Question instance 
  set_question_ins_id(newID:number):void{
    this.question_instance.id = newID;
  }

  set_question_ins_text(newText:string):void{
    this.question_instance.questionText = newText;
  }

  set_question_ins_type(newType:TypeQuestion):void{
    this.question_instance.type = newType;
  }

  set_question_ins_position(newPos:QuestionPosition):void{
    this.question_instance.questionPosition = newPos;
  }

  set_question_ins_conf_bar(newSet:boolean):void{
    this.question_instance.showConfidenceBar = newSet;
  }

  set_question_ins_startTS(newStartTS:number):void{
    this.question_instance.startTS = newStartTS;
  }

  set_question_ins_endTS(newEndTS:number):void{
    this.question_instance.endTS = newEndTS;
  }

  set_question_ins_diffTS(newDiffTS:number):void{
    this.question_instance.diffTS = newDiffTS;
  }

  set_question_ins_beingMeasured(newMeasured:boolean):void{
    this.question_instance.isBeingMeasured = newMeasured;
  }


  //getters for QuestionDB instance 
  get_questionDB_ins_id():number{
    return this.questionInDB_instance.id;
  }

  get_questionDB_ins_text():string{
    return this.questionInDB_instance.question;
  }

  get_questionDB_ins_question_type():number{
    return this.questionInDB_instance.type;
  }

  get_questionDB_ins_answer():string{
    return this.questionInDB_instance.answer;
  }

  get_questionDB_ins_question_pos():number{
    return this.questionInDB_instance.questionPosition;
  }

  get_questionDB_ins_startTS():number{
    return this.questionInDB_instance.startTS;
  }

  get_questionDB_ins_endTS():number{
    return this.questionInDB_instance.endTS;
  }

  get_questionDB_ins_diffTS():number{
    return this.questionInDB_instance.diffTS;
  }

  get_questionDB_ins_isMeasured():boolean{
    return this.questionInDB_instance.isBeingMeasured;
  }

  get_questionDB_ins_tags():string[]{
    return this.questionInDB_instance.tags
  }

  //getters for QuestionDB instance 
  set_questionDB_ins_id(newID:number):void{
    this.questionInDB_instance.id = newID;
  }

  set_questionDB_ins_text(newText:string):void{
    this.questionInDB_instance.question = newText;
  }

  set_questionDB_ins_question_type(newType:number):void{
    this.questionInDB_instance.type = newType;
  }

  set_questionDB_ins_answer(newAns:string):void{
    this.questionInDB_instance.answer = newAns;
  }

  set_questionDB_ins_question_pos(newPos:number):void{
    this.questionInDB_instance.questionPosition = newPos;
  }

  set_questionDB_ins_startTS(newStartTS:number):void{
    this.questionInDB_instance.startTS = newStartTS;
  }

  set_questionDB_ins_endTS(newEndTS:number):void{
    this.questionInDB_instance.endTS = newEndTS;
  }

  set_questionDB_ins_diffTS(newDiff:number):void{
    this.questionInDB_instance.diffTS = newDiff;
  }

  set_questionDB_ins_isMeasured(newMeasured:boolean):void{
    this.questionInDB_instance.isBeingMeasured = newMeasured;
  }
}