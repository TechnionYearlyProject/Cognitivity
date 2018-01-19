import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-question-preview',
  templateUrl: './question-preview.component.html',
  styleUrls: ['./question-preview.component.css']
})

/*
similar to the block preview component , this component can be used as a generic screen that 
allows a question's data to be previewed. 
the component is used to preview a single question.
*/
export class QuestionPreviewComponent implements OnInit {
  //default constructor.
  constructor() { }
  
  //we get the question as a passed input, thats the question we'll preview.
  @Input() question: any;

  //default initialization function.
  ngOnInit() {}

}
