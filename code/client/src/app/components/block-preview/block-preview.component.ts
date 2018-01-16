import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-block-preview',
  templateUrl: './block-preview.component.html',
  styleUrls: ['./block-preview.component.css']
})
export class BlockPreviewComponent implements OnInit {
  @Input() block:any;
  constructor() { }
  
  currIndex: number;
  ngOnInit() {
    this.currIndex = 0;
  }

  showQuestion(index) {
    return index == this.currIndex;
  }

}
