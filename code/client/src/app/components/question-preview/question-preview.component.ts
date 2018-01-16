import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-question-preview',
  templateUrl: './question-preview.component.html',
  styleUrls: ['./question-preview.component.css']
})
export class QuestionPreviewComponent implements OnInit {

  constructor() { }
  @Input() question: any;
  ngOnInit() {
  }

}
