import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core';

@Component({
  selector: 'app-create-open-question',
  templateUrl: './create-open-question.component.html',
  styleUrls: ['./create-open-question.component.css']
})
export class CreateOpenQuestionComponent implements OnInit {
  answerText : string;
  @Input() question: any;
  constructor() { }

  ngOnInit() {

    if(this.question == null){
      this.answerText = '';
    } else {
      this.answerText = this.question.answerText;
    }

  }

}
