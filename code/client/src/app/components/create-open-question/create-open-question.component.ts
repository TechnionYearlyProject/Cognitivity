import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-create-open-question',
  templateUrl: './create-open-question.component.html',
  styleUrls: ['./create-open-question.component.css']
})
export class CreateOpenQuestionComponent implements OnInit {
  answerText : string;
  constructor() { }

  ngOnInit() {
  }

}
