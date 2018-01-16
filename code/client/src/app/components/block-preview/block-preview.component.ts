import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-block-preview',
  templateUrl: './block-preview.component.html',
  styleUrls: ['./block-preview.component.css']
})
export class BlockPreviewComponent implements OnInit {
  @Input() block:any;
  @Output() finished: EventEmitter<any> = new EventEmitter();
  constructor() { }
  
  currIndex: number;
  ngOnInit() {
    this.currIndex = 0;
  }

  showQuestion(index) {
    return index == this.currIndex;
  }

  nextQuestion() {
    this.currIndex++;
    if (this.currIndex == this.block.questionList.length) {
      this.finished.emit(true);
    } else {
      this.finished.emit(false);
    }
  }

}
