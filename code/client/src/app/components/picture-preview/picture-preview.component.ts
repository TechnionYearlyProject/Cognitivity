/*
Author: Pe'er Sagiv
Date: 17.6.18
A class to add all images display to the test
*/

import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-picture-preview',
  templateUrl: './picture-preview.component.html',
  styleUrls: ['./picture-preview.component.css']
})
export class PicturePreviewComponent implements OnInit {
  @Input() picture: string;
  constructor() { }

  ngOnInit() {
  }

}
