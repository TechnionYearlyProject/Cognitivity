import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.css']
})
export class LoadingComponent implements OnInit {
  @Input() loading_value: string;
  @Input() color: string;
  colors : any;
  constructor() {


   }

  isWhiteColor(){
    return this.color == 'White';
  }
  isBlackColor(){
    return this.color == 'Black';
  }
  ngOnInit() {
    this.colors = {
      'white' : this.isWhiteColor(),
      'black' : this.isBlackColor()
    };
  }

}
