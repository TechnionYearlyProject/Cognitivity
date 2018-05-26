import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-test-finish',
  templateUrl: './test-finish.component.html',
  styleUrls: ['./test-finish.component.css']
})
export class TestFinishComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    document.body.classList.add('background-white');
  }

}
