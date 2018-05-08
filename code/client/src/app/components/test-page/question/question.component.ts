import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-test-page-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class TestPageQuestionComponent implements OnInit {
//default constructor.
constructor() { }
  
//we get the question as a passed input, thats the question we'll preview.
@Input() question: any;

//default initialization function.
ngOnInit() {}

}
