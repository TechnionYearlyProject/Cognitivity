import { Component, OnInit, Input } from '@angular/core';
import { TypeQuestion } from '../../models';


@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {
  questionName="Question 1";
  tags: string[];
  constructor() { }
  @Input() type: TypeQuestion;
  ngOnInit() {
    //Here we will pull the question from the DB
  }

}
